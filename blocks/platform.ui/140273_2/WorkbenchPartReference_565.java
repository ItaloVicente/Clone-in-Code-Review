		if (propId == IWorkbenchPartConstants.PROP_DIRTY) {
			IWorkbenchPart actualPart = getPart(false);
			if (actualPart != null) {
				SaveablesList modelManager = (SaveablesList) actualPart.getSite()
						.getService(ISaveablesLifecycleListener.class);
				modelManager.dirtyChanged(actualPart);
			}
		}
	}

	protected void partPropertyChanged(PropertyChangeEvent event) {
		firePartPropertyChange(event);
	}

	protected void releaseReferences() {

	}

		internalPropChangeListeners.add(listener);
	}

		internalPropChangeListeners.remove(listener);
	}

	protected void fireInternalPropertyChange(int id) {
