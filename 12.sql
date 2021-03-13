--12)Find user who left both rating and comment?

select m.fullname from member m
    inner join rating r on m.member_id = r.member_id
    inner join comments c on m.member_id = c.member_id
    group by m.fullname 