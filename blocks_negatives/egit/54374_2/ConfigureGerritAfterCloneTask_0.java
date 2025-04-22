						httpConnection = (HttpURLConnection) new URL(baseURL
								+ tmpPath + GERRIT_CONFIG_SERVER_VERSION_API)
								.openConnection();
						NetUtil.setSslVerification(repo, httpConnection);
						httpConnection.setReadTimeout(1000 * timeout);
						int responseCode = httpConnection.getResponseCode();
						switch (responseCode) {
