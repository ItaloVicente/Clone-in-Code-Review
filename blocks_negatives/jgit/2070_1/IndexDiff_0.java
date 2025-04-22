					if (!fileModeTree.equals(FileMode.TYPE_TREE)) {
						removed.add(treeWalk.getPathString());
						changesExist = true;
						if (workingTreeIterator != null)
							untracked.add(treeWalk.getPathString());
					}
