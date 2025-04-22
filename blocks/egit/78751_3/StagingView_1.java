	private void setStagingViewerInput(TreeViewer stagingViewer,
			StagingViewUpdate newInput, Object[] previous,
			Set<IPath> additionalPaths) {
		final Tree tree = stagingViewer.getTree();
		tree.setRedraw(false);
		Cursor oldCursor = tree.getCursor();
		tree.setCursor(tree.getDisplay().getSystemCursor(SWT.CURSOR_WAIT));

		try {
			TreeItem topItem = tree.getTopItem();
			final Set<Object> precedingObjects = new LinkedHashSet<>();
			if (topItem != null) {
				new TreeItemVisitor(tree.getItems()) {
					@Override
					public boolean visit(TreeItem treeItem) {
						precedingObjects.add(treeItem.getData());
						return true;
					}
				}.traverse(topItem);
				precedingObjects.remove(null);
			}

			boolean preserveTop = true;
			boolean keepSelectionVisible = false;
			StagingViewUpdate oldInput = (StagingViewUpdate) stagingViewer
					.getInput();
			if (oldInput != null && oldInput.repository == oldInput.repository
					&& oldInput.indexDiff != null) {
				StagingViewContentProvider contentProvider = getContentProvider(
						stagingViewer);
				ViewerComparator comparator = stagingViewer.getComparator();
				Map<String, Object> oldPaths = buildElementMap(stagingViewer,
						contentProvider, comparator);

				stagingViewer.setInput(newInput);
				if (previous != null) {
					expandPreviousExpandedAndPaths(previous, stagingViewer,
							additionalPaths);
				}

				StagingViewerUpdate stagingViewerUpdate = updateSelection(
						stagingViewer,
						contentProvider, oldPaths, buildElementMap(
								stagingViewer, contentProvider, comparator));

				if (stagingViewerUpdate == StagingViewerUpdate.REMOVED) {
						keepSelectionVisible = true;
				} else if (stagingViewerUpdate == StagingViewerUpdate.ADDED) {
					preserveTop = false;
				}
			} else {
				stagingViewer.setInput(newInput);
				if (previous != null) {
					expandPreviousExpandedAndPaths(previous, stagingViewer,
							additionalPaths);
				}
			}

			if (preserveTop) {
				TreeItem[] selection = tree.getSelection();
				TreeItem initialItem = keepSelectionVisible
						&& selection.length > 0 ? selection[0] : null;
				new TreeItemVisitor(tree.getItems()) {
					@Override
					public boolean visit(TreeItem treeItem) {
						if (precedingObjects.contains(treeItem.getData())) {
							tree.setTopItem(treeItem);
							return false;
						}
						return true;
					}
				}.traverse(initialItem);
			}
		} finally {
			tree.setRedraw(true);
			tree.setCursor(oldCursor);
		}
	}

	private static Map<String, Object> buildElementMap(TreeViewer stagingViewer,
			StagingViewContentProvider contentProvider,
			ViewerComparator comparator) {
		Map<String, Object> result = new LinkedHashMap<>();
		Object[] elements = contentProvider.getElements(null);
		comparator.sort(stagingViewer, elements);
		for (Object element : elements) {
			visitElement(stagingViewer, contentProvider, comparator, element,
					result);
		}
		return result;
	}

	private static boolean visitElement(TreeViewer stagingViewer,
			StagingViewContentProvider contentProvider,
			ViewerComparator comparator,
			Object element, Map<String, Object> paths) {
		if (element instanceof StagingEntry) {
			StagingEntry stagingEntry = (StagingEntry) element;
			if (contentProvider.isInFilter(stagingEntry)) {
				String path = stagingEntry.getPath();
				paths.put(path, stagingEntry);
				return true;
			}

			return false;
		}

		if (element instanceof StagingFolderEntry) {
			StagingFolderEntry stagingFolderEntry = (StagingFolderEntry) element;
			Object[] children = contentProvider.getChildren(stagingFolderEntry);
			comparator.sort(stagingViewer, children);

			IPath path = stagingFolderEntry.getPath();
			String pathString = path.toString();
			paths.put(pathString, stagingFolderEntry);

			boolean hasVisibleChildren = false;
			for (Object child : children) {
				if (visitElement(stagingViewer, contentProvider, comparator,
						child, paths)) {
					hasVisibleChildren = true;
				}
			}

			if (hasVisibleChildren) {
				return true;
			}

			paths.remove(pathString);
			return false;
		}

		return false;
	}

	private enum StagingViewerUpdate {
		ADDED, REMOVED, UNCHANGED
	}

	private static StagingViewerUpdate updateSelection(TreeViewer stagingViewer,
			StagingViewContentProvider contentProvider,
			Map<String, Object> oldPaths, Map<String, Object> newPaths) {
		Map<String, Object> addedPaths = new LinkedHashMap<>(newPaths);
		addedPaths.keySet().removeAll(oldPaths.keySet());
		if (!addedPaths.isEmpty()) {
			List<Object> newSelection = new ArrayList<>();
			Set<Object> elements = new LinkedHashSet<>(addedPaths.values());
			Set<Object> excludeChildren = new LinkedHashSet<>();
			for (Object element : elements) {
				if (element instanceof StagingEntry) {
					StagingEntry stagingEntry = (StagingEntry) element;
					if (!excludeChildren.contains(stagingEntry.getParent())) {
						newSelection.add(stagingEntry);
					}
				} else if (element instanceof StagingFolderEntry) {
					StagingFolderEntry stagingFolderEntry = (StagingFolderEntry) element;
					StagingFolderEntry parent = stagingFolderEntry.getParent();
					if (excludeChildren.contains(parent)) {
						excludeChildren.add(stagingFolderEntry);
					} else if (elements.containsAll(contentProvider
							.getStagingEntriesFiltered(stagingFolderEntry))) {
						newSelection.add(stagingFolderEntry);
						excludeChildren.add(stagingFolderEntry);
					}
				}
			}

			stagingViewer.setSelection(new StructuredSelection(newSelection),
					true);
			return StagingViewerUpdate.ADDED;
		} else {
			Map<String, Object> removedPaths = new LinkedHashMap<>(oldPaths);
			removedPaths.keySet().removeAll(newPaths.keySet());
			if (!removedPaths.isEmpty()) {
				Collection<Object> removedElements = removedPaths.values();
				Object firstRemovedElement = removedElements.iterator()
						.next();
				Object parent = contentProvider.getParent(firstRemovedElement);
				Object candidate = null;
				boolean visitSubsequentSiblings = false;
				for (Object oldElement : oldPaths.values()) {
					if (oldElement == firstRemovedElement) {
						if (candidate != null && contentProvider
								.getParent(candidate) == parent) {
							break;
						}

						visitSubsequentSiblings = true;
					}

					if (visitSubsequentSiblings) {
						if (!removedElements.contains(oldElement)
								&& contentProvider
										.getParent(oldElement) == parent) {
							candidate = oldElement;
							break;
						}
					} else if (candidate == null || oldElement == parent
							|| contentProvider
									.getParent(oldElement) == parent) {
						candidate = oldElement;
					}
				}

				if (candidate == null && !newPaths.isEmpty()) {
					candidate = newPaths.values().iterator().next();
				}

				if (candidate != null) {
					stagingViewer.setSelection(
							new StructuredSelection(candidate),
							true);
					return StagingViewerUpdate.REMOVED;
				}
			}

			return StagingViewerUpdate.UNCHANGED;
		}
	}

	private static abstract class TreeItemVisitor {
		private final TreeItem[] roots;

		public TreeItemVisitor(TreeItem[] roots) {
			this.roots = roots;
		}

		public abstract boolean visit(TreeItem treeItem);

		public void traverse(TreeItem treeItem) {
			if (treeItem == null) {
				treeItem = getLastItem(roots);
				if (treeItem == null) {
					return;
				}
			}
			if (treeItem.getData() != null && visit(treeItem)) {
				traversePrecedingSiblings(treeItem);
			}
		}

		private TreeItem getLastItem(TreeItem[] treeItems) {
			if (treeItems.length == 0) {
				return null;
			}
			TreeItem lastItem = treeItems[treeItems.length - 1];
			if (lastItem.getExpanded()) {
				TreeItem result = getLastItem(lastItem.getItems());
				if (result != null) {
					return result;
				}
			}
			return lastItem;
		}

		private boolean traversePrecedingSiblings(TreeItem treeItem) {
			TreeItem parent = treeItem.getParentItem();
			if (parent == null) {
				return traversePrecedingSiblings(roots, treeItem);
			}
			if (!traversePrecedingSiblings(parent.getItems(), treeItem)) {
				return false;
			}
			return traversePrecedingSiblings(parent);
		}

		private boolean traversePrecedingSiblings(TreeItem[] siblings,
				TreeItem treeItem) {
			boolean start = false;
			for (int i = siblings.length - 1; i >= 0; --i) {
				TreeItem sibling = siblings[i];
				if (start) {
					if (!traverseChildren(sibling)) {
						return false;
					}
				} else if (sibling == treeItem) {
					start = true;
				}
			}

			return true;
		}

		private boolean traverseChildren(TreeItem treeItem) {
			if (treeItem.getExpanded()) {
				TreeItem[] children = treeItem.getItems();
				for (int i = children.length - 1; i >= 0; --i) {
					if (!traverseChildren(children[i])) {
						return false;
					}
				}
			}
			return visit(treeItem);
		}
	}

	private static Object[] getVisibleExpandedElements(TreeViewer treeViewer) {
		List<Object> result = new ArrayList<>();
		for (TreeItem treeItem : treeViewer.getTree().getItems()) {
			gatherVisibleExpandedElements(result, treeItem);
		}
		return result.toArray();
	}

	private static void gatherVisibleExpandedElements(List<Object> result,
			TreeItem treeItem) {
		if (treeItem.getExpanded()) {
			Object data = treeItem.getData();
			if (data != null) {
				result.add(data);
				for (TreeItem childItem : treeItem.getItems()) {
					gatherVisibleExpandedElements(result, childItem);
				}
			}
		}
	}

