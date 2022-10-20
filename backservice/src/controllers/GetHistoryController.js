const {execSQL, execMultiSQL} = require("../db_services/mysqlService");

const getHistory = (stuID) => {
    // 查询用户历史操作
    let sql = `SELECT itemName,loanQuantity FROM lendTB WHERE stuID = ${stuID}`;
    checkResult = execMultiSQL(sql);
    if (checkResult) {
        checkResult.then((data) => {
            console.log(data);
        })
    }
    return checkResult;
}

module.exports = {
    getHistory
}