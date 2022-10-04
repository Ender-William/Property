var sqlMap = {
    selectUser: 'SELECT stuID FROM userinfoTB WHERE stuID = ? AND passwd = ? ',
    isUserExist: 'SELECT stuID FROM userinfoTB WHERE stuID = ? ',
    isTokenExist: 'SELECT token FROM tolenTB WHERE woken = ? LIMITI 1',
}

module.exports = sqlMap;