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
