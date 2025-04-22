			SafeRunner.run(new SafeRunnable() {

				@Override
				public void run() throws Exception {
					listener.selectionChanged(event);
				}
			});
