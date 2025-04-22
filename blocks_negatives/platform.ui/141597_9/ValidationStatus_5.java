		if (getSeverity() != other.getSeverity())
			return false;
		if (getMessage() == null) {
			if (other.getMessage() != null)
				return false;
		} else if (!getMessage().equals(other.getMessage()))
			return false;
		if (getException() == null) {
			if (other.getException() != null)
				return false;
		} else if (!getException().equals(other.getException()))
			return false;
		return true;
