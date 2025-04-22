				httpIn = new HttpInputStream(RSP_TYPE) {
					@Override
					HttpURLConnection call() throws IOException {
						httpOut.close();
						return conn;
					}
				};
