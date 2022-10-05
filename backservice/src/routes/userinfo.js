// 处理用户相关的数据
const { SuccessModel } = require("../model/responseModel");
const { getList, login, finduser, register, getUserInfoDetail, getCateInfo} = require('../controllers/process')
const handleProcessRoute = (req, res) => {
    // 定义处理路由的逻辑

    const method = req.method;

    // 登录处理
    if (method === 'POST' && req.path === '/api/login') {
        // /api/login?stuID={$studentID}&passwd={$password}&token={$token/null}
        // new successModule()
        const stuID = req.query.stuID || '';
        const passwd = req.query.passwd || '';
        const token = req.query.token || '';

        const listDataPromise = login(stuID,passwd,token);
        return listDataPromise.then((listData) => {
            // console.log(listData);
            return new SuccessModel(listData);
        });
    }

    // 查找用户名
    if (method === 'POST' && req.path ==='/api/finduser') {
        const stuID = req.query.stuID || '';

        const listDataPromise = finduser(stuID);
        return listDataPromise.then((listData) => {
            // console.log(listData);
            return new SuccessModel(listData);
        });
    }

    // 获取用户的详细信息
    if (method === 'POST' && req.path === '/api/userinfo') {
        const stuID = req.query.stuID || '';
        const listDataPromise = getUserInfoDetail(stuID);
        return listDataPromise.then((listData) => {
            // console.log(listData);
            return new SuccessModel(listData);
        });
    }

    // 注册处理
    if (method === 'POST' && req.path === '/api/register') {
        const stuID = req.query.stuID || '';
        const passwd = req.query.passwd || '';
        const stuName = req.query.stuName || '';
        const email = req.query.email || '';

        const listDataPromise = register(stuID, passwd, stuName, email);
        return listDataPromise.then((listData) => {
            // console.log(listData);
            return new SuccessModel(listData);
        });
    }

    // 获取 类别及其信息
    if (method === 'POST' && req.path === '/api/getcargoinfo') {
``      // /api/getcargoinfo?authority={$authority}
        const listDataPromise = getCateInfo();
        return listDataPromise.then((listData) => {
            // console.log(listData);
            return new SuccessModel(listData);
        });
    }

}

module.exports = handleProcessRoute;