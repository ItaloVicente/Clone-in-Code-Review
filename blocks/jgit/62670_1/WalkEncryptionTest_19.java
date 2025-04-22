				URL url = new URL(service);
				URLConnection c = url.openConnection();
				c.setConnectTimeout(500);
				c.setReadTimeout(500);
				BufferedReader reader = new BufferedReader(
						new InputStreamReader(c.getInputStream()));
				try {
					return reader.readLine();
				} finally {
					reader.close();
				}
			} catch (UnknownHostException | SocketTimeoutException e) {
						+ " determine public address";
