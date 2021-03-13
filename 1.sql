--General questions:
--1)Find the most expensive price?
--2)Find the most popular license?
--3)Find members who bought all license?
--4)Ñount as many comments as possible from the license?
--5)Display how many products was selected by member?
--6)Which license scored only the highest mark by member?
--7)Which license was most rejected?
--8)Ñalculate the total number of products sold?
--9)Find members by the average rating for one product?
--10)Find a buyer who has bought a large number of products and left a rating?
--11)Find user who left both rating and comment?
--12)What roles and access does the program have?
--13)What types of programs do we sell?
--14)Average rating of each product in our online store?



--1)Find the most expensive price?

select * from (select productname, max(price) as max_price from product
group by productname order by max_price desc) s
where rownum = 1;

--2)Find the most popular license?

select * from  (select count(t.quantity) as quantity, p.productname from transactions t
    inner join product p on t.product_id = p.product_id
    group by p.productname order by quantity desc) s
    where rownum = 1;

--3)Find members who bought all license?

select * from (select m.fullname as fullname, count(t.quantity) as quantity from transactions t
    inner join member m on t.member_id = m.member_id
    where t.approvalstatus = 'APPROVED'
    group by fullname order by quantity desc) s
    where rownum < 6

--4)Ñount as many comments as possible from the license?

select * from (select c.message as message, p.productname as productname, count(p.product_id) as message_count_per_product from comments c
    inner join product p on c.product_id = p.product_id
    group by message, productname
    order by message_count_per_product desc) s
    where rownum < 6;   

--5)Display how many products was selected by member?

select m.fullname, p.productname, count(t.quantity) as quantity from member m
    inner join transactions t on m.member_id = t.member_id
    inner join product p on t.product_id = p.product_id
    group by  m.fullname, p.productname
    order by quantity desc;

--6)Which license scored only the highest mark by member?

select p.productname, rt.description, r.value from rating r
    inner join product p on r.product_id = p.product_id
    inner join ratingtype rt on r.value = rt.valueid
    and r.value = 5

--7)Which license was most rejected?

select * from (select p.productname, count(t.quantity) as quantity from transactions t
    inner join product p on t.product_id = p.product_id
    where t.approvalstatus = 'REJECTED'
    group by p.productname
    order by quantity desc) s
    where rownum = 1;

--8)Ñalculate the total number of products sold?
select sum(quantity) from cart

--9)Find members by the average rating for one product?

select m.fullname, p.productname, avg(r.value) as avg_rate from member m
    inner join rating r on m.member_id = r.member_id
    inner join product p on r.product_id = p.product_id
    group by m.fullname, p.productname;

    
--10)Find a buyer who has bought a large number of products and left a rating?
select * from (select m.fullname, max(t.quantity) as quantity from member m
    left join transactions t on m.member_id = t.member_id
    left join rating r on m.member_id = r.member_id
    and t.approvalstatus = 'APPROVED'
    group by m.fullname
    order by quantity desc) x
    where rownum < 6

--11)Find user who left both rating and comment?
select m.fullname from member m
    inner join rating r on m.member_id = r.member_id
    inner join comments c on m.member_id = c.member_id
    group by m.fullname 

12)What roles and access does the program have?
Program has JDBC connection with two types of groups: OWNER, CLIENT.
This data is stored in database, member table, group_name field.
When user connects to the database, depends on username, database provides group_name.
In this way program is automatically switched to OWNER or CLIENT group.

13)What types of programs do we sell?
We sell Operating Systems, Databases, Graphical programs, Office programs.
For more information, please run below SQL script:

select * from producttype


--14)Average rating of each product in our online store?

select p.productname, round(avg(r.value), 2) avg_rate_per_product from rating r
    inner join product p on r.product_id = p.product_id
    group by p.productname
    


