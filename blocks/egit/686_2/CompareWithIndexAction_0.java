		final ITypedElement base = SaveableCompareEditorInput
				.createFileElement(baseFile);

		final ITypedElement next = getHeadTypedElement(baseFile);

		final GitCompareFileRevisionEditorInput in = new GitCompareFileRevisionEditorInput(
				base, next, null);
		CompareUI.openCompareEditor(in);
	}
