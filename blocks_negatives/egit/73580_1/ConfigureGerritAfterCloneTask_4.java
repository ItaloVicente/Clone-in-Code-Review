			HttpURLConnection httpConnection = null;
			try {
				int slash = 1;
				while (true) {
					InputStream in = null;
					try {
						httpConnection = (HttpURLConnection) new URL(baseURL
								+ tmpPath + GERRIT_CONFIG_SERVER_VERSION_API)
								.openConnection();
						NetUtil.setSslVerification(repo, httpConnection);
						httpConnection.setReadTimeout(1000 * timeout);
						int responseCode = httpConnection.getResponseCode();
						switch (responseCode) {
						case HttpURLConnection.HTTP_OK:
							in = httpConnection.getInputStream();
