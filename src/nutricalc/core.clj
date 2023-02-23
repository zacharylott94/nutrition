(ns nutricalc.core
  (:gen-class))
(require '[clojure.string :as string])


(defn add 
  "Adds a csv line to the csv"
  [entry]
  (as-> entry x  
      (string/join "," x)
      (string/join "\n" [x ""])
      (spit "test.csv" x :append true)
  )
  
)

(defn compareFirst
  "Takes two lists, compares their first items"
  [lst1 lst2]
  (= (first lst1) (first lst2))

)

(defn fetch
  "fetches the requested entry from the CSV file"
  [args]
  (-> (slurp "test.csv")
    (string/split #"\n") ;split file into lines
    (as-> x 
      (mapv (fn [x] (string/split x #",")) x)
      (filterv (partial compareFirst args ) x)
      (mapv println x) ;; throws out the vector just to print
      )
    
  )
  
  
  )


  
  


(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (cond 
    (= (first args) "add") (add (rest args))
    :else (fetch args)
  )
)

