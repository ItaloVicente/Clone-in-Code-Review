	public static class EmptyTypedElement implements ITypedElement{

		private String name;

		public EmptyTypedElement(String name) {
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

	}

