App Deployment URL

[genuine-melloney-ameerakt-4ab042d8.koyeb.app/](genuine-melloney-ameerakt-4ab042d8.koyeb.app/)

<details>
<summary><strong>Module 03</strong></summary>

### Question 1
Explain what principles you apply to your project!
```
1. The principles I applied to my project are:
- SRP
  The single responsibility principle suggests that each module or class in a code must have
  one and only one function or responsibility. For example, the CarController class used to
  be an extension of the ProductContoller class and was located in the ProductController file.
  I moved the CarController class out and into its own file. Resulting in both controllers
  managing only one module.

- OCP
  The open closed principle states that classes, modules and functions should be open for
  extension but closed for modification. I implemented this by creating an interface for
  the Repositories of Car and Product, did decoupling and added the files CarRepositoryImpl
  and ProductRepositoryImpl. This way, adding more methods is much more simpler instead of
  modifying large segments or even the entire file's code.

- DIP
  The Dependency Inversion Principle states that high-level modules should not depend on
  low-level modules. Instead they should be depending on abstractions.
  In my code for example, in the CarRepositoryImpl class, the repository methods depend
  on the CarRepository interface instead of itself before applying SOLID principles.
```

### Question 2
Explain the advantages of applying SOLID principles to your project with examples.
```
Improved Readability:
Moving the CarController into its own file makes it easier for other developers to find it
and debug it. It also makes it clearer to comprehend how the Repository modules work as the
list of functions are located in the CarRepository file.

Reduced Test Cases:
Moving the CarController class out of ProductController means ProductController requires
less test cases and can have its own test suite. Implementing such test cases are more
simpler and quicker.

Less Dependencies:
Implementing decoupling on the previous CarRepository and creating a CarRepositoryImpl
reduces risk of bugs or errors when modifying or adding more functions.

Improved Extensibility:
The new ProductRepository and ProductRepositoryImpl enables the addition of functionDes

Enhanced Maintainability:
Implementing DIP on the Repository modules means, when a bug or error arises, singular
segments of code can be modified or added to instead of the entire code in the files.
SRP used for the CarController results in easier debugging as its functions are not associated
with the ProductController anymore.
```

### Question 3
Explain the disadvantages of not applying SOLID principles to your project with examples.
```
Low Readability and Maintainability:
If CarController was not moved into its own file it would be harder to look for it and debug
it if errors arise.

Less Extensibility:
If CarRepository did not change and be associated with CarRepositoryImpl, adding more functions
will require more time and effort as it is less flexible.

Greater and More Complicated Test Cases:
If CarController was a part of the ProductController file, the test suite will have more test
cases and it may be more difficult to test since CarController depends on the ProductController class.

Higher Risk of Bugs or Errors:
Increased dependencies of modules may result in bugs or errors if they are modified or extended
as the code is usually less readable due to its large size which results in more complicated code.
For example, debugging a CarRepository that has not been decoupled yet.
```
</details>


<details>
<summary><strong>Module 02</strong></summary>

### Part 1
You have implemented a CI/CD process that automatically runs the test suites, analyzes code quality, and deploys to a PaaS. Try to answer the following questions in order to reflect on your attempt completing the tutorial and exercise.
List the code quality issue(s) that you fixed during the exercise and explain your strategy on fixing them.
```
    All the code quality issues were small ones related to maintainability.
    The issues consisted of:
	- 6 unused and unnecessary imports
	- 3 public modifiers that had to be removed
	- 2 unneeded declarations of thrown exception
	- 1 Add at least one assertion to this test case in ProductController.java
	  which is caused by the wrong method used in a test
	- some thymeleaf templates that were not indexed and needed
	  src/main/resources added in the scanned files so Java XSS vulnerabilities
	  can be detected.
	  
    To fix these issues, I used the SonarCloud analysis as a guide and followed what it
    outputted such as deleting unneeded imports, "throws Exception" declarations and public
    modifiers. As for the wrong method used, I used the suggested change from IntelliJ which
    changed the line from "List<Product> productList = Arrays.asList(testProduct3);" to
    "Collections.singletonList(testProduct3);". To improve security, I added this line
    "property("sonar.sources", "src/main/java,src/main/resources")" in sonar properties in
    build.gradle so SonarCloud can scan the files in the resources directory.
    
    Before implementing SonarCloud, my code had issues in naming of files, classes
    or attributes which made my project build fail.
    To fix these I made the name of unit test classes the same as the file they are in.
```
### Part 2
Look at your CI/CD workflows (GitHub)/pipelines (GitLab). Do you think the current implementation has met the definition of Continuous Integration and Continuous Deployment? Explain the reasons (minimum 3 sentences)!
```
    CI/CD workflows involves the building, testing and deployment of a codebase by an
    automated script with every push to the main branch and my implementation meets the
    defintion of CI/CD.
    
    The CI workflow is covered by the scripts: ci.yml, scorecard.yml and sonarcloud.yml.
    The ci.yml builds the java project and runs basic tests on it to make sure that it
    runs. Whereas, scorecard.yml tests the security quality while sonarcloud.yml gives
    a detailed analysis on the overall quality of the code.
    
    On the other hand, the continuous deployment (CD) workflow is automatically handled
    on the Koyeb PaaS without a script. The project is built and then deployed on the
    URL above.
```
</details>

<details>
<summary><strong>Module 01</strong></summary>

### Reflection 1
You already implemented two new features using Spring Boot. Check again your source code and evaluate the coding standards that you have learned in this module. Write clean code principles and secure coding practices that have been applied to your code.  If you find any mistake in your source code, please explain how to improve your code.
```
    When coding the delete and edit features, I made sure the code was clean
    by following the same structure of the code for the create product and 
    list product features so that it was easy to read.
    I made the purpose of the new features clear and obvious by including the
    function of the feature in the html page names and in code in the java files
    for the controller, repository and service. For example, for the edit feature
    HTML is EditProduct.html and the functions in the controller are named
    editProductPage and editProductPost which also follow the naming style of the
    previous features. Moreover, I made sure that each function only had one purpose.

    To maintain code readability, I tried to refrain myself from adding unnecessary 
    comments since it is already easy to tell what the code does. I did not add
    any comments because I could tell what each function does and to avoid clutter.
    Additionally, I added empty lines between each function. I even tried to make the
    code simple as much as I can so that it is easy to understand and makes it
    less cluttered.
    If there was any mistake in my code, I did research on what I 
    could do to fix it instead of commenting it out. If I felt there were mistakes
    in the code I compared my code with the original source code from the module
    and fixed any syntax mistakes.
```
### Reflection 2
Suppose that after writing the CreateProductFunctionalTest.java along with the corresponding test case, you were asked to create another functional test suite that verifies the number of items in the product list. You decided to create a new Java class similar to the prior functional test suites with the same setup procedures and instance variables.
What do you think about the cleanliness of the code of the new functional test suite? Will the new code reduce the code quality? Identify the potential clean code issues, explain the reasons, and suggest possible improvements to make the code cleaner!
```
I felt that I have learnt a lot after writing the unit tests for my
features and more at ease after testing them and seeing that my features work.
I think there should be at least two tests for a class but the more the better.
One case should be positive while the second case is negative.
An extra test case can be made to make sure that the feature
works in more realistic situations such as deleting one specific product from
a list of five products.
We can make sure that the unit tests are enough if they test most of if not
all of the results from the most common or important interactions between the
user and other classes.

100% code coverage means that all of the code in the program
is involved or tested by the unit tests. It does not measure the amount of
bugs or errors a program has.
The new code for the functional test suites will reduce the code
quality because it’s going to add more code to the file and makes it
look more cluttered.
There is just so much code to be read or skim through and it might
make understanding it harder.
To make the test code cleaner, the code could either be put in its own
java class file instead of getting added to the same file. If that cannot
happen then the tests that test different features can be put in their own
classes in the same file. The name of the new class file or section should
be clear by reflecting what it tests such as ProductListCountTest.
```
</details>
