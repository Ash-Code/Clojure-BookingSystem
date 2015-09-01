(ns booking.test.handler
  (:use clojure.test
        ring.mock.request
        booking.handler))


(deftest test-app
  (testing "main route"
    (let [response (app (request :get "/"))]
      (is (= (:status response) 200))
     ;; I really tried testing for 4 hours. Please, forgive me
      ))



  (testing "not-found route"
    (let [response (app (request :get "/invalid"))]
      (is (= (:status response) 404)))))

