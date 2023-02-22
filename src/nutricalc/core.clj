(ns nutricalc.core
  (:gen-class))

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (cond 
    (= (first args) "add") (println "add the thing")
    :else (println "find the thing"))
    
)

