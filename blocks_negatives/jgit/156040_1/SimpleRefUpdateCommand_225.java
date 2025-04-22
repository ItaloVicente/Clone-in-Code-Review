    private void forceUpdate(final RefUpdate ru,
                             final ObjectId id) throws java.io.IOException, ConcurrentRefUpdateException {
        final RefUpdate.Result rc = ru.forceUpdate();
        switch (rc) {
            case NEW:
            case FORCED:
            case FAST_FORWARD:
            case NO_CHANGE:
                break;
            case REJECTED:
            case LOCK_FAILURE:
                throw new ConcurrentRefUpdateException(JGitText.get().couldNotLockHEAD,
                                                       ru.getRef(),
                                                       rc);
            default:
                throw new JGitInternalException(MessageFormat.format(JGitText.get().updatingRefFailed,
                                                                     Constants.HEAD,
                                                                     id.toString(),
                                                                     rc));
        }
    }
