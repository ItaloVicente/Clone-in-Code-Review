		compositeListener = event -> {
			if (!visible) {
				return;
			}
			switch (event.type) {
			case SWT.MouseDown:
				if (!selectionListeners.isEmpty())
					notifySelectionListeners(event);
				break;
			case SWT.MouseDoubleClick:
				if (!selectionListeners.isEmpty())
					notifySelectionListeners(event);
				break;
			case SWT.MenuDetect:
				if (!menuDetectListeners.isEmpty())
					notifyMenuDetectListeners(event);
				break;
