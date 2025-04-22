
		if (
		resultingURL.getFile().contains(PLUGIN_URL)) {

			String originalFilePath = resultingURL.getFile();
			for (int i = 0; i < 2; i++) {
				originalFilePath = originalFilePath.substring(originalFilePath.indexOf('/') + 1);

			}

			String bundleID = originalFilePath.substring(0, originalFilePath.indexOf('/'));
			originalFilePath = originalFilePath.substring(originalFilePath.indexOf('/'), originalFilePath.length());

			if (originalFilePath.contains("/$nl$/")) { //$NON-NLS-1$
				originalFilePath = originalFilePath.replace("/$nl$/", "/"); //$NON-NLS-1$ //$NON-NLS-2$
			}

			try {

				URL themedIconURL = new URL(resultingURL.getProtocol(), resultingURL.getHost(), resultingURL.getPort(),
						PLUGIN_URL + bundleID + THEME_REPLACEMENTS_DIR + originalFilePath);

				String found = getFilePath(themedIconURL, false);
				if (found != null) {
					resultingURL = themedIconURL;
				}
			} catch (MalformedURLException e) {
				Policy.getLog().log(new Status(IStatus.ERROR, Policy.JFACE, e.getLocalizedMessage(), e));
			}

		}

		return resultingURL;
