
			boolean compareMode = Activator.getDefault().getPreferenceStore()
					.getBoolean(UIPreferences.TREE_COMPARE_COMPARE_MODE);

			if (compareMode) {
				left = getTypedElement(fileNode, fileNode.leftRevision,
						getBaseVersion());
				right = getTypedElement(fileNode, fileNode.rightRevision,
						getCompareVersion());

				GitCompareFileRevisionEditorInput compareInput = new GitCompareFileRevisionEditorInput(
						left, right, PlatformUI.getWorkbench()
								.getActiveWorkbenchWindow().getActivePage());
				CompareUtils.openInCompare(PlatformUI.getWorkbench()
						.getActiveWorkbenchWindow().getActivePage(),
						compareInput);
			} else {
				IFile file = fileNode.getFile();
				if (file != null)
					openFileInEditor(file.getLocation().toOSString());
			}
