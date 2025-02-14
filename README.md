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
```
I felt that I have learnt a lot after writing the unit tests for my features and more at
ease after testing them and seeing that my features work. 
I think there should be at least two tests for a class but the more the better. One
case should be positive while the second case is negative.
An extra test case can be made to make sure that the feature works in more realistic
situations such as deleting one specific product from a list of five products.
We can make sure that the unit tests are enough if they test most of if not all of the
results from the most common or important interactions between the user and other classes.
100% code coverage means that all of the code in the program is involved or tested by
the unit tests. It does not measure the amount of bugs or errors a program has.
The new code for the functional test suites will reduce the code quality because it’s
going to add more code to the file and makes it look more cluttered.
There is just so much code to be read or skim through and it might make understanding it harder.
To make the test code cleaner, the code could either be put in its own java class file
instead of getting added to the same file. If that cannot happen then the tests that
test different features can be put in their own classes in the same file. The name of
the new class file or section should be clear by reflecting what it tests such as ProductListCountTest.
```

</details>