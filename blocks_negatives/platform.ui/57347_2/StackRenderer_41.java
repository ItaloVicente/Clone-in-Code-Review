		ctf.addListener(SWT.Activate, new org.eclipse.swt.widgets.Listener() {
			@Override
			public void handleEvent(org.eclipse.swt.widgets.Event event) {
				if (event.detail == SWT.MouseDown) {
					CTabFolder ctf = (CTabFolder) event.widget;
					if (ctf.getSelection() == null)
						return;

					Point cp = event.display.getCursorLocation();
					cp = event.display.map(null, ctf, cp);
					CTabItem overItem = ctf.getItem(cp);

					if (overItem == null || overItem == ctf.getSelection()) {
						MUIElement uiElement = (MUIElement) ctf.getSelection()
								.getData(OWNING_ME);
						if (uiElement instanceof MPlaceholder)
							uiElement = ((MPlaceholder) uiElement).getRef();
						if (uiElement instanceof MPart)
							activate((MPart) uiElement);
					}
