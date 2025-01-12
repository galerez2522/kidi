# kidi

export JAVA_HOME=/Users/avigailerez/Documents/jdk-21.0.4.jdk/Contents/Home
export PATH=$JAVA_HOME/bin:$PATH

javac --module-path /Users/avigailerez/Documents/kidi/javafx-sdk-23.0.1/lib --add-modules javafx.controls -d . src/*.java

java --module-path /Users/avigailerez/Documents/kidi/javafx-sdk-23.0.1/lib --add-modules javafx.controls QuizApp