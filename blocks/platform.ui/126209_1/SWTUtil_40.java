				d.asyncExec(() -> d.disposeExec(() -> {
					synchronized (mapDisplayOntoWorkQueue) {
						q.cancelAll();
						mapDisplayOntoWorkQueue.remove(d);
					}
				}));
