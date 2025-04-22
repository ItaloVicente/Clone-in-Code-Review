








	private static String getCanonicalizeHeaderNames(
			Map<String
		List<String> sortedHeaders = new ArrayList<String>();
		sortedHeaders.addAll(headers.keySet());
		Collections.sort(sortedHeaders

		StringBuilder buffer = new StringBuilder();
		for (String header : sortedHeaders) {
			if (buffer.length() > 0)
			buffer.append(header.toLowerCase());
		}

		return buffer.toString();
	}

	private static String getCanonicalizedHeaderString(
			Map<String
		if (headers == null || headers.isEmpty()) {
		}

		List<String> sortedHeaders = new ArrayList<String>();
		sortedHeaders.addAll(headers.keySet());
		Collections.sort(sortedHeaders

		StringBuilder buffer = new StringBuilder();
		for (String key : sortedHeaders) {
			buffer.append(key.toLowerCase().replaceAll("\\s+"
					+ headers.get(key).replaceAll("\\s+"
		}

		return buffer.toString();
	}

	private static String getCanonicalizedQueryString(
			Map<String
		if (parameters == null || parameters.isEmpty()) {
		}

		SortedMap<String

		Iterator<Map.Entry<String
				.iterator();
		while (pairs.hasNext()) {
			Map.Entry<String
			String key = pair.getKey();
			String value = pair.getValue();
			sorted.put(urlEncode(key
		}

		StringBuilder builder = new StringBuilder();
		pairs = sorted.entrySet().iterator();
		while (pairs.hasNext()) {
			Map.Entry<String
			builder.append(pair.getKey());
			builder.append(pair.getValue());
			if (pairs.hasNext()) {
			}
		}

		return builder.toString();
	}

	private static String getCanonicalRequest(URL endpoint
			String queryParameters
			String canonicalizedHeaders
		return String.format("%s\n%s\n%s\n%s\n%s\n%s"
				httpMethod
				queryParameters
				bodyHash);
	}

	private static String getCanonicalizedResourcePath(URL endpoint) {
		if (endpoint == null) {
		}
		String path = endpoint.getPath();
		if (path == null || path.isEmpty()) {
		}

		String encodedPath = urlEncode(path
			return encodedPath;
		} else {
		}
	}

	private static byte[] hash(String s) {
		MessageDigest md = Constants.newMessageDigest();
		md.update(s.getBytes(StandardCharsets.UTF_8));
		return md.digest();
	}

	private static byte[] sign(String stringData
		try {
			Mac mac = Mac.getInstance(HMACSHA256);
			mac.init(new SecretKeySpec(key
			return mac.doFinal(data);
		} catch (Exception e) {
			throw new RuntimeException(
					"Unable to calculate a request signature: "
							+ e.getMessage()
					e);
		}
	}


	private static String getStringToSign(String scheme
			String dateTime
		return String.format("%s-%s\n%s\n%s\n%s"
				scheme
				toHex(hash(canonicalRequest)));
	}

	private static String toHex(byte[] bytes) {
		StringBuilder builder = new StringBuilder(2 * bytes.length);
		for (byte b : bytes) {
			builder.append(HEX.charAt((b & 0xF0) >> 4));
			builder.append(HEX.charAt(b & 0xF));
		}
		return builder.toString();
	}

	private static String urlEncode(String url
		String encoded;
		try {
			encoded = URLEncoder.encode(url
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException("UTF-8 encoding is not supported."
		}
		if (keepPathSlash) {
			encoded = encoded.replace("%2F"
		}
		return encoded;
	}

	private String region;

	private String bucket;

	private int expirationSeconds;

	private String storageClass;

	private final String accessKey;

	private final String secretKey;

