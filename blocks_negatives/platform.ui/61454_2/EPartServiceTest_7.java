		window.getContext().set(ISaveHandler.class.getName(),
				new PartServiceSaveHandler() {
					@Override
					public Save[] promptToSave(Collection<MPart> saveablePart) {
						return null;
					}
