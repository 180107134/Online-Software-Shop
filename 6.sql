--6)Display how many products was selected by member?

select m.fullname, p.productname, count(t.quantity) as quantity from member m
    inner join transactions t on m.member_id = t.member_id
    inner join product p on t.product_id = p.product_id
    group by  m.fullname, p.productname
    order by quantity desc;
