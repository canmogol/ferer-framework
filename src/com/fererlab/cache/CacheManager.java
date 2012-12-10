package com.fererlab.cache;


public class CacheManager {

	private static final String CACHE_CLIENT_XMEMCACHED = "xmemcached";
	private static final String CACHE_CLIENT_SPYMEMCACHED = "spymemcached";
	
	private static CacheManager cacheManager;
	private String cacheKeyPrefix = "";
	private String cacheClientType;
	private String memcachedAddress;

	private CacheClient cacheClient;

	private CacheManager(String memcachedAddress, String cacheKeyPrefix, String cacheClientType) {
		this.memcachedAddress = memcachedAddress;
		this.cacheKeyPrefix = cacheKeyPrefix;
		this.cacheClientType = cacheClientType;
	}

	public static synchronized CacheManager createInstance(String memcachedAddress, String cacheKeyPrefix, String cacheClientType) {

		if (cacheManager == null) {
			cacheManager = new CacheManager(memcachedAddress, cacheKeyPrefix, cacheClientType);
			cacheManager.getMemcachedClient();
		}

		return cacheManager;
	}

	private CacheClient getMemcachedClient() {

		if (this.cacheClient == null) {
			
			if(this.cacheClientType.equals(CACHE_CLIENT_XMEMCACHED)) {
				this.cacheClient = new XMemcachedClient(this.memcachedAddress, this.cacheKeyPrefix);
			} else if(this.cacheClientType.equals(CACHE_CLIENT_SPYMEMCACHED)) {
				this.cacheClient = new SpyMemcachedClient();
			}
		}

		return this.cacheClient;
	}

}
