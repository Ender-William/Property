const http = require('http');
const serverHandler = require('../app');

const PORT = 6500;

const server = http.createServer(serverHandler);

server.listen(PORT, () => {
    console.log('server running at port 6500...');
})