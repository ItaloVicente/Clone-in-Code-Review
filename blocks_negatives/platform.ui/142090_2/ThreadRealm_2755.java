        return Thread.currentThread() == thread;
    }

    /**
     * @return thread, <code>null</code> if not
     *         {@link #init(Thread) initialized}
     */
    public Thread getThread() {
        return thread;
    }

    /**
     * Queues the provided <code>runnable</code>.
     *
     * @param runnable
     */
    @Override
