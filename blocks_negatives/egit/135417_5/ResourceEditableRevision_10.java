		if (file == null) {
			if (other.file != null)
				return false;
		} else if (!file.equals(other.file))
			return false;
		if (runnableContext == null) {
			if (other.runnableContext != null)
				return false;
		} else if (!runnableContext.equals(other.runnableContext))
			return false;
		return true;
