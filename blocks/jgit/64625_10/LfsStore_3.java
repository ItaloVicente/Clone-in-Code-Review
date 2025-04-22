	@Option(name = "--region"
			metaVar = "metaVar_s3Region"

	@Option(name = "--bucket"
			metaVar = "metaVar_s3Bucket"

	@Option(name = "--storage-class"
			metaVar = "metaVar_s3StorageClass"
	StorageClass storageClass = StorageClass.REDUCED_REDUNDANCY;

	@Option(name = "--expire"
			metaVar = "metaVar_seconds"
	int expirationSeconds = 600;

	@Option(name = "--no-ssl-verify"
	boolean disableSslVerify = false;

