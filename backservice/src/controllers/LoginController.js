const {execSQL,execMultiSQL} = require('../db_services/mysqlService')

const login = (stuID, passwd) => {
    // 登录时的业务逻辑
    let sql = `select * from userinfoTB where`;

    if (stuID) {
        sql += ` stuID='${stuID}' `;
    }
    if (passwd) {
        sql += `and passwd='${passwd}' `;
    }
    sqlresult = execSQL(sql);
    if (sqlresult) {
        sqlresult.then((data) => {
            console.log(data);
        })
    }
    return execSQL(sql);
}

module.exports = {
    login
}