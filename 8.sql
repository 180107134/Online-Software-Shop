--8)Which license was most rejected?

select * from (select p.productname, count(t.quantity) as quantity from transactions t
    inner join product p on t.product_id = p.product_id
    where t.approvalstatus = 'REJECTED'
    group by p.productname
    order by quantity desc) s
    where rownum = 1;