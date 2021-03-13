--5)Which product sold more?

select * from  (select count(t.quantity) as quantity, p.productname from transactions t
    inner join product p on t.product_id = p.product_id
    group by p.productname order by quantity desc) s
    where rownum = 1;