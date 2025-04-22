				if (noConflicts) {
					DirCacheCheckout dco = new DirCacheCheckout(repo
							headCommit.getTree()
							merger.getResultTreeId());
					dco.setFailOnConflict(true);
					dco.checkout();
					RevCommit newHead = new Git(getRepository()).commit().call();
					return new MergeResult(newHead.getId()
							null
									headCommit.getId()
							MergeStatus.MERGED
