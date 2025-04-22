		if (url != null) {
			try {
				if (InternalPolicy.OSGI_AVAILABLE) {
					URL platformURL = FileLocator.find(url);
					if (platformURL != null) {
						url = platformURL;
					}
