		try {
			MPartStack stack = (MPartStack) tabFolder.getData(OWNING_ME);
			MUIElement element = stack.getSelectedElement();
			MPart curPart = (MPart) tabFolder.getTopRight().getData(THE_PART_KEY);
			MPart part = null;
			if (element != null) {
				part = (MPart) ((element instanceof MPart) ? element : ((MPlaceholder) element).getRef());
			}
