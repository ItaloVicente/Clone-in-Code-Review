				destination.getRealm().exec(() -> {
					if (destination == getTarget()) {
						updatingTarget = true;
					} else {
						updatingModel = true;
					}
					final MultiStatus multiStatus = BindingStatus.ok();

					try {
						if (clearDestination) {
							destination.clear();
