	protected static class LogEntry {

		private URIish uri;

		private List<CredentialItem> items;

		public LogEntry(URIish uri
			this.uri = uri;
			this.items = items;
		}

		public URIish getURIish() {
			return uri;
		}

		public List<CredentialItem> getItems() {
			return items;
		}
	}
