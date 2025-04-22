			IViewPart viewPart = (IViewPart) contributor;
			partActionBars = viewPart.getViewSite().getActionBars();
		}

		if (partActionBars != null) {
			IAction action = partActionBars.getGlobalActionHandler(ActionFactory.UNDO
				.getId());
			if (action != null) {
				actionBars.setGlobalActionHandler(ActionFactory.UNDO.getId(), action);
			}
			action = partActionBars.getGlobalActionHandler(ActionFactory.REDO
				.getId());
			if (action != null) {
				actionBars.setGlobalActionHandler(ActionFactory.REDO.getId(), action);
			}
		}
