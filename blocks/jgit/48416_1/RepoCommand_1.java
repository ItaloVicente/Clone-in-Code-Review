		Project setUrl(String url) {
			this.url = url;
			return this;
		}

		Project setDefaultRevision(String defaultRevision) {
			this.defaultRevision = defaultRevision;
			return this;
		}

		String getRevision() {
			return revision == null ? defaultRevision : revision;
		}

