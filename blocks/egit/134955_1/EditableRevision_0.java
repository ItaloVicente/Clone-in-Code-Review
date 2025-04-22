					@Override
					public String getToolTipText() {
						return EditableRevision.this.getName();
					}

					@Override
					public <T> T getAdapter(Class<T> adapter) {
						return null;
					}

					private IStorage storage;

					@Override
					public IStorage getStorage() throws CoreException {
						if (storage == null) {
							storage = new IEncodedStorage() {

								@Override
								public <T> T getAdapter(Class<T> adapter) {
									return null;
								}

								@Override
								public boolean isReadOnly() {
									return false;
								}

								@Override
								public String getName() {
									return EditableRevision.this.getName();
								}

								@Override
								public IPath getFullPath() {
									return null;
								}

								@Override
								public InputStream getContents()
										throws CoreException {
									return EditableRevision.this.getContents();
								}

								@Override
								public String getCharset()
										throws CoreException {
									return EditableRevision.this.getCharset();
								}
							};
