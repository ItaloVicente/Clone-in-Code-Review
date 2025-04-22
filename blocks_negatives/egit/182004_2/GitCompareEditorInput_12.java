		if (baseVersion == null) {
			if (other.baseVersion != null)
				return false;
		} else if (!baseVersion.equals(other.baseVersion))
			return false;
		if (compareVersion == null) {
			if (other.compareVersion != null)
				return false;
		} else if (!compareVersion.equals(other.compareVersion))
