        return getJob().getState() != Job.NONE;
    }

    /**
     * Return whether or not the receiver is blocked.
     *
     * @return boolean <code>true</code> if this is a currently
     * blocked job.
     */
    public boolean isBlocked() {
        return getBlockedStatus() != null;
    }

    /**
     * Return whether or not the job was cancelled in the UI.
     *
     * @return boolean
     */
    public boolean isCanceled() {
        return canceled;
    }

    @Override
