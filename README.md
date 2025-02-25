# UTPB-COSC-4380-Project1-2
This repo contains the assignment and provided code base for Projects 1 and 2 of the Cryptography class.

## Project 1

### Project Goals:
1) Improve understanding of coding in Java and/or Python as a method of accomplishing simple tasks and solving relatively simple problems.
2) Improve understanding of some of the more analog enciphering techniques we're discussing in class.

### Description:
For Project 1, I am asking you to produce either a Java or Python implementation of a polyalphabetic cipher.  I must be implemented as a class which is instantiable as a callable object and has at least the following functions exposed as an interface:
 * getBeta() returns the char[][] matrix used to create the mapping of c = p+k and p = c+k that we use for encryption and decryption.  Since the exact same beta matrix is required to decrypt a message if we want to run this code more than once, we need a way to retrieve the matrix from the application and store it externally.  We will similarly need a method of setting the matrix for a given instance of the cipher class, but the best method for this is probably to pass a char[][] or similar object as a parameter to a constructor
 * encrypt() accepts a plaintext string and applies the internally stored key to generate a ciphertext string, which it returns
 * decrypt() accepts a ciphertext string and performs the inverse of the encrypt operation, returning the original plaintext string
In order to accomplish this, the constructor for the class should accept the cipher's alphabet as a parameter.  Both the alphabet and the key should be stored as member variables within the class instance.  It will probably also be necessary to allow the getKey() method to be called in a static context and provide a constructor override which accepts a key as a parameter, as for security reasons the key variable should likely be declared both private and final.

### Grading Criteria:
1) If the final code uploaded to your repo does not compile, the grade is zero.
2) If the code crashes due to forseeable unhandled exceptions, the grade is zero.
3) For full points, the code should implement the algorithms and interfaces as described, and all interfaces should be easy to use and not unnecessarily complicated.

### Deliverables:
For Project 1, a program written in either Java or Python which implements the requirements as specified, along with some documentation of the process involved in writing the code and any resources referenced.

## Project 2

### Project Goals:
1) Improve understanding of coding in Java and/or Python as a method of accomplishing simple tasks and solving relatively simple problems.
2) Improve understanding of some of the more analog enciphering techniques we're discussing in class.
3) Gain experience with converting a known pen-and-paper technique to a valid code implementation.

### Description:
For Project 2, I am asking you to use either the provided Java code or the provided Python code, and write an algorithm which will programmatically crack an unknown colunar transposition cipher.  We will go over the cipher itself and the encrypt/decrypt operations in class, along with some discussion and demonstration with using statistical techniques (mostly bigrams and frequency analysis) to crack the cipher.  The provided codebase includes both the encrypt() and decrypt() methods, along with the requisite ancillary things like constructors and key generators.  You need only implement a crack() method which:
 * Accepts a ciphertext String
 * Performs some algorithm
 * Prints out the key used to generate the ciphertext
 * Returns the original plaintext string

### Grading Criteria:
1) If the final code uploaded to your repo does not compile, the grade is zero.
2) If the code crashes due to forseeable unhandled exceptions, the grade is zero.
3) For full points, the code should implement the algorithms and interfaces as described, and all interfaces should be easy to use and not unnecessarily complicated.

### Deliverables:
For Project 2, a crack() method which provides the required outputs, along with some documentation of the process involved and any resources referenced.