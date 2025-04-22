		AmazonS3Client s3client = new AmazonS3Client(credentials);
		s3client.setRegion(region);
        Date expiration = new Date();
		long expiresAt = expiration.getTime() + 1000 * presignedUrlValidity;
		expiration.setTime(expiresAt);

		GeneratePresignedUrlRequest req = new GeneratePresignedUrlRequest(
				bucket, id.getName());
		req.setMethod(HttpMethod.GET);
		req.setExpiration(expiration);

		URL url = s3client.generatePresignedUrl(req);
		System.out.println("URL: " + url);
