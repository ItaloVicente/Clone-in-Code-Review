	}

	protected IActionDelegate getDelegate() {
		return delegate;
	}

	protected boolean isOkToCreateDelegate() {
		String bundleId = configElement.getContributor().getName();
		return BundleUtility.isActive(bundleId);
	}

	protected void refreshEnablement() {
		if (enabler != null) {
			setEnabled(enabler.isEnabledForSelection(selection));
		}
		if (delegate != null) {
			delegate.selectionChanged(this, selection);
		}
	}

	@Override
