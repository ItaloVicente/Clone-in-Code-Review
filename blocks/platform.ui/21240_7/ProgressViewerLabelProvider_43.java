package org.eclipse.e4.ui.progress.internal;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.e4.ui.progress.WorkbenchJob;
import org.eclipse.e4.ui.progress.internal.FinishedJobs.KeptJobsListener;

public class ProgressViewerContentProvider extends ProgressContentProvider {
	protected AbstractProgressViewer progressViewer;

	private KeptJobsListener keptJobListener;

	private boolean showFinished;

	public ProgressViewerContentProvider(AbstractProgressViewer structured,
			boolean debug, boolean showFinished) {
		super(debug);
		progressViewer = structured;
		this.showFinished = showFinished;
		if (showFinished) {
			FinishedJobs.getInstance().addListener(getKeptJobListener());
		}
	}

	private KeptJobsListener getKeptJobListener() {
		keptJobListener = new KeptJobsListener() {

			public void finished(JobTreeElement jte) {
				final JobTreeElement element = jte;
				Job updateJob = new WorkbenchJob("Refresh finished") {//$NON-NLS-1$
					public IStatus runInUIThread(IProgressMonitor monitor) {
						refresh(new Object[] { element });
						return Status.OK_STATUS;
					}
					
					public boolean shouldSchedule() {
						return !progressViewer.getControl().isDisposed();
					}
					
					
					public boolean shouldRun() {
						return !progressViewer.getControl().isDisposed();
					}
				};
				updateJob.setSystem(true);
				updateJob.schedule();

			}

			public void removed(JobTreeElement jte) {
				final JobTreeElement element = jte;
				Job updateJob = new WorkbenchJob("Remove finished") {//$NON-NLS-1$
					public IStatus runInUIThread(IProgressMonitor monitor) {
						if (element == null) {
							refresh();
						} else {
							ProgressViewerContentProvider.this
									.remove(new Object[] { element });
						}
						return Status.OK_STATUS;
					}
				};
				updateJob.setSystem(true);
				updateJob.schedule();

			}

		};
		return keptJobListener;
	}

	public void refresh() {
		progressViewer.refresh(true);
	}

	public void refresh(Object[] elements) {
		Object[] refreshes = getRoots(elements, true);
		for (int i = 0; i < refreshes.length; i++) {
			progressViewer.refresh(refreshes[i], true);
		}
	}

	public Object[] getElements(Object inputElement) {
		Object[] elements = super.getElements(inputElement);

		if (!showFinished)
			return elements;

		Set kept = FinishedJobs.getInstance().getKeptAsSet();

		if (kept.size() == 0)
			return elements;

		Set all = new HashSet();

		for (int i = 0; i < elements.length; i++) {
			Object element = elements[i];
			all.add(element);
		}

		Iterator keptIterator = kept.iterator();
		while (keptIterator.hasNext()) {
			JobTreeElement next = (JobTreeElement) keptIterator.next();
			if (next.getParent() != null && all.contains(next.getParent()))
				continue;
			all.add(next);

		}

		return all.toArray();
	}

	private Object[] getRoots(Object[] elements, boolean subWithParent) {
		if (elements.length == 0) {
			return elements;
		}
		HashSet roots = new HashSet();
		for (int i = 0; i < elements.length; i++) {
			JobTreeElement element = (JobTreeElement) elements[i];
			if (element.isJobInfo()) {
				GroupInfo group = ((JobInfo) element).getGroupInfo();
				if (group == null) {
					roots.add(element);
				} else {
					if (subWithParent) {
						roots.add(group);
					}
				}
			} else {
				roots.add(element);
			}
		}
		return roots.toArray();
	}

	public void add(Object[] elements) {
		progressViewer.add(elements);

	}

	public void remove(Object[] elements) {
		progressViewer.remove(elements);

	}

	public void dispose() {
		super.dispose();
		if (keptJobListener != null) {
			FinishedJobs.getInstance().removeListener(keptJobListener);
		}
	}
}
