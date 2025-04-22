		if (element.getParent() != null && element.getParent().getRenderer() == StackRenderer.this) {
			CTabItem cti = findItemForPart(element, element.getParent());
			if (cti != null) {
				updateTab(cti, part, attName, newValue);
			}
			return;
		}
