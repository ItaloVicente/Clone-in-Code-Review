	@Override
	public String toString() {
		return this.getClass().getSimpleName() + "('" + job.getName() //$NON-NLS-1$
				+ "' isUser=" + job.isUser() //$NON-NLS-1$
				+ " isBlocked=" + isBlocked() //$NON-NLS-1$
				+ " priority=" + job.getPriority() //$NON-NLS-1$
				+ ")"; //$NON-NLS-1$
	}

