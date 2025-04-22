		try (final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
				final DiffFormatter diffFmt = createDiffFormatter(
						outputStream, monitor)) {

			diffFmt.setContext(contextLines);
			final StringBuilder sb = new StringBuilder();
			if (headerFormat != null && headerFormat != DiffHeaderFormat.NONE) {
				writeGitPatchHeader(sb);
			}
			diffFmt.setRepository(repository);
			diffFmt.setPathFilter(pathFilter);

			if (commit != null) {
				List<DiffEntry> diffs = diffFmt.scan(getParentId(), commit.getId());
				for (DiffEntry ent : diffs) {
					String path;
					if (ChangeType.DELETE.equals(ent.getChangeType())) {
						path = ent.getOldPath();
					} else {
						path = ent.getNewPath();
					}
					currentEncoding = CompareCoreUtils
							.getResourceEncoding(repository, path);
					diffFmt.format(ent);
				}
			} else {
				diffFmt.format(new DirCacheIterator(repository.readDirCache()),
						new FileTreeIterator(repository));
			}
			diffFmt.flush();

			appendOutputStream(sb, outputStream);

			if (DiffHeaderFormat.WORKSPACE == headerFormat) {
				updateWorkspacePatchPrefixes(sb, diffFmt);
			}
			patchContent = sb.toString();
		} catch (IOException e) {
			Activator.logError(
					CoreText.CreatePatchOperation_patchFileCouldNotBeWritten,
					e);
		}
	}

	private DiffFormatter createDiffFormatter(
			final ByteArrayOutputStream outputStream,
			IProgressMonitor monitor) {
		DiffFormatter diffFmt = new DiffFormatter(outputStream) {
