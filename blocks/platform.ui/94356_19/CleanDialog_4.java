
	private void createClearTextOld(Composite parent) {
		if ((filterText.getStyle() & SWT.ICON_CANCEL) == 0) {
			filterToolBar = new ToolBarManager(SWT.FLAT | SWT.HORIZONTAL);
			filterToolBar.createControl(parent);

			IAction clearTextAction = new Action("", IAction.AS_PUSH_BUTTON) {//$NON-NLS-1$
				@Override
				public void run() {
					filterText.setText(""); //$NON-NLS-1$
					filterText.selectAll();
				}
			};

			clearTextAction.setToolTipText(IDEWorkbenchMessages.CleanDialog_clearToolTip);
			clearTextAction.setImageDescriptor(
					JFaceResources.getImageRegistry().getDescriptor("org.eclipse.ui.internal.dialogs.CLEAR_ICON")); //$NON-NLS-1$
			clearTextAction
					.setDisabledImageDescriptor(JFaceResources.getImageRegistry()
							.getDescriptor("org.eclipse.ui.internal.dialogs.DCLEAR_ICON")); //$NON-NLS-1$

			filterToolBar.add(clearTextAction);
		} else {
			((GridLayout) parent.getLayout()).numColumns = 1;
		}
	}
