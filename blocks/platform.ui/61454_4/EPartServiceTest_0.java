		window.getContext().set(ISaveHandler.class.getName(), new PartServiceSaveHandler() {
			@Override
			public Save[] promptToSave(Collection<MPart> saveableParts) {
				int index = 0;
				Save[] prompt = new Save[saveableParts.size()];
				Iterator<MPart> it = saveableParts.iterator();
				while (it.hasNext()) {
					prompt[index] = prompt(returnValues, it.next(), saveablePart);
					index++;
				}
				return prompt;
			}
