const {execSQL} = require("../db_services/mysqlService");

const getCateName = (id) => {
    // 查询用户是否存在
    let sql = `select categoryName from cargoinfoTB where`;
    if (id) {
        sql += ` id='${id}'`;
    }
    sqlresult = execSQL(sql);
    if (sqlresult) {
        sqlresult.then((data) => {
            console.log(data);
        })
    }
    return sqlresult;
}

module.exports = {
    getCateName
}