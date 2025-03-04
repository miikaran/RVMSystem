## RVMSystem Explained

RVMSystem = Reverse Vending Machine (RVM) System, is mimicking the functionality of an
actual RVM, but with a few added features to make it unique. The project is terminal-based, 
and focuses mostly on the functionality, and not the UI/UX.

## Reverse Vending Machine Explained

Reverse vending machine is a machine that handles inserted items and recycles them by their
material, by putting the item to a correct pile. Before this can be done, the item must be
validated. Valid items have a certain value which depends on the item's size and material.
The value of an item is the "reward" the recycler receives when they have recycled.

When the recycler is done with recycling, they can choose to either donate their reward to some
listed charity or they can choose to print out a receipt which could be used to buy groceries, for example.

## Dependencies In The Project

The only 3rd party dependency this project has,
is the [`Google/gson`](https://github.com/google/gson) for converting 
Java objects into JSON and back.

## Project Overview and Structure
[Package specific class diagrams](https://github.com/miikaran/RVMSystem/tree/main/rvm/docs/uml/class_diagrams_from_packages) 

![New_Version_Class_Diagram](https://github.com/user-attachments/assets/79937d31-bed3-4fc8-bcfc-788dee43d829)

