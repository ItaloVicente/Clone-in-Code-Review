				final File file = new File(p
				try {
					if (file.isFile()) {
						return file.getAbsoluteFile();
					}
				} catch (SecurityException e) {
					LOG.warn(MessageFormat.format(
							JGitText.get().skipNotAccessiblePath
							file.getPath()));
				}
