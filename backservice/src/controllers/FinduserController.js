const {execSQL} = require("../db_services/mysqlService");

const finduser = (stuID) => {
    // 查询用户是否存在
    let sql = `select stuID from userinfoTB where`;
    if (stuID) {
        sql += ` stuID='${stuID}'`;
    }
    sqlresult = (sql);
    if (sqlresult) {
        sqlresult.then((data) => {
            console.log(data);
        })
    }
    return sqlresult;
}

module.exports = {
    finduser
}