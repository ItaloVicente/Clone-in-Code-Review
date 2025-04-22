				IStructuredSelection selection = getStructuredSelection();
				if (selection == null || selection.isEmpty()) {
					return;
				}
				List<FileDiff> diffs = new ArrayList<>();
				Iterator<?> items = selection.iterator();
				while (items.hasNext()) {
					diffs.add((FileDiff) items.next());
				}
				if (diffs.size() == 1) {
					showWorkingDirectoryFileDiff(diffs.get(0));
				} else if (!diffs.isEmpty()) {
					FileDiff first = diffs.get(0);
					Repository repository = first.getRepository();
					IPath workingTree = Path.fromOSString(
							repository.getWorkTree().getAbsolutePath());
					List<IPath> paths = diffs.stream().map(FileDiff::getPath)
							.map(workingTree::append)
							.collect(Collectors.toList());
					GitCompareEditorInput comparison = new GitCompareEditorInput(
							null, first.getCommit().name(), repository,
							paths.toArray(new IPath[0]));
					CompareUtils.openInCompare(
							CommitFileDiffViewer.this.site.getPage(),
							comparison);
				}
