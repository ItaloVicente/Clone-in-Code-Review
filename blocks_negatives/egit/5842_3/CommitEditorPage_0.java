			for (Ref ref : refsMap.values()) {
				if (ref.isSymbolic())
					continue;
				RevCommit headCommit = revWalk.parseCommit(ref.getObjectId());
				RevCommit base = revWalk.parseCommit(commit);
				if (revWalk.isMergedInto(base, headCommit))
					result.add(ref);
			}
		} catch (IOException ignored) {
