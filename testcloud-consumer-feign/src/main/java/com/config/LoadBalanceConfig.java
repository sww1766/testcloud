package com.config;

import org.springframework.context.annotation.Bean;

import com.netflix.loadbalancer.IRule;
import com.netflix.loadbalancer.RandomRule;

public class LoadBalanceConfig {

	@Bean
	public IRule ribbonRule() {
		return new RandomRule();
	}
}
