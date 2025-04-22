		refsChangedEvents = 0;
		handle = diskRepo.getListenerList()
				.addRefsChangedListener(refsChangedListener);
	}

	@After
	public void removeListener() {
		handle.remove();
		refsChangedEvents = 0;
