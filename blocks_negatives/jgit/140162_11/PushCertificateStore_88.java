							if (reader == null) {
								load();
							}
							if (commit != null) {
								rw = new RevWalk(reader);
								rw.setTreeFilter(AndTreeFilter.create(
										PathFilterGroup.create(
											Collections.singleton(PathFilter.create(path))),
										TreeFilter.ANY_DIFF));
								rw.setRewriteParents(false);
								rw.markStart(rw.parseCommit(commit));
