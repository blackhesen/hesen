package com.hesen.model.qiniu;

import java.io.Serializable;

/**
 * Created by hesen on 2017-11-23
 */
class MyPutRet implements Serializable{
	
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public String key;
    public String hash;
    public String bucket;
    public long fsize;
    
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getHash() {
		return hash;
	}
	public void setHash(String hash) {
		this.hash = hash;
	}
	public String getBucket() {
		return bucket;
	}
	public void setBucket(String bucket) {
		this.bucket = bucket;
	}
	public long getFsize() {
		return fsize;
	}
	public void setFsize(long fsize) {
		this.fsize = fsize;
	}
}
