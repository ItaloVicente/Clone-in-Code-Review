	private void showSingleFileComparison(IFile file, String refName) {
		final ITypedElement base = SaveableCompareEditorInput
				.createFileElement(file);

		final ITypedElement next;
		try {
			RepositoryMapping mapping = RepositoryMapping.getMapping(file);
			next = getElementForRef(mapping.getRepository(),
					mapping.getRepoRelativePath(file), refName);
		} catch (IOException e) {
			Activator.handleError(
					UIText.CompareWithIndexAction_errorOnAddToIndex, e, true);
			return;
		}

		final GitCompareFileRevisionEditorInput in = new GitCompareFileRevisionEditorInput(
				base, next, null);
		in.getCompareConfiguration().setRightLabel(refName);
		CompareUI.openCompareEditor(in);
	}

	private void synchronizeModel(final IFile file, Repository repo,
			String refName) {
		try {
			final GitSynchronizeData data = new GitSynchronizeData(repo,
					Constants.HEAD, refName, true);
			final GitSynchronizeDataSet dataSet = new GitSynchronizeDataSet(
					data);

			final ResourceMapping[] mappings = ResourceUtil
					.getResourceMappings(file,
							ResourceMappingContext.LOCAL_CONTEXT);

			GitModelSynchronize.launch(dataSet, mappings);
		} catch (IOException e) {
			Activator.handleError(
					UIText.CompareWithRefAction_errorOnSynchronize, e, true);
			return;
		}
	}

