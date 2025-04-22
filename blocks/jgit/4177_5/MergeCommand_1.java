				try {
					dco.checkout();
				} catch (org.eclipse.jgit.errors.CheckoutConflictException e) {
					return new MergeResult(srcCommit
							new ObjectId[] { headCommit
							MergeStatus.FAILED
							e.getConflictingPaths()
				}
