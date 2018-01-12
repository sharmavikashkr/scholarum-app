package com.scholarum.caching.setup;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.exceptions.JedisException;

@Service
public class RedisClient {

	private static final Logger logger = LoggerFactory.getLogger(RedisClient.class);

	@Autowired
	private JedisPool pool;

	public void addToCache(String hkey, String key, String value, int expiry) {
		Jedis jedis = pool.getResource();
		try {
			jedis.hset(hkey, key, value);
			jedis.expire(hkey, expiry);
		} catch (JedisException e) {
			logger.error("Exception while adding to redis : ", e);
		}
	}

	public String fetchFromCache(String hkey, String key) {
		Jedis jedis = pool.getResource();
		try {
			return jedis.hget(hkey, key);
		} catch (JedisException e) {
			logger.error("Exception while fetching from redis : ", e);
		}
		return null;
	}

	public void removeFromCache(String hkey, String key) {
		Jedis jedis = pool.getResource();
		try {
			jedis.hdel(hkey, key);
		} catch (JedisException e) {
			logger.error("Exception while deleting from redis : ", e);
		}
	}
}
