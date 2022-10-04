const charStr = 'abacdefghjklmnopqrstuvwxyzABCDEFGHJKLMNOPQRSTUVWXYZ0123456789';

/**
 * 随机生成索引
 * @param min 最小值
 * @param max 最大值
 * @param i 当前获取位置
 */
function RandomIndex(min, max, i) {
    let index = Math.floor(Math.random() * (max - min + 1) + min),
        numStart = charStr.length - 10;
    //如果字符串第一位是数字，则递归重新获取
    if (i == 0 && index >= numStart) {
        index = RandomIndex(min, max, i);
    }
    //返回最终索引值
    return index;
}

/**
 * 随机生成字符串
 * @param len 指定生成字符串长度
 */
function getRandomString(len) {
    let min = 0, max = charStr.length - 1, _str = '';
//判断是否指定长度，否则默认长度为15
    len = len || 15;
//循环生成字符串
    for (var i = 0, index; i < len; i++) {
        index = RandomIndex(min, max, i);
        _str += charStr[index];
    }
    return _str;
}

module.exports = getRandomString;