						Ref src = refs.get(line.substring(5));
						if (src != null) {
							refs.put(Constants.HEAD, new Ref(
									Ref.Storage.NETWORK, Constants.HEAD, src
											.getName(), src.getObjectId()));
						}
