	private ITypedElement getTypedElement(FileNode node, IFileRevision fileRevision, String versionName) {
		if (fileRevision instanceof LocalFileRevision) {
			LocalFileRevision localFileRevision = (LocalFileRevision) fileRevision;
			return new ResourceEditableRevision(fileRevision, localFileRevision.getFile(), PlatformUI.getWorkbench().getProgressService());
		} else if (fileRevision == null) {
			return new GitCompareFileRevisionEditorInput.EmptyTypedElement(
					NLS.bind(
							UIText.CompareTreeView_ItemNotFoundInVersionMessage,
							node.getPath(), versionName));
		} else {
			Repository repository = getRepository();
			String encoding = repository == null ? null
					: CompareCoreUtils.getResourceEncoding(repository,
							node.getRepoRelativePath());
			return new FileRevisionTypedElement(fileRevision, encoding);
		}
