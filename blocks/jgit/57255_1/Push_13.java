		try (Git git = new Git(db)) {
			PushCommand push = git.push();
			push.setDryRun(dryRun);
			push.setForce(force);
			push.setProgressMonitor(new TextProgressMonitor(errw));
			push.setReceivePack(receivePack);
			push.setRefSpecs(refSpecs);
			if (all)
				push.setPushAll();
			if (tags)
				push.setPushTags();
			push.setRemote(remote);
			push.setThin(thin);
			push.setTimeout(timeout);
			Iterable<PushResult> results = push.call();
			for (PushResult result : results) {
				try (ObjectReader reader = db.newObjectReader()) {
					printPushResult(reader
				}
