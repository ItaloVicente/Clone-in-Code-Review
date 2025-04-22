		if (!dirtyParts.isEmpty()) {
			ISaveHandler saveHandler = window.getContext().get(ISaveHandler.class);
			if (dirtyParts.size() == 1) {
				MPart part = dirtyParts.get(0);
				switch (saveHandler.promptToSave(part)) {
				case YES:
					partService.savePart(part, false);
					break;
				case NO:
					part.setDirty(false);
					partService.hidePart(part);
					break;
				case CANCEL:
					legacyWindow.firePerspectiveChanged(this, desc, CHANGE_RESET_COMPLETE);
					return;
				}
			} else {
				Save[] promptToSave = saveHandler.promptToSave(dirtyParts);
				for (Save save : promptToSave) {
					if (save == ISaveHandler.Save.CANCEL) {
						legacyWindow.firePerspectiveChanged(this, desc, CHANGE_RESET_COMPLETE);
						return;
					}
				}

				for (int i = 0; i < promptToSave.length; i++) {
					switch (promptToSave[i]) {
					case NO:
						dirtyParts.get(i).setDirty(false);
						partService.hidePart(dirtyParts.get(i));
						break;
					case YES:
						partService.savePart(dirtyParts.get(i), false);
						break;
					case CANCEL:
						break;
					}
				}
