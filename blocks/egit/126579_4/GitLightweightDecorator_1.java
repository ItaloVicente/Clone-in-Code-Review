package org.eclipse.egit.ui.internal.decorators;

import java.text.MessageFormat;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.egit.core.internal.indexdiff.IndexDiffChangedListener;
import org.eclipse.egit.core.internal.indexdiff.IndexDiffData;
import org.eclipse.egit.ui.internal.UIText;
import org.eclipse.jface.viewers.ILightweightLabelDecorator;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.LabelProviderChangedEvent;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.ui.PlatformUI;

public abstract class GitDecorator extends LabelProvider
		implements ILightweightLabelDecorator, IndexDiffChangedListener {

	private Object lock = new Object();

	private EventJob eventJob;

	public GitDecorator() {
		org.eclipse.egit.core.Activator.getDefault().getIndexDiffCache()
				.addIndexDiffChangedListener(this);
	}

	@Override
	public void dispose() {
		org.eclipse.egit.core.Activator.getDefault().getIndexDiffCache()
				.removeIndexDiffChangedListener(this);
		Job job;
		synchronized (lock) {
			job = eventJob;
			eventJob = null;
		}
		if (job != null) {
			job.cancel();
		}
	}

	protected void postLabelEvent() {
		getEventJob().post(this);
	}

	protected void fireLabelEvent() {
		LabelProviderChangedEvent event = new LabelProviderChangedEvent(this);
		PlatformUI.getWorkbench().getDisplay()
				.asyncExec(() -> fireLabelProviderChanged(event));
	}

	@Override
	public void indexDiffChanged(Repository repository,
			IndexDiffData indexDiffData) {
		postLabelEvent();
	}

	private EventJob getEventJob() {
		synchronized (lock) {
			if (eventJob == null) {
				eventJob = new EventJob(getName());
				eventJob.setSystem(true);
				eventJob.setUser(false);
			}
			return eventJob;
		}
	}

	protected abstract String getName();

	private static class EventJob extends Job {

		private static final long DELAY = 100L;

		private GitDecorator decorator;

		public EventJob(String name) {
			super(MessageFormat.format(UIText.GitDecorator_jobTitle, name));
		}

		@Override
		public IStatus run(IProgressMonitor monitor) {
			if (decorator != null) {
				decorator.fireLabelEvent();
			}
			return Status.OK_STATUS;
		}

		public void post(GitDecorator source) {
			this.decorator = source;
			if (getState() == SLEEPING || getState() == WAITING) {
				cancel();
			}
			schedule(DELAY);
		}
	}
}
