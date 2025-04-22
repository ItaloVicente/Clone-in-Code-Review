			int slash = 1;
			while (true) {
				HttpURLConnection httpConnection = null;
				try {
					httpConnection = (HttpURLConnection) new URL(baseURL
							+ tmpPath + GERRIT_CONFIG_SERVER_VERSION_API)
									.openConnection();
					NetUtil.setSslVerification(repo, httpConnection);
					httpConnection.setRequestMethod("GET"); //$NON-NLS-1$
					httpConnection.setReadTimeout(1000 * timeout);
					int responseCode = httpConnection.getResponseCode();
					switch (responseCode) {
					case HttpURLConnection.HTTP_OK:
						try (InputStream in = httpConnection.getInputStream()) {
