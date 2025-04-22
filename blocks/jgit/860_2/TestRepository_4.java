				ObjectId commitId;
				try {
					c.setTreeId(tree.writeTree(inserter));
					commitId = inserter.insert(Constants.OBJ_COMMIT
							.format(c));
					inserter.flush();
				} finally {
					inserter.release();
				}
				self = pool.lookupCommit(commitId);
