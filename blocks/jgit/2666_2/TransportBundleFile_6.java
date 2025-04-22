		public Set<String> getSchemes() {
			return schemeSet;
		}

		@Override
		public boolean canHandle(Repository local
			if (uri.getPath() == null
					|| uri.getPort() > 0
					|| uri.getUser() != null
					|| uri.getPass() != null
					|| uri.getHost() != null
					|| (uri.getScheme() != null && !getSchemes().contains(uri.getScheme())))
				return false;
			return true;
		}

		@Override
		public Transport open(Repository local
				throws NotSupportedException
			if ("bundle".equals(uri.getScheme())) {
				File path = local.getFS().resolve(new File(".")
				return new TransportBundleFile(local
			}

			return TransportLocal.PROTO_LOCAL.open(local
		}
	};
