		tabFolder.addTraverseListener(e -> {
			if (e.detail == SWT.TRAVERSE_ARROW_NEXT || e.detail == SWT.TRAVERSE_ARROW_PREVIOUS) {
				me.getTransientData().put(INHIBIT_FOCUS, true);
			} else if (e.detail == SWT.TRAVERSE_RETURN) {
				me.getTransientData().remove(INHIBIT_FOCUS);
				CTabItem cti = tabFolder.getSelection();
				if (cti != null) {
					MUIElement stackElement = (MUIElement) cti.getData(OWNING_ME);
					if (stackElement instanceof MPlaceholder)
						stackElement = ((MPlaceholder) stackElement).getRef();
					if ((stackElement instanceof MPart) && (tabFolder.isFocusControl())) {
						MPart thePart = (MPart) stackElement;
						renderer.focusGui(thePart);
