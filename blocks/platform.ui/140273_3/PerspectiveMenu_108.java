		int size = shortcuts.size();
		for (int i = 0; i < size; i++) {
			if (!list.contains(shortcuts.get(i))) {
				list.add(shortcuts.get(i));
			}
		}

		return list;
	}

	protected boolean getShowActive() {
		return showActive;
	}

	protected IWorkbenchWindow getWindow() {
		return window;
	}

	@Override
