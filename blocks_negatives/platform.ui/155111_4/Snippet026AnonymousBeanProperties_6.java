			this.name = checkNull(name);
		}

		private String checkNull(String string) {
			if (string == null) {
				throw new NullPointerException();
			}
			return string;
