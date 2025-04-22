		tabFolder.addListener(SWT.Activate, new org.eclipse.swt.widgets.Listener() {
			@Override
			public void handleEvent(org.eclipse.swt.widgets.Event event) {
				if (event.detail == SWT.MouseDown) {
					CTabFolder tabFolder = (CTabFolder) event.widget;
					if (tabFolder.getSelection() == null)
						return;

					Point cp = event.display.getCursorLocation();
					cp = event.display.map(null, tabFolder, cp);
					CTabItem overItem = tabFolder.getItem(cp);

					if (overItem == null || overItem == tabFolder.getSelection()) {
						MUIElement uiElement = (MUIElement) tabFolder.getSelection().getData(OWNING_ME);
						if (uiElement instanceof MPlaceholder)
							uiElement = ((MPlaceholder) uiElement).getRef();
						if (uiElement instanceof MPart)
							activate((MPart) uiElement);
					}
