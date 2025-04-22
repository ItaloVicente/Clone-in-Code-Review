				int indexOfSemicolon = ssp.indexOf(';');
				if (indexOfSemicolon < 0) {
					throw new IllegalArgumentException(
							NLS.bind(CoreText.GitURI_InvalidSCMURL,
									new String[] { uri.toString() }));
				}
