		if (preserveMerges
				&& shouldPick
				&& (Action.EDIT.equals(step.getAction()) || Action.PICK
						.equals(step.getAction()))) {
			writeRewrittenHashes();
		}
