--3)Find members who bought all license?

select * from (select m.fullname as fullname, count(t.quantity) as quantity from transactions t
    inner join member m on t.member_id = m.member_id
    where t.approvalstatus = 'APPROVED'
    group by fullname order by quantity desc) s
    where rownum < 6