				File additionalFile = null;
				if (isWindows()) {
					if (programData != null) {
						additionalFile = new File(new File(programData)
						if (additionalFile.isDirectory()) {
							additionalFile = new File(additionalFile
									Constants.CONFIG);
						}
					}
				}
