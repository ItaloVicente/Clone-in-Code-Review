		boolean submoduleSelected = false;
		for (Object item : sel.toArray())
			if (((FileDiff) item).isSubmodule()) {
				submoduleSelected = true;
				break;
			}

