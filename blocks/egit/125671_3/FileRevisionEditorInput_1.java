
	@Override
	public IPath getPath() {
		if (tmpFile == null) {
			tmpFile = writeTempFile();
		}
		return tmpFile;
	}

	private IPath writeTempFile() {
		java.nio.file.Path path;
		try {
			String tempName = getRevisionPrefix() + storage.getName();
			if (tempName.length() > 200) {
				tempName = storage.getName();
			}
			path = Files.createTempDirectory("egit") //$NON-NLS-1$
					.resolve(tempName);
			try (InputStream in = storage.getContents()) {
				Files.copy(in, path);
			}
			path = path.toAbsolutePath();
		} catch (CoreException | IOException e) {
			Activator.logError(MessageFormat.format(
					UIText.FileRevisionEditorInput_cannotWriteTempFile,
					storage.getName()), e);
			return new Path(""); //$NON-NLS-1$
		}
		File file = path.toFile();
		file.setReadOnly();
		Runtime.getRuntime().addShutdownHook(new Thread(() -> {
			if (file.setWritable(true) && file.delete()) {
				file.getParentFile().delete();
			} else {
				file.setReadOnly();
			}
		}));
		return new Path(path.toString());
	}

	private String getRevisionPrefix() {
		IFileRevision rev = AdapterUtils.adapt(this, IFileRevision.class);
		if (rev != null) {
			return rev.getContentIdentifier() + '_';
		}
		IFileState state = AdapterUtils.adapt(this, IFileState.class);
		if (state != null) {
			return DATE_FORMAT.format(new Date(state.getModificationTime()))
					+ '_';
		}
		return ""; //$NON-NLS-1$
	}
