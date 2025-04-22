			int rewriteFlag;
			if (w.getRewriteParents()) {
				pendingOutputType |= HAS_REWRITE | NEEDS_REWRITE;
				rewriteFlag = RevWalk.REWRITE;
			} else
				rewriteFlag = 0;
			rf = AndRevFilter.create(new TreeRevFilter(w
