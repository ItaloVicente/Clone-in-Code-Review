	private static URL getURL(String urlString) {
		URL result = null;
		try {
			result = new URL(urlString);
		} catch (MalformedURLException e) {
			Policy.getLog().log(new Status(IStatus.ERROR, Policy.JFACE, e.getLocalizedMessage(), e));
		}
		return result;
	}
