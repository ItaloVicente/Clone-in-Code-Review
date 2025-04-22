package org.eclipse.egit.ui.internal.dialogs;

import java.lang.reflect.InvocationTargetException;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.IJobChangeEvent;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.core.runtime.jobs.JobChangeAdapter;
import org.eclipse.egit.ui.Activator;

public abstract class CancelableFuture<V> {

	public static enum CancelMode {
		CANCEL,
		ABANDON,
		INTERRUPT
	}

	private static enum State {
		PRISTINE, SCHEDULED, CANCELING, INTERRUPT, CANCELED, DONE
	}

	private State state = State.PRISTINE;

	private V result;

	private InterruptibleJob job;

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

	public synchronized boolean isFinished() {
		return state == State.CANCELED || state == State.DONE;
	}

	public synchronized boolean isDone() {
		return state == State.DONE;
	}

	public synchronized V get()
			throws InterruptedException, InvocationTargetException {
		switch (state) {
		case DONE:
		case CANCELED:
			return result;
		case PRISTINE:
			start();
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
		try {
			done();
		} finally {
			notifyAll();
		}
	}

	private synchronized void interrupt() {
		state = State.INTERRUPT;
		job.interrupt();
		notifyAll(); // Abandon outstanding get() calls
	}

	protected void set(V value) {
		result = value;
	}

	protected void prepareRun() throws InvocationTargetException {
	}

	protected String getJobTitle() {
		return ""; //$NON-NLS-1$
	}

	protected abstract void run(IProgressMonitor monitor)
			throws InterruptedException, InvocationTargetException;

	protected void done() {
	}

	public synchronized void start() throws InvocationTargetException {
		if (job != null || state != State.PRISTINE) {
			return;
		}
		try {
			prepareRun();
		} catch (InvocationTargetException e) {
			finish(false);
			throw e;
		}
		job = new InterruptibleJob(getJobTitle()) {

			@Override
			protected IStatus run(IProgressMonitor monitor) {
				try {
					CancelableFuture.this.run(monitor);
					return Status.OK_STATUS;
				} catch (InterruptedException e) {
					return Status.CANCEL_STATUS;
				} catch (InvocationTargetException e) {
					synchronized (CancelableFuture.this) {
						if (state == State.CANCELING
								|| state == State.INTERRUPT) {
							return Status.CANCEL_STATUS;
						}
					}
					return Activator.createErrorStatus(e.getLocalizedMessage(),
							e);
				} catch (RuntimeException e) {
					return Activator.createErrorStatus(e.getLocalizedMessage(),
							e);
				}
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
