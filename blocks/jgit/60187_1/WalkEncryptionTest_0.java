				URL url = new URL(service);
				BufferedReader reader = new BufferedReader(
						new InputStreamReader(url.openStream()));
				try {
					return reader.readLine();
				} finally {
					reader.close();
				}
			} catch (UnknownHostException e) {
						+ " determine public address";
