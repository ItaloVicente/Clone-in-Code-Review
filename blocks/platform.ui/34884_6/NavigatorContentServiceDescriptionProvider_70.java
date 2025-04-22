package org.eclipse.ui.internal.navigator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.eclipse.core.runtime.SafeRunner;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.ITreePathContentProvider;
import org.eclipse.jface.viewers.TreePath;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.osgi.util.NLS;
import org.eclipse.ui.internal.navigator.extensions.NavigatorContentDescriptor;
import org.eclipse.ui.internal.navigator.extensions.NavigatorContentExtension;
import org.eclipse.ui.internal.navigator.extensions.NavigatorViewerDescriptor;
import org.eclipse.ui.internal.navigator.extensions.SafeDelegateTreeContentProvider;
import org.eclipse.ui.navigator.CommonViewer;
import org.eclipse.ui.navigator.INavigatorContentDescriptor;
import org.eclipse.ui.navigator.INavigatorViewerDescriptor;
import org.eclipse.ui.navigator.IPipelinedTreeContentProvider;
import org.eclipse.ui.navigator.OverridePolicy;

public class NavigatorContentServiceContentProvider implements ITreeContentProvider, ITreePathContentProvider {

	private static final Object[] NO_CHILDREN = new Object[0];

	private final NavigatorContentService contentService;

	private boolean disposeContentService;

	private final boolean enforceHasChildren;

	private Viewer viewer;

	public NavigatorContentServiceContentProvider(String aViewerId) {
		this(new NavigatorContentService(aViewerId));
		disposeContentService = true;
	}

	public NavigatorContentServiceContentProvider(NavigatorContentService aContentService) {
		super();
		contentService = aContentService;
		INavigatorViewerDescriptor vDesc = contentService.getViewerDescriptor();
		enforceHasChildren = vDesc.getBooleanConfigProperty(NavigatorViewerDescriptor.PROP_ENFORCE_HAS_CHILDREN);
	}

	@Override
	public void inputChanged(Viewer aViewer, Object anOldInput, Object aNewInput) {
		viewer = aViewer;
		contentService.updateService(aViewer, anOldInput, aNewInput);
	}

	@Override
	public Object[] getElements(Object anInputElement) {
		Set rootContentExtensions = contentService.findRootContentExtensions(anInputElement);
		return internalGetChildren(anInputElement, anInputElement, rootContentExtensions, ELEMENTS);
	}

	@Override
	public Object[] getChildren(Object aParentElement) {
		Set enabledExtensions = contentService.findContentExtensionsByTriggerPoint(aParentElement);
		return internalGetChildren(aParentElement, aParentElement, enabledExtensions, !ELEMENTS);
	}

	@Override
	public Object[] getChildren(TreePath parentPath) {
		Object aParentElement = internalAsElement(parentPath);
		Set enabledExtensions = contentService.findContentExtensionsByTriggerPoint(aParentElement);
		return internalGetChildren(aParentElement, parentPath, enabledExtensions, !ELEMENTS);
	}
	
	private static final boolean ELEMENTS = true;

	private Object[] internalGetChildren(final Object aParentElement,
			final Object aParentElementOrPath, final Set enabledExtensions, final boolean elements) {
		if (enabledExtensions.size() == 0) {
			return NO_CHILDREN;
		}
		final Set finalSet = new LinkedHashSet();
		final ContributorTrackingSet localSet = new ContributorTrackingSet(contentService);

		for (final Iterator itr = enabledExtensions.iterator(); itr.hasNext();) {
			SafeRunner.run(new NavigatorSafeRunnable() {
				NavigatorContentExtension foundExtension = (NavigatorContentExtension) itr.next();
				Object[] contributedChildren = null;
				NavigatorContentExtension[] overridingExtensions;

				@Override
				public void run() throws Exception {
					if (!isOverridingExtensionInSet(foundExtension.getDescriptor(),
							enabledExtensions)) {
						if (elements)
							contributedChildren = foundExtension.internalGetContentProvider()
									.getElements(aParentElementOrPath);
						else
							contributedChildren = foundExtension.internalGetContentProvider()
									.getChildren(aParentElementOrPath);
						overridingExtensions = foundExtension
								.getOverridingExtensionsForTriggerPoint(aParentElement);
						INavigatorContentDescriptor foundDescriptor = foundExtension
								.getDescriptor();
						localSet.setContributor(foundDescriptor, foundDescriptor);
						localSet.setContents(contributedChildren);

						if (overridingExtensions.length > 0) {
							pipelineChildren(aParentElement, overridingExtensions, foundDescriptor,
									localSet, elements);
						}
						finalSet.addAll(localSet);
					}
				}

				@Override
				public void handleException(Throwable e) {
					NavigatorPlugin.logError(0, NLS.bind(
							CommonNavigatorMessages.Exception_Invoking_Extension, new Object[] {
									foundExtension.getDescriptor().getId(), aParentElement }), e);
				}
			});
		}		

		return finalSet.toArray();
	}

	private void pipelineChildren(Object aParent, NavigatorContentExtension[] theOverridingExtensions,
			INavigatorContentDescriptor firstClassDescriptor, ContributorTrackingSet pipelinedChildren, boolean elements) {
		IPipelinedTreeContentProvider pipelinedContentProvider;
		NavigatorContentExtension[] overridingExtensions;
		for (int i = 0; i < theOverridingExtensions.length; i++) {

			if (theOverridingExtensions[i].internalGetContentProvider().isPipelined()) {
				pipelinedContentProvider = (IPipelinedTreeContentProvider) theOverridingExtensions[i]
						.internalGetContentProvider();
				pipelinedChildren.setContributor(theOverridingExtensions[i]
						.getDescriptor(), firstClassDescriptor);
				if (elements) {
					pipelinedContentProvider.getPipelinedElements(aParent, pipelinedChildren);
				} else {
					pipelinedContentProvider.getPipelinedChildren(aParent, pipelinedChildren);
				}
				overridingExtensions = theOverridingExtensions[i].getOverridingExtensionsForTriggerPoint(aParent);
				if (overridingExtensions.length > 0) {
					pipelineChildren(aParent, overridingExtensions, firstClassDescriptor, pipelinedChildren, elements);
				}
			}
		}
	}

	
	private boolean isOverridingExtensionInSet(INavigatorContentDescriptor aDescriptor, Set theEnabledExtensions) {

		if (aDescriptor.getSuppressedExtensionId() != null /*
				&& aDescriptor.getOverridePolicy() == OverridePolicy.InvokeAlwaysRegardlessOfSuppressedExt) {
			if (theEnabledExtensions.contains(contentService.getExtension(aDescriptor.getOverriddenDescriptor()))) {
				return true;
			}
		}
		return false;
	}

	private boolean isOverridingDescriptorInSet(INavigatorContentDescriptor aDescriptor, Set theEnabledDescriptors) {

		if (aDescriptor.getSuppressedExtensionId() != null /*
				&& aDescriptor.getOverridePolicy() == OverridePolicy.InvokeAlwaysRegardlessOfSuppressedExt) {
			if (theEnabledDescriptors.contains(aDescriptor.getOverriddenDescriptor())) {
				return true;
			}
		}
		return false;
	}

	@Override
	public Object getParent(final Object anElement) {
		final Set extensions = contentService.findContentExtensionsWithPossibleChild(anElement);
		final Object[] parent = new Object[1];

		for (Iterator itr = extensions.iterator(); itr.hasNext();) {
			final NavigatorContentExtension foundExtension = (NavigatorContentExtension) itr.next();

			SafeRunner.run(new NavigatorSafeRunnable() {
				NavigatorContentExtension[] overridingExtensions;

				@Override
				public void run() throws Exception {
					if (!isOverridingExtensionInSet(foundExtension.getDescriptor(), extensions)) {
						parent[0] = foundExtension.internalGetContentProvider()
								.getParent(anElement);
						overridingExtensions = foundExtension
								.getOverridingExtensionsForPossibleChild(anElement);
						if (overridingExtensions.length > 0) {
							parent[0] = pipelineParent(anElement, overridingExtensions, parent);
						}
					}
				}

				@Override
				public void handleException(Throwable e) {
					NavigatorPlugin.logError(0, NLS.bind(
							CommonNavigatorMessages.Exception_Invoking_Extension, new Object[] {
									foundExtension.getDescriptor().getId(), anElement }), e);
				}
			});

			if (parent[0] != null) {
				return parent[0];
			}
		}
		return parent[0];
	}

	@Override
	public TreePath[] getParents(Object anElement) {
		List paths = new ArrayList();
		TreePathCompiler compiler = new TreePathCompiler(anElement);
		Set compilers = findPaths(compiler);
		for (Iterator iter = compilers.iterator(); iter.hasNext();) {
			TreePathCompiler c = (TreePathCompiler) iter.next();
			paths.add(c.createParentPath());
		}
		return (TreePath[]) paths.toArray(new TreePath[paths.size()]);

	}
	private Object pipelineParent(Object anInputElement, NavigatorContentExtension[] theOverridingExtensions,
			Object theCurrentParent) {
		IPipelinedTreeContentProvider pipelinedContentProvider;
		NavigatorContentExtension[] overridingExtensions;
		Object aSuggestedParent = null;
		for (int i = 0; i < theOverridingExtensions.length; i++) {

			if (theOverridingExtensions[i].internalGetContentProvider().isPipelined()) {
				pipelinedContentProvider = (IPipelinedTreeContentProvider) theOverridingExtensions[i]
						.internalGetContentProvider();

				aSuggestedParent = pipelinedContentProvider.getPipelinedParent(anInputElement, aSuggestedParent);

				overridingExtensions = theOverridingExtensions[i]
						.getOverridingExtensionsForTriggerPoint(anInputElement);
				if (overridingExtensions.length > 0) {
					aSuggestedParent = pipelineParent(anInputElement, overridingExtensions, aSuggestedParent);
				}
			}
		}
		return aSuggestedParent != null ? aSuggestedParent : theCurrentParent;
	}

	@Override
	public boolean hasChildren(final Object anElementOrPath) {
		final Object anElement = internalAsElement(anElementOrPath);
		final Set enabledExtensions = contentService.findContentExtensionsByTriggerPoint(anElement);
		final boolean suggestedHasChildren[] = new boolean[1];

		for (final Iterator itr = enabledExtensions.iterator(); itr.hasNext();) {
			SafeRunner.run(new NavigatorSafeRunnable() {
				NavigatorContentExtension ext;

				@Override
				public void run() throws Exception {
					ext = (NavigatorContentExtension) itr.next();

					if (!ext.isLoaded() && !enforceHasChildren) {
						suggestedHasChildren[0] = true;
						return;
					}

					NavigatorContentExtension[] overridingExtensions;
					if (!isOverridingExtensionInSet(ext.getDescriptor(), enabledExtensions)) {
						SafeDelegateTreeContentProvider cp = ext.internalGetContentProvider();
						suggestedHasChildren[0] |= callNormalHasChildren(anElementOrPath,
								anElement, cp);
						overridingExtensions = ext
								.getOverridingExtensionsForTriggerPoint(anElement);

						if (overridingExtensions.length > 0) {
							suggestedHasChildren[0] = pipelineHasChildren(anElementOrPath,
									anElement, overridingExtensions, suggestedHasChildren[0]);
						}
						if (suggestedHasChildren[0]) {
							return;

						}
					}
				}

				@Override
				public void handleException(Throwable e) {
					NavigatorPlugin.logError(0, NLS.bind(
							CommonNavigatorMessages.Exception_Invoking_Extension, new Object[] {
									ext.getDescriptor().getId(), anElementOrPath }), e);
				}
			});
		}
		return suggestedHasChildren[0];
	}

	@Override
	public boolean hasChildren(TreePath path) {
		return hasChildren((Object)path);
	}

	private boolean callNormalHasChildren(Object anElementOrPath, Object anElement, SafeDelegateTreeContentProvider cp) {
		if (cp.isTreePath() && anElementOrPath instanceof TreePath) {
			ITreePathContentProvider tpcp = (ITreePathContentProvider) cp;
			return tpcp.hasChildren((TreePath) anElementOrPath);
		}
		return ((ITreeContentProvider) cp).hasChildren(anElement);
	}

	private boolean pipelineHasChildren(Object anElementOrPath, Object anElement,
			NavigatorContentExtension[] theOverridingExtensions, boolean suggestedHasChildren) {
		NavigatorContentExtension[] overridingExtensions;
		for (int i = 0; i < theOverridingExtensions.length; i++) {

			SafeDelegateTreeContentProvider cp = theOverridingExtensions[i].internalGetContentProvider();
			if (cp.isPipelinedHasChildren()) {
				suggestedHasChildren = cp.hasPipelinedChildren(
						anElement, suggestedHasChildren);
				overridingExtensions = theOverridingExtensions[i]
						.getOverridingExtensionsForTriggerPoint(anElement);
				if (overridingExtensions.length > 0) {
					suggestedHasChildren = pipelineHasChildren(anElementOrPath, anElement,
							overridingExtensions, suggestedHasChildren);
				}
			} else  {
				suggestedHasChildren |= callNormalHasChildren(anElementOrPath, anElement, cp);
			}
		}
		return suggestedHasChildren;
	}		

	@Override
	public void dispose() {
		if (disposeContentService) {
			contentService.dispose();
		}
	}

	private Object internalAsElement(Object parentElementOrPath) {
		if (parentElementOrPath instanceof TreePath) {
			TreePath tp = (TreePath) parentElementOrPath;
			if (tp.getSegmentCount() > 0) {
				return tp.getLastSegment();
			}
			return viewer.getInput();
		}
		return parentElementOrPath;
	}

	class CyclicPathException extends Exception {

		private static final long serialVersionUID = 2111962579612444989L;

		protected CyclicPathException(TreePathCompiler compiler, Object invalidSegment, boolean asChild) {
			super("Cannot add " + invalidSegment + //$NON-NLS-1$ 
					" to the list of segments in " + compiler + //$NON-NLS-1$ 
					(asChild ? " as a child." : " as a parent.")); //$NON-NLS-1$ //$NON-NLS-2$
		}
	}

	class TreePathCompiler {

		private final LinkedList segments = new LinkedList();

		protected TreePathCompiler(Object segment) {
			segments.add(segment);
		}

		protected TreePathCompiler(TreePathCompiler aCompiler) {
			segments.addAll(aCompiler.segments);
		}

		protected TreePathCompiler(TreePath aPath) {
			for (int i = 0; i < aPath.getSegmentCount(); i++) {
				segments.addLast(aPath.getSegment(i));
			}
		}

		protected void addParent(Object segment) throws CyclicPathException {
			if (segments.contains(segment)) {
				throw new CyclicPathException(this, segment, false);
			}
			segments.addFirst(segment);
		}

		protected void addChild(Object segment) throws CyclicPathException {
			if (segments.contains(segment)) {
				throw new CyclicPathException(this, segment, false);
			}
			segments.addLast(segment);
		}

		public TreePath createPath() {
			return new TreePath(segments.toArray());
		}

		public TreePath createParentPath() {
			LinkedList parentSegments = new LinkedList(segments);
			parentSegments.removeLast();
			return new TreePath(parentSegments.toArray());
		}

		public Object getLastSegment() {
			return segments.getLast();
		}

		public Object getFirstSegment() {
			return segments.getFirst();
		}

		@Override
		public String toString() {

			StringBuffer buffer = new StringBuffer();
			for (Iterator iter = segments.iterator(); iter.hasNext();) {
				Object segment = iter.next();
				buffer.append(segment).append("::"); //$NON-NLS-1$  
			}
			return buffer.toString();
		}

	}

	private Set findPaths(TreePathCompiler aPathCompiler) {

		Set/* <Object> */parents = findParents(aPathCompiler.getFirstSegment());
		Set/* <TreePathCompiler> */parentPaths = new LinkedHashSet();
		Set/* <TreePathCompiler> */foundPaths = Collections.EMPTY_SET;
		if (parents.size() > 0) {
			for (Iterator parentIter = parents.iterator(); parentIter.hasNext();) {
				Object parent = (Object) parentIter.next();
				TreePathCompiler c = new TreePathCompiler(aPathCompiler);
				try {
					c.addParent(parent);
					foundPaths = findPaths(c);
				} catch (CyclicPathException cpe) {
					String msg = cpe.getMessage() != null ? cpe.getMessage() : cpe.toString();
					NavigatorPlugin.logError(0, msg, cpe);
				}
				if (foundPaths.isEmpty())
					parentPaths.add(c);
				else
					parentPaths.addAll(foundPaths);
			}
		}
		return parentPaths;

	}

	private Set findParents(final Object anElement) {
		final Set descriptors = contentService.findDescriptorsWithPossibleChild(anElement, false);
		final Set parents = new LinkedHashSet();

		for (final Iterator itr = descriptors.iterator(); itr.hasNext();) {
			SafeRunner.run(new NavigatorSafeRunnable() {
				NavigatorContentDescriptor foundDescriptor;
				NavigatorContentExtension foundExtension;
				Object parent = null;

				@Override
				public void run() throws Exception {
					foundDescriptor = (NavigatorContentDescriptor) itr.next();
					foundExtension = contentService.getExtension(foundDescriptor);

					if (!isOverridingDescriptorInSet(foundExtension.getDescriptor(), descriptors)) {
						if (foundExtension.internalGetContentProvider().isTreePath()) {
							TreePath[] parentTreePaths = ((ITreePathContentProvider) foundExtension
									.internalGetContentProvider()).getParents(anElement);

							for (int i = 0; i < parentTreePaths.length; i++) {

								parent = parentTreePaths[i].getLastSegment();
								if ((parent = findParent(foundExtension, anElement, parent)) != null)
									parents.add(parent);
							}

						} else {
							parent = foundExtension.internalGetContentProvider().getParent(
									anElement);
							if ((parent = findParent(foundExtension, anElement, parent)) != null)
								parents.add(parent);
						}
					}
				}

				@Override
				public void handleException(Throwable e) {
					NavigatorPlugin.logError(0, NLS.bind(
							CommonNavigatorMessages.Exception_Invoking_Extension, new Object[] {
									foundExtension.getDescriptor().getId(), anElement }), e);
				}
			});
		}

		return parents;

	}

	private Object findParent(NavigatorContentExtension anExtension, Object anElement, Object aSuggestedParent) {

		Object lastValidParent = aSuggestedParent;
		Object suggestedOverriddenParent = null;
		IPipelinedTreeContentProvider piplineContentProvider;
		NavigatorContentExtension[] overridingExtensions = anExtension
				.getOverridingExtensionsForPossibleChild(anElement);
		for (int i = 0; i < overridingExtensions.length; i++) {
			if (overridingExtensions[i].internalGetContentProvider().isPipelined()) {
				piplineContentProvider = (IPipelinedTreeContentProvider) overridingExtensions[i].internalGetContentProvider();
				suggestedOverriddenParent = piplineContentProvider.getPipelinedParent(anElement, lastValidParent);

				if (suggestedOverriddenParent != null && !suggestedOverriddenParent.equals(aSuggestedParent))
					lastValidParent = suggestedOverriddenParent;

				lastValidParent = findParent(overridingExtensions[i], anElement, lastValidParent);
			}

		}
		return lastValidParent;
	}

}
