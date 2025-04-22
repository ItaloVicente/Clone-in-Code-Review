package org.eclipse.jgit.lfs.server.s3;

public class S3Config {
	private final String region;
	private final String bucket;
	private final String storageClass;
	private final String accessKey;
	private final String secretKey;
	private final int expirationSeconds;
	private final boolean disableSslVerify;

	public S3Config(String region
			String accessKey
			boolean disableSslVerify) {
		this.region = region;
		this.bucket = bucket;
		this.storageClass = storageClass;
		this.accessKey = accessKey;
		this.secretKey = secretKey;
		this.expirationSeconds = expirationSeconds;
		this.disableSslVerify = disableSslVerify;
	}

	public String getRegion() {
		return region;
	}

	public String getBucket() {
		return bucket;
	}

	public String getStorageClass() {
		return storageClass;
	}

	public String getAccessKey() {
		return accessKey;
	}

	public String getSecretKey() {
		return secretKey;
	}

	public int getExpirationSeconds() {
		return expirationSeconds;
	}

	boolean isDisableSslVerify() {
		return disableSslVerify;
	}

}
