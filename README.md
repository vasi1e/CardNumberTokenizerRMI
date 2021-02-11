# CardNumberTokenizerRMI
## What is it?
This is project that register tokens for card number, but it needs permission. The project stores information about registered users so you first have to loggin 
and then maybe you can register tokens or get card number from token.
## How is done?
Two modules: One for the server and the database and the other is for the app
### Database
Uses XML files to store information. You don't need to create them, the code does it itself.
### App:
JavaFx application with login form and card number/token transformation form.
## How to use it?
The project uses:
1. XStream jar
2. JavaFx
So you need to have it

In the file DB.java you need to initialize the varible pathToProjectFolderWithXMLFiles to string with the path to directory in which you store the project
## Future ideas for realization
1. Registration form
2. Use real database
