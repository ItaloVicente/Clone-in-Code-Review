				try {
					final File e = new File(p
					if (e.isFile()) {
						return e.getAbsoluteFile();
					}
				} catch (SecurityException ignored) {
				}
