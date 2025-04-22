				final File file = new File(p
				try {
					if (file.isFile()) {
						return file.getAbsoluteFile();
					}
				} catch (SecurityException e) {
					LOG.warn("The path '{}' isn't accessible. Skip it"
				}
