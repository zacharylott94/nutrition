(ns nutricalc.core
  (:require [clojure.string :as str])

  (:gen-class))


(def servingSize (atom 100)) ;100g
(def path (System/getenv "NUT"))

(defn add 
  "Adds a csv line to the csv"
  [entry]
  (as-> entry x  
      (str/join "," x)
      (str/join "\n" [x ""])
      (spit (str path "stash") x :append true)))
  
  


(defn compareFirst
  "Takes two lists, compares their first items"
  [[fst1 & _] [fst2 & _]] 
  (= fst1 fst2))




(defn fetch
  "fetches the requested entry from the CSV file"
  [[foodName amount]] ;parameter destructuring is wonderful
  (-> (slurp (str path "stash"))
    (str/split #"\n") ;split file into lines
    (as-> x 
      (map (fn [x] (str/split x #",")) x)
      (filter (partial compareFirst [foodName] ) x)

      (mapv (fn [[fst & rest]] (cons fst (->> (mapv (fn [x] (Float. x)) rest)
                                          (mapv (-> (Float. amount)
                                                    (/ @servingSize)
                                                    (->> (partial *))) )))) x))))
      
        
   

(defn setParam
  [line]

  (let [[param value] (vec (str/split line #" "))]
    (cond
      (= param "standard-portion") (swap! servingSize (fn [_] (Float. value)))
      )) 
  )

(defn grabConfig
  []
  (-> (slurp (str path "config"))
      (str/split #"\n")
      (->>(mapv setParam))
      ))
      
  



  
  
(defn help
  []
  (println "To recall a food, use `nut foodName amount`
To add a food, use `nut add foodName kcal protein fat carbs`
To pretty print, use `nut pretty foodName amount`"))
  

(defn linePrint
  [[name kcal protein fat carbs]]
  (printf "%s - kcal: %.1f, p: %.1f, f: %.1f, c: %.1f\n" name kcal protein fat carbs))
  

(defn prettyPrint
  [[name kcal protein fat carbs]]
  (printf "%s\n--------------\nkcal:    %.1f\nProtein: %.1f\nFat:     %.1f\nCarbs:   %.1f\n" name kcal protein fat carbs))

(defn serve
  [printMethod args]
  (->> (fetch args)
       (mapv printMethod))) ;; throws out the vector just to print
       
  


(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (grabConfig)

  (cond 
    (< (count args) 2) (help)
    (= (first args) "pretty") (serve prettyPrint (rest args))
    (= (first args) "add") (add (rest args))
    :else (serve linePrint args))
  (flush)
  )
    



