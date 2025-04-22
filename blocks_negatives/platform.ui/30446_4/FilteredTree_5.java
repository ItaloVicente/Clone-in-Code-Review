	/**
	 * Create the button that clears the text.
	 * 
	 * @param parent
	 *            parent <code>Composite</code> of toolbar button
	 */
	private void createClearTextOld(Composite parent) {
		if ((filterText.getStyle() & SWT.ICON_CANCEL) == 0) {
			filterToolBar = new ToolBarManager(SWT.FLAT | SWT.HORIZONTAL);
			filterToolBar.createControl(parent);

			IAction clearTextAction = new Action("", IAction.AS_PUSH_BUTTON) {//$NON-NLS-1$
				@Override
				public void run() {
					clearText();
				}
			};

			clearTextAction
					.setToolTipText(WorkbenchSWTMessages.FilteredTree_ClearToolTip);
			clearTextAction.setImageDescriptor(JFaceResources
					.getImageRegistry().getDescriptor(CLEAR_ICON));
			clearTextAction.setDisabledImageDescriptor(JFaceResources
					.getImageRegistry().getDescriptor(DISABLED_CLEAR_ICON));

			filterToolBar.add(clearTextAction);
		}
	}
