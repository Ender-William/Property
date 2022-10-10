const {execSQL} = require("../db_services/mysqlService");
const {finduser} = require("./FinduserController");


const register = (stuID, passwd, stuName, email) => {
    // 用户注册，注册成功并返回用户名
    // insert into userinfoTB (stuID,passwd,stuname,email,authority,state)
    // values (123456,"1111","张三","xxx@xxx.com","noaml",0)
    let sql = `insert into userinfoTB (stuID,passwd,stuname,email,authority,state) values (${stuID},"${passwd}","${stuName}","${email}","nomal",0)`;
    sqlresult = execSQL(sql);
    if (sqlresult) {
        sqlresult.then((data) => {
            console.log(data);
        })
    }
    return finduser(stuID);
}

module.exports = {
    register
}