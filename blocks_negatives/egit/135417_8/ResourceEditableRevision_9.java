		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((file == null) ? 0 : file.hashCode());
		result = prime * result
				+ ((runnableContext == null) ? 0 : runnableContext.hashCode());
		return result;
