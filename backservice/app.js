const querystring = require('querystring');

const handleProcessRoute = require('./src/routes/userinfo');

// 处理POST数据
const getPostDate = (req) => {
    const promise =  new Promise((resolve, reject) => {
        if (req.method !== 'POST') {
         resolve({});
         return;
        }

        if (req.headers['context-type'] !== 'appilication/json') {
            resolve({});
            return;
        }

        let postData = '';

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
        // 处理程序相关的业务
        const processDataPromise = handleProcessRoute(req, res);

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