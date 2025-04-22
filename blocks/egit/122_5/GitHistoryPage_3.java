	private class EmptyElement implements ITypedElement{

		private String name;

		public EmptyElement(String name) {
			this.name = name;
		}

		public Image getImage() {
			return null;
		}

		public String getName() {
			return name;
		}

		public String getType() {
			return null;
		}
	};

