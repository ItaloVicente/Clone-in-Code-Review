				destinationRealmReached = true;
				destination.getRealm().exec(() -> {
					if (destination == target) {
						updatingTarget = true;
					} else {
						updatingModel = true;
					}
					try {
						IStatus setterStatus = updateValueStrategy.doSet(destination, convertedValue);
