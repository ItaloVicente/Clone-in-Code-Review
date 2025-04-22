	public boolean setDescription(String description) {
		if (!Objects.equals(description, this.description)) {
			this.description = description;
			hashCode = HASH_INITIAL;
			string = null;
			return true;
		}
