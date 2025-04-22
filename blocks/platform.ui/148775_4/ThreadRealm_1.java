					if (doProcess) { // ignore spurious wake-ups
						if (queue.isEmpty()) {
							queue.wait();
						} else {
							runnable = queue.getFirst();
						}
