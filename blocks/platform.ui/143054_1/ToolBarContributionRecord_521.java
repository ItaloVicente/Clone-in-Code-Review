	private MWindow getWindow() {
		EObject n = (EObject) toolbarModel;
		while (n.eContainer() != null) {
			n = n.eContainer();
			if (n instanceof MWindow) {
				return (MWindow) n;
			}
		}
		return null;
	}

