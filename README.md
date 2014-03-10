# Conway's Game of Life

This is a Scala implementation of Conway's Game of Life in a toroid. I am working on a machine-learning component which predictively attempts 
to step through time in the past.

## Usage

Launch files are text files with rows of `T . . T` patterns representing a Game of Life grid, where `T` indicates a live cell and
`.` indicates a dead one. Whitespace is ignored. When creating new files, keep in mind the implications of 
the simulation being run in a toroid. This means cells on the top rows affect cells on the bottom rows and vice versa. Likewise with cells on the left and the right. As patterns expand, they will wrap around edges
with sometimes unintended consequences.

There are several files in `simulations` which represent interesting patterns 
found in Game of Life, still lifes, spaceships, oscillators, and Methuselahs.

To launch a Swing visualization of a Game of Life instance, 

    $ sbt
    [info] Set current project to conway (in build file:/Users/andrea/workspace/conway/)
    > compile
    [success] Total time: 1 s, completed Feb 27, 2014 11:19:14 AM
    > run simulations/osc-toad6.txt 
    
Selecting the `Go` button will run the simulation; to halt the simulation, select `Stop`. Alternately, you can step through one
step at a time by selecting `Step`. To quit the program, select `Exit`.
