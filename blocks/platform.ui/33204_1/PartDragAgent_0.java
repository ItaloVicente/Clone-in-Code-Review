	private MWindow getContainingWindow(MUIElement element) {
		MUIElement curParent = element;
		while (curParent != null && !(curParent instanceof MWindow)) {
			curParent = curParent.getParent();
			if (curParent instanceof MArea)
				curParent = curParent.getCurSharedRef();
			if (!curParent.isToBeRendered())
				curParent = null;
		}
		return (MWindow) curParent;
	}

