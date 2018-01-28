第一步：
password模式：
http://localhost:8080/oauth/token?username=user_1&password=123456&grant_type=password&scope=select&client_id=client_2&client_secret=123456
响应如下：
`{"access_token":"950a7cc9-5a8a-42c9-a693-40e817b1a4b0","token_type":"bearer","refresh_token":"773a0fcd-6023-45f8-8848-e141296cb3cb","expires_in":27036,"scope":"select"}`

client模式：
http://localhost:8080/oauth/token?grant_type=client_credentials&scope=select&client_id=client_1&client_secret=123456
响应如下：
`{"access_token":"56465b41-429d-436c-ad8d-613d476ff322","token_type":"bearer","expires_in":25074,"scope":"select"}`

第二步：
携带accessToken参数访问受保护的资源：
使用password模式获得的token:
http://localhost:8080/order/1?access_token=950a7cc9-5a8a-42c9-a693-40e817b1a4b0
得到了之前匿名访问无法获取的资源：

使用client模式获得的token:
http://localhost:8080/order/1?access_token=56465b41-429d-436c-ad8d-613d476ff322
得到了之前匿名访问无法获取的资源：