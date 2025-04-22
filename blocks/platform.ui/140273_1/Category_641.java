	public boolean setDescription(String description) {
		if (!Util.equals(description, this.description)) {
			this.description = description;
			hashCode = HASH_INITIAL;
			string = null;
			return true;
		}
