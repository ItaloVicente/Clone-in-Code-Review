

	private static class AmazonV4Signer {

		private byte[] signingKey;
		private String signingKeyDate;
		private String scopeString;
		private String credentialString;

		AmazonV4Signer(String accessKey
			signingKey = createSigningKey(secretKey
			scopeString = createScopeString(signingKeyDate
			credentialString = accessKey + '/' + scopeString;
		}

		private static byte[] createSigningKey(String secretKey
			byte[] dateKey = hmacSha256("AWS4" + secretKey
			byte[] dateRegionKey = hmacSha256(dateKey
			byte[] dateRegionServiceKey = hmacSha256(dateRegionKey
			return hmacSha256(dateRegionServiceKey
		}

		private static String createScopeString(String dateString
			return String.format("%s/%s/%s/aws4_request"
		}

		private String buildAuthorizationHeader(List<String> signedHeaders
			Map<String
			authTokens.put("Credential"
			authTokens.put("SignedHeaders"
			authTokens.put("Signature"
			return "AWS4-HMAC-SHA256 " + joinStringMap(authTokens
		}

		private String calculateSignatureString(String stringToSign) {
			byte[] signature = hmacSha256(signingKey
			return bytesToHexString(signature);
		}

		private String createStringToSign(String canonicalRequest
			List<String> items = new ArrayList<>();
			items.add(timestamp);
			items.add(scopeString);
			items.add(bytesToHexString(sha256(utf8Encode(canonicalRequest))));
			return joinStringCollection(items
		}

		private Map<String
			Map<String
			for(final Map.Entry<String
				final String hdr = entry.getKey();
					headers.put(StringUtils.toLowerCase(hdr)
				}
			}

				headers.put("host"
			}
			return headers;
		}

		private String createCanonicalRequest(HttpURLConnection conn

			String httpMethod = conn.getRequestMethod();
			String canonicalUri = conn.getURL().getPath().replace("%2F"

			Map<String
			String canonicalQueryString = joinStringMap(sortMapByKey(params)

			Map<String

			String canonicalHeaders = joinStringMap(headers
			String signedHeaders = joinStringCollection(new ArrayList<>(headers.keySet())

			List<String> items = new ArrayList<>();
			items.add(httpMethod);
			items.add(canonicalUri);
			items.add(canonicalQueryString);
			items.add(canonicalHeaders);
			items.add(signedHeaders);
			items.add(payloadHash);
			return joinStringCollection(items
		}

		private static <K
			return new TreeMap<>(map);
		}

		private static Map<String
			String query = url.getQuery();
			if(query == null) {
				return Collections.emptyMap();
			}

			Map<String
			for(String pair: pairs) {
				int idx = pair.indexOf('=');
				queryPairs.put(pair.substring(0
			}
			return queryPairs;
		}

		private void sign(HttpURLConnection conn
			String payloadHash = bytesToHexString(sha256(payload));
			conn.addRequestProperty("x-amz-content-sha256"

			conn.addRequestProperty("x-amz-date"

			String canonicalRequest = createCanonicalRequest(conn
			String stringToSign = createStringToSign(canonicalRequest
			String signature = calculateSignatureString(stringToSign);

			List<String> signedHeaders = new ArrayList<>(sortMapByKey(getHeadersToSign(conn)).keySet());
			String authorization = buildAuthorizationHeader(signedHeaders
			conn.addRequestProperty("Authorization"
		}

		private static String joinStringCollection(Collection<String> collection
			StringBuilder sb = new StringBuilder();
			for(String s: collection) {
				sb.append(s).append(delimiter);
			}
			return sb.toString();
		}

		private static String joinStringMap(Map<String
			StringBuilder sb = new StringBuilder();
			for(Map.Entry<String
				sb.append(e.getKey());
				sb.append(keyValueDelimiter);
				sb.append(e.getValue());
				sb.append(pairDelimiter);
			}
			return sb.toString();
		}

		private static String timestampString(String format) {
			DateFormat dfm = new SimpleDateFormat(format);
			return dfm.format(new Date());
		}

		private static byte[] hmacSha256(String key
			return hmacSha256(utf8Encode(key)
		}

		private static byte[] hmacSha256(byte[] key
			return hmacSha256(key
		}

		private static byte[] hmacSha256(byte[] key
			try {
				mac.init(new SecretKeySpec(key
				return mac.doFinal(data);
			} catch(NoSuchAlgorithmException e) {
				throw new IllegalStateException("Hashing algorithm is not available"
			} catch(InvalidKeyException e) {
				throw new IllegalArgumentException(e);
			}
		}

		private static byte[] sha256(byte[] data) {
			try {
				md.update(data);
				return md.digest();
			} catch (NoSuchAlgorithmException e) {
				throw new IllegalStateException("Hashing algorithm is not available"
			}
		}

		private static byte[] utf8Encode(String s) {
			try {
			} catch(UnsupportedEncodingException e) {
						"and must be supported"
			}
		}

		private static String bytesToHexString(byte[] data) {
			StringBuilder sb = new StringBuilder();
			for (byte b : data) {
				sb.append(String.format("%02x"
			}
			return sb.toString();
		}

	}

