		if (baseVersion == null) {
			if (other.baseVersion != null)
				return false;
		} else if (!baseVersion.equals(other.baseVersion))
			return false;
		if (compareVersion == null) {
			if (other.compareVersion != null)
				return false;
		} else if (!compareVersion.equals(other.compareVersion))
			return false;
		if (repository == null) {
			if (other.repository != null)
				return false;
		} else if (other.repository == null || !repository.getDirectory().equals(
				other.repository.getDirectory()))
