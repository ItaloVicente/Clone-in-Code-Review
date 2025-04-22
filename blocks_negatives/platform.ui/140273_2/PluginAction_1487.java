    }

    /**
     * Returns the action delegate if created. Can be <code>null</code>
     * if the delegate is not created yet or if previous delegate
     * creation failed.
     */
    protected IActionDelegate getDelegate() {
        return delegate;
    }

    /**
     * Returns true if the declaring plugin has been loaded
     * and there is no need to delay creating the delegate
     * any more.
     */
    protected boolean isOkToCreateDelegate() {
        String bundleId = configElement.getContributor().getName();
        return BundleUtility.isActive(bundleId);
    }

    /**
     * Refresh the action enablement.
     */
    protected void refreshEnablement() {
        if (enabler != null) {
            setEnabled(enabler.isEnabledForSelection(selection));
        }
        if (delegate != null) {
            delegate.selectionChanged(this, selection);
        }
    }

    @Override
