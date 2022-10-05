const mysql = require('mysql');
const {MYSQL_CONFIG} = require("../config/db");

const connection = mysql.createConnection(MYSQL_CONFIG);

// 开始连接
connection.connect();

// 执行 SQL 语句并返回单条数据
function execSQL(sql) {
    const promise = new Promise((resolve, reject) => {
        connection.query(sql, (err, result) => {
            if (err) {
                reject(err);
                return;
            }
            resolve(result[0]);
        })
    })
    console.log(promise);
    return promise;
}

// 执行 SQL 语句并返回多条数据
function execMultiSQL(sql) {
    const promise = new Promise((resolve, reject) => {
        connection.query(sql, (err, result) => {
            if (err) {
                reject(err);
                return;
            }
            resolve(result);
        })
    })
    console.log(promise);
    return promise;
}

module.exports = {
    execSQL,
    execMultiSQL
}