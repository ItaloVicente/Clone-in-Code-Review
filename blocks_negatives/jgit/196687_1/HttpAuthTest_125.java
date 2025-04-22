	public void testHttpAuthScanResponse() {
		checkResponse(new String[] { basicHeader }, BASIC);
		checkResponse(new String[] { digestHeader }, DIGEST);
		checkResponse(new String[] { negotiateHeader }, NEGOTIATE);
		checkResponse(new String[] { basicHeader, digestHeader }, DIGEST);
		checkResponse(new String[] { digestHeader, basicHeader }, DIGEST);
		checkResponse(new String[] { digestHeader, negotiateHeader }, NEGOTIATE);
		checkResponse(new String[] { negotiateHeader, digestHeader }, NEGOTIATE);
		checkResponse(new String[] { ntlmHeader, basicHeader, digestHeader,
				bearerHeader }, DIGEST);
		checkResponse(new String[] { ntlmHeader, basicHeader, bearerHeader },
