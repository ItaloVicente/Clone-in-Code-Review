
	private static class FolderNode extends DiffNode {

		private final Image image;

		private String name;

		FolderNode(IDiffContainer parent, String name, Image image) {
			super(parent, Differencer.NO_CHANGE);
			this.name = name;
			this.image = image;
		}

		@Override
		public String getName() {
			return name;
		}

		void setName(String name) {
			this.name = name;
		}

		@Override
		public Image getImage() {
			return image;
		}

		@Override
		public boolean equals(Object other) {
			return super.equals(other);
		}

		@Override
		public int hashCode() {
			return super.hashCode();
		}
	}
