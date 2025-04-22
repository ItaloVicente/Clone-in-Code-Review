		if (modifiedContent != null) {
			return new ByteArrayInputStream(modifiedContent);
		}
		return createStream();
	}

	@Override
	public void setContent(byte[] contents) {
		fDirty = true;
		modifiedContent = contents;
		fireContentChanged();
	}

	@Override
	public byte[] getContent() {
		if (modifiedContent == null) {
			try {
				InputStream is = createStream();
				modifiedContent = Utilities.readBytes(is);
			} catch (CoreException e) {
				Activator.handleStatus(e.getStatus(), false);
			}
		}
		return modifiedContent;
	}

	@Override
	public void discardBuffer() {
		super.discardBuffer();
		if (sharedDocumentAdapter != null) {
			sharedDocumentAdapter.releaseBuffer();
		}
		modifiedContent = null;
	}

	@Override
	protected InputStream createStream() throws CoreException {
