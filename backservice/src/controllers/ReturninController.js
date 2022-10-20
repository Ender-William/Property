const {execSQL} = require("../db_services/mysqlService");

const returnin = (sn,num,stuID) => {
    // 归还操作
    let sql = `select cateid,quantity,itemName from cargodetailTB where`;
    if (sn) {
        sql += ` sn='${sn}'`;
    }
    checkResult = execSQL(sql);
    Result = checkResult.then((ListData) => {
        // console.log(ListData);
        if (ListData === undefined) {
            // 说明没有查询到物品
            let backdata = {
                "code": 0
            }
            return backdata;
        }
        // 如果查询到物品
        console.log(num);
        console.log(num < ListData.quantity);
        if (num !== 0 && num > 0) {
            // 借出物品数量小于库存数量
            // console.log(num);
            let saveSql = `UPDATE cargodetailTB SET quantity = quantity + ${num} WHERE sn='${sn}'`;
            execSQL(saveSql);

            // 获取当前时间戳
            let nowTimeStamp = Date.now();
            let returnTBsql = `INSERT INTO returnTB VALUES(null,${stuID},'${ListData.itemName}','${sn}',${num},1,'${nowTimeStamp}')`;
            execSQL(returnTBsql);

            let CargoDetail = `UPDATE cargoinfoTB SET sonTotality = sonTotality + ${num} WHERE id = ${ListData.cateid}`;
            execSQL(CargoDetail);

            let backdata = {
                "code": 1
            }
            return backdata;
        }
        // 归还物品数量有误
        let backdata = {
            "code": 0
        }
        return backdata;
    })

    return Result;
}

module.exports = {
    returnin
}