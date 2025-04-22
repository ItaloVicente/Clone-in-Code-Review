		if (obj instanceof IAdaptable) {
			IAdaptable adapt = (IAdaptable) obj;
			URL url = getURLFromAdaptable(adapt);
			if (url!=null)
				setURL(url.toExternalForm());
		}
