	private void cleanupMenuManagerRec(MenuManagerRenderer renderer, MMenu m) {
		for (MMenuElement e : m.getChildren()) {
			if (e instanceof MMenu) {
				cleanupMenuManagerRec(renderer, (MMenu) e);
			}
		}
		renderer.clearModelToManager(m, null);
	}

