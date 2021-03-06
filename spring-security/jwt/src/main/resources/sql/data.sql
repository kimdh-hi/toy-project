insert into role(role_id, name)
    values
           ('role-01', '관리자'),
           ('role-02', '사용자')
;

insert into role_authority(role_id, authority)
    values
        ('role-01', 'ADMIN'), ('role-01', 'USER'),
        ('role-02', 'USER')
;

insert into account(id, username, password, role_id, password_update_date)
    values
        ('account-01', 'admin', '{noop}admin', 'role-01', '2022-05-01'),
        ('account-03', 'admin2', '{noop}admin', 'role-01', '2020-01-01'),
        ('account-02', 'user', '{noop}user', 'role-02', '2020-01-01')
;

