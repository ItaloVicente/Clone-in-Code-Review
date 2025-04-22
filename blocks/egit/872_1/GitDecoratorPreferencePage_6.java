			try {
				((IPersistentPreferenceStore)store).save();
				Activator.broadcastPropertyChange(new PropertyChangeEvent(this,
						Activator.DECORATORS_CHANGED, null, null));
			} catch (IOException e) {
				Activator.handleError(e.getMessage(), e, true);
			}
