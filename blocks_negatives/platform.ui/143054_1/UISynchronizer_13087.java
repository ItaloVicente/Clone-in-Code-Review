        if ((runnable == null) || lockListener.isUI()
                || !lockListener.isLockOwner()) {
            super.syncExec(runnable);
            return;
        }
        PendingSyncExec work = new PendingSyncExec(runnable);
        work.setOperationThread(Thread.currentThread());
        lockListener.addPendingWork(work);
        asyncExec(() -> lockListener.doPendingWork());
