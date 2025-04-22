
	class WWinPartServiceSaveHandler extends PartServiceSaveHandler {

		public boolean saveParts(Collection<MPart> dirtyParts, boolean confirm, boolean addNonPartSources) {
			if (addNonPartSources) {
				throw new UnsupportedOperationException();
			}
			return saveParts(dirtyParts, confirm);
		}
	}
