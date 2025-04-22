					HttpObjectDB d = new HttpObjectDB(objectsUrl);
					WalkFetchConnection wfc = new WalkFetchConnection(this, d);
					BufferedReader br = new BufferedReader(
							new InputStreamReader(in, Constants.CHARSET));
					try {
						wfc.available(d.readAdvertisedImpl(br));
					} finally {
						br.close();
					}
					return wfc;
