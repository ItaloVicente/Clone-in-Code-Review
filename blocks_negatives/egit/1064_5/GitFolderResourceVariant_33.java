	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		} else if (obj == null) {
			return false;
		} else if (getClass() != obj.getClass()) {
			return false;
		}
		GitFolderResourceVariant other = (GitFolderResourceVariant) obj;
		return getResource().equals(other.getResource());
