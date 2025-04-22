							RevCommit c = rw.next();
							if (c != null) {
								try (TreeWalk tw = TreeWalk.forPath(
										rw.getObjectReader()
										c.getTree())) {
									next = read(tw);
								}
