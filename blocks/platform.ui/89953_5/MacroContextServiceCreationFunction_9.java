package org.eclipse.e4.core.macros.internal;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import javax.inject.Inject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.IPath;
import org.eclipse.e4.core.contexts.ContextInjectionFactory;
import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.core.macros.Activator;
import org.eclipse.e4.core.macros.EMacroContext;
import org.eclipse.e4.core.macros.IMacroCommand;
import org.eclipse.e4.core.macros.IMacroCommandCreator;
import org.eclipse.e4.core.macros.IMacroContextListener;
import org.eclipse.e4.core.macros.IMacroPlaybackContext;

public class MacroContextImplementation implements EMacroContext {

	private MacroManager fMacroManager;

	public MacroManager getMacroManager() {
		if (fMacroManager == null) {
			Activator plugin = Activator.getDefault();
			IPath stateLocation = plugin.getStateLocation();
			stateLocation.append("macros"); //$NON-NLS-1$
			File userHome = new File(System.getProperty("user.home")); //$NON-NLS-1$
			File eclipseUserHome = new File(userHome, ".eclipse"); //$NON-NLS-1$
			File eclipseUserHomeMacros = new File(eclipseUserHome, "org.eclipse.e4.core.macros"); //$NON-NLS-1$
			File eclipseUserHomeMacrosLoadDir = new File(eclipseUserHomeMacros, "macros"); //$NON-NLS-1$
			if (!eclipseUserHomeMacrosLoadDir.exists()) {
				eclipseUserHomeMacrosLoadDir.mkdirs();
			}
			File[] macrosDirectory = { stateLocation.toFile(), eclipseUserHomeMacrosLoadDir };
			fMacroManager = new MacroManager(macrosDirectory);
		}
		return fMacroManager;
	}


	public static final String MACRO_COMMAND_EXTENSION_POINT = "org.eclipse.e4.core.macros.macroCommandCreators"; //$NON-NLS-1$
	public static final String MACRO_COMMAND_ID = "macroCommandId"; //$NON-NLS-1$
	public static final String MACRO_COMMAND_CLASS = "macroCommandCreatorClass"; //$NON-NLS-1$

	private static Map<String, IMacroCommandCreator> fCommandIdToMacroCreator;

	public static final String MACRO_LISTENERS_EXTENSION_POINT = "org.eclipse.e4.core.macros.macroListeners"; //$NON-NLS-1$
	public static final String MACRO_LISTENER_CLASS = "macroListenerClass"; //$NON-NLS-1$

	private boolean fLoadedExtensionListeners = false;

	private IEclipseContext fEclipseContext;

	@Inject
	public MacroContextImplementation(IEclipseContext eclipseContext) {
		this.fEclipseContext = eclipseContext;
	}

	private void loadExtensionPointsMacroListeners() {
		if (!fLoadedExtensionListeners && fEclipseContext != null) {
			fLoadedExtensionListeners = true;
			IExtensionRegistry registry = fEclipseContext.get(IExtensionRegistry.class);

			MacroManager macroManager = getMacroManager();
			for (IConfigurationElement ce : registry.getConfigurationElementsFor(MACRO_LISTENERS_EXTENSION_POINT)) {
				String macroListenerClass = ce.getAttribute(MACRO_LISTENER_CLASS);
				if (macroListenerClass != null) {
					try {
						IMacroContextListener macroListener = (IMacroContextListener) ce
								.createExecutableExtension(MACRO_LISTENER_CLASS);
						ContextInjectionFactory.inject(macroListener, fEclipseContext);
						macroManager.addMacroListener(macroListener);
					} catch (CoreException e) {
						Activator.log(e);
					}
				} else {
					Activator.log(new RuntimeException(
							"Wrong definition for extension: " + MACRO_LISTENERS_EXTENSION_POINT + ": " + ce)); //$NON-NLS-1$ //$NON-NLS-2$
				}
			}
		}
	}

	private Map<String, IMacroCommandCreator> getCommandIdToMacroCreator() {
		if (fCommandIdToMacroCreator == null && fEclipseContext != null) {
			IExtensionRegistry registry = fEclipseContext.get(IExtensionRegistry.class);

			Map<String, IMacroCommandCreator> validCommandIds = new HashMap<>();
			for (IConfigurationElement ce : registry.getConfigurationElementsFor(MACRO_COMMAND_EXTENSION_POINT)) {
				String macroCommandId = ce.getAttribute(MACRO_COMMAND_ID);
				String macroCommandClass = ce.getAttribute(MACRO_COMMAND_CLASS);
				if (macroCommandId != null && macroCommandClass != null) {
					try {
						IMacroCommandCreator macroCreator = (IMacroCommandCreator) ce
								.createExecutableExtension(MACRO_COMMAND_CLASS);

						ContextInjectionFactory.inject(macroCreator, fEclipseContext);
						validCommandIds.put(macroCommandId, macroCreator);
					} catch (CoreException e) {
						Activator.log(e);
					}
				} else {
					Activator.log(new RuntimeException(
							"Wrong definition for extension: " + MACRO_COMMAND_EXTENSION_POINT + ": " + ce)); //$NON-NLS-1$ //$NON-NLS-2$
				}
			}
			fCommandIdToMacroCreator = validCommandIds;
		}
		return fCommandIdToMacroCreator;
	}

	@Override
	public boolean isRecording() {
		if (fMacroManager == null) {
			return false;
		}
		return getMacroManager().isRecording();
	}

	@Override
	public boolean isPlayingBack() {
		if (fMacroManager == null) {
			return false;
		}
		return getMacroManager().isPlayingBack();
	}

	@Override
	public void addMacroCommand(IMacroCommand macroCommand) {
		getMacroManager().addMacroCommand(macroCommand);
	}

	@Override
	public void toggleMacroRecord() {
		loadExtensionPointsMacroListeners();
		getMacroManager().toggleMacroRecord(this, getCommandIdToMacroCreator());
	}

	@Override
	public void playbackLastMacro(IMacroPlaybackContext macroPlaybackContext) {
		loadExtensionPointsMacroListeners();
		getMacroManager().playbackLastMacro(this, macroPlaybackContext, getCommandIdToMacroCreator());
	}

	@Override
	public void addMacroContextListener(IMacroContextListener listener) {
		getMacroManager().addMacroListener(listener);
	}

	@Override
	public void removeMacroContextListener(IMacroContextListener listener) {
		getMacroManager().removeMacroListener(listener);
	}

	public IMacroContextListener[] getMacroContextListeners() {
		return getMacroManager().getMacroListeners();
	}

}
