		if (getClass() != obj.getClass())
			return false;
		GitModelBlob other = (GitModelBlob) obj;
		if (change == null) {
			if (other.change != null)
				return false;
		} else if (!change.equals(other.change))
			return false;
		if (path == null) {
			if (other.path != null)
				return false;
		} else if (!path.equals(other.path))
