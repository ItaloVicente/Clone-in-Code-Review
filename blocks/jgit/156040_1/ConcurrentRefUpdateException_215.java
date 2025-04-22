	public ConcurrentRefUpdateException(final String message
		super(rc == null ? message
				: message + ". " + MessageFormat.format(JGitText.get().refUpdateReturnCodeWas
		this.rc = rc;
		this.ref = ref;
	}
