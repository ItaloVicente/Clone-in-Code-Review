			storage = new IEncodedStorage() {
				public Object getAdapter(Class adapter) {
					return null;
				}

				public boolean isReadOnly() {
					return true;
				}

				public String getName() {
					return GitBlobResourceVariant.this.getName();
				}

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
								.getDescriptionFor(getContents(), getName(),
										IContentDescription.ALL);
						return description == null ? null : description
								.getCharset();
					} catch (IOException e) {
						throw new CoreException(new Status(IStatus.ERROR,
								Activator.getPluginId(), e.getMessage(), e));
