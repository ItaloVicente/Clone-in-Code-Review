
		public Transport open(URIish uri) throws NotSupportedException
				TransportException {
				File path = FS.DETECTED.resolve(new File(".")
				return new TransportBundleFile(uri
			}
			return TransportLocal.PROTO_LOCAL.open(uri);
		}
