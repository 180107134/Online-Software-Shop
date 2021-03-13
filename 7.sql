--7)Which license scored only the highest mark by member?

select p.productname, rt.description, r.value from rating r
    inner join product p on r.product_id = p.product_id
    inner join ratingtype rt on r.value = rt.valueid
    and r.value = 5