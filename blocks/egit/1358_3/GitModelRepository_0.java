		RevWalk rw = new RevWalk(repo);
		RevFlag localFlag = rw.newFlag("local"); //$NON-NLS-1$
		RevFlag remoteFlag = rw.newFlag("local"); //$NON-NLS-1$
		RevFlagSet allFlags = new RevFlagSet();
		allFlags.add(localFlag);
		allFlags.add(remoteFlag);
