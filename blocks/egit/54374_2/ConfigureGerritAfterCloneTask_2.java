						HttpGet httpGet = new HttpGet(baseURL + tmpPath
								+ GERRIT_CONFIG_SERVER_VERSION_API);
						RequestConfig c = RequestConfig.custom()
								.setConnectTimeout(1000 * timeout)
								.setSocketTimeout(1000 * timeout).build();
						httpGet.setConfig(c);
						r = httpclient.execute(httpGet);
						switch (r.getStatusLine().getStatusCode()) {
