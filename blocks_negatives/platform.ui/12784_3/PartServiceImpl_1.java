
		if (confirm && saveHandler != null) {
			List<MPart> dirtyPartsList = Collections.unmodifiableList(new ArrayList<MPart>(
					dirtyParts));
			Save[] decisions = saveHandler.promptToSave(dirtyPartsList);
			for (Save decision : decisions) {
				if (decision == Save.CANCEL) {
					return false;
				}
			}

			for (int i = 0; i < decisions.length; i++) {
				if (decisions[i] == Save.YES) {
					if (!savePart(dirtyPartsList.get(i), false)) {
						return false;
					}
				}
			}
			return true;
