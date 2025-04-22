			return;
		} else if (selected instanceof IFile) {
			final IFile res = (IFile) selected;
			LocalFileRevision revision = new LocalFileRevision(res);
			left = new FileEditableRevision(revision, res, PlatformUI.getWorkbench().getProgressService());
			GitFileRevision rightRevision = compareVersionMap.get(new Path(
					repositoryMapping.getRepoRelativePath(res)));
			if (rightRevision == null) {
				right = new GitCompareFileRevisionEditorInput.EmptyTypedElement(
						NLS
								.bind(
										UIText.CompareTreeView_ItemNotFoundInVersionMessage,
										res.getName(), getCompareVersion()));
			} else {
				String encoding = CompareCoreUtils.getResourceEncoding(res);
				right = new FileRevisionTypedElement(rightRevision, encoding);
			}
		} else if (selected instanceof GitFileRevision) {
			GitFileRevision rightRevision = (GitFileRevision) selected;
			left = new GitCompareFileRevisionEditorInput.EmptyTypedElement(NLS
					.bind(UIText.CompareTreeView_ItemNotFoundInVersionMessage,
							rightRevision.getName(), getBaseVersion()));
			right = new FileRevisionTypedElement(rightRevision, null);
		} else if (selected instanceof PathNode) {
			PathNode node = (PathNode) selected;
			String encoding = CompareCoreUtils.getResourceEncoding(
					getRepository(), node.getRepoRelativePath());
			switch (node.type) {
			case FILE_BOTH_SIDES_DIFFER:
			case FILE_BOTH_SIDES_SAME: {
				GitFileRevision rightRevision = compareVersionMap
						.get(node.path);
				right = new FileRevisionTypedElement(rightRevision, encoding);
				GitFileRevision leftRevision = baseVersionMap.get(node.path);
				left = new FileRevisionTypedElement(leftRevision, encoding);
				break;
			}
			case FILE_DELETED: {
				GitFileRevision rightRevision = compareVersionMap
						.get(node.path);
				right = new FileRevisionTypedElement(rightRevision, encoding);
				left = new GitCompareFileRevisionEditorInput.EmptyTypedElement(
						NLS
								.bind(
										UIText.CompareTreeView_ItemNotFoundInVersionMessage,
										rightRevision.getName(),
										getBaseVersion()));
				break;
			}
			case FILE_ADDED: {
				GitFileRevision leftRevision = baseVersionMap.get(node.path);
				left = new FileRevisionTypedElement(leftRevision, encoding);
				right = new GitCompareFileRevisionEditorInput.EmptyTypedElement(
						NLS
								.bind(
										UIText.CompareTreeView_ItemNotFoundInVersionMessage,
										leftRevision.getName(),
										getCompareVersion()));
				break;
			}
			case FOLDER:
				TreeViewer tv = (TreeViewer) event.getViewer();
				tv.setExpandedState(selected, !tv.getExpandedState(selected));
				return;
			default:
				return;
			}

		} else if (selected instanceof PathNodeAdapter) {
			PathNodeAdapter node = (PathNodeAdapter) selected;
			GitFileRevision rightRevision = compareVersionMap
					.get(node.pathNode.path);
			String encoding = CompareCoreUtils.getResourceEncoding(
					getRepository(), node.pathNode.getRepoRelativePath());
			right = new FileRevisionTypedElement(rightRevision, encoding);
			left = new GitCompareFileRevisionEditorInput.EmptyTypedElement(NLS
					.bind(UIText.CompareTreeView_ItemNotFoundInVersionMessage,
							node.pathNode.path.lastSegment(), getBaseVersion()));
		} else
			return;
