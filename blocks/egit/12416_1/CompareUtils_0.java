	private static void setIndexEntryContents(final Repository repository,
			final String gitPath, final byte[] newContent) {
		DirCache cache = null;
		try {
			cache = repository.lockDirCache();
			DirCacheEditor editor = cache.editor();
			if (newContent.length == 0) {
				editor.add(new DirCacheEditor.DeletePath(gitPath));
			} else {
				int length;
				byte[] content;
				WorkingTreeOptions workingTreeOptions = repository.getConfig()
						.get(WorkingTreeOptions.KEY);
				AutoCRLF autoCRLF = workingTreeOptions.getAutoCRLF();
				switch (autoCRLF) {
				case FALSE:
					content = newContent;
					length = newContent.length;
					break;
				case INPUT:
				case TRUE:
					EolCanonicalizingInputStream in = new EolCanonicalizingInputStream(
							new ByteArrayInputStream(newContent), true);
					content = new byte[newContent.length];
					length = IO.readFully(in, content, 0);
					break;
				default:
					throw new IllegalArgumentException(
							"Unknown autocrlf option " + autoCRLF); //$NON-NLS-1$
				}

				editor.add(new DirCacheEntryEditor(gitPath, repository,
						content, length));
			}
			try {
				editor.commit();
			} catch (RuntimeException e) {
				if (e.getCause() instanceof IOException)
					throw (IOException) e.getCause();
				else
					throw e;
			}

		} catch (IOException e) {
			Activator.handleError(
					UIText.CompareWithIndexAction_errorOnAddToIndex, e, true);
		} finally {
			if (cache != null)
				cache.unlock();
		}
	}

