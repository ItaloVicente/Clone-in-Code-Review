
	private void throwExceptionOnIllegalScheme(String scheme) {
		try {
			new URI(scheme, "foo", ""); //$NON-NLS-1$ //$NON-NLS-2$
		} catch (URISyntaxException e1) {
		}
	}

}
