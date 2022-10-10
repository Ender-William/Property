const querystring = require('querystring');

// 设置 Handler 数据处理路由
const handleProcessRoute = require('./src/routes/userinfo');

// POST 数据的处理部分
const getPostDate = (req) => {
    // 开启 Promise
    const promise =  new Promise((resolve, reject) => {

        // 处理 method 为 POST 的数据
        if (req.method !== 'POST') {
         resolve({});
         return;
        }

        // 设置返回的数据格式，格式为 application/json
        if (req.headers['context-type'] !== 'appilication/json') {
            resolve({});
            return;
        }

        // 设置空的 postData
        let postData = '';

        //
        req.on('data', (chunk) => {
            postData += chunk.toString();
        });

        req.on('end', () => {
            if (!postData) {
                // 如果 postData 的值为空
                resolve({});
                return;
            }
            resolve(
                JSON.parse(postData)
            );
        })
    });
    return promise;
}

// 业务代码
const serverHandler = (req, res) => {
    res.setHeader('Content-Type', 'application/json;charset=utf-8');

    // 获取 path
    const url = req.url;
    req.path = url.split('?')[0];

    // 解析 query
    req.query = querystring.parse(url.split('?')[1]);
    // console.log(req.query);

    // 处理 POST 数据
    getPostDate(req).then((postData) => {
        // console.log(postData);
        req.body = postData;
        //console.log(postData);
        // 将 req res 数据交由路由进行处理，并获得 Promise 数据
        const processDataPromise = handleProcessRoute(req, res);

        // 如果匹配到了路由
        if (processDataPromise) {
            processDataPromise.then((processData) => {
                res.end(
                    JSON.stringify(processData)
                );
            })
            return;
        }

        // 未匹配到任何路由
        res.writeHead(404,{'Content-Type':'text/plain'});
        res.write('404 Not Found');
        res.end();

    });
}

module.exports = serverHandler;