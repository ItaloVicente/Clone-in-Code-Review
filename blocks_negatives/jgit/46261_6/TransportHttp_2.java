			openStream(null);
		}

		void openStream(final String redirectUrl) throws IOException {
			conn = httpOpen(
					METHOD_POST,
					redirectUrl == null ? new URL(baseUrl, serviceName) : new URL(redirectUrl),
