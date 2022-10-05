[toc]

# Introduction

`Property` 项目是一个 `物资/资产` 管理软件，`Version 1` 版本是本学期安卓课设作业的提交版本，适用于 `API 29` 及以上版本的安卓系统。该软件可以实现



------

# Chapter 1 Technology stack 技术栈

## Section 1 Android

1. **IDE: Android Studio 2021.2.1 Patch 2**
2. **SDK: SDK30**
3. **Java Version: Java 11 / Java 16**
4. **Emulator: Google Pixel 4 XL with API 30**
5. **Wireless ADB Device: Redmi K40 Pro**



## Section 2 BackService

1. **Language: Node.js v15.13.0**
2. **MQ: MySQL 8.0.23**
3. **PKG Manager: npm v7.7.6**
4. **Database Visualization: Navicat Premium v15.0.26**
5. **API Test: ApiPost / Postman**
6. **Server OS: OS X 12.4 / Ubuntu 18.04**



## Section 3 Document

1. **Language: Markdown**
2. **Editor: Typora**



# Chapter 2 Database

## Section 1 userinfoTB

用户信息表，这个表用来存储用户账户的基本信息，包括了 学号/用户名，密码，姓名，邮件，权限以及注销标识符 这些信息。

| 字段名称  | 数据类型 | 是否主键 |            说明            |
| :-------: | :------: | :------: | :------------------------: |
|   stuID   |   int    |   Key    |        学号/用户名         |
|  passwd   | varchar  |          |            密码            |
|   name    | varchar  |          |          用户姓名          |
|   email   | varchar  |          |        电子邮件地址        |
| authority | varchar  |          | 权限（Version 1 中无应用） |
|   state   |   int    |          | 注销为 1 ，正常未注销为 0  |

在当前版本，也就是 `Version 1` 当中，`authority` 是没有具体的应用的，因为线下开课到结课也就四五周时间，再加上有许多的事情需要并行处理，因此这个功能留到后面的版本再进行运用。



## Section 2 cargoinfoTB

大类别信息表，这个表用来储存大类别的类别名称、子类总数以及全部子项的总库存。

|   字段名称    | 数据类型 | 是否主键 |            说明            |
| :-----------: | :------: | :------: | :------------------------: |
|      id       |   int    |   Key    |          类别序号          |
| categoryName  | carchar  |   Key    |          类别名称          |
|    sonItem    |   int    |          |     该类别下的子类总数     |
|  sonTotality  |   int    |          |      所有子类的总库存      |
| categoryState |   int    |          | 类别被删除为0，未被删除为1 |



## Section 3 cargodetailTB

物品详情信息，储存了物品的唯一编码（条形码或二维码）、物品名称、物品所属的父类，储存空间编码，物品总数，物品照片，物品创建日期以及物品的创建者。

这里需要注意的是，物品的照片是用的是==二进制数据流==进行储存，直接储存到数据库当中。

|    字段名称    |  数据类型  | 是否主键 |                       说明                       |
| :------------: | :--------: | :------: | :----------------------------------------------: |
|       sn       |  varchar   |   Key    | 物品的唯一编码，可以是二维码信息也可是条形码信息 |
|    itemName    |  varchar   |   Key    |                     物品名称                     |
|       id       |    int     |   Key    |               物品所属的父类的 ID                |
| storageAddress |  varchar   |          |   物品存储空间（该字段很长一段时间不会被启用）   |
|    quantity    |    int     |          |               物品的总数量，即库存               |
| introImagePath | mediumblob |          |              介绍图片，二进制数据流              |
|   createDate   |  varchar   |          |                 物品被创建的日期                 |
|    creator     |    int     |          |                   物品的创建者                   |

这里有两个点需要解释一下：

1. `storageAddress` 这个字段在 `Vsersion 1`版本没有任何实质性意义，在很长一段时间的后续版本也不会有任何实质意义，保留物品的存储信息是为了日后为 AR/MR 眼镜的应用留出应用空间，至于到底什么时候回被启用，就看本人的能力如何了；
2. `introImagePath` 字段是用来保存照片的二进制数据流的，在 MySQL 中 `mediumblob` 数据类型可以保存最大 `16MB` 的图片，至于说为何不使用 `Blob` 或 `TinyBlob` ，可能是因为本人希望展现的图片能尽可能地清晰。因为许多的物资管理的软件所展示的图片质量一言难尽，特别是免费软件，更是如此，因此为了能尽可能展示高清图片，我选择了这种数据类型。



## Section 4 lendTB

借出信息 包括了操作码（也就是操作顺序），学号/用户名，物品名称，物品唯一编码，出借顺序以及操作日期。

|    字段名称     | 数据类型 | 是否主键 |          说明          |
| :-------------: | :------: | :------: | :--------------------: |
| lendOperateCode |   int    |   Key    |    操作码，操作顺序    |
|      stuID      |   int    |          |   出借者唯一 ID 标识   |
|    itemName     | varchar  |          |     被借出物品名称     |
|       sn        | varchar  |   Key    | 被借出物品唯一 SN 标识 |
|  loanQuantity   |   int    |          |        借出数量        |
|   operateDate   |   int    |          |        操作日期        |



## Section 5 returnTB

|     字段名称      | 数据类型 | 是否主键 |          说明          |
| :---------------: | :------: | :------: | :--------------------: |
| returnOperateCode |   int    |   Key    |    操作码，操作顺序    |
|       stuID       |   int    |          |   出借者唯一 ID 标识   |
|     itemName      | varchar  |          |     被借出物品名称     |
|        sn         | varchar  |   Key    | 被借出物品唯一 SN 标识 |
|   loanQuantity    |   int    |          |        借出数量        |
|    operateDate    |   int    |          |        操作日期        |









## Section 1 Login

登录接口，用于请求登录并返回对应状态，编码使用 `charset=utf-8`，使用 `POST` 方法。

接口 URL：`/api/login`，通过在 URL 后添加变量传送数据。

接口代码如下：

```javascript
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
```

请求数据结构如下：

```
/api/login?stuID={$studentID}&passwd={$password}&token={$token/null}
```

请求数据案例：

数据请求在本机完成，故使用 `localhost`

```
http://localhost:6500/api/login?stuID=1111&passwd=admin
```

正确返回格式如下：

```json
{
	"data": {
		"stuID": 1111,
		"passwd": "admin",
		"stuname": "admin",
		"email": "1234567@123.com",
		"authority": "admin",
		"state": 0
	},
	"error": 0
}
```

参数/信息错误的返回格式如下：

```json
{
	"error": 0
}
```

接口错误时返回格式如下 ( Content-Type: text/plain )：

```json
404 Not Found
```

