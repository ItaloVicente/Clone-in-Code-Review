			final List<MPart> toSave = new ArrayList<MPart>(toPrompt.size());
			for (int i = 0; i < response.length; i++) {
				final Save save = response[i];
				final MPart mPart = toPrompt.get(i);
				if (save == Save.CANCEL) {
					return;
				} else if (save == Save.YES) {
					toSave.add(mPart);
				}
