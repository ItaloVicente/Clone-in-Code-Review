			service.busyCursorWhile(new IRunnableWithProgress() {
				@Override
				public void run(IProgressMonitor monitor) {
					SortingJob job = new SortingJob(CachedMarkerBuilder.this);
					job.run(monitor);
				}
