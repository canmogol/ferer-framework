package com.fererlab.cache;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import net.rubyeye.xmemcached.MemcachedClient;
import net.rubyeye.xmemcached.XMemcachedClientBuilder;
import net.rubyeye.xmemcached.command.BinaryCommandFactory;
import net.rubyeye.xmemcached.transcoders.SerializingTranscoder;
import net.rubyeye.xmemcached.utils.AddrUtil;


public class XMemcachedClient implements CacheClient{

	private String cacheKeyPrefix;
	private MemcachedClient memcachedClient;
	
	public XMemcachedClient(String memcachedAddress, String cacheKeyPrefix) {
		super();
		this.cacheKeyPrefix = cacheKeyPrefix;
		
		if (this.memcachedClient == null) {

			XMemcachedClientBuilder xMemcachedClientBuilder = new XMemcachedClientBuilder(AddrUtil.getAddresses(memcachedAddress));
			try {
				
				xMemcachedClientBuilder.setCommandFactory(new BinaryCommandFactory());
				this.memcachedClient = xMemcachedClientBuilder.build();
				this.memcachedClient.setOpTimeout(5000L);
				this.memcachedClient.setTranscoder(new SerializingTranscoder(10485760));
				this.memcachedClient.setConnectTimeout(15000L);
				
			} catch (IOException e) {
				// TODO : handle or log exception
				throw new RuntimeException(e);
			}
		}
	}

	@Override
	public void put(String key, int time, Object object) {
		try {
			getMemcachedClient().add(cacheKeyPrefix + "_" + key, time, object);
		} catch (Exception e) {
			// TODO : handle or log exception
		}
	}

	@Override
	public void remove(String key) {
		try {
			getMemcachedClient().delete(cacheKeyPrefix + "_" + key);
		} catch (Exception e) {
			// TODO : handle or log exception
		}
	}

	@Override
	public void update(String key, int time, Object object) {
		try {
			getMemcachedClient().replace(cacheKeyPrefix + "_" + key, time, object);
		} catch (Exception e) {
			// TODO : handle or log exception
		}
	}

	@Override
	public Object get(String key) {
		
		Object object = null;
		try {
			object = getMemcachedClient().get(cacheKeyPrefix + "_" + key);
		} catch (Exception e) {
			// TODO : handle or log exception
		}
		
		return object;
	}

	@Override
	public Map<String, Object> getBulk(List<String> keys) {
		
		Map<String, Object> objectMap = null;

		try {
			objectMap = getMemcachedClient().get(cacheKeyPrefix + "_" + keys);
		} catch (Exception e) {
			// TODO : handle or log exception
		}
		
		return objectMap;
	}

	@Override
	public void shutdown() {
		try {
			getMemcachedClient().shutdown();
		} catch (IOException e) {
			// TODO : handle or log exception
			throw new RuntimeException(e);
		}
	}

	protected MemcachedClient getMemcachedClient() {
		return memcachedClient;
	}
	
}
