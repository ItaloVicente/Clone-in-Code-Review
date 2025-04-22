	private static void openMergeToolInternal(CompareEditorInput input) {
		CompareUI.openCompareEditor(input);
	}

	private static void openMergeToolExternal(CompareEditorInput input) {
		final GitMergeEditorInput gitMergeInput = (GitMergeEditorInput) input;
		DiffContainerJob job = new DiffContainerJob(
				"Prepare filelist for external merge tools", gitMergeInput); //$NON-NLS-1$
		job.schedule();
		try {
			job.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		IDiffContainer diffCont = job.getDiffContainer();
		executeExternalToolForChildren(diffCont);
	}

	private static void executeExternalToolForChildren(
			IDiffContainer diffCont) {
		if (diffCont != null && diffCont.hasChildren()) {
			IDiffElement[] difContChilds = diffCont.getChildren();
			for (IDiffElement diffElement : difContChilds) {
				switch (diffElement.getKind()) {
				case Differencer.NO_CHANGE:
					executeExternalToolForChildren(
							(IDiffContainer) diffElement);
					break;
				case Differencer.PSEUDO_CONFLICT:
					break;
				case Differencer.CONFLICTING:
				}
			}
		}
	}

