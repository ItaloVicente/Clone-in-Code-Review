	private void checkConnectivity(ProgressMonitor pm
			throws IOException {
		pm.beginTask(JGitText.get().countingObjects
		try (ObjectWalk ow = new ObjectWalk(ctx)) {
			for (Ref r : repo.getAllRefs().values()) {
				RevObject tip;
				try {
					tip = ow.parseAny(r.getObjectId());
					if (r.getLeaf().getName().startsWith(Constants.R_HEADS)
							&& tip.getType() != Constants.OBJ_COMMIT) {
						errors.getNonCommitHeads().add(r.getLeaf().getName());
					}
				} catch (MissingObjectException e) {
					errors.getMissingObjects().add(e.getObjectId());
					continue;
				}
				ow.markStart(tip);
			}
			try {
				ow.checkConnectivity();
			} catch (MissingObjectException e) {
				errors.getMissingObjects().add(e.getObjectId());
			}
		}
		pm.endTask();
	}

