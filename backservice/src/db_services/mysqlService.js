const mysql = require('mysql');
const dbConfig = require('../config/db');
const sqlMap = require('./sqlMap');
const {MYSQL_CONFIG} = require("../config/db");

const connection = mysql.createConnection(MYSQL_CONFIG);

// 开始连接
connection.connect();

// 执行 SQL 语句
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
    return promise;
}

module.exports = {
    execSQL
}