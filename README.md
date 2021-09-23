# DSA
Java Project implementing own version of data structures to further understanding of Java's data structures.

Purpose:
Overview DSA Assignment Project files, functionality, testing and any
bugs/issues unable to be fixed.

Files in Project:
DeclarationOfOriginality.pdf
Documentation.pdf
DSAGraph.java
DSALinkedList.java
FileManager.java
Operations.java
SimulationMode.java
SocialSim.java
Sorting.java
UnitTestGraph.java
UnitTestLinkedList.java
testfile.txt
eventfile.txt
testfile2.txt
eventfile2.txt
eventfile3.txt
DSA Assignment Report.docx
readme.txt

How to compile:
Type 'javac *.java' to compile all java files, and run the program using
'java SocialSim' to open the instructions on how to use the program.
'java SocialSim -i' will open interactive mode, allowing the user to input
multiple files, and adjust the probability while running between timesteps
'java SocialSim -s <testfile> <eventfile> <likeProb> <followProb>' will open
the program in simulation mode where the program will run the timesteps on a
prompt until the user exits, printing each timestep to a new file each time.

Test Files:
Tested using the testfile.txt and eventfile.txt files as import.
Then tested using the testfile2.txt and eventfile2.txt files.

Functionality:
- Reads the files containing the 'people' and their followers, and an event file
- Creates a linked list which saves the people and the edges between them
- Prints Menu for User to select from
- Allows user to view the current status of the graph/network
- Allows user to save the result of the timesteps to a file (in same form as
  the input file)
- Interactive mode: allows for the user to change the probabilities, enter
                    multiple event files, and display statistics and information
                    about the graph itself between timesteps.
- Simulation mode: will run the program based on the command line arguments
                   input initially

Tested On:
Windows 10 Education
Ubuntu Linux, Windows Sub System

Bugs/Memory Leaks:
- When inputting a filename for importing a test file or event file, if the
  file name doesn't exist it will throw an exception and close the program.
- Due to recursive function, when the simulation has a large amount of nodes
  and a large chance to like a post, the program will input too many recursions
  and will throw a stackoverflow error. Optimising the recursive function has
  decreased the probability that this occurs, although it is confirmed that at
  a like chance of 50% using testfile2, eventfile2 and eventfile3 will cause
  stack overflow.
