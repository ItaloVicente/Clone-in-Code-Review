
		UITestCase.processEvents();
		UITestCase.processEventsUntil(() -> logView.getElements().length > 0, 30000);
		AbstractEntry[] elements = logView.getElements();
		List<AbstractEntry> list = Arrays.asList(elements).stream()
				.filter(x -> ((LogEntry) x).getMessage().startsWith("To avoid deadlock")).collect(Collectors.toList());
		assertEquals("Unexpected list content: " + list, 1, list.size());
		AbstractEntry[] children = list.get(0).getChildren(list.get(0));
		assertEquals("Unexpected children content: " + Arrays.toString(children), 2, children.length);
		String label = ((LogEntry) children[0]).getMessage();
		assertTrue("Unexpected: " + label, label.startsWith("UI thread"));
		label = ((LogEntry) children[1]).getMessage();
		assertTrue("Unexpected: " + label, label.startsWith("SyncExec"));
