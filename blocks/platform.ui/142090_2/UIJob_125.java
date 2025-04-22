		if (monitor.isCanceled()) {
			return Status.CANCEL_STATUS;
		}
		UISynchronize uiSynchronize = getUiSynchronize();
		if (uiSynchronize == null) {
			return Status.CANCEL_STATUS;
		}
