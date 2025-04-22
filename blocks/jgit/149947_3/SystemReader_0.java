				File additionalFile = null;
				if (isWindows()) {
					if (!StringUtils.isEmptyOrNull(programData)) {
						additionalFile = new File(new File(programData)
						if (additionalFile.isDirectory()) {
							additionalFile = new File(additionalFile
									Constants.CONFIG);
						}
					}
				}
