								myListeners.add(repo.getListenerList()
										.addIndexChangedListener(
												myIndexChangedListener));
								myListeners.add(repo.getListenerList()
										.addRefsChangedListener(
												myRefsChangedListener));
