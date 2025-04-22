package org.eclipse.ui.progress;

import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.ListenerList;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.IJobChangeEvent;
import org.eclipse.core.runtime.jobs.IJobChangeListener;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.core.runtime.jobs.JobChangeAdapter;
import org.eclipse.jface.viewers.AbstractTreeViewer;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.osgi.util.NLS;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.IWorkbenchPartSite;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.internal.progress.ProgressMessages;
import org.eclipse.ui.internal.util.Util;
import org.eclipse.ui.model.IWorkbenchAdapter;

public class DeferredTreeContentManager {

	AbstractTreeViewer treeViewer;

	IWorkbenchSiteProgressService progressService;

	private ListenerList updateCompleteListenerList;

	class DeferredContentFamily {
		protected DeferredTreeContentManager manager;
		protected Object element;

		DeferredContentFamily(DeferredTreeContentManager schedulingManager,
				Object object) {
			this.manager = schedulingManager;
			this.element = object;
		}
	}

	@Deprecated
	public DeferredTreeContentManager(ITreeContentProvider provider,
			AbstractTreeViewer viewer, IWorkbenchPartSite site) {
		this(viewer, site);
	}

	@Deprecated
	public DeferredTreeContentManager(ITreeContentProvider provider,
			AbstractTreeViewer viewer) {
		this(viewer);
	}

	public DeferredTreeContentManager(AbstractTreeViewer viewer,
			IWorkbenchPartSite site) {
		this(viewer);
		Object siteService = Util.getAdapter(site,
				IWorkbenchSiteProgressService.class);
		if (siteService != null) {
			progressService = (IWorkbenchSiteProgressService) siteService;
		}
	}

	public DeferredTreeContentManager(AbstractTreeViewer viewer) {
		treeViewer = viewer;
	}

	public boolean mayHaveChildren(Object element) {
		Assert.isNotNull(element,
				ProgressMessages.DeferredTreeContentManager_NotDeferred);
		IDeferredWorkbenchAdapter adapter = getAdapter(element);
		return adapter != null && adapter.isContainer();
	}

	public Object[] getChildren(final Object parent) {
		IDeferredWorkbenchAdapter element = getAdapter(parent);
		if (element == null) {
			return null;
		}
		PendingUpdateAdapter placeholder = createPendingUpdateAdapter();
		startFetchingDeferredChildren(parent, element, placeholder);
		return new Object[] { placeholder };
	}

	protected PendingUpdateAdapter createPendingUpdateAdapter() {
		return new PendingUpdateAdapter();
	}

	protected IDeferredWorkbenchAdapter getAdapter(Object element) {
		return (IDeferredWorkbenchAdapter) Util.getAdapter(element,
				IDeferredWorkbenchAdapter.class);
	}

	protected void startFetchingDeferredChildren(final Object parent,
			final IDeferredWorkbenchAdapter adapter,
			final PendingUpdateAdapter placeholder) {
		final IElementCollector collector = createElementCollector(parent,
				placeholder);
		cancel(parent);
		String jobName = getFetchJobName(parent, adapter);
		Job job = new Job(jobName) {
			@Override
			public IStatus run(IProgressMonitor monitor) {
				adapter.fetchDeferredChildren(parent, collector, monitor);
				if (monitor.isCanceled()) {
					return Status.CANCEL_STATUS;
				}
				return Status.OK_STATUS;
			}

			@Override
			public boolean belongsTo(Object family) {
				if (family instanceof DeferredContentFamily) {
					DeferredContentFamily contentFamily = (DeferredContentFamily) family;
					if (contentFamily.manager == DeferredTreeContentManager.this) {
						return isParent(contentFamily, parent);
					}
				}
				return false;

			}

			private boolean isParent(DeferredContentFamily family, Object child) {
				if (family.element.equals(child)) {
					return true;
				}
				IWorkbenchAdapter workbenchAdapter = getWorkbenchAdapter(child);
				if (workbenchAdapter == null) {
					return false;
				}
				Object elementParent = workbenchAdapter.getParent(child);
				if (elementParent == null) {
					return false;
				}
				return isParent(family, elementParent);
			}

			private IWorkbenchAdapter getWorkbenchAdapter(Object element) {
				return (IWorkbenchAdapter) Util.getAdapter(element,
						IWorkbenchAdapter.class);
			}
		};
		job.addJobChangeListener(new JobChangeAdapter() {
			@Override
			public void done(IJobChangeEvent event) {
				runClearPlaceholderJob(placeholder);
			}
		});
		job.setRule(adapter.getRule(parent));
		if (progressService == null) {
			job.schedule();
		} else {
			progressService.schedule(job);
		}
	}

	protected String getFetchJobName(Object parent,
			IDeferredWorkbenchAdapter adapter) {
		return NLS.bind(
				ProgressMessages.DeferredTreeContentManager_FetchingName,
				adapter.getLabel(parent));
	}

	protected void addChildren(final Object parent, final Object[] children,
			IProgressMonitor monitor) {
		WorkbenchJob updateJob = new WorkbenchJob(
				ProgressMessages.DeferredTreeContentManager_AddingChildren) {
			@Override
			public IStatus runInUIThread(IProgressMonitor updateMonitor) {
				if (treeViewer.getControl().isDisposed()
						|| updateMonitor.isCanceled()) {
					return Status.CANCEL_STATUS;
				}
				treeViewer.add(parent, children);
				return Status.OK_STATUS;
			}
		};
		updateJob.setSystem(true);
		updateJob.schedule();

	}

	public boolean isDeferredAdapter(Object element) {
		return getAdapter(element) != null;
	}

	protected void runClearPlaceholderJob(final PendingUpdateAdapter placeholder) {
		if (placeholder.isRemoved() || !PlatformUI.isWorkbenchRunning()) {
			return;
		}
		WorkbenchJob clearJob = new WorkbenchJob(
				ProgressMessages.DeferredTreeContentManager_ClearJob) {
			@Override
			public IStatus runInUIThread(IProgressMonitor monitor) {
				if (!placeholder.isRemoved()) {
					Control control = treeViewer.getControl();
					if (control.isDisposed()) {
						return Status.CANCEL_STATUS;
					}
					treeViewer.remove(placeholder);
					placeholder.setRemoved(true);
				}
				return Status.OK_STATUS;
			}
		};
		clearJob.setSystem(true);
		
		if (updateCompleteListenerList != null) {
			Object[] listeners = updateCompleteListenerList.getListeners();
			for (int i = 0; i < listeners.length; i++) {
				clearJob
						.addJobChangeListener((IJobChangeListener) listeners[i]);
			}
		}
		clearJob.schedule();
	}

	public void cancel(Object parent) {
		if (parent == null) {
			return;
		}

		Job.getJobManager().cancel(new DeferredContentFamily(this, parent));
	}

	protected IElementCollector createElementCollector(final Object parent,
			final PendingUpdateAdapter placeholder) {
		return new IElementCollector() {
			@Override
			public void add(Object element, IProgressMonitor monitor) {
				add(new Object[] { element }, monitor);
			}

			@Override
			public void add(Object[] elements, IProgressMonitor monitor) {
				addChildren(parent, elements, monitor);
			}

			@Override
			public void done() {
				runClearPlaceholderJob(placeholder);
			}
		};
	}

	public void addUpdateCompleteListener(IJobChangeListener listener){
		if (listener == null && updateCompleteListenerList != null) {
			Object[] listeners = updateCompleteListenerList.getListeners();
			if (listeners.length == 1) {
				removeUpdateCompleteListener((IJobChangeListener) listeners[0]);
			}
		} else {
			if (updateCompleteListenerList == null) {
				updateCompleteListenerList = new ListenerList();
			}
			updateCompleteListenerList.add(listener);
		}
	}

	public void removeUpdateCompleteListener(IJobChangeListener listener) {
		if (updateCompleteListenerList != null) {
			updateCompleteListenerList.remove(listener);
		}
	}

}
