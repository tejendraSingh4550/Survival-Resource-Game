# Survival Resource Manager

A small Java console game for practicing the requested topics. Start at the camp menu, collect random supplies, inspect your inventory, use supplies, or add a resource yourself.

## Run it

You need a JDK installed. Open a terminal in this folder and run:

```text
javac Main.java
java Main
```

## Topics in the project

- **Classes & objects:** `SurvivalGame` and `Resource` define the game and its resource stacks; `main` creates objects.
- **Encapsulation:** `Resource` keeps its fields private and validates changes through methods.
- **ArrayList:** the game stores and updates the inventory in an `ArrayList<Resource>`.
- **Scanner:** reads menu choices and other player input.
- **Random:** scavenging picks a random resource and quantity.
- **Methods:** game actions are split into small methods such as `collectResource` and `useResource`.
- **Loops:** the menu repeats until exit; inventory and input are processed with loops.
- **Switch case:** dispatches the selected menu action.
- **Exception handling:** invalid numbers and invalid resource operations are handled without crashing the game.
