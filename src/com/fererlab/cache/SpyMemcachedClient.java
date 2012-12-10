package com.fererlab.cache;

import java.util.List;
import java.util.Map;

public class SpyMemcachedClient implements CacheClient{

	@Override
	public void put(String key, int time, Object object) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void remove(String key) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(String key, int time, Object object) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Object get(String key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, Object> getBulk(List<String> keys) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void shutdown() {
		// TODO Auto-generated method stub
		
	}

}
