(ns nutricalc.core
  (:require [clojure.string :as str])

  (:gen-class))



(defn add 
  "Adds a csv line to the csv"
  [entry]
  (as-> entry x  
      (str/join "," x)
      (str/join "\n" [x ""])
      (spit "test.csv" x :append true)
  )
  
)

(defn compareFirst
  "Takes two lists, compares their first items"
  [[fst1 & _] [fst2 & _]] 
  (= fst1 fst2)

)

(def info [:name :kcal :protein :fat :carbs])
(def servingSize 100) ;100g

(defn fetch
  "fetches the requested entry from the CSV file"
  [[foodName amount]] ;parameter destructuring is wonderful
  (-> (slurp "test.csv")
    (str/split #"\n") ;split file into lines
    (as-> x 
      (map (fn [x] (str/split x #",")) x)
      (filter (partial compareFirst [foodName] ) x)

      (mapv (fn [[fst & rest]] (cons fst (->> (mapv (fn [x] (Float. x)) rest)
                                    (mapv (-> (Float. amount)
                                              (/ servingSize)
                                              (->> (partial *))) )))) x)
      
      )  
  ) 
)



  
  


(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (cond 
    (= (first args) "add") (add (rest args))
    :else (->> (fetch args)
               (mapv println) ;; throws out the vector just to print
          )
  )
)

