	void signRequest(final HttpURLConnection c
		if(amazonV4Signer != null) {
			amazonV4Signer.sign(c
		} else {
			authorize(c);
		}
	}

