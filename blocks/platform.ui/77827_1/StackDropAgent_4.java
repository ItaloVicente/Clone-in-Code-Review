		EModelService ms = dndManager.getModelService();
		MWindow dragElementWin = ms.getTopLevelWindowFor(dragElement);
		MWindow dropWin = ms.getTopLevelWindowFor(stack);
		if (dragElementWin != dropWin) {
			System.out.println("StackDropAgent dragging over wrong window");
			return false;
		}
