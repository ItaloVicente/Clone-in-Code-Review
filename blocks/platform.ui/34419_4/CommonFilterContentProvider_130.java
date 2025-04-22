package org.eclipse.ui.internal.navigator.extensions;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.runtime.SafeRunner;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.StructuredViewerInternals;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.StructuredViewer;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Item;
import org.eclipse.ui.internal.navigator.NavigatorContentService;
import org.eclipse.ui.internal.navigator.NavigatorSafeRunnable;
import org.eclipse.ui.internal.navigator.Policy;

public class StructuredViewerManager {

	private StructuredViewer viewer;

	private Object cachedOldInput;

	private Object cachedNewInput;

	private Map viewerDataMap;
	
	static class StructuredViewerAccess extends StructuredViewerInternals {
		static class Listener implements StructuredViewerInternals.AssociateListener {
			private final NavigatorContentService contentService;
			private final Map viewerDataMap;
			public Listener(NavigatorContentService contentService, Map viewerDataMap) {
				this.contentService = contentService;
				this.viewerDataMap = viewerDataMap;
			}
			@Override
			public void associate(Object element, Item item) {
				NavigatorContentDescriptor desc = contentService.getContribution(element);
				contentService.forgetContribution(element);
				synchronized (viewerDataMap) {
					if (viewerDataMap.containsKey(element)) {
						if (Policy.DEBUG_VIEWER_MAP)
							System.out.println("associate: SKIPPED " + element + " item: " + item + " desc: " + desc + " FOUND"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
						return;
					}
					viewerDataMap.put(element, desc);
					if (Policy.DEBUG_VIEWER_MAP)
						System.out.println("associate: " + element + " item: " + item + " desc: " + desc); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
				}
			}
			@Override
			public void disassociate(Item item) {
				synchronized (viewerDataMap) {
					if (Policy.DEBUG_VIEWER_MAP)
						System.out.println("disassociate:  item: " + item + " object: " + item.getData()); //$NON-NLS-1$ //$NON-NLS-2$
					viewerDataMap.remove(item.getData());
				}
			}

			@Override
			public void filteredOut(Object element) {
				contentService.forgetContribution(element);
				synchronized (viewerDataMap) {
					if (Policy.DEBUG_VIEWER_MAP)
						System.out.println("filteredOut: object: " + element); //$NON-NLS-1$
					viewerDataMap.remove(element);
				}
			}
		}
		protected static void hookAssociateListener(StructuredViewer v, Map viewerDataMap, NavigatorContentService contentService) {
			StructuredViewerInternals.setAssociateListener(v, new Listener(contentService, viewerDataMap));
		}
	}
	
	public Object getData(Object element) {
		synchronized (viewerDataMap) {
			return viewerDataMap.get(element);
		}
	}

	public void resetViewerData() {
		synchronized (viewerDataMap) {
			if (Policy.DEBUG_VIEWER_MAP)
				System.out.println("viewer map RESET"); //$NON-NLS-1$
			viewerDataMap.clear();
		}
	}

	public StructuredViewerManager(StructuredViewer aViewer, NavigatorContentService contentService) {
		super();
		viewer = aViewer;
		viewerDataMap = new HashMap();
		StructuredViewerAccess.hookAssociateListener(viewer, viewerDataMap, contentService);
	}

	public Viewer getViewer() {
		return viewer;
	}

	public void inputChanged(Object anOldInput, Object aNewInput) {
		cachedOldInput = anOldInput;
		cachedNewInput = aNewInput;
	}

	public void inputChanged(Viewer aViewer, Object anOldInput, Object aNewInput) {
		viewer = (StructuredViewer) aViewer;
		cachedOldInput = anOldInput;
		cachedNewInput = aNewInput;
	}

	public boolean initialize(final IStructuredContentProvider aContentProvider) {
		final boolean[] result = new boolean[1];
		SafeRunner.run(new NavigatorSafeRunnable() {
			@Override
			public void run() throws Exception {
				if (aContentProvider != null) {
					aContentProvider.inputChanged(viewer, cachedOldInput, cachedNewInput);
				}
				result[0] = true;
			}
		});
		return result[0];
	}

	public void safeRefresh() {

		final Viewer localViewer = viewer;

		if (localViewer == null || localViewer.getControl().isDisposed())
			return;
		Display display = localViewer.getControl().getDisplay();
		if (display.isDisposed())
			return;
		display.syncExec(new Runnable() {
			@Override
			public void run() {
				if (localViewer.getControl().isDisposed())
					return;
				SafeRunner.run(new NavigatorSafeRunnable() {
					@Override
					public void run() throws Exception {
						localViewer.getControl().setRedraw(false);
						localViewer.refresh();
					}
				});
				localViewer.getControl().setRedraw(true);
			}
		});

	}

}
