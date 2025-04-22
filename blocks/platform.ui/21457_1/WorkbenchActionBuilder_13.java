		nextPerspectiveAction = ActionFactory.NEXT_PERSPECTIVE.create(window);
		register(nextPerspectiveAction);
		prevPerspectiveAction = ActionFactory.PREVIOUS_PERSPECTIVE
				.create(window);
		register(prevPerspectiveAction);
		ActionFactory.linkCycleActionPair(nextPerspectiveAction,
				prevPerspectiveAction);
