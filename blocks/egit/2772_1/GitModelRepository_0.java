
			if (includeLocal) {
				GitModelCache gitModelCache = new GitModelCache(this, srcCommit);
				if (gitModelCache.getChildren().length > 0)
					result.add(gitModelCache);

				GitModelWorkingTree gitModelWorkingTree = getLocaWorkingTreeChanges();
				if (gitModelWorkingTree != null)
					result.add(gitModelWorkingTree);
			}

			if (srcRev.equals(dstRev))
				return result;

			RevFlag localFlag = rw.newFlag("local"); //$NON-NLS-1$
			RevFlag remoteFlag = rw.newFlag("remote"); //$NON-NLS-1$
			RevFlagSet allFlags = new RevFlagSet();
			allFlags.add(localFlag);
			allFlags.add(remoteFlag);
			rw.carry(allFlags);

