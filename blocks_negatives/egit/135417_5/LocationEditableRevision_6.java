		final int prime = 31;
		int result = super.hashCode();
		result = prime * result
				+ ((location == null) ? 0 : location.hashCode());
		result = prime * result
				+ ((runnableContext == null) ? 0 : runnableContext.hashCode());
		return result;
