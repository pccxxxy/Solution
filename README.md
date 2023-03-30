This document aims to instruct users how to run the program on their computer, along with an overview on the key deliverables to give idea on what those major components do.

# Key deliverables

**Tasks.scala:**

Solution code for generating answers to all 5 questions.

**Test.scala:**

Test script for testing whether the tasks.scala works as expected on all 5 questions

**Solution\>answer\>questionNo\>.csv:**
<img width="742" alt="Screenshot 2023-03-30 at 2 11 57 pm" src="https://user-images.githubusercontent.com/77183391/228729400-6634290f-bd38-467f-b7c6-9944b14795e9.png">

Csv files of answers to all 5 questions

**README.md:**

Step-by-step guide on how to run the program, including solutions to tasks and test scripts.

# Prerequisite

**Java JDK**

1. Download and install Java JDK

    Download from Java official website:

    [https://www.java.com/en/download/](https://www.java.com/en/download/).

    Alternatively, download based on the IntelliJ's prompt after opening the project in IntellJ

1. Set environment variable specifying the location of executables Java

    Tutorial for macOS:

    [https://www.youtube.com/watch?v=pxi3iIy4F5A](https://www.youtube.com/watch?v=pxi3iIy4F5A)

    Tutorial for Windows:

    [https://www.guru99.com/install-java.html](https://www.guru99.com/install-java.html)

**IntelliJ IDE**

1. Download and install IntelliJ IDE [https://www.jetbrains.com/idea/download/#section=windows](https://www.jetbrains.com/idea/download/#section=windows)


**Scala plugin**

1. Open the IntelliJ IDE app
2. Go to Plugins\>Marketplace and Search for 'Scala' there to install Scala as a plugin
![install scala](https://user-images.githubusercontent.com/77183391/228729908-c07ba2cf-87e5-4f03-8950-2bb172ff6461.jpg)


# Run the project on your computer

1. Open intelliJ IDE. Restart intelliJ IDE if you just got the Scala plugin installed.
2. Open the entire folder of the project in IntelliJ
3. Setup Scala SDK
    1. Open tasks.scala in src\>main\>scala.
    
    2. IntelliJ will raise a prompt similar to the picture below to Setup Scala SDK. That means Scala SDK are missing or not being
       applied on the project yet. Click on the 'Setup Scala SDK'.
       <img width="1022" alt="Screenshot 2023-03-30 at 1 02 40 pm" src="https://user-images.githubusercontent.com/77183391/228727410-12e54fc8-e9f7-4929-bde9-7a1d2884d80d.png">
       
    3. Select the Scala plugin previously installed on the pop-up window and click okay.
        <img width="446" alt="Screenshot 2023-03-30 at 1 07 27 pm" src="https://user-images.githubusercontent.com/77183391/228727481-bcbb09a0-6c42-44a8-99e7-00fb1e71e868.png">
    
    4. If you didn't see the prompt, right click on the root folder 'Solution', select 'Open module setting' and choose 'Global             Libraries'. Set the 'Name' and 'Scala version' to the Scala plugin installed.
        <img width="1276" alt="Screenshot 2023-03-30 at 12 27 11 pm" src="https://user-images.githubusercontent.com/77183391/228729340-761bf8d6-a3f3-4019-b2cd-5cfd528f82a4.png">

    
4. Setup Java JDK
    1. Keep the tasks.scala open
    
    2. You will aslo see a prompt asking you to set up Java JDK. To solve, simply follow the IntellJ's prompt and choose the              options matching to your JDK previously installed to set up the missing parts.
    
    3. In case there isn't any prompt showing up, you can set up JDK by right clicking on the root folder 'Solution', select 'Open        module setting' and choose 'SDKs'. Set the 'Name' and 'Path' to the corresponding JDK.
        <img width="1276" alt="Screenshot 2023-03-30 at 12 26 25 pm" src="https://user-images.githubusercontent.com/77183391/228729124-680b9769-bc32-4b50-942d-87ee97ccaa85.png">

5. Check the dependencies
In this case, the pom.xml containing all the dependencies required have been provided in the project pack. 

The dependencies include scalatest(for running the test script), Spark library(for running the Spark sql query) and maven(for creating those project structures involving the usage of Java JDK). 

So you should be ready for moving onto the next step, as long as this pom.xml is correctly placed in the folder you are opening.

6. Check the Scala version
Modify the Scala version in pom.xml to match the version you are using.

7. Arrange the file structure
    1. Right click on main src/main/scala folder, choose mark directory as\>root folder.
   
    2. Right click on the src/test folder, choose mark directory as\>test source root folder.
        <img width="360" alt="Screenshot 2023-03-30 at 1 40 04 pm" src="https://user-images.githubusercontent.com/77183391/228728049-c99f17f7-f2c0-4d89-a8e7-529425466010.png">
    
8. Run the tasks.scala 
    1. Right click on the tasks.scala and select 'run tasks'
    
    2. The tasks.scala will display the answer to the 5 questions on the 'run' tab upon running.
       It will only display the top 20 rows for better readability. 

    3. Full answers to all questions will be saved on a separate csv file in the 'answer' folder
    
10. Run the test.scala
    1. Right click on the test.scala and select 'run test'
    
    2. The 'run' tab will show whether the test on all 5 questions are passed. The following message will show for any unpassed            tests.
        <img width="982" alt="Screenshot 2023-03-29 at 9 11 17 pm" src="https://user-images.githubusercontent.com/77183391/228728539-8de65ffd-b930-4bcf-9199-9067997d5b60.png">

    3. To run a test for a particular question, click on the green button next to the test script corresponding to each question.
        <img width="1024" alt="Screenshot 2023-03-30 at 2 02 47 pm" src="https://user-images.githubusercontent.com/77183391/228728524-f9b8def9-f0db-424b-af11-fe78a1c0b444.png">


# Link to Git repo
https://github.com/pccxxxy/Solution
