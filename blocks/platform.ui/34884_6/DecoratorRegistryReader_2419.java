package org.eclipse.ui.internal.decorators;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.StringTokenizer;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtension;
import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.ListenerList;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.SafeRunner;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.dynamichelpers.ExtensionTracker;
import org.eclipse.core.runtime.dynamichelpers.IExtensionChangeHandler;
import org.eclipse.core.runtime.dynamichelpers.IExtensionTracker;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.jface.resource.LocalResourceManager;
import org.eclipse.jface.resource.ResourceManager;
import org.eclipse.jface.util.SafeRunnable;
import org.eclipse.jface.viewers.DecorationContext;
import org.eclipse.jface.viewers.IBaseLabelProvider;
import org.eclipse.jface.viewers.IColorDecorator;
import org.eclipse.jface.viewers.IDecorationContext;
import org.eclipse.jface.viewers.IDelayedLabelDecorator;
import org.eclipse.jface.viewers.IFontDecorator;
import org.eclipse.jface.viewers.ILabelDecorator;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.ILightweightLabelDecorator;
import org.eclipse.jface.viewers.LabelDecorator;
import org.eclipse.jface.viewers.LabelProviderChangedEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.IDecoratorManager;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.internal.IPreferenceConstants;
import org.eclipse.ui.internal.LegacyResourceSupport;
import org.eclipse.ui.internal.Workbench;
import org.eclipse.ui.internal.WorkbenchMessages;
import org.eclipse.ui.internal.WorkbenchPlugin;
import org.eclipse.ui.internal.registry.IWorkbenchRegistryConstants;
import org.eclipse.ui.internal.util.PrefUtil;
import org.eclipse.ui.internal.util.Util;
import org.eclipse.ui.progress.WorkbenchJob;

public class DecoratorManager implements ILabelProviderListener,
		IDecoratorManager, IExtensionChangeHandler {

	private static String EXTENSIONPOINT_UNIQUE_ID = WorkbenchPlugin.PI_WORKBENCH
			+ "." + IWorkbenchRegistryConstants.PL_DECORATORS; //$NON-NLS-1$

	public static final Object FAMILY_DECORATE = new Object();

	private DecorationScheduler scheduler;

	private LightweightDecoratorManager lightweightManager;

	private ListenerList listeners = new ListenerList();

	private FullDecoratorDefinition[] fullDefinitions;

	private FullTextDecoratorRunnable fullTextRunnable = new FullTextDecoratorRunnable();

	private FullImageDecoratorRunnable fullImageRunnable = new FullImageDecoratorRunnable();

	private static final FullDecoratorDefinition[] EMPTY_FULL_DEF = new FullDecoratorDefinition[0];

	private final String PREFERENCE_SEPARATOR = ","; //$NON-NLS-1$

	private final String VALUE_SEPARATOR = ":"; //$NON-NLS-1$

	private final String P_TRUE = "true"; //$NON-NLS-1$

	private final String P_FALSE = "false"; //$NON-NLS-1$

	private LocalResourceManager resourceManager;

	
	private static class ManagedWorkbenchLabelDecorator extends LabelDecorator
			implements ILabelDecorator, IDelayedLabelDecorator,
			IColorDecorator, IFontDecorator {

		private final DecoratorManager decoratorManager;
		private LocalResourceManager resourceManager;

		public ManagedWorkbenchLabelDecorator(DecoratorManager decoratorManager) {
			this.decoratorManager = decoratorManager;
			this.resourceManager = null;
		}

		private LocalResourceManager getResourceManager() {
			if (resourceManager == null) {
				resourceManager = new LocalResourceManager(decoratorManager
						.getResourceManager());
			}
			return resourceManager;
		}

		@Override
		public Image decorateImage(Image image, Object element,
				IDecorationContext context) {
			return decoratorManager.decorateImage(image, element, context,
					getResourceManager());
		}

		@Override
		public String decorateText(String text, Object element,
				IDecorationContext context) {
			return decoratorManager.decorateText(text, element, context);
		}

		@Override
		public boolean prepareDecoration(Object element, String originalText,
				IDecorationContext context) {
			return decoratorManager.prepareDecoration(element, originalText,
					context);
		}

		@Override
		public boolean prepareDecoration(Object element, String originalText) {
			return prepareDecoration(element, originalText,
					DecorationContext.DEFAULT_CONTEXT);
		}

		@Override
		public Font decorateFont(Object element) {
			return decoratorManager.decorateFont(element);
		}

		@Override
		public Color decorateBackground(Object element) {
			return decoratorManager.decorateBackground(element);
		}

		@Override
		public Color decorateForeground(Object element) {
			return decoratorManager.decorateForeground(element);
		}

		@Override
		public Image decorateImage(Image image, Object element) {
			return decorateImage(image, element,
					DecorationContext.DEFAULT_CONTEXT);
		}

		@Override
		public String decorateText(String text, Object element) {
			return decorateText(text, element,
					DecorationContext.DEFAULT_CONTEXT);
		}

		@Override
		public void addListener(ILabelProviderListener listener) {
			decoratorManager.addListener(listener);
		}

		@Override
		public void dispose() {
			if (resourceManager != null) {
				resourceManager.dispose();
				resourceManager = null;
			}
		}

		@Override
		public boolean isLabelProperty(Object element, String property) {
			return decoratorManager.isLabelProperty(element, property);
		}

		@Override
		public void removeListener(ILabelProviderListener listener) {
			decoratorManager.removeListener(listener);
		}

	}

	public DecoratorManager() {

		scheduler = new DecorationScheduler(this);
		IExtensionTracker tracker = PlatformUI.getWorkbench()
				.getExtensionTracker();
		tracker.registerHandler(this, ExtensionTracker
				.createExtensionPointFilter(getExtensionPointFilter()));

		resourceManager = null;
	}

	private void initializeDecoratorDefinitions() {
		DecoratorRegistryReader reader = new DecoratorRegistryReader();
		Collection values = reader
				.readRegistry(Platform.getExtensionRegistry());

		ArrayList full = new ArrayList();
		ArrayList lightweight = new ArrayList();
		Iterator allDefinitions = values.iterator();
		IExtensionTracker configurationElementTracker = PlatformUI
				.getWorkbench().getExtensionTracker();
		while (allDefinitions.hasNext()) {
			DecoratorDefinition nextDefinition = (DecoratorDefinition) allDefinitions
					.next();
			if (nextDefinition.isFull()) {
				full.add(nextDefinition);
			} else {
				lightweight.add(nextDefinition);
			}

			configurationElementTracker.registerObject(nextDefinition
					.getConfigurationElement().getDeclaringExtension(),
					nextDefinition, IExtensionTracker.REF_WEAK);
		}

		fullDefinitions = new FullDecoratorDefinition[full.size()];
		full.toArray(fullDefinitions);

		LightweightDecoratorDefinition[] lightweightDefinitions = new LightweightDecoratorDefinition[lightweight
				.size()];
		lightweight.toArray(lightweightDefinitions);

		lightweightManager = new LightweightDecoratorManager(
				lightweightDefinitions);

		applyDecoratorsPreference();
	}

	public void addDecorator(DecoratorDefinition definition) {
		if (definition.isFull()) {
			if (getFullDecoratorDefinition(definition.getId()) == null) {
				FullDecoratorDefinition[] oldDefs = getFullDefinitions();
				fullDefinitions = new FullDecoratorDefinition[fullDefinitions.length + 1];
				System
						.arraycopy(oldDefs, 0, fullDefinitions, 0,
								oldDefs.length);
				fullDefinitions[oldDefs.length] = (FullDecoratorDefinition) definition;
				clearCaches();
				updateForEnablementChange();
			}
		} else {
			if (getLightweightManager().addDecorator(
					(LightweightDecoratorDefinition) definition)) {
				clearCaches();
				updateForEnablementChange();
			}
		}
		((Workbench) PlatformUI.getWorkbench()).getExtensionTracker()
				.registerObject(
						definition.getConfigurationElement()
								.getDeclaringExtension(), definition,
						IExtensionTracker.REF_WEAK);
	}

	static Collection getDecoratorsFor(Object element,
			DecoratorDefinition[] enabledDefinitions) {

		ArrayList decorators = new ArrayList();

		for (int i = 0; i < enabledDefinitions.length; i++) {
			if (enabledDefinitions[i].isEnabledFor(element)) {
				decorators.add(enabledDefinitions[i]);
			}
		}

		return decorators;

	}

	@Override
	public void addListener(ILabelProviderListener listener) {
		listeners.add(listener);
	}

	@Override
	public void removeListener(ILabelProviderListener listener) {
		listeners.remove(listener);
		scheduler.listenerRemoved(listener);
	}

	ILabelProviderListener[] getListeners() {
		Object[] array = listeners.getListeners();
		ILabelProviderListener[] listenerArray = new ILabelProviderListener[array.length];
		System.arraycopy(array, 0, listenerArray, 0, listenerArray.length);
		return listenerArray;
	}

	void fireListener(final LabelProviderChangedEvent event,
			final ILabelProviderListener listener) {
		SafeRunner.run(new SafeRunnable() {
			@Override
			public void run() {
				listener.labelProviderChanged(event);
			}
		});

	}

	void fireListeners(final LabelProviderChangedEvent event) {
		Object[] array = listeners.getListeners();
		for (int i = 0; i < array.length; i++) {
			final ILabelProviderListener l = (ILabelProviderListener) array[i];
			SafeRunner.run(new SafeRunnable() {
				@Override
				public void run() {
					l.labelProviderChanged(event);
				}
			});
		}
	}

	void fireListenersInUIThread(final LabelProviderChangedEvent event) {

		if (!PlatformUI.isWorkbenchRunning()) {
			return;
		}

		if (Thread.currentThread() == PlatformUI.getWorkbench().getDisplay()
				.getThread()) {
			fireListeners(event);
			return;
		}

		WorkbenchJob updateJob = new WorkbenchJob(
				WorkbenchMessages.DecorationScheduler_UpdateJobName) {
			@Override
			public IStatus runInUIThread(IProgressMonitor monitor) {
				fireListeners(event);
				return Status.OK_STATUS;
			}

			@Override
			public boolean belongsTo(Object family) {
				return FAMILY_DECORATE == family;
			}
		};
		updateJob.setSystem(true);
		updateJob.schedule();

	}

	
	public String decorateText(String text, Object element,
			IDecorationContext context) {
		Object adapted = getResourceAdapter(element);
		String result = scheduler.decorateWithText(text, element, adapted,
				context);
		FullDecoratorDefinition[] decorators = getDecoratorsFor(element);
		for (int i = 0; i < decorators.length; i++) {
			if (decorators[i].isEnabledFor(element)) {
				String newResult = safeDecorateText(element, result,
						decorators[i]);
				if (newResult != null) {
					result = newResult;
				}
			}
		}

		if (adapted != null) {
			decorators = getDecoratorsFor(adapted);
			for (int i = 0; i < decorators.length; i++) {
				if (decorators[i].isAdaptable()
						&& decorators[i].isEnabledFor(adapted)) {
					String newResult = safeDecorateText(adapted, result,
							decorators[i]);
					if (newResult != null) {
						result = newResult;
					}
				}
			}
		}

		return result;
	}

	
	@Override
	public String decorateText(String text, Object element) {
		return decorateText(text, element, DecorationContext.DEFAULT_CONTEXT);
	}

	private String safeDecorateText(Object element, String start,
			FullDecoratorDefinition decorator) {
		fullTextRunnable.setValues(start, element, decorator);
		SafeRunner.run(fullTextRunnable);
		String newResult = fullTextRunnable.getResult();
		return newResult;
	}

	public Image decorateImage(Image image, Object element,
			IDecorationContext context, ResourceManager localResourceManager) {
		Object adapted = getResourceAdapter(element);
		Image result = scheduler.decorateWithOverlays(image, element, adapted,
				context, localResourceManager);
		FullDecoratorDefinition[] decorators = getDecoratorsFor(element);

		for (int i = 0; i < decorators.length; i++) {
			if (decorators[i].isEnabledFor(element)) {
				Image newResult = safeDecorateImage(element, result,
						decorators[i]);
				if (newResult != null) {
					result = newResult;
				}
			}
		}


		if (adapted != null) {
			decorators = getDecoratorsFor(adapted);
			for (int i = 0; i < decorators.length; i++) {
				if (decorators[i].isAdaptable()
						&& decorators[i].isEnabledFor(adapted)) {
					Image newResult = safeDecorateImage(adapted, result,
							decorators[i]);
					if (newResult != null) {
						result = newResult;
					}
				}
			}
		}

		return result;
	}

	@Override
	public Image decorateImage(Image image, Object element) {
		return decorateImage(image, element, DecorationContext.DEFAULT_CONTEXT,
				getResourceManager());
	}

	private Image safeDecorateImage(Object element, Image start,
			FullDecoratorDefinition decorator) {
		fullImageRunnable.setValues(start, element, decorator);
		SafeRunner.run(fullImageRunnable);
		Image newResult = fullImageRunnable.getResult();
		return newResult;
	}

	private Object getResourceAdapter(Object element) {
		Object adapted = LegacyResourceSupport
				.getAdaptedContributorResource(element);
		if (adapted != element) {
			return adapted; // Avoid applying decorator twice
		}
		return null;
	}

	@Override
	public boolean isLabelProperty(Object element, String property) {
		return isLabelProperty(element, property, true);
	}

	public boolean isLabelProperty(Object element, String property,
			boolean checkAdapted) {
		boolean fullCheck = isLabelProperty(element, property,
				getDecoratorsFor(element));

		if (fullCheck) {
			return fullCheck;
		}

		boolean lightweightCheck = isLabelProperty(element, property,
				getLightweightManager().getDecoratorsFor(element));

		if (lightweightCheck) {
			return true;
		}

		if (checkAdapted) {
			Object adapted = getResourceAdapter(element);
			if (adapted == null || adapted == element) {
				return false;
			}

			fullCheck = isLabelProperty(adapted, property,
					getDecoratorsFor(adapted));
			if (fullCheck) {
				return fullCheck;
			}

			return isLabelProperty(adapted, property, lightweightManager
					.getDecoratorsFor(adapted));
		}
		return false;
	}

	private boolean isLabelProperty(Object element, String property,
			DecoratorDefinition[] decorators) {
		for (int i = 0; i < decorators.length; i++) {
			if (decorators[i].isEnabledFor(element)
					&& decorators[i].isLabelProperty(element, property)) {
				return true;
			}
		}

		return false;
	}

	private FullDecoratorDefinition[] enabledFullDefinitions() {

		FullDecoratorDefinition[] full = getFullDefinitions();
		if (full.length == 0) {
			return full;
		}
		ArrayList result = new ArrayList();
		for (int i = 0; i < full.length; i++) {
			if (full[i].isEnabled()) {
				result.add(full[i]);
			}
		}
		FullDecoratorDefinition[] returnArray = new FullDecoratorDefinition[result
				.size()];
		result.toArray(returnArray);
		return returnArray;
	}

	@Override
	public void dispose() {
	}

	public void clearCaches() {
		getLightweightManager().reset();
		fullTextRunnable.clearReferences();
		fullImageRunnable.clearReferences();
	}

	public void updateForEnablementChange() {
		scheduler.clearResults();
		fireListenersInUIThread(new LabelProviderChangedEvent(this));
		writeDecoratorsPreference();
	}

	public DecoratorDefinition[] getAllDecoratorDefinitions() {
		LightweightDecoratorDefinition[] lightweightDefinitions = getLightweightManager()
				.getDefinitions();
		DecoratorDefinition[] returnValue = new DecoratorDefinition[fullDefinitions.length
				+ lightweightDefinitions.length];
		System.arraycopy(fullDefinitions, 0, returnValue, 0,
				fullDefinitions.length);
		System.arraycopy(lightweightDefinitions, 0, returnValue,
				fullDefinitions.length, lightweightDefinitions.length);
		return returnValue;
	}

	@Override
	public void labelProviderChanged(LabelProviderChangedEvent event) {
		Object[] elements = event.getElements();
		scheduler.clearResults();
		if (elements == null) {
			fireListeners(event);
		} else {
			for (int i = 0; i < elements.length; i++) {
				Object adapted = getResourceAdapter(elements[i]);
				scheduler.queueForDecoration(elements[i], adapted, true, null,
						DecorationContext.DEFAULT_CONTEXT);
			}
		}
	}

	private void writeDecoratorsPreference() {
		StringBuffer enabledIds = new StringBuffer();
		writeDecoratorsPreference(enabledIds, getFullDefinitions());
		writeDecoratorsPreference(enabledIds, getLightweightManager()
				.getDefinitions());

		WorkbenchPlugin.getDefault().getPreferenceStore().setValue(
				IPreferenceConstants.ENABLED_DECORATORS, enabledIds.toString());
		PrefUtil.savePrefs();
	}

	private void writeDecoratorsPreference(StringBuffer enabledIds,
			DecoratorDefinition[] definitions) {
		for (int i = 0; i < definitions.length; i++) {
			enabledIds.append(definitions[i].getId());
			enabledIds.append(VALUE_SEPARATOR);
			if (definitions[i].isEnabled()) {
				enabledIds.append(P_TRUE);
			} else {
				enabledIds.append(P_FALSE);
			}

			enabledIds.append(PREFERENCE_SEPARATOR);
		}
	}

	public void applyDecoratorsPreference() {

		String preferenceValue = WorkbenchPlugin.getDefault()
				.getPreferenceStore().getString(
						IPreferenceConstants.ENABLED_DECORATORS);

		StringTokenizer tokenizer = new StringTokenizer(preferenceValue,
				PREFERENCE_SEPARATOR);
		Set enabledIds = new HashSet();
		Set disabledIds = new HashSet();
		while (tokenizer.hasMoreTokens()) {
			String nextValuePair = tokenizer.nextToken();

			String id = nextValuePair.substring(0, nextValuePair
					.indexOf(VALUE_SEPARATOR));
			if (nextValuePair.endsWith(P_TRUE)) {
				enabledIds.add(id);
			} else {
				disabledIds.add(id);
			}
		}

		FullDecoratorDefinition[] full = getFullDefinitions();
		for (int i = 0; i < full.length; i++) {
			String id = full[i].getId();
			if (enabledIds.contains(id)) {
				full[i].setEnabled(true);
			} else {
				if (disabledIds.contains(id)) {
					full[i].setEnabled(false);
				}
			}
		}

		LightweightDecoratorDefinition[] lightweightDefinitions = getLightweightManager()
				.getDefinitions();
		for (int i = 0; i < lightweightDefinitions.length; i++) {
			String id = lightweightDefinitions[i].getId();
			if (enabledIds.contains(id)) {
				lightweightDefinitions[i].setEnabled(true);
			} else {
				if (disabledIds.contains(id)) {
					lightweightDefinitions[i].setEnabled(false);
				}
			}
		}

	}

	public void shutdown() {
		FullDecoratorDefinition[] full = getFullDefinitions();
		for (int i = 0; i < full.length; i++) {
			if (full[i].isEnabled()) {
				full[i].setEnabled(false);
			}
		}
		if (lightweightManager != null) {
			getLightweightManager().shutdown();
		}
		scheduler.shutdown();
		dispose();
	}

	@Override
	public boolean getEnabled(String decoratorId) {
		DecoratorDefinition definition = getDecoratorDefinition(decoratorId);
		if (definition == null) {
			return false;
		}
		return definition.isEnabled();
	}

	@Override
	public ILabelDecorator getLabelDecorator() {
		return new ManagedWorkbenchLabelDecorator(this);
	}

	public ResourceManager getResourceManager() {
		if (resourceManager == null) {
			resourceManager = new LocalResourceManager(JFaceResources
					.getResources(PlatformUI.getWorkbench().getDisplay()));
		}
		return resourceManager;
	}

	@Override
	public void setEnabled(String decoratorId, boolean enabled) {
		DecoratorDefinition definition = getDecoratorDefinition(decoratorId);
		if (definition != null) {
			definition.setEnabled(enabled);
			clearCaches();
			updateForEnablementChange();
		}
	}

	@Override
	public IBaseLabelProvider getBaseLabelProvider(String decoratorId) {
		IBaseLabelProvider fullProvider = getLabelDecorator(decoratorId);
		if (fullProvider == null) {
			return getLightweightLabelDecorator(decoratorId);
		}
		return fullProvider;
	}

	@Override
	public ILabelDecorator getLabelDecorator(String decoratorId) {
		FullDecoratorDefinition definition = getFullDecoratorDefinition(decoratorId);

		if (definition != null && definition.isEnabled()) {
			ILabelDecorator result = definition.getDecorator();
			if (result == null) {
				try {
					result = definition.internalGetDecorator();
				} catch (CoreException e) {
					WorkbenchPlugin.log(e);
				}
			}
			return result;
		}
		return null;
	}

	@Override
	public ILightweightLabelDecorator getLightweightLabelDecorator(
			String decoratorId) {
		LightweightDecoratorDefinition definition = getLightweightManager()
				.getDecoratorDefinition(decoratorId);
		if (definition != null && definition.isEnabled()) {
			return definition.getDecorator();
		}
		return null;
	}

	private DecoratorDefinition getDecoratorDefinition(String decoratorId) {
		DecoratorDefinition returnValue = getFullDecoratorDefinition(decoratorId);
		if (returnValue == null) {
			return getLightweightManager().getDecoratorDefinition(decoratorId);
		}
		return returnValue;
	}

	private FullDecoratorDefinition getFullDecoratorDefinition(
			String decoratorId) {
		int idx = getFullDecoratorDefinitionIdx(decoratorId);
		if (idx != -1) {
			return getFullDefinitions()[idx];
		}
		return null;
	}

	private int getFullDecoratorDefinitionIdx(String decoratorId) {
		FullDecoratorDefinition[] full = getFullDefinitions();
		for (int i = 0; i < full.length; i++) {
			if (full[i].getId().equals(decoratorId)) {
				return i;
			}
		}
		return -1;
	}

	private FullDecoratorDefinition[] getDecoratorsFor(Object element) {

		if (element == null) {
			return EMPTY_FULL_DEF;
		}

		Collection decorators = getDecoratorsFor(element,
				enabledFullDefinitions());
		FullDecoratorDefinition[] decoratorArray = EMPTY_FULL_DEF;
		if (decorators.size() > 0) {
			decoratorArray = new FullDecoratorDefinition[decorators.size()];
			decorators.toArray(decoratorArray);
		}

		return decoratorArray;
	}

	public LightweightDecoratorManager getLightweightManager() {
		if (lightweightManager == null) {
			initializeDecoratorDefinitions();
		}
		return lightweightManager;
	}

	@Override
	public void update(String decoratorId) {

		IBaseLabelProvider provider = getBaseLabelProvider(decoratorId);
		if (provider != null) {
			scheduler.clearResults();
			fireListeners(new LabelProviderChangedEvent(provider));
		}

	}

	public boolean prepareDecoration(Object element, String originalText,
			IDecorationContext context) {
		if (scheduler.isDecorationReady(element, context)
				|| !getLightweightManager().hasEnabledDefinitions()) {
			return true;
		}

		boolean force = true;
		if (originalText == null || originalText.length() == 0) {
			force = false;
		}

		scheduler.queueForDecoration(element, getResourceAdapter(element),
				force, originalText, context);

		return !force;
	}

	@Override
	public boolean prepareDecoration(Object element, String originalText) {
		return prepareDecoration(element, originalText,
				DecorationContext.DEFAULT_CONTEXT);
	}

	public Font decorateFont(Object element) {
		return scheduler.getFont(element, getResourceAdapter(element));
	}

	public Color decorateBackground(Object element) {
		return scheduler.getBackgroundColor(element,
				getResourceAdapter(element));
	}

	public Color decorateForeground(Object element) {
		return scheduler.getForegroundColor(element,
				getResourceAdapter(element));
	}

	private FullDecoratorDefinition[] getFullDefinitions() {
		if (fullDefinitions == null) {
			initializeDecoratorDefinitions();
		}
		return fullDefinitions;
	}

	private IExtensionPoint getExtensionPointFilter() {
		return Platform.getExtensionRegistry().getExtensionPoint(
				EXTENSIONPOINT_UNIQUE_ID);
	}

	@Override
	public void addExtension(IExtensionTracker tracker,
			IExtension addedExtension) {
		IConfigurationElement addedElements[] = addedExtension
				.getConfigurationElements();
		for (int i = 0; i < addedElements.length; i++) {
			DecoratorRegistryReader reader = new DecoratorRegistryReader();
			reader.readElement(addedElements[i]);
			for (Iterator j = reader.getValues().iterator(); j.hasNext();) {
				addDecorator((DecoratorDefinition) j.next());
			}
		}
	}

	@Override
	public void removeExtension(IExtension source, Object[] objects) {

		boolean shouldClear = false;
		for (int i = 0; i < objects.length; i++) {
			if (objects[i] instanceof DecoratorDefinition) {
				DecoratorDefinition definition = (DecoratorDefinition) objects[i];
				if (definition.isFull()) {
					int idx = getFullDecoratorDefinitionIdx(definition.getId());
					if (idx != -1) {
						FullDecoratorDefinition[] oldDefs = getFullDefinitions();
						Util
								.arrayCopyWithRemoval(
										oldDefs,
										fullDefinitions = new FullDecoratorDefinition[fullDefinitions.length - 1],
										idx);
						shouldClear = true;
					}
				} else {
					shouldClear |= getLightweightManager().removeDecorator(
							(LightweightDecoratorDefinition) definition);
				}
			}
		}

		if (shouldClear) {
			clearCaches();
			updateForEnablementChange();
		}

	}

}
