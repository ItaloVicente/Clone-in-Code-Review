		} else {
			List<MPartStack> stacks = modelService.findElements(persp == null ? win : persp, null,
					MPartStack.class, null, EModelService.PRESENTATION);
			for (MPartStack theStack : stacks) {
				if (theStack == element || !theStack.isToBeRendered())
					continue;

				if (getWindowFor(theStack) != win)
					continue;
