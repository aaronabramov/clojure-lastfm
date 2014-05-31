(ns lastfm.client
  (:require
    [org.httpkit.client :as http]
    [clojure.data.json :as json]
    [clojure.walk :refer [stringify-keys]]))

(def ^:private base-url "http://ws.audioscrobbler.com/2.0/")

(defn ^:private md5
  "Generate a md5 checksum for the given string"
  [token]
  (let [hash-bytes
         (doto (java.security.MessageDigest/getInstance "MD5")
               (.reset)
               (.update (.getBytes token)))]
       (.toString
         (new java.math.BigInteger 1 (.digest hash-bytes)) ; Positive and the size of the number
         16))) ; Use base16 i.e. hex

(defn signed-params [query-params api-secret]
  (let [params-wo-format (dissoc query-params :format) ;; lastfm api bug. it doesn't include format params in signature
        sorted-param-string (reduce str (flatten (seq (into (sorted-map) (stringify-keys params-wo-format)))))
        res-str (str sorted-param-string api-secret)
        signature (md5 res-str)]
    (assoc query-params :api_sig signature)))

(defn make-req [{:keys [method params api-secret]}]
  (let [query-params (assoc params :method method :format "json")
        p (promise)]
    (println (signed-params query-params api-secret))
    (http/get base-url {:query-params (signed-params query-params api-secret)}
              (fn [{:keys [body error]}]
                (if error
                  (deliver p {:error error})
                  (deliver p {:body (json/read-str body)}))))
    p))

(defn get-token [api-key api-secret]
    (let [res @(make-req {:method "auth.getToken" :params {:api_key api-key} :api-secret api-secret})]
      (get-in res [:body "token"])))

(defn get-session [api-key api-secret token]
  (let [res @(make-req {:method "auth.getSession" :params {:api_key api-key :token token} :api-secret api-secret})]
    res))

(defn get-auth-url [api-key token]
  (str "http://www.last.fm/api/auth/?api_key=" api-key "&token=" token))
