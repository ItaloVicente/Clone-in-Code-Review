			MUIElement selectedElement = stack.getSelectedElement();
			if (selectedElement != null) {
				selectedElement.getTransientData().put(SELECTED_BEFORE, oldSel);
				try {
					lsr.showTab(selectedElement);
				} finally {
					selectedElement.getTransientData().remove(SELECTED_BEFORE);
				}
			}
