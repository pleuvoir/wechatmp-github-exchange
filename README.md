

### 功能

对微信公众号（订阅号）发送消息，会将内容推送到  [future-look](https://github.com/pleuvoir/future-look) 仓库

### 说明

1. 首先去 [微信公众平台](https://mp.weixin.qq.com) 申请微信公众号，个人开发者一般申请订阅号。
   申请成功后，公众平台提供了开发者功能，只要正确配置了服务器 URL 并启用，那么当微信公众号收到消息会转发到 Web 项目，因而开发者可以根据现有的 API 实现自己的需求。
  
2. github 提交部分可参照已有实现，进行修改。


### 项目配置

```yml 
wechatmp:
    configs:
      - appId:  开发者ID(AppID)
        secret: 开发者密码(AppSecret) 
        token:  令牌(Token)
        aesKey: 消息加解密密钥(EncodingAESKey)

github:
      username: github 帐号
      oauthAccessToken: 访问令牌
      targetRepository: 待提交仓库  
```


### 打包运行

```
mvn clean package
java -jar wechatmp-github-exchange-1.0.0.jar
```
