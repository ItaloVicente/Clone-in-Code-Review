		super.dispose();
		for (Iterator it = partsWithListeners.iterator(); it.hasNext();) {
			IWorkbenchPart part = (IWorkbenchPart) it.next();
			part.removePropertyListener(this);
		}
		partsWithListeners.clear();
	}
