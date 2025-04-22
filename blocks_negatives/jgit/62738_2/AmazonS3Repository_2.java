	public Response.Action getUploadAction(AnyLongObjectId id) {
		AmazonS3Client s3client = new AmazonS3Client(credentials);
		s3client.setRegion(region);
        Date expiration = new Date();
		long expiresAt = expiration.getTime() + 1000 * presignedUrlValidity;
		expiration.setTime(expiresAt);

		GeneratePresignedUrlRequest req = new GeneratePresignedUrlRequest(
				bucket, id.getName());
		req.setMethod(HttpMethod.PUT);
		req.setExpiration(expiration);

		URL url = s3client.generatePresignedUrl(req);
		Response.Action a = new Response.Action();
		a.href = url.toString();
		return a;
