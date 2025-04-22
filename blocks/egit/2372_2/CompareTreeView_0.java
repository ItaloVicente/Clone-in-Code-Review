		} else if (selected instanceof PathNodeAdapter) {
			PathNodeAdapter node = (PathNodeAdapter) selected;
			GitFileRevision rightRevision = rightVersionMap
					.get(node.pathNode.path);
			right = new FileRevisionTypedElement(rightRevision);
			left = new GitCompareFileRevisionEditorInput.EmptyTypedElement(NLS
					.bind(UIText.CompareTreeView_ItemNotFoundInVersionMessage,
							node.pathNode.path.lastSegment(), getLeftVersion()));
