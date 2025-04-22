				try {
					final File e = new File(p
					if (e.isFile()) {
						return e.getAbsoluteFile();
					}
				} catch (SecurityException e) {
					LOG.warn("The path '{}' isn't accessible. Skip it"
				}
