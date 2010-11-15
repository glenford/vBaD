
vBaD - Visualising Builds and Deploys
=====================================

vBad aims to provide a simple to setup and use platform
to help you visualise your builds and deploys by providing
a style of mashup dashboard.


!! IMPORTANT !!

This repository is still in flux, and is far from stable,
use this at your own risk.


History
-------

vBaD is based upon some cobbled together scripts and web
pages that I used some time ago.  Unfortunately those scripts
and pages were lost.  vBaD is an attempt to take those ideas
to the next level, at the moment development is prototypical,
testing is light, structure is non-ideal, but hopefully that
will evolve as time goes on.


Current Features
----------------

Currently vBaD provides:

+ builds embedded into a single jar, 
+ basic dashboard with a rotating iframe with some information
  areas
  - iframe times how long it takes to load and reports that in
    a status pane


Usage
-----

The quickest way to make use of vBaD at present is to clone
the github repository and then use sbt to run it.

sbt can be found here:

http://code.google.com/p/simple-build-tool/

then do the following:

bash$ git clone git://github.com/glenford/vBaD.git
bash$ cd vBaD
bash$ sbt update jetty-run

It will download all its dependencies and build vBaD, then you
will then have vBaD running on localhost at port 8080.

Point your browser at http://localhost:8080

If you wish to build to a self-contained jar then do the following:

bash$ sbt update proguard

If you wish to work on the codebase and use IntelliJ, ensure you
have the scala plugin, and use the following to generate a project
file

bash$ sbt idea



License
-------

vBaD is licensed under the Apache 2.0 License

