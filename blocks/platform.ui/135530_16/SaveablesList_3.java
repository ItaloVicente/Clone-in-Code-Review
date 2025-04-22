	private boolean promptForSaving(List<Saveable> modelsToSave, final IShellProvider shellProvider,
			IRunnableContext runnableContext, final boolean canCancel, boolean stillOpenElsewhere,
			Map<Saveable, Save> saveOptionMap) {
		List<Saveable> tobeSaved = new ArrayList<>();
		if (saveOptionMap == null || saveOptionMap.size() == 0) {
			return promptForSaving(modelsToSave, shellProvider, runnableContext, canCancel, stillOpenElsewhere);
		}
		if (modelsToSave.size() > 0) {
			for (Saveable saveable : modelsToSave) {
				Save option = saveOptionMap.get(saveable);
				if (option != null && option == Save.YES) {
					tobeSaved.add(saveable);
				}
			}
		}
		return saveModels(tobeSaved, shellProvider, runnableContext);
	}
