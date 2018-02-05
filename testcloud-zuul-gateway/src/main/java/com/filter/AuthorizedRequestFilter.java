package com.filter;

import com.netflix.zuul.ZuulFilter;
import org.springframework.stereotype.Component;

@Component
public class AuthorizedRequestFilter extends ZuulFilter {	// 进行授权访问处理

	@Override
	public Object run() {	// 表示具体的过滤执行操作
		return null;
	}

	@Override
	public boolean shouldFilter() {	// 该Filter是否要执行
		return true ;
	}

	@Override
	public int filterOrder() {
		return 0;	// 设置优先级，数字越大优先级越低
	}

	@Override
	public String filterType() {
		// 在进行Zuul过滤的时候可以设置其过滤执行的位置，那么此时有如下几种类型：
		// 1、pre：在请求发出之前执行过滤，如果要进行访问，肯定在请求前设置头信息
		// 2、route：在进行路由请求的时候被调用；
		// 3、post：在路由之后发送请求信息的时候被调用；
		// 4、error：出现错误之后进行调用
		return "pre";
	}

}
