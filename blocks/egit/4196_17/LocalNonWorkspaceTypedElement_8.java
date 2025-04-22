	@Override
	public boolean isEditable() {
		IResource resource = getResource();
		return resource.getType() == IResource.FILE && exists;
	}

	@Override
	public void update() {
		exists = getResource().getFullPath().toFile().exists();
	}

	@Override
	public boolean exists() {
		return exists;
	}

	@Override
	public boolean isSharedDocumentsEnable() {
		return useSharedDocument && getResource().getType() == IResource.FILE && exists;
	}

	@Override
	public void enableSharedDocument(boolean enablement) {
		this.useSharedDocument = enablement;
	}

	@Override
	public void setContent(byte[] contents) {
		fDirty = true;
		super.setContent(contents);
	}

	@Override
	public void commit(IProgressMonitor monitor) throws CoreException {
		if (isDirty()) {
			if (isConnected()) {
				super.commit(monitor);
			} else {
				IResource resource = getResource();
				if (resource instanceof IFile) {
					FileOutputStream out = null;
					File file = ((IFile) resource).getFullPath().toFile();
					try {
						if (!file.exists())
							file.createNewFile();
						out = new FileOutputStream(file);
						out.write(getContent());
						fDirty = false;
					} catch (FileNotFoundException e) {
						throw new CoreException(null);
					} catch (IOException e) {
						throw new CoreException(null);
					} finally {
						fireContentChanged();
						RepositoryMapping mapping = RepositoryMapping.getMapping(resource);
						if (mapping != null) {
							mapping.getRepository().fireEvent(new IndexChangedEvent());
						}
						if (out != null)
							try {
								out.close();
							} catch (IOException ex) {
							}
					}
				}
			}
		}
	}

	@Override
	public boolean isDirty() {
		return fDirty || (sharedDocumentAdapter != null && sharedDocumentAdapter.hasBufferedContents());
	}

	public Object getAdapter(Class adapter) {
		if (adapter == ISharedDocumentAdapter.class) {
			if (isSharedDocumentsEnable())
				return getSharedDocumentAdapter();
			else
				return null;
		}
		return Platform.getAdapterManager().getAdapter(this, adapter);
	}

