    public ConcurrentRefUpdateException(final String message,
                                        final Ref ref,
                                        final RefUpdate.Result rc) {
        super(rc == null ? message : message + ". " + MessageFormat.format(JGitText.get().refUpdateReturnCodeWas,
                                                                           new Object[]{rc}));
        this.rc = rc;
        this.ref = ref;
    }
