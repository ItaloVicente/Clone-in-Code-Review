	public enum Type {
		Local("LOCAL")
		Remote("REMOTE")
		Merged("MERGED")
		Base("BASE")

		Type(String name) {
			this.name = name;
		}

		public String getName() {
			return name;
		}

		private String name;
	}

