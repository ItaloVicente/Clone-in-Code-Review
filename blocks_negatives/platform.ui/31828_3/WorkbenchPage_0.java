			if (processSaveable2(dirtyParts)) {
				return false;
			}

			modelsToSave = convertToSaveables(dirtyParts, closing, addNonPartSources);
			if (modelsToSave.isEmpty()) {
				return true;
			}

			if (SaveableHelper.waitForBackgroundSaveJobs(modelsToSave)) {
				return false;
			}

			if (modelsToSave.size() == 1) {
				modelsToSave = promptForSaving(shellProvider, (Saveable) modelsToSave.get(0));
			} else {
				modelsToSave = promptForSaving(shellProvider, modelsToSave);
			}
			if (modelsToSave == null) {
				return false;
			}
		} else {
			modelsToSave = convertToSaveables(dirtyParts, closing, addNonPartSources);
		}

		if (modelsToSave.isEmpty()) {
			return true;
