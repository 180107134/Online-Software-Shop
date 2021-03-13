--15)Average rating of each product in our online store?

select p.productname, round(avg(r.value), 2) avg_rate_per_product from rating r
    inner join product p on r.product_id = p.product_id
    group by p.productname
    