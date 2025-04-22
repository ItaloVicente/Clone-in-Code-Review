		final EModelService ms = dndManager.getModelService();

		MWindow topWin = ms.getTopLevelWindowFor(stack);
		MWindow containingWin = getContainingWindow(stack);
		if (topWin == containingWin) {
			List<MPartStack> stacks = ms.findElements(topWin, null, MPartStack.class, null,
					EModelService.PRESENTATION);
			boolean okToDrag = false;
			for (MPartStack theStack : stacks) {
				if (theStack == stack || !theStack.isToBeRendered())
					continue;

				MWindow tw = ms.getTopLevelWindowFor(theStack);
				MWindow cw = getContainingWindow(theStack);
				if (tw != cw)
					continue;

				okToDrag = true;
				break;
			}
			
			if (!okToDrag)
				return null;
		}

