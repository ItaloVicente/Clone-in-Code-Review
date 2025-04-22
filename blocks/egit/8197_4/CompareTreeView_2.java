		} else if (selected instanceof FileNode) {
			FileNode fileNode = (FileNode) selected;
			left = getTypedElement(fileNode, fileNode.leftRevision, getBaseVersion());
			right = getTypedElement(fileNode, fileNode.rightRevision, getCompareVersion());

			GitCompareFileRevisionEditorInput compareInput = new GitCompareFileRevisionEditorInput(
					left, right, PlatformUI.getWorkbench()
							.getActiveWorkbenchWindow().getActivePage());
			CompareUtils.openInCompare(PlatformUI.getWorkbench()
					.getActiveWorkbenchWindow().getActivePage(), compareInput);
		}
	}
