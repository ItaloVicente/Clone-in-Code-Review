		if (repository == null) {
			if (other.repository != null)
				return false;
		} else if (other.repository == null || !repository.getDirectory().equals(
				other.repository.getDirectory()))
			return false;
		if (!Arrays.equals(resources, other.resources))
			return false;
		return true;
