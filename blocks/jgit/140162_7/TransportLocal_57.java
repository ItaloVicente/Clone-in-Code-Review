		public boolean canHandle(URIish uri
				String remoteName) {
			if (uri.getPath() == null || uri.getPort() > 0
					|| uri.getUser() != null || uri.getPass() != null
					|| uri.getHost() != null || (uri.getScheme() != null
							&& !getSchemes().contains(uri.getScheme())))
