# wechatmp-github-exchange

微信公众号推送自动提交到 github 指定仓库

### 说明

[微信公众平台官网](https://mp.weixin.qq.com)

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

打开 shell 或cmd，进入 ngrok 目录，运行 `ngrok -config ngrok.cfg -subdomain wechatmp-github-exchange 9000` 如果运行失败，请更换 `wechatmp-github-exchange` 为其它字符串，直至连接成功


### 打包运行

```
mvn clean package
java -jar wechatmp-github-exchange-1.0.0.jar
```


### 注意

在 eclipse 中如果 Installed JREs 选择的是 jre 则在 eclipse 中连接 github 没有问题，但是却无法打包，如果改为了 jdk，则可以打包，却无法在 eclipse 中连接 github 

解决方案为更新 jdk 目录下安全相关的包，可根据出现的错误具体解决
