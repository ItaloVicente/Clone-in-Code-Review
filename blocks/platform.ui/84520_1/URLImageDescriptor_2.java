		ImageData result = null;
		try {
			result = getImageData(new URL(url));
		} catch (MalformedURLException e) {
			Policy.getLog().log(new Status(IStatus.ERROR, Policy.JFACE, e.getLocalizedMessage(), e));
		}
		return result;
