		if (location != null) {
			if (!location.equals(other.location)) {
				return false;
			}
		} else {
			if (other.location != null) {
				return false;
			}
		}
		return name.equals(other.name);
