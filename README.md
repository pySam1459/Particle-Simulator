# Particle-Simulator
A simple 2D particle simulator


## Controls
* Left Mouse Button  = Gravity
* Right Mouse Button = Anti-Gravity
* r key              = Reset


## Configuration
The file 'sysetm.cfg' contains all config settings
* n          = Int (0 -> 500,000) : Number of Particles (starts to lag past 500,000)
* G          = Float : Gravitational Constant  (~100,000 seems good)
* spread     = { random, uniform, block (a square of particles) } : Reset Arrangement
* offset     = Int, Int : x, y offset for the block spread
* dim        = Int, Int : Window dimensions
* boxed      = Bool { true, false } : Whether the particles are constrained to the window
* resistance = Float (0.0 -> 1.0) : 0 resistance, no energy is lost ; 1 resistance, all energy is lost after 1 second
* colour     = Int, Int, Int, (Int) : Red, Green, Blue, (Alpha)  colour of the particles
* background = Int, Int, Int, (Int) : r, g, b, (a)  colour of the background
