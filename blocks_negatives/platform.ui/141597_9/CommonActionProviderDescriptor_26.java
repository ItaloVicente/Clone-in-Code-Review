		if (definedId == null) {
			if (other.definedId != null)
				return false;
		} else if (!definedId.equals(other.definedId))
			return false;
		if (visibilityId == null) {
			if (other.visibilityId != null)
				return false;
		} else if (!visibilityId.equals(other.visibilityId))
			return false;
		return true;
