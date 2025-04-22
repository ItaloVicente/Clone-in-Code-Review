				try {
					lfsUrl = discoverLfsUrl(db
							remoteUrl);
				} catch (URISyntaxException | IOException
						| CommandFailedException e) {
					ex = e;
				}
