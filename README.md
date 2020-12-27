# Application Development Fundamentals and Object Oriented Design Patterns homework 

## 1 creational GoF pattern 
Builder - builder() method in Timer class

## 1 behavioral GoF pattern
Chain of Responsibility: TimersController-> TimersService

## 1 structural GoF pattern
ResponseTimer is adapter for Timer

## 4 non-creational GoF patterns in total
* TimerState is implementation of State pattern
* Observer (TimerObserver)
* ResponseTimer and Timer lists are Iterable (used in TimerService)
* Memento (Memento and TimeSnapshot classes - backup and restore timers)

## How to run
TimerApplicationTests
or
TimerApplication and http://localhost:8080/
