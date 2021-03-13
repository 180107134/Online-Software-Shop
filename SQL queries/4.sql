--4)Count as many comments as possible from the license?

select * from (select c.message as message, p.productname as productname, count(p.product_id) as message_count_per_product from comments c
    inner join product p on c.product_id = p.product_id
    group by message, productname
    order by message_count_per_product desc) s
    where rownum < 6;   