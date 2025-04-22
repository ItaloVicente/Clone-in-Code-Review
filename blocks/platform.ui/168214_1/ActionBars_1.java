	private void forceUpdateTopRight() {
		MStackElement element = part;
		if (element.getCurSharedRef() != null) {
			element = element.getCurSharedRef();
		}
		MUIElement parentElement = element.getParent();

		if (!(parentElement instanceof MPartStack)) {
			return;
		}

		Object widget = parentElement.getWidget();
		if (widget instanceof CTabFolder) {
			((StackRenderer) parentElement.getRenderer()).adjustTopRight((CTabFolder) widget);
		}
	}

