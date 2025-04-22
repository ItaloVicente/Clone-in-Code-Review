		myIndexChangedHandle = Repository.getGlobalListenerList()
				.addIndexChangedListener(this);
		myRefsChangedHandle = Repository.getGlobalListenerList()
				.addRefsChangedListener(this);
		GitProjectData.addRepositoryChangeListener(this);
		ResourcesPlugin.getWorkspace().addResourceChangeListener(this,
				IResourceChangeEvent.POST_CHANGE);
