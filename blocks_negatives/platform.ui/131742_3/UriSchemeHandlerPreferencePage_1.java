		/*
		 * (non-Javadoc)
		 *
		 * @see
		 * org.eclipse.jface.viewers.ICheckStateListener#checkStateChanged(org.eclipse.
		 * jface.viewers.CheckStateChangedEvent)
		 */
		@Override
		public void checkStateChanged(CheckStateChangedEvent event) {
			handleCheckbox(event);

		}

		private void handleSelection() {
			IStructuredSelection selection = tableViewer.getStructuredSelection();
			Object firstElement = selection != null ? selection.getFirstElement() : null;
			if (firstElement != null && firstElement instanceof UiSchemeInformation) {
				setSchemeDetails((UiSchemeInformation) firstElement);
			}
		}

		private void setSchemeDetails(UiSchemeInformation schemeInfo) {
			if (schemeInfo.checked) {
				handlerLocation.setText(
						NLS.bind(IDEWorkbenchMessages.UrlHandlerPreferencePage_Label_Message_Current_Application,
								currentLocation));

			} else if (schemeIsHandledByOther(schemeInfo.information)) {
				handlerLocation
						.setText(NLS.bind(IDEWorkbenchMessages.UrlHandlerPreferencePage_Label_Message_Other_Application,
								schemeInfo.information.getHandlerInstanceLocation()));
			} else {
				handlerLocation.setText(IDEWorkbenchMessages.UrlHandlerPreferencePage_Label_Message_No_Application);
			}
		}

		private void handleCheckbox(CheckStateChangedEvent event) {
			UiSchemeInformation schemeInformation = (UiSchemeInformation) event.getElement();

			if (event.getChecked() && schemeIsHandledByOther(schemeInformation.information)) {
				schemeInformation.checked = false;
				tableViewer.setChecked(schemeInformation, schemeInformation.checked);

				messageDialogWrapper.openWarning(getShell(),
						IDEWorkbenchMessages.UriHandlerPreferencePage_Warning_OtherApp,
						NLS.bind(IDEWorkbenchMessages.UriHandlerPreferencePage_Warning_OtherApp_Description,
								schemeInformation.information.getHandlerInstanceLocation(),
								schemeInformation.information.getName()));

				return;
			}
			schemeInformation.checked = event.getChecked();
			setSchemeDetails(schemeInformation);
