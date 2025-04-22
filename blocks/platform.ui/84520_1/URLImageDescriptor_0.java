			URL xUrl = null;
			try {
				xUrl = getxURL(new URL(url), zoom);
			} catch (MalformedURLException e) {
				Policy.getLog().log(new Status(IStatus.ERROR, Policy.JFACE, e.getLocalizedMessage(), e));
			}
			if (xUrl == null) {
