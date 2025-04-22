	}

	private String getPath(AnyLongObjectId oid) {
		return oid.getName();
	}

	private URL getObjectUrl(String region
		URL endpointUrl;
		try {
			endpointUrl = new URL(String.format(
		} catch (MalformedURLException e) {
			throw new RuntimeException(
					"Unable to parse service endpoint: " + e.getMessage());
		}
		return endpointUrl;
	}

	private String computeSignature(URL objectUrl
			String region
			Map<String

		SimpleDateFormat dateTimeFormat = new SimpleDateFormat(
				ISO8601_BASIC_FORMAT);
		dateTimeFormat.setTimeZone(new SimpleTimeZone(0
		Date now = new Date();
		String dateTimeStamp = dateTimeFormat.format(now);

		StringBuilder h = new StringBuilder(objectUrl.getHost());
		int port = objectUrl.getPort();
		if (port > -1) {
		}
		headers.put("Host"

		String canonicalizedHeaderNames = getCanonicalizeHeaderNames(headers);
		String canonicalizedHeaders = getCanonicalizedHeaderString(headers);

		SimpleDateFormat dateStampFormat = new SimpleDateFormat(
				DATE_STRING_FORMAT);
		dateStampFormat.setTimeZone(new SimpleTimeZone(0
		String dateStamp = dateStampFormat.format(now);
		String scope = String.format("%s/%s/%s/%s"
				TERMINATOR);

		queryParameters.put("X-Amz-Algorithm"
		queryParameters.put("X-Amz-Credential"

		queryParameters.put("X-Amz-Date"
		queryParameters.put("X-Amz-SignedHeaders"

		String canonicalizedQueryParameters = getCanonicalizedQueryString(
				queryParameters);

		String canonicalRequest = getCanonicalRequest(objectUrl
				canonicalizedQueryParameters
				canonicalizedHeaders

		String stringToSign = getStringToSign(SCHEME
				scope

		byte[] kSecret = (SCHEME + secretKey).getBytes();
		byte[] kDate = sign(dateStamp
		byte[] kRegion = sign(region
		byte[] kService = sign(S3
		byte[] kSigning = sign(TERMINATOR
		byte[] signature = sign(stringToSign

		StringBuilder s = new StringBuilder();
		return s.toString();
