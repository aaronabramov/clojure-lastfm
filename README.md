# clojure-lastfm

```clojure
(def api-key "1234567890")

(def api-secret "0987654321")

(let [token (get-token api-key api-secret)]
  (get-auth-url api-key token))

;; Your application needs to open a web browser and
;; send the user to last.fm/api/auth with your API
;; key and auth token as parameters. Use an HTTP GET
;; request. Your request will look like this:

(get-session api-key api-secret token)
;; => {:body {"session" {"name" "name", "key" "8482ded6ddf53598391c02a0c2c57b6b", "subscriber" "0"}}}
```

## License

Copyright © 2014 FIXME

Distributed under the Eclipse Public License either version 1.0 or (at
your option) any later version.
