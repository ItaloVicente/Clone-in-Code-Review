		if (policy != UpdateListStrategy.POLICY_NEVER) {
			if (policy != UpdateListStrategy.POLICY_ON_REQUEST || explicit) {
				if (!destination.getRealm().isCurrent()) {
					/*
					 * If the destination is different from the source realm, we have to avoid lazy
					 * diff calculation.
					 */
					diff.getDifferences();
				}
				destination.getRealm().exec(() -> {
					if (destination == target) {
						updatingTarget = true;
					} else {
						updatingModel = true;
					}
					final MultiStatus multiStatus = BindingStatus.ok();
