		for (ListenerHandle h : indexChangedListenerHandles) {
			h.remove();
		}
		for (ListenerHandle h : refsChangedListenerHandles) {
			h.remove();
		}
		indexChangedListenerHandles.clear();
		refsChangedListenerHandles.clear();
		submodules.clear();
