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



  
  
(defn help
  []
  (println "To recall a food, use `nut foodName grams`
To add a food, use `nut add foodName kcal protein fat carbs`")
  )

(defn linePrint
  [[name kcal protein fat carbs]]
  (printf "%s - kcal: %.1f, p: %.1f, f: %.1f, c: %.1f\n" name kcal protein fat carbs)
  )

(defn prettyPrint
  [[name kcal protein fat carbs]]
  (printf "%s\n--------------\nkcal:    %.1f\nProtein: %.1f\nFat:     %.1f\nCarbs:   %.1f\n" name kcal protein fat carbs))

(defn serve
  [printMethod args]
  (->> (fetch args)
       (mapv printMethod) ;; throws out the vector just to print
       )
  )


(defn -main
  "I don't do a whole lot ... yet."
  [& args]

  (cond 
    (< (count args) 2) (help)
    (= (first args) "pretty") (serve prettyPrint (rest args))
    (= (first args) "add") (add (rest args))
    :else (serve linePrint args)
    )
)


