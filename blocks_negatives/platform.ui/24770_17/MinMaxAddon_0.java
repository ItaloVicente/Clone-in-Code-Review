			List<MPartStack> globalStacks = modelService.findElements(win, null, MPartStack.class,
					null, EModelService.OUTSIDE_PERSPECTIVE);
			for (MPartStack gStack : globalStacks) {
				if (gStack == element || !gStack.isToBeRendered())
					continue;

				if (gStack.getWidget() != null && !gStack.getTags().contains(MINIMIZED)) {
					elementsToMinimize.add(gStack);
				}
			}
