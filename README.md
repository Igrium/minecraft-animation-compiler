# What is this?
The Minecraft Animation System is a set of programs designed to let you animate armor stands and other entities in Minecraft. This is a standalone Java program designed compile JSON files produced by a [Blender plugin](https://github.com/Sam54123/mc-animation-blender/) into a .mcfunction file that can be called in Minecraft.

## Installation
To install, first make sure you have Java installed on your computer. If you don't have it, download it from the [Oracle website](https://www.oracle.com/technetwork/java/javase/downloads/jre8-downloads-2133155.html).

Once you have obtained Java, either download this program from the releases tab of this repo or download the source code and compile it yourself. The source code version will be more up to date, but might be more unstable, as it is under development.

## To Use
The Minecraft Animation Compiler is a command line program, which means you'll have to launch it from the command line. To do this, open Command Prompt on Windows or Terminal on Mac and navigate to the folder where you downloaded the jar. To compile an animation, type `java -jar mcanim.jar [path to json] [path to destination folder]`.

To play the animation ingame, create a dummy objective called `mcanim.frame` and initialize it to `0` for each entity which you plan to play an animation on. This objective can be manipulated to change an entity's position in the animation. Then, call the animation function every tick in the scope of each entity on which you want to play the animation. The way you impliment this is up to you.

Each animation has a unique id stored in its JSON file, and this can be used to determine which entities should play which animations. Multiple animations may not share an id, and it's up to the user to enforce that.

## Contact

This is still very early alpha software. If you spot any bugs, make sure to tell me. You can email me at thesam54123@gmail.com. If you feel you can improve upon this and the related repos, feel free to create a pull request. This is built in Java 1.8.0

Datapack demo: http://download1338.mediafire.com/1je3pbd4qi7g/k5zobc942rm7tmi/animation.zip
