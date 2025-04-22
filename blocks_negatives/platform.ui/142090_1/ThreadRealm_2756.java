        synchronized (queue) {
            queue.add(runnable);
            queue.notifyAll();
        }
    }

    /**
     * Returns after the realm has completed all runnables currently on its
     * queue.  Do not call from the realm's thread.
     *
     * @throws IllegalStateException
     *             if the ThreadRealm is not blocking on its thread.
     * @throws IllegalStateException
     *             if invoked from the realm's own thread.
     */
    public void processQueue() {
        if (Thread.currentThread() == thread) {
            throw new IllegalStateException(
                    "Cannot execute this method in the realm's own thread");
        }

        try {
            synchronized (queue) {
                while (!queue.isEmpty()) {
                    if (!block)
                        throw new IllegalStateException(
                                "Cannot process queue, ThreadRealm is not blocking on its thread");
                    queue.wait();
                }
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public boolean isBlocking() {
        return block;
    }

    /**
     * Blocks the current thread invoking runnables.
     */
    public void block() {
        if (block) {
            throw new IllegalStateException("Realm is already blocking.");
        }

        if (Thread.currentThread() != thread) {
            throw new IllegalStateException("The current thread is not the correct thread.");
        }

        try {
            block = true;

            synchronized (queue) {
            }

            while (block) {
                Runnable runnable = null;
                synchronized (queue) {
                    if (queue.isEmpty()) {
                        queue.wait();
                    } else {
                        runnable = queue.getFirst();
                    }
                }

                if (runnable != null) {
                    safeRun(runnable);
                    synchronized (queue) {
                        queue.removeFirst();
                        queue.notifyAll();
                    }
                }
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } finally {
            block = false;
        }
    }

    /**
     * Unblocks the thread.
     */
    public void unblock() {
        block = false;

        synchronized (queue) {
            queue.notifyAll();
        }
    }

    /**
     * Blocks until the ThreadRealm is blocking on its own thread.
     */
    public void waitUntilBlocking() {
        if (Thread.currentThread() == thread) {
            throw new IllegalStateException(
                    "Cannot execute this method in the realm's own thread");
        }

        while (!block) {
            synchronized (queue) {
                try {
                    queue.wait();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }
    }
