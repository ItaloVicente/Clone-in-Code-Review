        if (fIsDeleted) {
			return true;
		}
        if (fContainer != null) {
			return fContainer.testDeleted();
		}
