		IWorkingSet[] sets = workingSetManager.getAllWorkingSets();
		for (IWorkingSet wset : sets) {
			if (!backup.contains(wset)) {
				workingSetManager.removeWorkingSet(wset);
			}
		}
