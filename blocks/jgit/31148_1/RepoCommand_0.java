				URI uri = new URI(defaultRemote);
				if (uri.getHost() != null) {
					remoteUrl = uri.toString();
				} else {
					uri = new URI(baseUrl);
					remoteUrl = uri.resolve(
							remotes.get(defaultRemote)).toString();
				}
