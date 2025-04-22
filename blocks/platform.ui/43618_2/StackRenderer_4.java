		MElementContainer<MUIElement> parent = part.getParent();
		if (parent != null
				&& parent.getRenderer() == StackRenderer.this) {
			CTabItem cti = findItemForPart(part, parent);
			if (cti != null) {
				updateTab(cti, part, attName, newValue);
			}
			return;
		}
