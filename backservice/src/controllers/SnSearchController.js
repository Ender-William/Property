const { execSQL} = require("../db_services/mysqlService");

const searchSN = (sn) => {
    // 调取 cargoinfoTB 中 Category Detail Info 的全部信息
    let sql = `SELECT sn,itemName,cateid,quantity FROM cargodetailTB WHERE sn='${sn}'`;
    sqlresult = execSQL(sql);
    if (sqlresult) {
        sqlresult.then((data) => {
            console.log(data);
        })
    }
    return sqlresult;
}

module.exports = {
    searchSN,
}