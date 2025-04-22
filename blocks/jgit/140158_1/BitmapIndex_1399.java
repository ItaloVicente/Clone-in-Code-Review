
package org.eclipse.jgit.lib;

import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import org.eclipse.jgit.lib.internal.WorkQueue;

public abstract class BatchingProgressMonitor implements ProgressMonitor {
	private long delayStartTime;

	private TimeUnit delayStartUnit = TimeUnit.MILLISECONDS;

	private Task task;

	public void setDelayStart(long time
		delayStartTime = time;
		delayStartUnit = unit;
	}

	@Override
	public void start(int totalTasks) {
	}

	@Override
	public void beginTask(String title
		endTask();
		task = new Task(title
		if (delayStartTime != 0)
			task.delay(delayStartTime
	}

	@Override
	public void update(int completed) {
		if (task != null)
			task.update(this
	}

	@Override
	public void endTask() {
		if (task != null) {
			task.end(this);
			task = null;
		}
	}

	@Override
	public boolean isCancelled() {
		return false;
	}

	protected abstract void onUpdate(String taskName

	protected abstract void onEndTask(String taskName

	protected abstract void onUpdate(String taskName
			int workTotal

	protected abstract void onEndTask(String taskName
			int workTotal

	private static class Task implements Runnable {
		private final String taskName;

		private final int totalWork;

		private volatile boolean display;

		private Future<?> timerFuture;

		private boolean output;

		private int lastWork;

		private int lastPercent;

		Task(String taskName
			this.taskName = taskName;
			this.totalWork = totalWork;
			this.display = true;
		}

		void delay(long time
			display = false;
			timerFuture = WorkQueue.getExecutor().schedule(this
		}

		@Override
		public void run() {
			display = true;
		}

		void update(BatchingProgressMonitor pm
			lastWork += completed;

			if (totalWork == UNKNOWN) {
				if (display) {
					pm.onUpdate(taskName
					output = true;
					restartTimer();
				}
			} else {
				int currPercent = lastWork * 100 / totalWork;
				if (display) {
					pm.onUpdate(taskName
					output = true;
					restartTimer();
					lastPercent = currPercent;
				} else if (currPercent != lastPercent) {
					pm.onUpdate(taskName
					output = true;
					lastPercent = currPercent;
				}
			}
		}

		private void restartTimer() {
			display = false;
			timerFuture = WorkQueue.getExecutor().schedule(this
					TimeUnit.SECONDS);
		}

		void end(BatchingProgressMonitor pm) {
			if (output) {
				if (totalWork == UNKNOWN) {
					pm.onEndTask(taskName
				} else {
					int pDone = lastWork * 100 / totalWork;
					pm.onEndTask(taskName
				}
			}
			if (timerFuture != null)
		}
	}
}
