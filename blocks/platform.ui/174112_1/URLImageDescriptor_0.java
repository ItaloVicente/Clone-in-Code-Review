
		if (!JFaceResources.getString(JFacePreferences.CUSTOM_ICON_THEME).equals(JFacePreferences.CUSTOM_ICON_THEME)
				&& url.getFile().contains(PLUGIN_URL)) {

			String bundleID = url.getFile();
			for (int i = 0; i < 2; i++) {
				bundleID = bundleID.substring(bundleID.indexOf('/') + 1);

			}
			bundleID = bundleID.substring(0, bundleID.indexOf('/'));

			String fileName = url.getFile().substring(url.getFile().lastIndexOf('/'));

			try {

				URL themedIconURL = new URL(url.getProtocol(), url.getHost(), url.getPort(),
						PLUGIN_URL + bundleID + ICON_REPLACEMENTS_DIR + fileName);

				String found = getFilePath(themedIconURL, zoom == 100);
				if (found != null) {
					url = themedIconURL;
				}
			} catch (MalformedURLException e) {
				e.printStackTrace();
			}

		}


