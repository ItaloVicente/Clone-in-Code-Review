
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((file == null) ? 0 : file.hashCode());
		result = prime * result
				+ ((runnableContext == null) ? 0 : runnableContext.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		FileEditableRevision other = (FileEditableRevision) obj;
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
	}

