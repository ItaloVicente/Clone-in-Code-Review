	private class ObjectHyperlink implements IHyperlink {

		ObjectLink link;

		public ObjectHyperlink(ObjectLink link) {
			this.link = link;
		}

		@Override
		public IRegion getHyperlinkRegion() {
			return link.getRegion();
		}
