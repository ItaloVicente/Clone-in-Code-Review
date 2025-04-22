				URL resource = Platform.getBundle(plugin.getPlugin())
						.getResource(".options"); //$NON-NLS-1$
				if (resource != null) {
					InputStream is = resource.openStream();
					try {
						props.load(is);
					} finally {
						is.close();
					}
				}
