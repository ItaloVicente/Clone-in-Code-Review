package org.eclipse.ui.internal.navigator;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import org.osgi.service.prefs.BackingStoreException;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.SafeRunner;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.preferences.IEclipsePreferences;
import org.eclipse.core.runtime.preferences.InstanceScope;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.StructuredViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.ui.IMemento;
import org.eclipse.ui.internal.navigator.dnd.NavigatorDnDService;
import org.eclipse.ui.internal.navigator.extensions.ExtensionSequenceNumberComparator;
import org.eclipse.ui.internal.navigator.extensions.NavigatorContentDescriptor;
import org.eclipse.ui.internal.navigator.extensions.NavigatorContentDescriptorManager;
import org.eclipse.ui.internal.navigator.extensions.NavigatorContentExtension;
import org.eclipse.ui.internal.navigator.extensions.NavigatorViewerDescriptor;
import org.eclipse.ui.internal.navigator.extensions.NavigatorViewerDescriptorManager;
import org.eclipse.ui.internal.navigator.extensions.SafeDelegateTreeContentProvider;
import org.eclipse.ui.internal.navigator.extensions.StructuredViewerManager;
import org.eclipse.ui.internal.navigator.sorters.NavigatorSorterService;
import org.eclipse.ui.navigator.ICommonLabelProvider;
import org.eclipse.ui.navigator.IDescriptionProvider;
import org.eclipse.ui.navigator.IExtensionActivationListener;
import org.eclipse.ui.navigator.IExtensionStateModel;
import org.eclipse.ui.navigator.IMementoAware;
import org.eclipse.ui.navigator.INavigatorActivationService;
import org.eclipse.ui.navigator.INavigatorContentDescriptor;
import org.eclipse.ui.navigator.INavigatorContentExtension;
import org.eclipse.ui.navigator.INavigatorContentService;
import org.eclipse.ui.navigator.INavigatorContentServiceListener;
import org.eclipse.ui.navigator.INavigatorDnDService;
import org.eclipse.ui.navigator.INavigatorFilterService;
import org.eclipse.ui.navigator.INavigatorPipelineService;
import org.eclipse.ui.navigator.INavigatorSaveablesService;
import org.eclipse.ui.navigator.INavigatorSorterService;
import org.eclipse.ui.navigator.INavigatorViewerDescriptor;

public class NavigatorContentService implements IExtensionActivationListener,
		IMementoAware, INavigatorContentService {

	public static final String WIDGET_KEY = "org.eclipse.ui.navigator"; //$NON-NLS-1$
	
	private static final NavigatorContentDescriptorManager CONTENT_DESCRIPTOR_REGISTRY = NavigatorContentDescriptorManager
			.getInstance();

	private static final NavigatorViewerDescriptorManager VIEWER_DESCRIPTOR_REGISTRY = NavigatorViewerDescriptorManager
			.getInstance();

	private static final ITreeContentProvider[] NO_CONTENT_PROVIDERS = new ITreeContentProvider[0];

	private static final ILabelProvider[] NO_LABEL_PROVIDERS = new ILabelProvider[0];

	private static final INavigatorContentDescriptor[] NO_DESCRIPTORS = new INavigatorContentDescriptor[0];

	private static final String[] NO_EXTENSION_IDS = new String[0];

	private final NavigatorViewerDescriptor viewerDescriptor;

	private final List<INavigatorContentServiceListener> listeners = new ArrayList<INavigatorContentServiceListener>();

	private final Map<INavigatorContentDescriptor, NavigatorContentExtension> contentExtensions = new HashMap<INavigatorContentDescriptor, NavigatorContentExtension>();

	private StructuredViewerManager structuredViewerManager;

	private ITreeContentProvider[] rootContentProviders;

	private ITreeContentProvider contentProvider;

	private Map<Object, INavigatorContentDescriptor> contributionMemory;
	private Map<Object, INavigatorContentDescriptor> contributionMemoryFirstClass;
	
	private ILabelProvider labelProvider;

	private final VisibilityAssistant assistant;

	private NavigatorFilterService navigatorFilterService;

	private NavigatorSorterService navigatorSorterService;

	private INavigatorPipelineService navigatorPipelineService;

	private INavigatorDnDService navigatorDnDService;

	private INavigatorActivationService navigatorActivationService;

	private NavigatorSaveablesService navigatorSaveablesService;

	private NavigatorExtensionStateService navigatorExtensionStateService;

	private IDescriptionProvider descriptionProvider;

	private boolean contentProviderInitialized;

	private boolean labelProviderInitialized;

	private boolean isDisposed;
	
	public NavigatorContentService(String aViewerId) {
		super();
		aViewerId = aViewerId != null ? aViewerId : ""; //$NON-NLS-1$
		viewerDescriptor = VIEWER_DESCRIPTOR_REGISTRY
				.getNavigatorViewerDescriptor(aViewerId);
		assistant = new VisibilityAssistant(viewerDescriptor,
				getActivationService());
		getActivationService().addExtensionActivationListener(this);
		contributionMemory = new HashMap<Object, INavigatorContentDescriptor>();
		contributionMemoryFirstClass = new HashMap<Object, INavigatorContentDescriptor>();
	}

	public NavigatorContentService(String aViewerId, StructuredViewer aViewer) {
		this(aViewerId);
		structuredViewerManager = new StructuredViewerManager(aViewer, this);
	}

	@Override
	public String[] getVisibleExtensionIds() {

		List<String> visibleExtensionIds = new ArrayList<String>();

		NavigatorContentDescriptor[] descriptors = CONTENT_DESCRIPTOR_REGISTRY
				.getAllContentDescriptors();
		for (int i = 0; i < descriptors.length; i++) {
			if (assistant.isVisible(descriptors[i].getId())) {
				visibleExtensionIds.add(descriptors[i].getId());
			}
		}
		if (visibleExtensionIds.isEmpty()) {
			return NO_EXTENSION_IDS;
		}
		return visibleExtensionIds
				.toArray(new String[visibleExtensionIds.size()]);

	}

	@Override
	public INavigatorContentDescriptor[] getVisibleExtensions() {
		List<NavigatorContentDescriptor> visibleDescriptors = new ArrayList<NavigatorContentDescriptor>();

		NavigatorContentDescriptor[] descriptors = CONTENT_DESCRIPTOR_REGISTRY
				.getAllContentDescriptors();
		for (int i = 0; i < descriptors.length; i++) {
			if (assistant.isVisible(descriptors[i].getId())) {
				visibleDescriptors.add(descriptors[i]);
			}
		}
		if (visibleDescriptors.isEmpty()) {
			return NO_DESCRIPTORS;
		}
		return visibleDescriptors
				.toArray(new INavigatorContentDescriptor[visibleDescriptors
						.size()]);

	}

		List<NavigatorContentDescriptor> result = new ArrayList<NavigatorContentDescriptor>();

		NavigatorContentDescriptor[] descriptors = CONTENT_DESCRIPTOR_REGISTRY
				.getContentDescriptorsWithSaveables();
		for (int i = 0; i < descriptors.length; i++) {
			if (assistant.isVisible(descriptors[i].getId())
					&& assistant.isActive(descriptors[i])) {
				result.add(descriptors[i]);
			}
		}
		if (result.isEmpty()) {
			return NO_DESCRIPTORS;
		}
		return result
				.toArray(new INavigatorContentDescriptor[result.size()]);

	}

	@Override
	public INavigatorContentDescriptor[] bindExtensions(String[] extensionIds,
			boolean isRoot) {
		if (extensionIds == null || extensionIds.length == 0) {
			return NO_DESCRIPTORS;
		}

		for (int i = 0; i < extensionIds.length; i++) {
			assistant.bindExtensions(extensionIds, isRoot);
		}
		Set<INavigatorContentDescriptor> boundDescriptors = new HashSet<INavigatorContentDescriptor>();
		INavigatorContentDescriptor descriptor;
		for (int i = 0; i < extensionIds.length; i++) {
			descriptor = CONTENT_DESCRIPTOR_REGISTRY
					.getContentDescriptor(extensionIds[i]);
			if (descriptor != null) {
				boundDescriptors.add(descriptor);
			}
		}

		if (boundDescriptors.size() == 0) {
			return NO_DESCRIPTORS;
		}
		
		if (Policy.DEBUG_EXTENSION_SETUP) {
			System.out.println("bindExtensions: " + //$NON-NLS-1$
					boundDescriptors);
		}
		return boundDescriptors
				.toArray(new INavigatorContentDescriptor[boundDescriptors
						.size()]);

	}

	@Override
	public ITreeContentProvider createCommonContentProvider() {
		if (contentProviderInitialized) {
			return contentProvider;
		}
		synchronized (this) {
			if (contentProvider == null) {
				contentProvider = new NavigatorContentServiceContentProvider(
						this);
			}
			contentProviderInitialized = true;
		}
		return contentProvider;
	}

	@Override
	public ILabelProvider createCommonLabelProvider() {
		if (labelProviderInitialized) {
			return labelProvider;
		}
		synchronized (this) {
			if (labelProvider == null) {
				labelProvider = new NavigatorContentServiceLabelProvider(this);
			}
			labelProviderInitialized = true;
		}
		return labelProvider;
	}

	@Override
	public IDescriptionProvider createCommonDescriptionProvider() {
		if (descriptionProvider != null) {
			return descriptionProvider;
		}
		synchronized (this) {
			if (descriptionProvider == null) {
				descriptionProvider = new NavigatorContentServiceDescriptionProvider(
						this);
			}
		}
		return descriptionProvider;
	}

	@Override
	public void dispose() {
		if (navigatorSaveablesService != null) {
			assistant.removeListener(navigatorSaveablesService);
		}
		if (navigatorSorterService != null) {
			assistant.removeListener(navigatorSorterService);
		}
		synchronized (this) {
			for (Iterator<NavigatorContentExtension> contentItr = contentExtensions.values().iterator(); contentItr
					.hasNext();) {
				contentItr.next().dispose();
			}
		}
		getActivationService().removeExtensionActivationListener(this);
		assistant.dispose();
		isDisposed = true;
	}

	protected void updateService(Viewer aViewer, Object anOldInput,
			Object aNewInput) {

		if (isDisposed)
			return;
		synchronized (this) {

			if (structuredViewerManager == null) {
				structuredViewerManager = new StructuredViewerManager((StructuredViewer) aViewer, this);
				structuredViewerManager.inputChanged(anOldInput, aNewInput);
			} else {
				structuredViewerManager.inputChanged(aViewer, anOldInput,
						aNewInput);
			}

			for (Iterator<NavigatorContentExtension> contentItr = contentExtensions.values().iterator(); contentItr
					.hasNext();) {
				NavigatorContentExtension ext = contentItr
						.next();
				if (ext.isLoaded()) {
					structuredViewerManager
							.initialize(ext.internalGetContentProvider());
				}
			}

			rootContentProviders = extractContentProviders(findRootContentExtensions(aNewInput));
		}
	}

	@Override
	public IExtensionStateModel findStateModel(String anExtensionId) {
		if (anExtensionId == null) {
			return null;
		}
		INavigatorContentDescriptor desc = CONTENT_DESCRIPTOR_REGISTRY
				.getContentDescriptor(anExtensionId);
		if (desc == null) {
			return null;
		}
		INavigatorContentExtension ext = getExtension(desc);
		if (ext == null) {
			return null;
		}
		return ext.getStateModel();
	}

	public ITreeContentProvider[] findRootContentProviders(Object anElement) {
		if (rootContentProviders != null) {
			return rootContentProviders;
		}
		synchronized (this) {
			if (rootContentProviders == null) {
				rootContentProviders = extractContentProviders(findRootContentExtensions(anElement));

			}
		}
		return rootContentProviders;
	}
	
	public Collection<NavigatorContentExtension> findPossibleLabelExtensions(Object anElement) {
		LinkedHashSet<NavigatorContentExtension> contributors = new LinkedHashSet<NavigatorContentExtension>();
		INavigatorContentDescriptor sourceDescriptor = getSourceOfContribution(anElement);
		
		Set<INavigatorContentDescriptor> possibleChildDescriptors = findDescriptorsWithPossibleChild(anElement, false);

		if (sourceDescriptor != null) {
			possibleChildDescriptors.add(sourceDescriptor);
		}

		for (Iterator<INavigatorContentDescriptor> iter = possibleChildDescriptors.iterator(); iter.hasNext();) {
			NavigatorContentDescriptor ncd = (NavigatorContentDescriptor) iter.next();
			findOverridingLabelExtensions(anElement, ncd, contributors);
		}
		
		return contributors;
	}

	private void findOverridingLabelExtensions(Object anElement,
			INavigatorContentDescriptor descriptor, LinkedHashSet<NavigatorContentExtension> contributors) {
		ListIterator iter = ((NavigatorContentDescriptor) descriptor).getOverridingExtensionsListIterator(false);
		while (iter.hasPrevious()) {
			INavigatorContentDescriptor child = (INavigatorContentDescriptor) iter.previous();
			if (assistant.isVisibleAndActive(child) && child.isPossibleChild(anElement)) {
				findOverridingLabelExtensions(anElement, child, contributors);
			}
		}
		contributors.add(getExtension(descriptor));
	}
	
	public ILabelProvider[] findRelevantLabelProviders(Object anElement) {
		Collection<NavigatorContentExtension> extensions = findPossibleLabelExtensions(anElement);
		
		if (extensions.size() == 0) {
			return NO_LABEL_PROVIDERS;
		}
		List<ICommonLabelProvider> resultProvidersList = new ArrayList<ICommonLabelProvider>();
		for (Iterator<NavigatorContentExtension> itr = extensions.iterator(); itr.hasNext();) {
			resultProvidersList.add(itr.next().getLabelProvider());
		}
		return resultProvidersList.toArray(new ILabelProvider[resultProvidersList.size()]);
	}

	@Override
	public Set<NavigatorContentExtension> findRootContentExtensions(Object anElement) {
		return findRootContentExtensions(anElement, true);
	}

	public Set<NavigatorContentExtension> findRootContentExtensions(Object anElement,
			boolean toRespectViewerRoots) {

		SortedSet<NavigatorContentExtension> rootExtensions = new TreeSet<NavigatorContentExtension>(
				ExtensionSequenceNumberComparator.INSTANCE);
		if (toRespectViewerRoots

			NavigatorContentDescriptor[] descriptors = CONTENT_DESCRIPTOR_REGISTRY
					.getAllContentDescriptors();

			NavigatorContentExtension extension = null;
			for (int i = 0; i < descriptors.length; i++) {
				if (isActive(descriptors[i].getId())
						&& isRootExtension(descriptors[i].getId())) {
					extension = getExtension(descriptors[i]);
					if (!extension.hasLoadingFailed()) {
						rootExtensions.add(extension);
					}
				}
			}
		}
		if (rootExtensions.isEmpty()) {
			return findContentExtensionsByTriggerPoint(anElement);
		}
		return rootExtensions;
	}

	public Set<NavigatorContentExtension> findOverrideableContentExtensionsForPossibleChild(
			Object anElement) {
		Set<NavigatorContentExtension> overrideableExtensions = new TreeSet<NavigatorContentExtension>(
				ExtensionSequenceNumberComparator.INSTANCE);
		Set<INavigatorContentDescriptor> descriptors = findDescriptorsWithPossibleChild(anElement, false);
		for (Iterator<INavigatorContentDescriptor> iter = descriptors.iterator(); iter.hasNext();) {
			INavigatorContentDescriptor descriptor = iter
					.next();
			if (descriptor.hasOverridingExtensions()) {
				overrideableExtensions.add(getExtension(descriptor));
			}
		}
		return overrideableExtensions;
	}

	@Override
	public INavigatorContentDescriptor getContentDescriptorById(
			String anExtensionId) {
		return CONTENT_DESCRIPTOR_REGISTRY.getContentDescriptor(anExtensionId);
	}

	@Override
	public INavigatorContentExtension getContentExtensionById(
			String anExtensionId) {
		NavigatorContentDescriptor descriptor = CONTENT_DESCRIPTOR_REGISTRY
				.getContentDescriptor(anExtensionId);
		if (descriptor != null)
			return getExtension(descriptor);
		return null;
	}

	@Override
	public Set<NavigatorContentExtension> findContentExtensionsByTriggerPoint(Object anElement) {
		return findContentExtensionsByTriggerPoint(anElement, true, !CONSIDER_OVERRIDES);
	}

	public Set<NavigatorContentExtension> findContentExtensionsByTriggerPoint(Object anElement,
			boolean toLoadIfNecessary, boolean computeOverrides) {
		Set<INavigatorContentDescriptor> enabledDescriptors = findDescriptorsByTriggerPoint(anElement, computeOverrides);
		return extractDescriptorInstances(enabledDescriptors, toLoadIfNecessary);
	}

	@Override
	public Set<NavigatorContentExtension> findContentExtensionsWithPossibleChild(Object anElement) {
		return findContentExtensionsWithPossibleChild(anElement, true);
	}

	public Set<NavigatorContentExtension> findContentExtensionsWithPossibleChild(Object anElement,
			boolean toLoadIfNecessary) {
		Set<INavigatorContentDescriptor> enabledDescriptors = findDescriptorsWithPossibleChild(anElement);
		return extractDescriptorInstances(enabledDescriptors, toLoadIfNecessary);
	}

	public void rememberContribution(INavigatorContentDescriptor source,
			INavigatorContentDescriptor firstClassSource, Object element) {
		synchronized (this) {
			if (contributionMemory.get(element) == null
					|| contributionMemoryFirstClass.get(element) == firstClassSource) {
				if (Policy.DEBUG_RESOLUTION)
					System.out
							.println("rememberContribution: " + Policy.getObjectString(element) + " source: " + source); //$NON-NLS-1$//$NON-NLS-2$
				contributionMemory.put(element, source);
				contributionMemoryFirstClass.put(element, firstClassSource);
			}
		}
	}

	public void forgetContribution(Object element) {
		synchronized (this) {
			contributionMemory.remove(element);
			contributionMemoryFirstClass.remove(element);
		}
	}

	public NavigatorContentDescriptor getContribution(Object element)
	{
		NavigatorContentDescriptor desc;
		synchronized (this) {
			desc = (NavigatorContentDescriptor) contributionMemory.get(element);
		}
		return desc;
	}
	
	public int getContributionMemorySize() {
		synchronized (this) {
			return contributionMemory.size();
		}
	}
	
	public synchronized NavigatorContentDescriptor getSourceOfContribution(Object element) {
		if (element == null)
			return null;
		if (structuredViewerManager == null)
			return null;
		NavigatorContentDescriptor src;
		synchronized (this) {
			src = (NavigatorContentDescriptor) contributionMemory.get(element);
		}
		if (src != null)
			return src;
		return (NavigatorContentDescriptor) structuredViewerManager.getData(element);
	}
	public static final boolean CONSIDER_OVERRIDES = true;
	
	public Set<INavigatorContentDescriptor> findDescriptorsByTriggerPoint(Object anElement, boolean considerOverrides) {
		NavigatorContentDescriptor descriptor = getSourceOfContribution(anElement);
		Set<INavigatorContentDescriptor> result = new TreeSet<INavigatorContentDescriptor>(ExtensionSequenceNumberComparator.INSTANCE);
		if (descriptor != null) {
			result.add(descriptor);
		}
		result.addAll(CONTENT_DESCRIPTOR_REGISTRY
				.findDescriptorsForTriggerPoint(anElement, assistant, considerOverrides));
		return result;
	}

	public Set<INavigatorContentDescriptor> findDescriptorsWithPossibleChild(Object anElement) {
		return findDescriptorsWithPossibleChild(anElement, true);
	}

	public Set<INavigatorContentDescriptor> findDescriptorsWithPossibleChild(Object anElement,
			boolean toComputeOverrides) {
		Set<INavigatorContentDescriptor> result = new TreeSet<INavigatorContentDescriptor>(ExtensionSequenceNumberComparator.INSTANCE);
		result.addAll(CONTENT_DESCRIPTOR_REGISTRY
				.findDescriptorsForPossibleChild(anElement, assistant,
						toComputeOverrides));
		return result;
	}

	@Override
	public void onExtensionActivation(String aViewerId,
			String[] aNavigatorExtensionId, boolean toEnable) {
		synchronized (this) {
			SafeRunner.run(new NavigatorSafeRunnable() {
				@Override
				public void run() throws Exception {
					NavigatorContentDescriptor key;
					NavigatorContentExtension extension;
					for (Iterator<INavigatorContentDescriptor> iter = contentExtensions.keySet().iterator(); iter
							.hasNext();) {
						key = (NavigatorContentDescriptor) iter.next();
						INavigatorActivationService activation = getActivationService();
						if (!activation.isNavigatorExtensionActive(key.getId())) {
							extension = contentExtensions
									.get(key);
							iter.remove();
							extension.dispose();
						}
					}
				}
			});
		}
		if (structuredViewerManager != null) {
			structuredViewerManager.resetViewerData();
		}
		update();
	}

	@Override
	public void update() {
		rootContentProviders = null;
		if (structuredViewerManager != null) {
			structuredViewerManager.safeRefresh();
		}
	}

	@Override
	public final String getViewerId() {
		return viewerDescriptor.getViewerId();
	}

	public Object getViewerElementData(Object element) {
		if (structuredViewerManager != null) {
			return structuredViewerManager.getData(element);
		}
		return null;
	}
	
	public final NavigatorContentExtension getExtension(
			INavigatorContentDescriptor aDescriptorKey) {
		return getExtension(aDescriptorKey, true);
	}

	public final NavigatorContentExtension getExtension(
			INavigatorContentDescriptor aDescriptorKey,
			boolean toLoadIfNecessary) {
		NavigatorContentExtension extension = contentExtensions
				.get(aDescriptorKey);
		if (extension != null || !toLoadIfNecessary) {
			return extension;
		}

		synchronized (this) {
			extension = contentExtensions
					.get(aDescriptorKey);
			if (extension == null) {
				contentExtensions.put(aDescriptorKey,
						(extension = new NavigatorContentExtension(
								(NavigatorContentDescriptor) aDescriptorKey,
								this, structuredViewerManager)));
				notifyListeners(extension);
			}
		}
		return extension;

	}

	@Override
	public INavigatorViewerDescriptor getViewerDescriptor() {
		return viewerDescriptor;
	}

	@Override
	public void restoreState(final IMemento aMemento) {
		synchronized (this) {
			for (Iterator extensionItr = getExtensions().iterator(); extensionItr.hasNext();) {
				final NavigatorContentExtension element = (NavigatorContentExtension) extensionItr
						.next();
				SafeRunner.run(new NavigatorSafeRunnable(((NavigatorContentDescriptor) element
						.getDescriptor()).getConfigElement()) {
					@Override
					public void run() throws Exception {
						element.restoreState(aMemento);
					}
				});
			}
		}
	}

	@Override
	public void saveState(final IMemento aMemento) {
		synchronized (this) {
			for (Iterator extensionItr = getExtensions().iterator(); extensionItr.hasNext();) {
				final NavigatorContentExtension element = (NavigatorContentExtension) extensionItr
						.next();
				SafeRunner.run(new NavigatorSafeRunnable(((NavigatorContentDescriptor) element
						.getDescriptor()).getConfigElement()) {
					@Override
					public void run() throws Exception {
						element.saveState(aMemento);
					}
				});
			}
		}
	}

	@Override
	public boolean isActive(String anExtensionId) {
		return assistant.isActive(anExtensionId);
	}

	@Override
	public boolean isVisible(String anExtensionId) {
		return assistant.isVisible(anExtensionId);
	}

	protected final Collection getExtensions() {
		return (contentExtensions.size() > 0) ? Collections
				.unmodifiableCollection(contentExtensions.values())
				: Collections.EMPTY_LIST;
	}

	@Override
	public void addListener(INavigatorContentServiceListener aListener) {
		listeners.add(aListener);
	}

	@Override
	public INavigatorFilterService getFilterService() {
		if (navigatorFilterService == null) {
			navigatorFilterService = new NavigatorFilterService(this);
		}
		return navigatorFilterService;
	}

	@Override
	public INavigatorSorterService getSorterService() {
		if (navigatorSorterService == null) {
			navigatorSorterService = new NavigatorSorterService(this);
			assistant.addListener(navigatorSorterService);
		}
		return navigatorSorterService;
	}

	@Override
	public INavigatorPipelineService getPipelineService() {
		if (navigatorPipelineService == null) {
			navigatorPipelineService = new NavigatorPipelineService(this);
		}
		return navigatorPipelineService;
	}

	@Override
	public INavigatorDnDService getDnDService() {
		if (navigatorDnDService == null) {
			navigatorDnDService = new NavigatorDnDService(this);
		}
		return navigatorDnDService;
	}

	@Override
	public INavigatorActivationService getActivationService() {

		if (navigatorActivationService == null) {
			navigatorActivationService = new NavigatorActivationService(this);
		}
		return navigatorActivationService;
	}

	@Override
	public INavigatorSaveablesService getSaveablesService() {
		synchronized (this) {
			if (navigatorSaveablesService == null) {
				navigatorSaveablesService = new NavigatorSaveablesService(this);
				assistant.addListener(navigatorSaveablesService);
			}
			return navigatorSaveablesService;
		}
	}

	public NavigatorExtensionStateService getExtensionStateService() {
		if (navigatorExtensionStateService == null) {
			navigatorExtensionStateService = new NavigatorExtensionStateService(
					this);
		}
		return navigatorExtensionStateService;
	}

	public Shell getShell() {
		if (structuredViewerManager != null
				&& structuredViewerManager.getViewer() != null) {
			return structuredViewerManager.getViewer().getControl().getShell();
		}
		return null;
	}

	protected boolean isRootExtension(String anExtensionId) {
		return assistant.isRootExtension(anExtensionId);
	}

	@Override
	public void removeListener(INavigatorContentServiceListener aListener) {
		listeners.remove(aListener);
	}

	@Override
	public String toString() {
		return "ContentService[" + viewerDescriptor.getViewerId() + "]"; //$NON-NLS-1$//$NON-NLS-2$
	}

	private void notifyListeners(final NavigatorContentExtension aDescriptorInstance) {

		if (listeners.size() == 0) {
			return;
		}

		final List<INavigatorContentServiceListener> failedListeners = new ArrayList<INavigatorContentServiceListener>();

		for (Iterator<INavigatorContentServiceListener> listenersItr = listeners.iterator(); listenersItr.hasNext();) {
			final INavigatorContentServiceListener listener = listenersItr
					.next();
			SafeRunner.run(new NavigatorSafeRunnable() {

				@Override
				public void run() throws Exception {
					listener.onLoad(aDescriptorInstance);
				}

				@Override
				public void handleException(Throwable e) {
					super.handleException(e);
					failedListeners.add(listener);
				}
			});
		}

		if (failedListeners.size() > 0) {
			listeners.removeAll(failedListeners);
		}		
	}

	private ITreeContentProvider[] extractContentProviders(
			Set<NavigatorContentExtension> theDescriptorInstances) {
		if (theDescriptorInstances.size() == 0) {
			return NO_CONTENT_PROVIDERS;
		}
		List<SafeDelegateTreeContentProvider> resultProvidersList = new ArrayList<SafeDelegateTreeContentProvider>();
		for (Iterator<NavigatorContentExtension> itr = theDescriptorInstances.iterator(); itr.hasNext();) {
			resultProvidersList.add(itr.next()
					.internalGetContentProvider());
		}
		return resultProvidersList
				.toArray(new ITreeContentProvider[resultProvidersList.size()]);
	}

	private Set<NavigatorContentExtension> extractDescriptorInstances(Set<INavigatorContentDescriptor> theDescriptors,
			boolean toLoadAllIfNecessary) {
		if (theDescriptors.size() == 0) {
			return Collections.EMPTY_SET;
		}
		Set<NavigatorContentExtension> resultInstances = new TreeSet<NavigatorContentExtension>(ExtensionSequenceNumberComparator.INSTANCE);
		for (Iterator<INavigatorContentDescriptor> descriptorIter = theDescriptors.iterator(); descriptorIter
				.hasNext();) {
			NavigatorContentExtension extension = getExtension(
					(NavigatorContentDescriptor) descriptorIter.next(),
					toLoadAllIfNecessary);
			if (extension != null) {
				resultInstances.add(extension);

			}
		}
		return resultInstances;
	}

	public Viewer getViewer() {
		return structuredViewerManager.getViewer();
	}

	
	static IEclipsePreferences getPreferencesRoot() {
		IEclipsePreferences root = (IEclipsePreferences) Platform.getPreferencesService().getRootNode().node(
				InstanceScope.SCOPE);
		return (IEclipsePreferences) root.node(NavigatorPlugin.PLUGIN_ID);
	}
	
	
	static void flushPreferences(IEclipsePreferences prefs) {
		try {
			prefs.flush();
		} catch (BackingStoreException e) {
			IStatus status = new Status(IStatus.ERROR, Platform.PI_RUNTIME, IStatus.ERROR,
					CommonNavigatorMessages.NavigatorContentService_problemSavingPreferences, e);
			Platform.getLog(Platform.getBundle(NavigatorPlugin.PLUGIN_ID)).log(status);
		}
	}

}
