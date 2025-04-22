		InputStream result = null;
		try {
			result = getStream(new URL(url));
		} catch (MalformedURLException e) {
			Policy.getLog().log(new Status(IStatus.ERROR, Policy.JFACE, e.getLocalizedMessage(), e));
		}
		return result;
