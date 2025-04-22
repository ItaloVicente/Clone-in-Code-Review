		applicationContext.set(ISaveHandler.class.getName(), new PartServiceSaveHandler() {
			@Override
			public Save[] promptToSave(Collection<MPart> saveablePart) {
				Save[] ret = new Save[saveablePart.size()];
				Arrays.fill(ret, ISaveHandler.Save.YES);
				return ret;
			}
