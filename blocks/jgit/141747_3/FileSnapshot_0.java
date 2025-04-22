	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof FileSnapshot)) {
			return false;
		}
		FileSnapshot other = (FileSnapshot) obj;
		return equals(other);
