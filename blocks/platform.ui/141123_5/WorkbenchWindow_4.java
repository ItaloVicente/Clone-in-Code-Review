
	class WWinPartServiceSaveHandler extends PartServiceSaveHandler {

		public boolean saveParts(Collection<MPart> dirtyParts, boolean confirm, boolean closing, boolean addNonPartSources) {
			if (addNonPartSources || closing) {
				throw new UnsupportedOperationException();
			}
			return saveParts(dirtyParts, confirm);
		}
	}
