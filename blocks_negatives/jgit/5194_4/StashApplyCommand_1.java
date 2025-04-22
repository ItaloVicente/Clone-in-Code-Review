				treeWalk.addTree(headTree);
				treeWalk.addTree(indexTree);
				treeWalk.addTree(wtCommit.getTree());
				treeWalk.setFilter(TreeFilter.ANY_DIFF);
				File workingTree = repo.getWorkTree();
				while (treeWalk.next()) {
					String path = treeWalk.getPathString();
					File file = new File(workingTree, path);
					AbstractTreeIterator headIter = treeWalk.getTree(0,
							AbstractTreeIterator.class);
					AbstractTreeIterator indexIter = treeWalk.getTree(1,
							AbstractTreeIterator.class);
					AbstractTreeIterator wtIter = treeWalk.getTree(2,
							AbstractTreeIterator.class);
					if (wtIter != null) {
						DirCacheEntry entry = new DirCacheEntry(
								treeWalk.getRawPath());
						entry.setObjectId(wtIter.getEntryObjectId());
						DirCacheCheckout.checkoutEntry(repo, file, entry);
					} else {
						if (indexIter != null && headIter != null
								&& !indexIter.idEqual(headIter))
							editor.add(new DeletePath(path));
						FileUtils.delete(file, FileUtils.RETRY
								| FileUtils.SKIP_MISSING);
					}
				}
