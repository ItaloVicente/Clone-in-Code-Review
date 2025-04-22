				store.setValue(SHOW_FIND_TOOLBAR, isChecked());
				if (store.needsSaving()) {
					try {
						store.save();
					} catch (IOException e) {
						Activator.handleError(e.getMessage(), e, false);
					}
				}
