			}
		}
		for (;;) {
			RevCommit c = unshallowCommits.next();
			if (c == null) {
				break;
			}
			pending.unpop(c);
