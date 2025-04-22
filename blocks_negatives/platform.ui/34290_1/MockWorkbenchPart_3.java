        return title;
    }

    /* (non-Javadoc)
     * @see org.eclipse.ui.tests.api.MockPart#setInitializationData(org.eclipse.core.runtime.IConfigurationElement, java.lang.String, java.lang.Object)
     */
    @Override
	public void setInitializationData(IConfigurationElement config,
            String propertyName, Object data) throws CoreException {
        super.setInitializationData(config, propertyName, data);
        title = config.getAttribute("name");
    }

    protected void setSiteInitialized() {
        setSiteInitialized(getSite().getKeyBindingService() != null
                & getSite().getPage() != null
                & getSite().getSelectionProvider() != null
                & getSite().getWorkbenchWindow() != null
                & testActionBars(getActionBars()));
    }

    /**
     * @param actionBars
     * @return
     */
    private boolean testActionBars(IActionBars bars) {
        return bars != null && bars.getMenuManager() != null
                && bars.getToolBarManager() != null
                && bars.getStatusLineManager() != null;

    }

    protected abstract IActionBars getActionBars();
