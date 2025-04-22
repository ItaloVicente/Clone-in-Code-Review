	public interface UrlSchemeHandler {
		public String getScheme();

		public URI getPlatformURI(URI uri);
	}

	private Map<String, UrlSchemeHandler> schemeHandler = new HashMap<>();

