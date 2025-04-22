	@Override
	public void updateElement(UIElement element, Map parameters) {
		IWorkbenchLocationService wls = element
				.getServiceLocator()
				.getService(IWorkbenchLocationService.class);
		IWorkbenchWindow window = wls.getWorkbenchWindow();
		if (!(window instanceof WorkbenchWindow))
			return;
		MUIElement trimStatus = getTrimStatus((WorkbenchWindow) window);
		if(trimStatus != null) {
			element.setText(trimStatus.isVisible() ? WorkbenchMessages.ToggleStatusBarVisibilityAction_hide_text
					: WorkbenchMessages.ToggleStatusBarVisibilityAction_show_text);
		}
	}

	private static MUIElement getTrimStatus(WorkbenchWindow window) {
		EModelService modelService = window.getService(EModelService.class);
		MUIElement searchRoot = window.getModel();
		return modelService.find(BOTTOM_TRIM_ID, searchRoot);
	}

