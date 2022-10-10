// 加载 http 库文件
const http = require('http');
// 开启服务器异步成像，异步程序由 app.js 文件开始
const serverHandler = require('../app');

// 监听端口
const PORT = 6500;

// 创建服务器
const server = http.createServer(serverHandler);

// 开始进行端口监听
server.listen(PORT, () => {
    console.log('Property Back Service at Version 1');
    console.log('server running at port 6500...');
})