	private LocalResourceTypedElement createWithHiddenResource(URI uri,
			String name, Charset encoding) throws IOException {
		IFile tmp = createHiddenResource(uri, name, encoding);
		return new LocalResourceTypedElement(tmp);
	}

	private IFile createHiddenResource(URI uri, String name, Charset encoding)
			throws IOException {
		try {
			IFile tmp = HiddenResources.INSTANCE.createFile(uri, name, encoding,
					null);
			if (toDelete == null) {
				toDelete = new ArrayList<>();
			}
			toDelete.add(tmp);
			return tmp;
		} catch (CoreException e) {
			throw new IOException(e.getMessage(), e);
		}
	}

	private void updateIndexTimestamp(Repository repository, String gitPath) {
		DirCache cache = null;
		try {
			cache = repository.lockDirCache();
			DirCacheEditor editor = cache.editor();
			editor.add(new PathEdit(gitPath) {

				private boolean done;

				@Override
				public void apply(DirCacheEntry ent) {
					if (!done && ent.getStage() > 0) {
						ent.setLastModified(Instant.now());
						done = true;
					}
				}
			});
			editor.commit();
		} catch (IOException e) {
			Activator.logError(MessageFormat.format(
					UIText.GitMergeEditorInput_ErrorUpdatingIndex, gitPath), e);
		} finally {
			if (cache != null) {
				cache.unlock();
			}
		}
	}

