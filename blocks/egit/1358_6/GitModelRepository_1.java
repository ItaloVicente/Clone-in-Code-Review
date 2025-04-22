			RevCommit srcCommit = rw.parseCommit(srcRev);
			srcCommit.add(localFlag);
			rw.markStart(srcCommit);

			RevCommit dstCommit = rw.parseCommit(dstRev);
			dstCommit.add(remoteFlag);
			rw.markStart(dstCommit);

			for (RevCommit nextCommit : rw) {
				if (nextCommit.hasAll(allFlags))
					break;

				if (nextCommit.has(localFlag))
					result.add(new GitModelCommit(this, nextCommit, RIGHT));
				else if (nextCommit.has(remoteFlag))
					result.add(new GitModelCommit(this, nextCommit, LEFT));
			}
