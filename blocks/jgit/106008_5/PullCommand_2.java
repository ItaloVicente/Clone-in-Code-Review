			if (remoteUri == null) {
				try {
					remoteUri = new URIish(remote).toString();
					remote = remoteUri;
					headRef = new RefSpec();
				} catch(URISyntaxException e) {
				}
			}
