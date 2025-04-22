		destination.getRealm().exec(new Runnable() {
			@Override
			public void run() {
				if (destination == getTarget()) {
					updatingTarget = true;
				} else {
					updatingModel = true;
				}
				MultiStatus multiStatus = BindingStatus.ok();
