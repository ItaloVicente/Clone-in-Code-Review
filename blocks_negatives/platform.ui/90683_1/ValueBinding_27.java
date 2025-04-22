					destinationRealmReached = true;
					destination.getRealm().exec(new Runnable() {
						@Override
						public void run() {
							if (destination == target) {
								updatingTarget = true;
							} else {
								updatingModel = true;
							}
							try {
								IStatus setterStatus = updateValueStrategy
										.doSet(destination, convertedValue);
