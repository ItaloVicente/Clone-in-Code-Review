			String propertyStr = System.getProperty("e4.impExpPerspectiveEnabled"); //$NON-NLS-1$
			if (propertyStr == null) {
				impExpEnabled = true;
			} else {
				impExpEnabled = Boolean.parseBoolean(propertyStr);
			}
