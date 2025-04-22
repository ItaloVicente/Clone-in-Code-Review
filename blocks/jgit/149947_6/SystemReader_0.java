				File additionalFile = null;
				if (isWindows()) {
					if (!StringUtils.isEmptyOrNull(programData)) {
						File directory = new File(new File(programData)
						if (directory.isDirectory()) {
							additionalFile = new File(directory
									Constants.CONFIG);
						}
					}
				}
