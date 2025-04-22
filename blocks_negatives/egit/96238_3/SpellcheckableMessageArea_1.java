
			boolean wasEnabled= isEnabled();
			boolean isEnabled= (fOperationTarget != null && fOperationTarget.canDoOperation(fOperationCode));
			setEnabled(isEnabled);

			if (wasEnabled != isEnabled)
				firePropertyChange(ENABLED, wasEnabled ? Boolean.TRUE : Boolean.FALSE, isEnabled ? Boolean.TRUE : Boolean.FALSE);
