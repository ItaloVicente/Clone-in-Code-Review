			if (srcRev.equals(dstRev))
				return result;

			RevFlagSet allFlags = new RevFlagSet();
			allFlags.add(localFlag);
			allFlags.add(remoteFlag);
			rw.carry(allFlags);

			srcCommit.add(localFlag);
			rw.markStart(srcCommit);

			RevCommit dstCommit = rw.parseCommit(dstRev);
			dstCommit.add(remoteFlag);
			rw.markStart(dstCommit);

			for (RevCommit nextCommit : rw) {
				if (nextCommit.hasAll(allFlags))
					break;

				if (nextCommit.has(localFlag))
					result.add(new GitModelCommit(this, nextCommit, RIGHT,
							pathFilter));
				else if (nextCommit.has(remoteFlag))
					result.add(new GitModelCommit(this, nextCommit, LEFT,
							pathFilter));
