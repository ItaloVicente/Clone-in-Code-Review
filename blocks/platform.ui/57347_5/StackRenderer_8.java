		tabFolder.addListener(SWT.Activate, event -> {
			if (event.detail == SWT.MouseDown) {
				CTabFolder tabFolder1 = (CTabFolder) event.widget;
				if (tabFolder1.getSelection() == null)
					return;

				Point cp = event.display.getCursorLocation();
				cp = event.display.map(null, tabFolder1, cp);
				CTabItem overItem = tabFolder1.getItem(cp);

				if (overItem == null || overItem == tabFolder1.getSelection()) {
					MUIElement uiElement = (MUIElement) tabFolder1.getSelection().getData(OWNING_ME);
					if (uiElement instanceof MPlaceholder)
						uiElement = ((MPlaceholder) uiElement).getRef();
					if (uiElement instanceof MPart)
						activate((MPart) uiElement);
