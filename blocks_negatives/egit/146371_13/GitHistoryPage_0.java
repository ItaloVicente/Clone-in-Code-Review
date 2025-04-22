				if (historyPage.store.needsSaving())
					try {
						historyPage.store.save();
					} catch (IOException e) {
						Activator.handleError(e.getMessage(), e, false);
					}
