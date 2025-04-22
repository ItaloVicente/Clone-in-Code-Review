			if (remoteUri == null) {
				try {
                    remoteUri = new URIish(remote).toString();
                    remote = remoteUri;
				} catch(URISyntaxException e) {
				}
			}
