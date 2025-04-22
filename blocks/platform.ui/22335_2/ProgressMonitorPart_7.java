				return escapeMetaCharacters(JFaceResources
						.format("Set_SubTask", new Object[] { fTaskName, fSubTaskName }));//$NON-NLS-1$
			return escapeMetaCharacters(fTaskName);

		} else if (hasSubtask) {
			return escapeMetaCharacters(fSubTaskName);

		} else {
			return ""; //$NON-NLS-1$
		}
	}

	@Override
	public void worked(int work) {
		internalWorked(work);
	}

	@Override
	public void clearBlocked() {
		blockedStatus = null;
		updateLabel();

	}

	@Override
	public void setBlocked(IStatus reason) {
		blockedStatus = reason;
		updateLabel();

	}

	private void setCancelEnabled(boolean enabled) {
		if (fStopButton != null && !fStopButton.isDisposed()) {
			fStopButton.setEnabled(enabled);
			if (enabled)
				fToolBar.setFocus();
		}
	}
