			MessageBox mb;
			if (item.getIContributionItem() instanceof HandledContributionItem) {
				mb = new MessageBox(viewer.getControl().getShell(), SWT.OK | SWT.ICON_WARNING | SWT.SHEET);
				final String errorExplanation = NLS.bind(
						WorkbenchMessages.HideItemsCannotMakeVisible_unavailableCommandItemText, item.getLabel());
				mb.setMessage(errorExplanation);
			} else {
				mb = new MessageBox(viewer.getControl().getShell(), SWT.YES | SWT.NO | SWT.ICON_WARNING | SWT.SHEET);
				mb.setText(WorkbenchMessages.HideItemsCannotMakeVisible_dialogTitle);
				final String errorExplanation = NLS.bind(
						WorkbenchMessages.HideItemsCannotMakeVisible_unavailableCommandGroupText, item.getLabel(),
						item.getActionSet());
				final String message = NLS
						.bind("{0}{1}{1}{2}", new Object[] { errorExplanation, CustomizePerspectiveDialog.NEW_LINE, WorkbenchMessages.HideItemsCannotMakeVisible_switchToCommandGroupTab }); //$NON-NLS-1$
				mb.setMessage(message);
			}
