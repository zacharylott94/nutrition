(ns nutricalc.core
  (:gen-class))
(require '[clojure.string :as string])


(defn add 
  "Adds a csv line to the csv"
  [entry]
  (as-> entry x  
      (rest x)
      (string/join "," x)
      (string/join "\n" [x ""])
      (spit "test.csv" x :append true)
  )
  
)


  
  


(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (cond 
    (= (first args) "add") (add args)
    :else (-> (slurp "test.csv") println)
  )
)

