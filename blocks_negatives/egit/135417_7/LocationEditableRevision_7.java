		if (location == null) {
			if (other.location != null)
				return false;
		} else if (!location.equals(other.location))
			return false;
		if (runnableContext == null) {
			if (other.runnableContext != null)
				return false;
		} else if (!runnableContext.equals(other.runnableContext))
			return false;
		return true;
