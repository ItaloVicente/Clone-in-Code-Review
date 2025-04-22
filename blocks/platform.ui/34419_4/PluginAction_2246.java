package org.eclipse.ui.internal;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.HashMap;
import org.eclipse.core.runtime.preferences.IEclipsePreferences;
import org.eclipse.core.runtime.preferences.IEclipsePreferences.PreferenceChangeEvent;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.util.OpenStrategy;
import org.eclipse.ui.IEditorDescriptor;
import org.eclipse.ui.IEditorRegistry;
import org.eclipse.ui.IFileEditorMapping;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferenceConstants;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.WorkbenchException;
import org.eclipse.ui.internal.decorators.DecoratorManager;
import org.eclipse.ui.internal.progress.ProgressManager;
import org.eclipse.ui.internal.registry.EditorRegistry;
import org.eclipse.ui.internal.util.PrefUtil;

public class PlatformUIPreferenceListener implements
		IEclipsePreferences.IPreferenceChangeListener {
	
	private static PlatformUIPreferenceListener singleton;
	
	public static IEclipsePreferences.IPreferenceChangeListener getSingleton(){
		if(singleton == null) {
			singleton = new PlatformUIPreferenceListener();
		}
	    return singleton;
	}

	@Override
	public void preferenceChange(PreferenceChangeEvent event) {

		String propertyName = event.getKey();
		if (IPreferenceConstants.ENABLED_DECORATORS.equals(propertyName)) {
			DecoratorManager manager = WorkbenchPlugin.getDefault()
					.getDecoratorManager();
			manager.applyDecoratorsPreference();
			manager.clearCaches();
			manager.updateForEnablementChange();
			return;
		}

		if (IWorkbenchPreferenceConstants.SHOW_SYSTEM_JOBS.equals(propertyName)) {
			boolean setting = PrefUtil.getAPIPreferenceStore().getBoolean(
					IWorkbenchPreferenceConstants.SHOW_SYSTEM_JOBS);

			ProgressManager.getInstance().setShowSystemJobs(setting);
		}
		
		if (IWorkbenchPreferenceConstants.DEFAULT_PERSPECTIVE_ID.equals(propertyName)) {
			IWorkbench workbench = PlatformUI.getWorkbench();

			workbench.getPerspectiveRegistry().setDefaultPerspective(
					PrefUtil.getAPIPreferenceStore().getString(
							IWorkbenchPreferenceConstants.DEFAULT_PERSPECTIVE_ID));
			return;
		}

		if (IWorkbenchPreferenceConstants.DOCK_PERSPECTIVE_BAR
				.equals(propertyName)) {
			IWorkbench workbench = PlatformUI.getWorkbench();
			IWorkbenchWindow[] workbenchWindows = workbench
					.getWorkbenchWindows();
			for (int i = 0; i < workbenchWindows.length; i++) {
				IWorkbenchWindow window = workbenchWindows[i];
				if (window instanceof WorkbenchWindow) {
				}
			}
			return;
		}

		if (IWorkbenchPreferenceConstants.SHOW_TRADITIONAL_STYLE_TABS
				.equals(propertyName)) {

			IWorkbench workbench = PlatformUI.getWorkbench();
			IWorkbenchWindow[] workbenchWindows = workbench
					.getWorkbenchWindows();
			for (int i = 0; i < workbenchWindows.length; i++) {
				IWorkbenchWindow window = workbenchWindows[i];
				if (window instanceof WorkbenchWindow) {
				}
			}
			return;
		}

		if (IPreferenceConstants.RESOURCES.equals(propertyName)) {
			IEditorRegistry registry = WorkbenchPlugin.getDefault()
					.getEditorRegistry();
			if (registry instanceof EditorRegistry) {
				EditorRegistry editorRegistry = (EditorRegistry) registry;
				IPreferenceStore store = WorkbenchPlugin.getDefault()
						.getPreferenceStore();
				Reader reader = null;
				try {
					String xmlString = store
							.getString(IPreferenceConstants.RESOURCES);
					if (xmlString != null && xmlString.length() > 0) {
						reader = new StringReader(xmlString);
						HashMap<String, IEditorDescriptor> editorMap = new HashMap<String, IEditorDescriptor>();
						int i = 0;
						IEditorDescriptor[] descriptors = editorRegistry
								.getSortedEditorsFromPlugins();
						for (i = 0; i < descriptors.length; i++) {
							IEditorDescriptor descriptor = descriptors[i];
							editorMap.put(descriptor.getId(), descriptor);
						}
						descriptors = editorRegistry.getSortedEditorsFromOS();
						for (i = 0; i < descriptors.length; i++) {
							IEditorDescriptor descriptor = descriptors[i];
							editorMap.put(descriptor.getId(), descriptor);
						}
						IFileEditorMapping[] maps = editorRegistry.getFileEditorMappings();
						for (int j = 0; j < maps.length; j++) {
							IFileEditorMapping fileEditorMapping = maps[j];
							IEditorDescriptor descriptor = fileEditorMapping.getDefaultEditor();
							if (descriptor != null && !editorMap.containsKey(descriptor.getId())) {
								editorMap.put(descriptor.getId(), descriptor);
							}
						}
						editorRegistry.readResources(editorMap, reader);
					}
				} catch (WorkbenchException e) {
					WorkbenchPlugin.log(e);
				} finally {
					if (reader != null) {
						try {
							reader.close();
						} catch (IOException e) {
							WorkbenchPlugin.log(e);
						}
					}
				}
			}
		}

		if (IPreferenceConstants.OPEN_ON_SINGLE_CLICK.equals(propertyName)
				|| IPreferenceConstants.SELECT_ON_HOVER.equals(propertyName)
				|| IPreferenceConstants.OPEN_AFTER_DELAY.equals(propertyName)
				|| IPreferenceConstants.SELECT_ON_HOVER.equals(propertyName)) {
			initializeSingleClickOption();
		}

	}

	private static void initializeSingleClickOption() {
		IPreferenceStore store = WorkbenchPlugin.getDefault().getPreferenceStore();
		boolean openOnSingleClick = store.getBoolean(IPreferenceConstants.OPEN_ON_SINGLE_CLICK);
		boolean selectOnHover = store.getBoolean(IPreferenceConstants.SELECT_ON_HOVER);
		boolean openAfterDelay = store.getBoolean(IPreferenceConstants.OPEN_AFTER_DELAY);
		int singleClickMethod = openOnSingleClick ? OpenStrategy.SINGLE_CLICK
				: OpenStrategy.DOUBLE_CLICK;
		if (openOnSingleClick) {
			if (selectOnHover) {
				singleClickMethod |= OpenStrategy.SELECT_ON_HOVER;
			}
			if (openAfterDelay) {
				singleClickMethod |= OpenStrategy.ARROW_KEYS_OPEN;
			}
		}
		OpenStrategy.setOpenMethod(singleClickMethod);
	}

}
