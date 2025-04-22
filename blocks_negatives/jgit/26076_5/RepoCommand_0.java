					try {
						objectId = callback.sha1(uri);
					} catch (GitAPIException e) {
						throw new RemoteUnavailableException(uri, e);
					} catch (IllegalArgumentException e) {
						throw new ManifestErrorException(e);
