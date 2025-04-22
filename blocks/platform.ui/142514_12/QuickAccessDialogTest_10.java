		Set<Shell> formerShells = new HashSet<>(Arrays.asList(Display.getDefault().getShells()));
		handlerService.executeCommand("org.eclipse.ui.window.quickAccess", null); //$NON-NLS-1$
		Set<Shell> newShells = new HashSet<>(Arrays.asList(Display.getDefault().getShells()));
		assertEquals(formerShells.size() + 1, newShells.size());
		newShells.removeAll(formerShells);
		assertEquals(1, newShells.size());
		assertTrue(isQuickAccessShell.test(newShells.iterator().next()));
