
--10)Find members by the average rating for one product?

select m.fullname, p.productname, avg(r.value) as avg_rate from member m
    inner join rating r on m.member_id = r.member_id
    inner join product p on r.product_id = p.product_id
    group by m.fullname, p.productname;