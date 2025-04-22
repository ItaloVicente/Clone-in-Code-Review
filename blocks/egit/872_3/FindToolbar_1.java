				store.setValue(UIPreferences.FINDTOOLBAR_IGNORE_CASE,
						caseItem.getSelection());
				if (store.needsSaving()){
					try {
						store.save();
					} catch (IOException e1) {
						Activator.handleError(e1.getMessage(), e1, false);
					}
				}
