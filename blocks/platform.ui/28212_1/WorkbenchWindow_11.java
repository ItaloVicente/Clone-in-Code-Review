			page.setPerspective(perspective);
			firePageActivated();
			if (newWindow) {
				page.fireInitialPartVisibilityEvents();
			} else {
				page.updatePerspectiveActionSets();
			}
			partService.setPage(page);
			updateActionSets();
