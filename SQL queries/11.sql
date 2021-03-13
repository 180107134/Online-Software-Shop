--10)Find a buyer who has bought a large number of products and left a rating?

select * from (select m.fullname, max(t.quantity) as quantity from member m
    left join transactions t on m.member_id = t.member_id
    left join rating r on m.member_id = r.member_id
    and t.approvalstatus = 'APPROVED'
    group by m.fullname
    order by quantity desc) x
    where rownum < 6