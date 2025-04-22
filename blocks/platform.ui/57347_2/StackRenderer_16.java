			MElementContainer<MUIElement> parent = part.getParent();
			if (parent != null
					&& parent.getRenderer() == StackRenderer.this) {
				CTabItem cti1 = findItemForPart(part, parent);
				if (cti1 != null) {
					updateTab(cti1, part, attName, newValue);
