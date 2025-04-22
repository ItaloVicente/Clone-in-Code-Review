    }

    /**
     * Returns whether the status line control is created
     * and not disposed.
     *
     * @return <code>true</code> if the control is created
     *	and not disposed, <code>false</code> otherwise
     */
    private boolean statusLineExist() {
        return statusLine != null && !statusLine.isDisposed();
    }

    @Override
