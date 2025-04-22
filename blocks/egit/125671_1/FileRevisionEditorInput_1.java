
	@Override
	public IPath getPath() {
		if (tmpFile == null) {
			tmpFile = new IPath[] { writeTempFile() };
		}
		return tmpFile[0];
	}

	private IPath writeTempFile() {
		try {
			java.nio.file.Path file = Files.createTempDirectory("egit") //$NON-NLS-1$
					.resolve(getTempFileName());
			try (InputStream in = storage.getContents()) {
				Files.copy(in, file);
			}
			return new Path(file.toAbsolutePath().toString());
		} catch (CoreException | IOException e) {
			Activator.logError(MessageFormat.format(
					UIText.FileRevisionEditorInput_cannotWriteTempFile,
					storage.getName()), e);
		}
		return new Path(""); //$NON-NLS-1$
	}

	private String getTempFileName() {
		IFileRevision rev = AdapterUtils.adapt(this, IFileRevision.class);
		if (rev != null) {
			return rev.getContentIdentifier() + '_' + storage.getName();
		}
		IFileState state = AdapterUtils.adapt(this, IFileState.class);
		if (state != null) {
			return DATE_FORMAT.format(new Date(state.getModificationTime()))
					+ '_' + storage.getName();
		}
		return storage.getName();
	}
