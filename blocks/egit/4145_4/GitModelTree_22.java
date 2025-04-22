	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		GitModelTree other = (GitModelTree) obj;
		if (getParent() == null) {
			if (other.getParent() != null)
				return false;
		} else if (!getParent().equals(other.getParent()))
			return false;
		if (path == null) {
			if (other.path != null)
				return false;
		} else if (!path.equals(other.path))
			return false;
		return true;
