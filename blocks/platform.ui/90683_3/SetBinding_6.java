		destination.getRealm().exec(() -> {
			if (destination == getTarget()) {
				updatingTarget = true;
			} else {
				updatingModel = true;
			}
			MultiStatus multiStatus = BindingStatus.ok();
