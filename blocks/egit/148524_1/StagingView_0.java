		try {
			boolean jobScheduled = !internalCommit(pushUpstream,
					() -> enableAllWidgets(true));
			reEnable = !jobScheduled;
		} catch (RuntimeException e) { // See bugs 550336, 550513
			reEnable = true;
			Activator.handleError(e.getLocalizedMessage(), e, true);
		} finally {
			if (reEnable) {
				enableAllWidgets(true);
			}
		}
	}
