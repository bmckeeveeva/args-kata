# Args Kata (Java)

Please see the project [README](../README.md) for an overview

Code prior to refactoring is in `src/main/java/args/before`

A sample solution is in `src/main/java/args/after`

Unit tests are in `src/test/java/args`

## Prerequisites

The code was tested under Java 11 and Gralde 6

## Gradle commands

- `./gradlew test`: run all tests
- `./gradlew check`: run style check, tests and coverage check

## Coding Style

This repo uses the [Google Java Style](https://google.github.io/styleguide/javaguide.html)

## My approach

* Consider refactorings.  What gives most bang for our buck?
  * Replace conditions with polymorphism
  * Separate "schema" and "args" responsibilities
  * Cleaning up method bodies
* Chose "replace conditionals with polymorphism".  I never really took a deeper look at the method bodies.  The methods were small, so I was not terribly concerned.
* Scratch refactoring: Tried creating "handlers" that had their own arg map.  I did not like this option because my "handler" has methods that use its state, methods that don't, and methods that mutate state it doesn't own (currentArgument counter integer).  Due to it having its own state that we need some of the time we'd have to make new instances of this class for each args parse.  Since we only parse arguments once on a typical CLI app this doesn't practically affect performance, so while I considered this it isn't the strongest factor in this decision.
* I decided I would make stateless handlers which would mutate state given to it.  The easiest way I figured I could start this was to take the existing state on Args and externalize it into a class called State.  I used git to clear my scratch refactoring then I used refactoring tools to make State then manually added a constructor that took schema and args. - commit
* I made all the type-specific method static using refactoring tools.  The tool allowed me to make State a parameter since it could not longer be a member variable.  I moved the static methods into type-specific "handler" classes like IntegerHandler.  I did this one type at a time, running tests after each.  This also forced me to move a few helper methods to a Util class so they could be reused.  - commit
* Its time for manual refactoring.  I created a proper Handler interface and manually modified each "handler" class to implement it.  I did this one type at a time.  After creating the type class, for example IntegerHandler, I would make a member variable on Args for IntegerHandler and update the code with the old static method calls to use the member variable and new normalized method names.  I'd run the tests to make sure everything passed.  Repeat for all types.  - commit
* Manually replace the conditionals in Args by looping over a list of "handlers".  I realized there were error cases in each method that needed this so I made another Handler implementation called ErrorCaseHandler.  Make sure tests still pass.  - commit
* I moved the work in the constructor into the "parse" method so you didn't need to make a new instance every time you wanted to parse.  I renamed Args to ArgsFactory and made a new interface called Args that had the result getter methods on it.  I made State implement Args, which was easy since it already held the made data these methods returned.
* I manually split schema and args responsibilities.