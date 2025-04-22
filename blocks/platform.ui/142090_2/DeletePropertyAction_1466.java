									Properties properties = new Properties();
									properties.load(propertiesFile.getContents());
									monitor.worked(1);

									properties.remove(data.getName());
									monitor.worked(1);

									ByteArrayOutputStream output = new ByteArrayOutputStream();
									properties.store(output, null);
									monitor.worked(1);

									propertiesFile.setContents(
														new ByteArrayInputStream(output.toByteArray()),
																IResource.FORCE | IResource.KEEP_HISTORY, monitor);
									monitor.worked(1);
