		if (markers == null) {
			if (other.markers != null)
				return false;
		} else if (!markers.equals(other.markers))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
