# Nutricalc

A CSV-driven, locally hosted, nutrition repository and macronutrient calculator for the terminal.

## Scope

This software is intended for people who only need to keep track of a handful of food items, portion their food by some universal standard portion (for me it's 100 grams), only care about macronutrients and calorie counts, and don't want to be bogged down by menus.

## build

`lein uberjar` to build

`lein run <params>` to test

## use

set an environment variable `NUT` to point to a path to store the csv and config files
set up a blank csv file and make a config file with the line `standard-portion val` where val is your standard portion

`nut <food> <grams>` -> All values in-line

`nut pretty <food> <serving>` -> A prettier print

`nut add <name> <kcal> <protein> <fat> <carbs>` -> entry added to csv

## Design Choices

I chose clojure as a challenge to work with a new language while also keeping the portability of the program. The JVM is ubiquitous. I may as well leverage it.