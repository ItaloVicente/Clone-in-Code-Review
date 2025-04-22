
					public IPath getFullPath() {
						return null;
					}

					public InputStream getContents() throws CoreException {
						return new ByteArrayInputStream(bytes);
					}

					public String getCharset() throws CoreException {
						IContentTypeManager manager = Platform
								.getContentTypeManager();
						try {
							IContentDescription description = manager
									.getDescriptionFor(getContents(),
											getName(), IContentDescription.ALL);
							return description == null ? null : description
									.getCharset();
						} catch (IOException e) {
							throw new CoreException(new Status(IStatus.ERROR,
									Activator.getPluginId(), e.getMessage(), e));
						}
					}
				};
			} catch (IOException e) {
				throw new TeamException(new Status(IStatus.ERROR, Activator
						.getPluginId(), e.getMessage(), e));
			}
