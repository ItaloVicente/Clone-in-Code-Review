		cloneSource.saveUriInPrefs();
		if (parentContainer == null) {
			runAsJob(uri, op);
		} else {
			runInParentContainer(op);
		}
		return true;
	}
