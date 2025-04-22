
		Object initDone = context.get(ALL_SETS_INITIALIZED);
		if (initDone == null) {
			context.set(ALL_SETS_INITIALIZED, Boolean.TRUE);
			window.getActionPresentation().setActionSets(sets);
			window.updateActionSets();
		}

