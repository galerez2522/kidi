# Kidi - Multiplication Game

## Description
This game is an interactive tool designed to help children learn the multiplication table (and also division). The game offers animations that demonstrate the multiplication and division operations in a fun and engaging way, while allowing players to choose between multiplication, division, or a combination of both. Teachers can adjust the game to meet each student's needs, with options to add extra time for questions or practice specific operations based on the student's performance.  

## System Requirements
The game is built in Java and requires JavaFX components to run. To play the game, the following components need to be installed:  

JDK 21 (or higher)  
JavaFX 23.0.1 (or higher)  
  
### Step 1: Download and Install the JDK  
Download JDK 21 (or higher) from Oracle's official website.  
  
### Step 2: Download and Install JavaFX  
Download JavaFX 23.0.1 (or higher) from Gluon JavaFX website.  
  
Once downloaded, extract the JavaFX SDK files to a location on your computer.    
  
### Step 3: Set Up Environment Variables  
After downloading and installing the JDK and JavaFX, you will need to set up the environment variables to use them properly.  
  
Set JAVA_HOME and PATH:  
```bash
export JAVA_HOME=/path/to/your/jdk
export PATH=$JAVA_HOME/bin:$PATH
```  
Replace /path/to/your/jdk with the path where your JDK is installed.  
  
Set JavaFX Path:  
```bash  
export JAVA_FX_LIB_PATH=/path/to/javafx-sdk/lib
```  
Replace "/path/to/javafx-sdk/lib" with the path to the lib folder of the JavaFX SDK you downloaded.
  
### Step 4: Download the JAR File from GitHub  
To run the game, you can simply download the JAR file and run it directly.  
  
https://github.com/galerez2522/kidi/releases/download/v1.0/MyGame.jar  
  
### Step 5: Running the Game  
After downloading the JAR file, follow these steps to run the game:  
  
Run the JAR file with the following command:  
```bash
java --module-path /path/to/javafx-sdk/lib --add-modules javafx.controls -jar MyGame.jar
```  
This command:  

--module-path /path/to/javafx-sdk/lib: Specifies the path to the JavaFX lib directory.  
--add-modules javafx.controls: Adds the javafx.controls module at runtime.   
-jar MyGame.jar: Runs the JAR file you downloaded.  

## Enjoy !
  
  







