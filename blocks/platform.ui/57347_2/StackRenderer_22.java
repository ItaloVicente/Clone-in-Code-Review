		ctf.addListener(SWT.Activate, event -> {
			if (event.detail == SWT.MouseDown) {
				CTabFolder ctf1 = (CTabFolder) event.widget;
				if (ctf1.getSelection() == null)
					return;

				Point cp = event.display.getCursorLocation();
				cp = event.display.map(null, ctf1, cp);
				CTabItem overItem = ctf1.getItem(cp);

				if (overItem == null || overItem == ctf1.getSelection()) {
					MUIElement uiElement = (MUIElement) ctf1.getSelection()
							.getData(OWNING_ME);
					if (uiElement instanceof MPlaceholder)
						uiElement = ((MPlaceholder) uiElement).getRef();
					if (uiElement instanceof MPart)
						activate((MPart) uiElement);
