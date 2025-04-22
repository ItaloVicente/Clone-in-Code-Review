		ISaveHandler saveHandler = getContextForParent(part).get(
				ISaveHandler.class);
		if (saveHandler != null) {
			final List<MPart> toPrompt = new ArrayList<MPart>(others);
			toPrompt.retainAll(partService.getDirtyParts());

			final Save[] response = saveHandler.promptToSave(toPrompt);
			final List<MPart> toSave = new ArrayList<MPart>(toPrompt.size());
			for (int i = 0; i < response.length; i++) {
				final Save save = response[i];
				final MPart mPart = toPrompt.get(i);
				if (save == Save.CANCEL) {
					return;
				} else if (save == Save.YES) {
					toSave.add(mPart);
				}
			}
			saveHandler.saveParts(toSave, false);

			for (MPart other : others) {
				partService.hidePart(other);
			}
			return;
		}

