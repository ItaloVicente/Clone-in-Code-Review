            if (result == null) {
                result = new WorkQueue(d);
                final WorkQueue q = result;
                mapDisplayOntoWorkQueue.put(d, result);
                d.asyncExec(new Runnable() {
                    @Override
					public void run() {
                        d.disposeExec(new Runnable() {
                            @Override
							public void run() {
                                synchronized (mapDisplayOntoWorkQueue) {
                                    q.cancelAll();
                                    mapDisplayOntoWorkQueue.remove(d);
                                }
                            }
                        });
                    }
                });
            }
            return result;
        }
    }
