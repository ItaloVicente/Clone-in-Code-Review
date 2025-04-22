			RevCommit commit = rw.next();
			if (commit.has(srcFlag))
				return OUTGOING | CHANGE;
			else if (commit.has(dstFlag))
				return INCOMING | CHANGE;
			else
				return CONFLICTING | CHANGE;
		}
