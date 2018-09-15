

### 功能

对微信公众号（订阅号）发送消息，会将内容推送到  [future-look](https://github.com/pleuvoir/future-look) 仓库

### 说明

1. 首先去 [微信公众平台](https://mp.weixin.qq.com) 申请微信公众号，个人开发者一般申请订阅号。
   申请成功后，公众平台提供了开发者功能，只要正确配置了服务器 URL 并启用，那么当微信公众号收到消息会转发到本地 Web 项目，因而开发者可以根据现有的      API 实现自己的需求。


2. ngrok 是一个反向代理，通过在公共的端点和本地运行的 Web 服务器之间建立一个安全的通道。简单的说，就是通过 ngrok 实现内网穿透，使得外网可以访问本地项目。
    
   启动：打开 shell 或 cmd，进入 ngrok 目录，运行 `ngrok -config ngrok.cfg -subdomain           wechatmp-github-exchange 9000` 如果运行失败，更换 `wechatmp-github-exchange` 为其它字符串，直至连接成功。其中`wechatmp-github-exchange 9000` 为需要映射到外网的项目名称及端口。


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

### 已知问题

在 eclipse 中如果 Installed JREs 选择的是 jre 则在 eclipse 中连接 github 没有问题，但是却无法打包，如果改为了 jdk，则可以打包，却无法在 eclipse 中连接 github 

解决方案为更新 jdk 目录下安全相关的包，可根据出现的错误具体解决
