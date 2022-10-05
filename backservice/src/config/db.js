let MYSQL_CONFIG = {};


MYSQL_CONFIG = {
    host: '127.0.0.1',      // 新建数据库连接时的 主机名或ID地址 内容
    user: 'root',
    database: 'property',      // 数据库名
    password: 'passwd',   // root 密码
    port: '3306'
}


module.exports = {
    MYSQL_CONFIG
}