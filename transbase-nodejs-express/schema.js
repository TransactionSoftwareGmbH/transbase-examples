const { Transbase } = require("@transaction/transbase-nodejs");
const transbase = new Transbase({
  url: "//localhost:2024/cashbook",
  user: "tbadmin",
  password: "transbase",
});
transbase.query("drop table if exists cashbook;");
transbase.query(`create table cashbook
(
	nr 	integer not null primary key auto_increment,
	date 	timestamp not null default currentdate,
	amount 	numeric(10,2) not null,
	comment varchar(*)
);`);

transbase.query(
  `insert into cashbook values (default, currentdate, -9.50, 'Lunch');`
);
transbase.query(
  `insert into cashbook values (default, currentdate, -9.50, 'Lunch');`
);

transbase.close();
