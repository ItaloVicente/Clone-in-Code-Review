			mergeBaseAncestor = walker.allocFlag();

			for (;;) {
				RevCommit c = _next();
				if (c == null) {
					break;
				}
				ret.add(c);
			}
		} finally {
			walker.freeFlag(branchMask | mergeBaseAncestor);
