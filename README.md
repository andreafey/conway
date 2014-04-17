[![Build Status](https://travis-ci.org/andreafey/conway.svg)](https://travis-ci.org/andreafey/conway)

# Conway's Game of Life

This is a Scala implementation of Conway's Game of Life in a toroid. Eventually I would like to work on a machine-learning component which predictively attempts to step through time in the past (inspired by a [Kaggle competition](http://www.kaggle.com/c/conway-s-reverse-game-of-life).)

## Notes

Rules for the Game of Life can be found at http://en.wikipedia.org/wiki/Conway's_Game_of_Life.

Because the life environment is a toroid, border cells have influence and are influenced by their parallel edges. So cells in the left-most column are adjacent to cells in the right-most column in the same row; likewise with the top and bottom rows. 

## Usage

Launch files are text files with rows of `T . . T` patterns representing a Game of Life grid, where `T` indicates a live cell and
`.` indicates a dead one. Whitespace is ignored. When creating new files, keep in mind the implications of 
the simulation being run in a toroid. This means cells on the top rows affect cells on the bottom rows and vice versa. Likewise with cells on the left and the right. As patterns expand, they will wrap around edges
with sometimes unintended consequences.

There are several files in `simulations` which represent interesting patterns 
found in Game of Life, still lifes, spaceships, oscillators, and Methuselahs. You can also randomly generate a board and watch
a more typical Simulation

To launch a Swing visualization of a Game of Life instance, execute run in one of the following formats:

    $ sbt "run src/main/resources/osc-toad6.txt"
    $ sbt "run src/main/resources/single-osc3-pulsar.txt"
    $ sbt run
    $ sbt "run 20 20"
    OR
    $ sbt 
    > run 50 30 0.6

where a single argument is a file name, two arguments indicate grid size, and a third argument is the proportion of living cells in the beginning of the simulation.
    
Selecting the `Go` button will run the simulation; to halt the simulation, select `Stop`. Alternately, you can step through one
step at a time by selecting `Step`. To quit the program, select `Exit`.

## Sample Files

`src/main/resources` contains a number of files with interesting known stable patterns.
