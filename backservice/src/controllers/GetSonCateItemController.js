const {execMultiSQL} = require("../db_services/mysqlService");

const getCateSon = (cateID) => {
    // 调取 cargoinfoTB 中 Category Detail Info 的全部信息
    let sql = `SELECT itemName,quantity FROM cargodetailTB WHERE cateid='${cateID}'`;
    sqlresult = execMultiSQL(sql);
    if (sqlresult) {
        sqlresult.then((data) => {
            console.log(data);
        })
    }
    return sqlresult;
}

module.exports = {
    getCateSon,
}