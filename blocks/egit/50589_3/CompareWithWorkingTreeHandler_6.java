			if (mapping != null) {
				final String gitPath = mapping.getRepoRelativePath(file);
				final String commitPath = getRenamedPath(gitPath, commit);
				ITypedElement right = CompareUtils.getFileRevisionTypedElement(
						commitPath, commit, mapping.getRepository());
				final GitCompareFileRevisionEditorInput in = new GitCompareFileRevisionEditorInput(
						SaveableCompareEditorInput.createFileElement(file),
						right, null);
				CompareUtils.openInCompare(workBenchPage, in);
			}
