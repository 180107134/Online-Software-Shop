# Online-Software-Shop
Online Software Shop

**2. Project description**

1) Define and describe the project goal and its applications as well as its benefits in the real-world. Describe the problem and its solution that you propose.

Our project is an online software store. The user enters our online store, where he places an order. The user's order goes to the admin panel. If available, the admin changes the status of the request to "approved". If for internal reasons the client cannot receive this product, the admin changes the status of the product to "refused". If the status is "approved", the quantity of the item is reduced by one.





2) Define the scope of the project. Define the project scope that your team can finish the proposed goals (and project functions/features) within CSS348 class. Unfinished projects will be graded with low score.
We have project functions like:
  user/admin log in.
  add product in to basket.
  Admin can change product statuses.
  calculate product price and quantity.
  users transactions.
  user’s payment.
  add comment to the product.




3) Choose a type of the user-interface (web, desktop, mobile, etc.). You have complete freedom in choosing the type of the user-interface. Note, make sure you can connect the interface with the database of your choice. For example, using JDBC drivers (https://en.wikipedia.org/wiki/JDBC_driver). Describe your decisions in the project description.

Type of the user-interface: desktop.


4) Choose a programming language and database server. Explain your decisions in the project 
description.

We will use Java programming language, as well as MySQL database. Because, for us, it is much more understandable, and since we learned Java in the previous semesters, Java is much more convenient for us.

**3. Project Use-Case diagram and questions over the data**

General questions:<br>
1)Find the most expensive price?<br>
2)Find the most popular license?<br>
3)Find members who bought all license?<br>
4)Сount as many comments as possible from the license?<br>
5)Which product sold more?<br>
6)Display how many products was selected by member?<br>
7)Which license scored only the highest mark by member?<br>
8)Which license was most rejected?<br>
9)Сalculate the total number of products sold?<br>
10)Find  members by the average rating for one product?<br>
11)Find a buyer who has bought a large number of products and left a rating?<br>
12)Find user who left both rating and comment?<br>
13)What roles and access does the program have?<br>
14)What types of programs do we sell?<br>
15)Average rating of each product in our online store?<br>

Use-case UML diagram:
![alt text](/UML.jpg)

**4. Data modeling and database design**

ER Diagram

All our products are stored in «Product» table.<br>
All information about clients are stored in «Member» table.<br>
We have a windows, linux and mac products, so this types of products are stored in «ProductType» table.<br>
Clients backet with product/products are stored in «Card» table.<br>
We have a windows, linux and mac categories of products, so this rating type of categories are stored in «RatingType» table.<br>
All ratings about selected product stored in «Rating» table.<br>
All comments from clients about products are stored in «Comments» table.<br>
All transactions with client and admin, like add in the backet product, buying selected product and etc. are stored in «Transactions» table.<br>

![alt text](/ERD.jpg)

DDL<br>
[DDL queries](/DDL.sql)

DML<br>
[DML queries](/DML.sql)

**5. SQL queries**

SQL queries stored in the "main/SQL Queries" folder.

