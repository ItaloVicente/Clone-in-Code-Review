
	private static class ChangeList {

		public static enum CancelMode {
			CANCEL,
			ABANDON,
			INTERRUPT
		}

		private static enum State {
			PRISTINE, SCHEDULED, CANCELING, INTERRUPT, CANCELED, DONE
		}

		private final Repository repository;

		private final String uriText;

		private State state = State.PRISTINE;

		private List<Change> result;

		private InterruptibleJob job;

		public ChangeList(Repository repository, String uriText) {
			this.repository = repository;
			this.uriText = uriText;
		}

		public synchronized boolean cancel(CancelMode cancellation) {
			CancelMode mode = cancellation == null ? CancelMode.CANCEL
					: cancellation;
			switch (state) {
			case PRISTINE:
				finish(false);
				return true;
			case SCHEDULED:
				state = State.CANCELING;
				boolean canceled = job.cancel();
				if (canceled) {
					state = State.CANCELED;
				} else if (mode == CancelMode.INTERRUPT) {
					interrupt();
				} else if (mode == CancelMode.ABANDON) {
					notifyAll();
				}
				return canceled;
			case CANCELING:
				if (mode == CancelMode.INTERRUPT) {
					interrupt();
				} else if (mode == CancelMode.ABANDON) {
					notifyAll();
				}
				return false;
			case INTERRUPT:
				if (mode != CancelMode.CANCEL) {
					notifyAll();
				}
				return false;
			case CANCELED:
				return true;
			default:
				return false;
			}
		}

		public synchronized boolean isDone() {
			return state == State.CANCELED || state == State.DONE;
		}

		public synchronized List<Change> get()
				throws InterruptedException, InvocationTargetException {
			switch (state) {
			case DONE:
			case CANCELED:
				return result;
			case PRISTINE:
				fetch();
				return get();
			default:
				wait();
				if (state == State.CANCELING || state == State.INTERRUPT) {
					throw new InterruptedException();
				}
				return get();
			}
		}

		private synchronized void finish(boolean done) {
			state = done ? State.DONE : State.CANCELED;
			job = null;
			notifyAll(); // We're done, wake up all outstanding get() calls
		}

		private synchronized void interrupt() {
			state = State.INTERRUPT;
			job.interrupt();
			notifyAll(); // Abandon outstanding get() calls
		}

		public synchronized void fetch() throws InvocationTargetException {
			if (job != null || state != State.PRISTINE) {
				return;
			}
			ListRemoteOperation listOp;
			try {
				listOp = new ListRemoteOperation(repository,
						new URIish(uriText),
						Activator.getDefault().getPreferenceStore().getInt(
								UIPreferences.REMOTE_CONNECTION_TIMEOUT));
			} catch (URISyntaxException e) {
				finish(false);
				throw new InvocationTargetException(e);
			}
			job = new InterruptibleJob(MessageFormat.format(
					UIText.FetchGerritChangePage_FetchingRemoteRefsMessage,
					uriText)) {

				@Override
				protected IStatus run(IProgressMonitor monitor) {
					try {
						listOp.run(monitor);
					} catch (InterruptedException e) {
						return Status.CANCEL_STATUS;
					} catch (InvocationTargetException e) {
						synchronized (ChangeList.this) {
							if (state == State.CANCELING
									|| state == State.INTERRUPT) {
								return Status.CANCEL_STATUS;
							}
						}
						return Activator
								.createErrorStatus(e.getLocalizedMessage(), e);
					}
					List<Change> changes = new ArrayList<>();
					for (Ref ref : listOp.getRemoteRefs()) {
						Change change = Change.fromRef(ref.getName());
						if (change != null) {
							changes.add(change);
						}
					}
					Collections.sort(changes, Collections.reverseOrder());
					result = changes;
					return Status.OK_STATUS;
				}

			};
			job.addJobChangeListener(new JobChangeAdapter() {

				@Override
				public void done(IJobChangeEvent event) {
					IStatus status = event.getResult();
					finish(status != null && status.isOK());
				}

			});
			job.setUser(false);
			job.setSystem(true);
			state = State.SCHEDULED;
			job.schedule();
		}

		private static abstract class InterruptibleJob extends Job {

			public InterruptibleJob(String name) {
				super(name);
			}

			public void interrupt() {
				Thread thread = getThread();
				if (thread != null) {
					thread.interrupt();
				}
			}
		}
	}
