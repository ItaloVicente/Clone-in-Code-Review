		if (location != null) {
			locationMemento = XMLMemento.createWriteRoot(IWorkbenchConstants.TAG_POSITION);
			location.saveState(locationMemento);
			location.releaseState();
		}
		return true;
	}

	void saveState(IMemento mem, ArrayList entries) {
		mem.putString(IWorkbenchConstants.TAG_HISTORY_LABEL, getHistoryText());
		if (locationMemento != null) {
			IMemento childMem = mem.createChild(IWorkbenchConstants.TAG_POSITION);
			childMem.putMemento(locationMemento);
		} else if (location != null) {
			IMemento childMem = mem.createChild(IWorkbenchConstants.TAG_POSITION);
			location.saveState(childMem);
		}
	}

	void restoreState(IMemento mem) {
		historyText = mem.getString(IWorkbenchConstants.TAG_HISTORY_LABEL);
		locationMemento = mem.getChild(IWorkbenchConstants.TAG_POSITION);
	}

	@Override
