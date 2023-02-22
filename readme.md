# Nutricalc

A CSV-driven, locally hosted, nutrition repository and macronutrient calculator for the terminal.

## Scope

This software is intended for people who only need to keep track of a handful of food items, portion their food by weight (grams specifically), only care about macronutrients and calorie counts, and don't want to be bogged down by menus.

## use

`nut <food> <grams>` -> `<kcal> <protein> <fat> <carbs>`

`nut add <name> <kcal> <protein> <fat> <carbs>` -> `added to CSV`

## Design Choices

I chose clojure as a challenge to work with a new language while also keeping the portability of the program. The JVM is ubiquitous. I may as well leverage it.