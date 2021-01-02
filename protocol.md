# 协议

## 域名格式

### 可访问网址

1. 主页 example.com
2. 微博详情 example.com/detail/{weibo_id}
3. 用户主页 example.com/user/{user_id}
4. 登录 example.com/login
5. 注册 example.com/reg
6. 忘记密码 example.com/forget

### API网址

1. 查询微博 example.com/api/weibo
2. 查询评论 example.com/api/comment
3. 查询我的关注 example.com/api/follow
4. 查询关注我的 example.com/api/follower
5. 图片 example.com/api/img/{img_id}

## json格式

1. 前端->后端 查询微博

    ```json
    {
        "sort_by":"排序方式",
        //time_up,time_down,like_up,like_down,comment_up,comment_down
        "page":"页面数"
    }
    ```

2. 后端->前端 微博内容

    ```json
    {
        "id":"微博id",
        "user_id":"用户",
        "user_name":"用户名",
        "time":"时间",
        "content":"正文内容",
        "like":{
            "count":"点赞数",
            "liked":"是否已经点赞"
        },
        "comment":"评论数",
        "img_id":"图片id"
    }
    ```

3. 前端->后端 发布微博

    //TODO

4. 前端->后端 查询评论

    ```json
    {
        "id":"微博id",
        "page":"页面数"
    }
    ```

5. 后端->前端 评论内容

    ```json
    {
        "user_id":"用户id",
        "user_name":"用户名",
        "time":"时间",
        "content":"正文内容"
    }
    ```

6. 前端->后端 发布评论

    ```json
    {
        "content":"评论内容"
    }
    ```
