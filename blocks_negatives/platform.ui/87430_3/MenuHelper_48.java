			toolItem.getTransientData().put(AbstractContributionItem.DISPOSABLE, new Runnable() {
						@Override
						public void run() {
							action.removePropertyChangeListener(propertyListener);
						}
					});
