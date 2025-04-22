		MUIElement stateElement = changedElement;
		if (changedElement instanceof MPartStack) {
			MPartStack stack = (MPartStack) changedElement;
			MArea area = getAreaFor(stack);
			if (area != null && !(area.getWidget() instanceof CTabFolder))
				stateElement = area.getCurSharedRef();
		} else if (changedElement instanceof MArea)
			stateElement = changedElement.getCurSharedRef();
