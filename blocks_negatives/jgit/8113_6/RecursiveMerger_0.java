				RevCommit saved_b1;
				RevCommit saved_b2;
				callDepth++;

				/*
				 * When the merge fails, the result contains files with conflict
				 * markers. The cleanness flag is ignored, it was never actually
				 * used, as result of merge_trees has always overwritten it: the
				 * committed "conflicts" were already resolved.
				 */
				dircache.clear();
				saved_b1 = h1;
				saved_b2 = h2;

				mergeRecursive(callDepth, firstCommonAncestor, commonAncestor,
						null);
