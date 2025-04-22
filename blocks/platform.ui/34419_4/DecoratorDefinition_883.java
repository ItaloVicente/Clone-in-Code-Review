package org.eclipse.ui.internal.decorators;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.jface.resource.ResourceManager;
import org.eclipse.jface.viewers.DecorationContext;
import org.eclipse.jface.viewers.IDecorationContext;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.LabelProviderChangedEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.internal.WorkbenchMessages;
import org.eclipse.ui.progress.UIJob;
import org.eclipse.ui.progress.WorkbenchJob;

public class DecorationScheduler {

	static final ILabelProviderListener[] EMPTY_LISTENER_LIST = new ILabelProviderListener[0];

	Map resultCache = new HashMap();

	List awaitingDecoration = new ArrayList();

	Set pendingUpdate = new HashSet();

	Object pendingKey = new Object();

	Map awaitingDecorationValues = new HashMap();

	DecoratorManager decoratorManager;

	boolean shutdown = false;

	Job decorationJob;

	UIJob updateJob;

	private Collection removedListeners = Collections
			.synchronizedSet(new HashSet());

	private Job clearJob;

	static final int NEEDS_INIT = -1;

	static final int UPDATE_DELAY = 100;

	DecorationScheduler(DecoratorManager manager) {
		decoratorManager = manager;
		createDecorationJob();
	}


	public String decorateWithText(String text, Object element,
			Object adaptedElement, IDecorationContext context) {

		DecorationResult decoration = getResult(element, adaptedElement,
				context);

		if (decoration == null) {
			return text;
		}

		return decoration.decorateWithText(text);

	}


	synchronized void queueForDecoration(Object element, Object adaptedElement,
			boolean forceUpdate, String undecoratedText,
			IDecorationContext context) {

		Assert.isNotNull(context);
		DecorationReference reference = (DecorationReference) awaitingDecorationValues
				.get(element);
		if (reference != null) {
			if (forceUpdate) {// Make sure we don't loose a force
				reference.setForceUpdate(forceUpdate);
			}
			reference.addContext(context);
		} else {
			reference = new DecorationReference(element, adaptedElement,
					context);
			reference.setForceUpdate(forceUpdate);
			reference.setUndecoratedText(undecoratedText);
			awaitingDecorationValues.put(element, reference);
			awaitingDecoration.add(element);
			if (shutdown) {
				return;
			}
			decorationJob.schedule();
		}

	}

	public Image decorateWithOverlays(Image image, Object element,
			Object adaptedElement, IDecorationContext context, ResourceManager manager) {

		DecorationResult decoration = getResult(element, adaptedElement,
				context);

		if (decoration == null) {
			return image;
		}
		return decoration.decorateWithOverlays(image, manager);
	}

	private DecorationResult getResult(Object element, Object adaptedElement,
			IDecorationContext context) {

		if (element == null) {
			return null;
		}

		DecorationResult decoration = internalGetResult(element, context);

		if (decoration == null) {
			queueForDecoration(element, adaptedElement, false, null, context);
			return null;
		}
		return decoration;

	}

	private DecorationResult internalGetResult(Object element,
			IDecorationContext context) {
		Map results = (Map) resultCache.get(context);
		if (results != null) {
			return (DecorationResult) results.get(element);
		}
		return null;
	}

	protected void internalPutResult(Object element,
			IDecorationContext context, DecorationResult result) {
		Map results = (Map) resultCache.get(context);
		if (results == null) {
			results = new HashMap();
			resultCache.put(context, results);
		}
		results.put(element, result);
	}

	synchronized void decorated() {

		if (shutdown) {
			return;
		}

		if (updateJob == null) {
			updateJob = getUpdateJob();
		}

		updateJob.schedule(UPDATE_DELAY);
	}

	synchronized void shutdown() {
		shutdown = true;
	}

	synchronized DecorationReference nextElement() {

		if (shutdown || awaitingDecoration.isEmpty()) {
			return null;
		}
		Object element = awaitingDecoration.remove(0);

		return (DecorationReference) awaitingDecorationValues.remove(element);
	}

	private void createDecorationJob() {
		decorationJob = new Job(
				WorkbenchMessages.DecorationScheduler_CalculationJobName) {
			@Override
			public IStatus run(IProgressMonitor monitor) {

				synchronized (DecorationScheduler.this) {
					if (shutdown) {
						return Status.CANCEL_STATUS;
					}
				}

				while (updatesPending()) {

					try {
						Thread.sleep(100);
					} catch (InterruptedException e) {
						schedule();
						return Status.CANCEL_STATUS;
					}
				}

				monitor.beginTask(
						WorkbenchMessages.DecorationScheduler_CalculatingTask,
						100);
				DecorationReference reference;
				monitor.worked(5);
				int workCount = 5;
				while ((reference = nextElement()) != null) {

					if (workCount < 90) {
						monitor.worked(1);
						workCount++;
					}

					monitor.subTask(reference.getSubTask());
					Object element = reference.getElement();
					boolean force = reference.shouldForceUpdate();
					IDecorationContext[] contexts = reference.getContexts();
					for (int i = 0; i < contexts.length; i++) {
						IDecorationContext context = contexts[i];
						ensureResultCached(element, force, context);
					}

					synchronized (DecorationScheduler.this) {
						if (awaitingDecoration.isEmpty()) {
							decorated();
						}
					}
				}
				monitor.worked(100 - workCount);
				monitor.done();
				return Status.OK_STATUS;
			}

			private void ensureResultCached(Object element, boolean force,
					IDecorationContext context) {
				boolean elementIsCached = internalGetResult(element, context) != null;
				if (elementIsCached) {
					synchronized (pendingKey) {
						pendingUpdate.add(element);
					}

				}

				if (!elementIsCached) {
					DecorationBuilder cacheResult = new DecorationBuilder(
							context);
					decoratorManager.getLightweightManager().getDecorations(
							element, cacheResult);

					if (cacheResult.hasValue() || force) {


						internalPutResult(element, context, cacheResult
								.createResult());

						synchronized (pendingKey) {
							pendingUpdate.add(element);
						}
						

					}
				}
			}

			@Override
			public boolean belongsTo(Object family) {
				return DecoratorManager.FAMILY_DECORATE == family;
			}

			@Override
			public boolean shouldRun() {
				return PlatformUI.isWorkbenchRunning();
			}
		};

		decorationJob.setSystem(true);
		decorationJob.setPriority(Job.DECORATE);
		decorationJob.schedule();
	}

	protected boolean updatesPending() {
		if (updateJob != null && updateJob.getState() != Job.NONE) {
			return true;
		}
		if (clearJob != null && clearJob.getState() != Job.NONE) {
			return true;
		}
		return false;
	}

	void clearResults() {
		if (clearJob == null) {
			clearJob = getClearJob();
		}
		clearJob.schedule();
	}

	private Job getClearJob() {
		Job clear = new Job(
				WorkbenchMessages.DecorationScheduler_ClearResultsJob) {

			@Override
			protected IStatus run(IProgressMonitor monitor) {
				resultCache.clear();
				return Status.OK_STATUS;
			}

			@Override
			public boolean shouldRun() {
				return PlatformUI.isWorkbenchRunning();
			}

		};
		clear.setSystem(true);

		return clear;
	}

	private WorkbenchJob getUpdateJob() {
		WorkbenchJob job = new WorkbenchJob(
				WorkbenchMessages.DecorationScheduler_UpdateJobName) {

			int currentIndex = NEEDS_INIT;

			LabelProviderChangedEvent labelProviderChangedEvent;

			ILabelProviderListener[] listeners;

			@Override
			public IStatus runInUIThread(IProgressMonitor monitor) {

				synchronized (DecorationScheduler.this) {
					if (shutdown) {
						return Status.CANCEL_STATUS;
					}
				}

				if (currentIndex == NEEDS_INIT) {
					if (hasPendingUpdates()) {
					    resetState();
						return Status.OK_STATUS;
					}
					setUpUpdates();
				}

				if (listeners.length == 0) {
				    resetState();
				    return Status.OK_STATUS;
				}

				monitor.beginTask(
						WorkbenchMessages.DecorationScheduler_UpdatingTask,
						IProgressMonitor.UNKNOWN);

				long startTime = System.currentTimeMillis();
				while (currentIndex < listeners.length) {
					ILabelProviderListener listener = listeners[currentIndex];
					currentIndex++;

					if (!removedListeners.contains(listener)) {
						decoratorManager.fireListener(
								labelProviderChangedEvent, listener);
					}

					if ((System.currentTimeMillis() - startTime) >= UPDATE_DELAY / 2) {
						break;
					}
				}

				monitor.done();

				if (currentIndex >= listeners.length) {
				    resetState();
					if (!hasPendingUpdates()) {
						decorated();
					}
					labelProviderChangedEvent = null;
					listeners = EMPTY_LISTENER_LIST;
				} else {
					schedule(UPDATE_DELAY);// Reschedule if we are not done
				}
				return Status.OK_STATUS;
			}

            private void resetState() {
                currentIndex = NEEDS_INIT;// Reset
                removedListeners.clear();
                if (awaitingDecoration.isEmpty()) {
                    resultCache.clear();
                }
            }
            
			private void setUpUpdates() {
				removedListeners.clear();
				currentIndex = 0;
				synchronized (pendingKey) {
					Object[] elements = pendingUpdate
							.toArray(new Object[pendingUpdate.size()]);
					pendingUpdate.clear();
					labelProviderChangedEvent = new LabelProviderChangedEvent(
							decoratorManager, elements);
				}
				listeners = decoratorManager.getListeners();
			}

			@Override
			public boolean belongsTo(Object family) {
				return DecoratorManager.FAMILY_DECORATE == family;
			}

			@Override
			public boolean shouldRun() {
				return PlatformUI.isWorkbenchRunning();
			}
		};

		job.setSystem(true);
		return job;
	}

	public boolean isDecorationReady(Object element, IDecorationContext context) {
		return internalGetResult(element, context) != null;
	}

	public Color getBackgroundColor(Object element, Object adaptedElement) {
		DecorationResult decoration = getResult(element, adaptedElement,
				DecorationContext.DEFAULT_CONTEXT);

		if (decoration == null) {
			return null;
		}
		return decoration.getBackgroundColor();
	}

	public Font getFont(Object element, Object adaptedElement) {
		DecorationResult decoration = getResult(element, adaptedElement,
				DecorationContext.DEFAULT_CONTEXT);

		if (decoration == null) {
			return null;
		}
		return decoration.getFont();
	}

	public Color getForegroundColor(Object element, Object adaptedElement) {
		DecorationResult decoration = getResult(element, adaptedElement,
				DecorationContext.DEFAULT_CONTEXT);

		if (decoration == null) {
			return null;
		}
		return decoration.getForegroundColor();
	}

	public boolean processingUpdates() {
		return !hasPendingUpdates() && !awaitingDecoration.isEmpty();
	}

	void listenerRemoved(ILabelProviderListener listener) {
		if (updatesPending()) {// Only keep track of them if there are updates
			removedListeners.add(listener);
		}
		if (!updatesPending()) {
			removedListeners.remove(listener);
		}

	}

	boolean hasPendingUpdates() {
		synchronized (pendingKey) {
			return pendingUpdate.isEmpty();
		}

	}
}
