		return title;
	}

	@Override
	public void setInitializationData(IConfigurationElement config, String propertyName, Object data) {
		super.setInitializationData(config, propertyName, data);
		title = config.getAttribute("name");
	}

	protected void setSiteInitialized() {
		setSiteInitialized(getSite().getKeyBindingService() != null & getSite().getPage() != null
				& getSite().getSelectionProvider() != null & getSite().getWorkbenchWindow() != null
				& testActionBars(getActionBars()));
	}

	private boolean testActionBars(IActionBars bars) {
		return bars != null && bars.getMenuManager() != null && bars.getToolBarManager() != null
				&& bars.getStatusLineManager() != null;

	}

	protected abstract IActionBars getActionBars();
