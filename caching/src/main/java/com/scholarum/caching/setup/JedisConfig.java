package com.scholarum.caching.setup;

import java.net.URI;
import java.net.URISyntaxException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.Protocol;

@Configuration
public class JedisConfig {

	@Value("${jedis.pool.host}")
	private String jedisHost;

	@Value("${jedis.pool.max.idle}")
	private String maxIdle;

	@Value("${jedis.pool.max.wait}")
	private String maxWait;

	public @Bean JedisPool getJedisPool() {
		try {
			JedisPoolConfig config = new JedisPoolConfig();
			config.setMaxIdle(Integer.valueOf(maxIdle));
			config.setMinIdle(1);
			config.setMaxWaitMillis(Integer.valueOf(maxWait));
			config.setNumTestsPerEvictionRun(3);
			config.setTestOnBorrow(true);
			config.setTestOnReturn(true);
			config.setTestWhileIdle(true);
			config.setTimeBetweenEvictionRunsMillis(30000);
			URI jedisURI = new URI(jedisHost);
			return new JedisPool(config, jedisURI.getHost(), jedisURI.getPort(), Protocol.DEFAULT_TIMEOUT, null);
		} catch (URISyntaxException e) {
			throw new RuntimeException("Redis couldn't be configured:" + jedisHost);
		}
	}

}
