			if (result == null) {
				result = new WorkQueue(d);
				final WorkQueue q = result;
				mapDisplayOntoWorkQueue.put(d, result);
				d.asyncExec(() -> d.disposeExec(() -> {
					synchronized (mapDisplayOntoWorkQueue) {
						q.cancelAll();
						mapDisplayOntoWorkQueue.remove(d);
					}
				}));
			}
			return result;
		}
	}
