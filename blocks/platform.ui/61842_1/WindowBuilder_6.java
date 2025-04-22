		window.getTransientData().put(PERSPECTIVES, perspectives);
	}

	private void createTrimBars(MPerspective perspective) {
		List<MUIElement> minimizedElements = modelService.findElements(perspective, null,
				MUIElement.class, Arrays.asList(IPresentationEngine.MINIMIZED));
		for (MUIElement element : minimizedElements) {
			createTrim(element, perspective);
		}
	}

	private int getBarIndex(MUIElement element, String trimStr) {
		if (trimStr == null)
			return -1;

		String[] stacks = trimStr.split("#"); //$NON-NLS-1$
		for (String stackInfo : stacks) {
			String[] vals = stackInfo.split(" "); //$NON-NLS-1$
			if (vals[0].equals(element.getElementId())) {
				return Integer.parseInt(vals[1]);
			}
		}
		return -1;
	}

	private int getIndex(MUIElement element, String trimStr) {
		if (trimStr == null)
			return -1;

		String[] stacks = trimStr.split("#"); //$NON-NLS-1$
		for (String stackInfo : stacks) {
			String[] vals = stackInfo.split(" "); //$NON-NLS-1$
			if (vals[0].equals(element.getElementId())) {
				return Integer.parseInt(vals[2]);
			}
		}
		return -1;
	}

	private void createTrim(MUIElement element, MPerspective perspective) {
		if (!(window instanceof MTrimmedWindow)) {
			return;
		}
		String trimStr = perspective.getPersistedState().get("trims"); //$NON-NLS-1$
		MTrimmedWindow win = (MTrimmedWindow) window;

		String trimId = element.getElementId() + getMinimizedElementSuffix(perspective);
		MToolControl trimStack = (MToolControl) modelService.find(trimId, window);

		if (trimStack == null) {
			trimStack = MenuFactoryImpl.eINSTANCE.createToolControl();
			trimStack.setElementId(trimId);
			trimStack.setContributionURI(TrimStack.CONTRIBUTION_URI);
			trimStack.getTags().add("TrimStack"); //$NON-NLS-1$

			MTrimBar bar = getBarForElement(element, win, trimStr);
			int index = getIndex(element, trimStr);
			if (index == -1 || index >= bar.getChildren().size())
				bar.getChildren().add(trimStack);
			else
				bar.getChildren().add(index, trimStack);

			bar.setVisible(true);

		} else {
			MUIElement parent = trimStack.getParent();
			parent.setVisible(true);
			trimStack.setToBeRendered(true);
		}
	}

	private String getMinimizedElementSuffix(MPerspective perspective) {
		String id = "(minimized)"; //$NON-NLS-1$
		if (perspective != null) {
			id = '(' + perspective.getElementId() + ')';
		}
		return id;
	}

	private MTrimBar getBarForElement(MUIElement element, MTrimmedWindow window, String trimStr) {
		SideValue side = SideValue.get(getBarIndex(element, trimStr));
		if (side == null) {
			side = SideValue.BOTTOM;
		}
		MTrimBar bar = modelService.getTrim(window, side);
		return bar;
