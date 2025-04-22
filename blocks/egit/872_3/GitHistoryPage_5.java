			store.setValue(prefName, isChecked());
			if (store.needsSaving()) {
				try {
					store.save();
				} catch (IOException e) {
					Activator.handleError(e.getMessage(), e, false);
				}
			}
