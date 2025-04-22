				if (noConflicts) {
					DirCacheCheckout dco = new DirCacheCheckout(repo
							headCommit
							merger.getResultTreeId());
					dco.setFailOnConflict(true);
					dco.checkout();
					new Git(getRepository()).commit().call();
					return new MergeResult(headCommit.getId()
							headCommit.getId()
									headCommit.getId()
							MergeStatus.MERGED
