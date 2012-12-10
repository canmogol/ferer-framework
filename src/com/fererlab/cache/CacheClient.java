package com.fererlab.cache;

import java.util.List;
import java.util.Map;

public interface CacheClient {

	public void put(String key, int time, Object object);
		
	public void remove(String key);
	
	public void update(String key, int time, Object object);
	
	public Object get(String key);
	
	public Map<String, Object> getBulk(List<String> keys);
	
	public void shutdown();
	
}
