		@Deprecated
		public default byte[] readFile(String uri
				throws GitAPIException
			return readFileWithMode(uri
		}

		@NonNull
		public RemoteFile readFileWithMode(String uri
