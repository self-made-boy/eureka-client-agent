在eureka-client发出的请求中增加自定义请求头

使用方式
```shell
mvn clean package
java  -javaagent:/yourpath/eureka-client-agent.jar -Deureka.header.xxx=xxx  -jar  eureka-app.jar
```
