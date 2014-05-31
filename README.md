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

;; http://www.last.fm/api/auth/?api_key=xxxxxxxxxxx&token=xxxxxxxx
;; If the user is not logged in to Last.fm, they will be
;; redirected to the login page before being asked to grant
;; your application permission to use their account. On this
;; page they will see the name of your application, along with
;; the application description and logo as supplied in Section 1.
;; Once the user has granted your application permission to use
;; their account, the browser-based process is over and the user
;; is asked to close their browser and return to your application.

(get-session api-key api-secret token)
;; => {:body {"session" {"name" "name", "key" "8482ded6ddf53598391c02a0c2c57b6b", "subscriber" "0"}}}
```

## License

Copyright © 2014 FIXME

Distributed under the Eclipse Public License either version 1.0 or (at
your option) any later version.
