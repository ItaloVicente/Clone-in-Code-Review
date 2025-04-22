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
					try {
						return repository.open(id, Constants.OBJ_BLOB)
								.openStream();
					} catch (IOException err) {
						throw new TeamException(new Status(IStatus.ERROR,
								Activator.getPluginId(), err.getMessage(), err));
