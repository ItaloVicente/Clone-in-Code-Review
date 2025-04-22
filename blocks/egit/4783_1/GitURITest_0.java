
	protected static class ScmUrlImportDescription {
		private String url;
		private String project;
		private HashMap properties;

		public ScmUrlImportDescription(String url, String project) {
			this.url = url;
			this.project = project;
		}

		public String getProject() {
			return project;
		}

		public String getUrl() {
			return url;
		}

		public URI getUri() {
			return URI.create(url.replaceAll("\"", "")); //$NON-NLS-1$//$NON-NLS-2$
		}

		public void setUrl(String url) {
			this.url = url;
		}

		public synchronized void setProperty(String key, Object value) {
			if (properties == null) {
				properties = new HashMap();
			}
			if (value == null) {
				properties.remove(key);
			} else {
				properties.put(key, value);
			}

		}

		public synchronized Object getProperty(String key) {
			if (properties == null) {
				return null;
			}
			return properties.get(key);
		}
	}

