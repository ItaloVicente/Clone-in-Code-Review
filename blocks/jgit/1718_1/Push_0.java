		Git git = new Git(db);
		PushCommand push = git.push();
		push.setDryRun(dryRun);
		push.setForce(force);
		push.setProgressMonitor(new TextProgressMonitor());
		push.setReceivePack(receivePack);
		push.setRefSpecs(refSpecs);
		push.setRemote(remote);
		push.setThin(thin);
		push.setTimeout(timeout);
		Iterable<PushResult> results = push.call();
		for (PushResult result : results) {
