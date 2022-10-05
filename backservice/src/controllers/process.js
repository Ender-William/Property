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

const finduser = (stuID) => {
    // 查询用户是否存在
    let sql = `select stuID from userinfoTB where`;
    if (stuID) {
        sql += ` stuID='${stuID}'`;
    }
    sqlresult = execSQL(sql);
    if (sqlresult) {
        sqlresult.then((data) => {
            console.log(data);
        })
    }
    return sqlresult;
}

const register = (stuID, passwd, stuName, email) => {
    // 用户注册，注册成功并返回用户名
    // insert into userinfoTB (stuID,passwd,stuname,email,authority,state)
    // values (123456,"1111","张三","xxx@xxx.com","noaml",0)
    let sql = `insert into userinfoTB (stuID,passwd,stuname,email,authority,state) values (${stuID},"${passwd}","${stuName}","${email}","nomal",0)`;
    sqlresult = execSQL(sql);
    if (sqlresult) {
        sqlresult.then((data) => {
            console.log(data);
        })
    }
    return finduser(stuID);
}

const getUserInfoDetail = (stuID) => {
    // 获取用户账号信息
    let sql = `select * from userinfoTB where`;
    if (stuID) {
        sql += ` stuID='${stuID}'`;
    }
    sqlresult = execSQL(sql);
    if (sqlresult) {
        sqlresult.then((data) => {
            console.log(data);
        })
    }
    return sqlresult;
}

const getCateInfo = () => {
    // 调取 cargoinfoTB 中 Category Detail Info 的全部信息
    let sql = `SELECT * FROM cargoinfoTB`;
    sqlresult = execMultiSQL(sql);
    if (sqlresult) {
        sqlresult.then((data) => {
            console.log(data);
        })
    }
    return sqlresult;
}

const getList = (stuID, passwd, token) => {
    // 从数据库获得数据
    return [
        {
            id:1,
            title:1,
            passage:1
        },
        {
            id:2,
            title:2,
            passage:2
        }
    ]
}

module.exports = {
    getList,
    login,
    finduser,
    register,
    getUserInfoDetail,
    getCateInfo,
}