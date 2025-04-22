							try {
								current = factories.next().loadKeys(context)
										.iterator();
							} catch (IOException | GeneralSecurityException e) {
								throw new RuntimeException(e);
							}
