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
