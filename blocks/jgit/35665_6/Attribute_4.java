		int result = hashCode;
		if (result == 0) {
			final int prime = 31;
			result = 17;
			result = prime * result + key.hashCode();
			result = prime * result + state.hashCode();
			result = prime * result + ((value == null) ? 0 : value.hashCode());
			hashCode = result;
		}
