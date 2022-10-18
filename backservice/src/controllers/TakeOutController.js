const {execSQL} = require("../db_services/mysqlService");

const takeout = (sn,num,stuID) => {
    // 借出操作
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
        if (num < ListData.quantity) {
            // 借出物品数量小于库存数量
            restore = (ListData.quantity) - num;
            // console.log(num);
            let saveSql = `UPDATE cargodetailTB SET quantity=${restore} WHERE sn='${sn}'`;
            execSQL(saveSql);

            // 获取当前时间戳
            let nowTimeStamp = Date.now();
            let lendTBsql = `INSERT INTO lendTB VALUES(null,${stuID},'${ListData.itemName}','${sn}',${num},'${nowTimeStamp}')`;
            execSQL(lendTBsql);

            let CargoDetail = `UPDATE cargoinfoTB SET sonTotality = sonTotality - ${num} WHERE id = ${ListData.cateid}`;
            execSQL(CargoDetail);

            let backdata = {
                "code": 1
            }
            return backdata;
        }
        // 出借物品数量大于库存数量
        let backdata = {
            "code": 0
        }
        return backdata;
    })

    return Result;
}

module.exports = {
    takeout
}