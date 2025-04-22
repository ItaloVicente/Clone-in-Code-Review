		log.addLogListener(logListener, logFilter);

		try {
			populateTestToolbar(menus, manager);

			assertTrue("We should not get these 'MenuManager cannot be cast to org.eclipse.jface.action.ContributionItem' ClassCastException.", cces.isEmpty()); //$NON-NLS-N$

			IContributionItem[] items = manager.getItems();
			assertEquals(6, items.length);
		} finally {
			menus.releaseContributions(manager);
			log.removeLogListener(logListener);
			cces.clear();
		}
	}
