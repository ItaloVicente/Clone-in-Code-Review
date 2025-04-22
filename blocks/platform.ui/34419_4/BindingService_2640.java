package org.eclipse.ui.internal.keys;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.StringTokenizer;
import org.eclipse.core.commands.Command;
import org.eclipse.core.commands.CommandManager;
import org.eclipse.core.commands.ParameterizedCommand;
import org.eclipse.core.commands.common.HandleObject;
import org.eclipse.core.commands.common.NotDefinedException;
import org.eclipse.core.commands.util.Tracing;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtension;
import org.eclipse.core.runtime.IExtensionDelta;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.IRegistryChangeEvent;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.bindings.Binding;
import org.eclipse.jface.bindings.BindingManager;
import org.eclipse.jface.bindings.Scheme;
import org.eclipse.jface.bindings.keys.IKeyLookup;
import org.eclipse.jface.bindings.keys.KeyBinding;
import org.eclipse.jface.bindings.keys.KeyLookupFactory;
import org.eclipse.jface.bindings.keys.KeySequence;
import org.eclipse.jface.bindings.keys.KeyStroke;
import org.eclipse.jface.bindings.keys.ParseException;
import org.eclipse.jface.bindings.keys.SWTKeySupport;
import org.eclipse.jface.contexts.IContextIds;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.eclipse.jface.util.Util;
import org.eclipse.swt.SWT;
import org.eclipse.ui.IMemento;
import org.eclipse.ui.IWorkbenchPreferenceConstants;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.WorkbenchException;
import org.eclipse.ui.XMLMemento;
import org.eclipse.ui.internal.ShowViewMenu;
import org.eclipse.ui.internal.WorkbenchPlugin;
import org.eclipse.ui.internal.misc.Policy;
import org.eclipse.ui.internal.registry.IWorkbenchRegistryConstants;
import org.eclipse.ui.internal.services.PreferencePersistence;
import org.eclipse.ui.keys.IBindingService;

public class BindingPersistence extends PreferencePersistence {

	private static final boolean DEBUG = Policy.DEBUG_KEY_BINDINGS;

	private static final int INDEX_ACTIVE_SCHEME = 0;

	private static final int INDEX_BINDING_DEFINITIONS = 1;

	private static final int INDEX_SCHEME_DEFINITIONS = 2;

	private static final String LEGACY_DEFAULT_SCOPE = "org.eclipse.ui.globalScope"; //$NON-NLS-1$

	private static final Map r2_1KeysByName = new HashMap();

	static {
		final IKeyLookup lookup = KeyLookupFactory.getDefault();
		r2_1KeysByName.put(IKeyLookup.BACKSPACE_NAME, lookup
				.formalKeyLookupInteger(IKeyLookup.BACKSPACE_NAME));
		r2_1KeysByName.put(IKeyLookup.TAB_NAME, lookup
				.formalKeyLookupInteger(IKeyLookup.TAB_NAME));
		r2_1KeysByName.put(IKeyLookup.RETURN_NAME, lookup
				.formalKeyLookupInteger(IKeyLookup.RETURN_NAME));
		r2_1KeysByName.put(IKeyLookup.ENTER_NAME, lookup
				.formalKeyLookupInteger(IKeyLookup.ENTER_NAME));
		r2_1KeysByName.put(IKeyLookup.ESCAPE_NAME, lookup
				.formalKeyLookupInteger(IKeyLookup.ESCAPE_NAME));
		r2_1KeysByName.put(IKeyLookup.ESC_NAME, lookup
				.formalKeyLookupInteger(IKeyLookup.ESC_NAME));
		r2_1KeysByName.put(IKeyLookup.DELETE_NAME, lookup
				.formalKeyLookupInteger(IKeyLookup.DELETE_NAME));
		r2_1KeysByName.put(IKeyLookup.SPACE_NAME, lookup
				.formalKeyLookupInteger(IKeyLookup.SPACE_NAME));
		r2_1KeysByName.put(IKeyLookup.ARROW_UP_NAME, lookup
				.formalKeyLookupInteger(IKeyLookup.ARROW_UP_NAME));
		r2_1KeysByName.put(IKeyLookup.ARROW_DOWN_NAME, lookup
				.formalKeyLookupInteger(IKeyLookup.ARROW_DOWN_NAME));
		r2_1KeysByName.put(IKeyLookup.ARROW_LEFT_NAME, lookup
				.formalKeyLookupInteger(IKeyLookup.ARROW_LEFT_NAME));
		r2_1KeysByName.put(IKeyLookup.ARROW_RIGHT_NAME, lookup
				.formalKeyLookupInteger(IKeyLookup.ARROW_RIGHT_NAME));
		r2_1KeysByName.put(IKeyLookup.PAGE_UP_NAME, lookup
				.formalKeyLookupInteger(IKeyLookup.PAGE_UP_NAME));
		r2_1KeysByName.put(IKeyLookup.PAGE_DOWN_NAME, lookup
				.formalKeyLookupInteger(IKeyLookup.PAGE_DOWN_NAME));
		r2_1KeysByName.put(IKeyLookup.HOME_NAME, lookup
				.formalKeyLookupInteger(IKeyLookup.HOME_NAME));
		r2_1KeysByName.put(IKeyLookup.END_NAME, lookup
				.formalKeyLookupInteger(IKeyLookup.END_NAME));
		r2_1KeysByName.put(IKeyLookup.INSERT_NAME, lookup
				.formalKeyLookupInteger(IKeyLookup.INSERT_NAME));
		r2_1KeysByName.put(IKeyLookup.F1_NAME, lookup
				.formalKeyLookupInteger(IKeyLookup.F1_NAME));
		r2_1KeysByName.put(IKeyLookup.F2_NAME, lookup
				.formalKeyLookupInteger(IKeyLookup.F2_NAME));
		r2_1KeysByName.put(IKeyLookup.F3_NAME, lookup
				.formalKeyLookupInteger(IKeyLookup.F3_NAME));
		r2_1KeysByName.put(IKeyLookup.F4_NAME, lookup
				.formalKeyLookupInteger(IKeyLookup.F4_NAME));
		r2_1KeysByName.put(IKeyLookup.F5_NAME, lookup
				.formalKeyLookupInteger(IKeyLookup.F5_NAME));
		r2_1KeysByName.put(IKeyLookup.F6_NAME, lookup
				.formalKeyLookupInteger(IKeyLookup.F6_NAME));
		r2_1KeysByName.put(IKeyLookup.F7_NAME, lookup
				.formalKeyLookupInteger(IKeyLookup.F7_NAME));
		r2_1KeysByName.put(IKeyLookup.F8_NAME, lookup
				.formalKeyLookupInteger(IKeyLookup.F8_NAME));
		r2_1KeysByName.put(IKeyLookup.F9_NAME, lookup
				.formalKeyLookupInteger(IKeyLookup.F9_NAME));
		r2_1KeysByName.put(IKeyLookup.F10_NAME, lookup
				.formalKeyLookupInteger(IKeyLookup.F10_NAME));
		r2_1KeysByName.put(IKeyLookup.F11_NAME, lookup
				.formalKeyLookupInteger(IKeyLookup.F11_NAME));
		r2_1KeysByName.put(IKeyLookup.F12_NAME, lookup
				.formalKeyLookupInteger(IKeyLookup.F12_NAME));
		r2_1KeysByName.put(IKeyLookup.F13_NAME, lookup
				.formalKeyLookupInteger(IKeyLookup.F13_NAME));
		r2_1KeysByName.put(IKeyLookup.F14_NAME, lookup
				.formalKeyLookupInteger(IKeyLookup.F14_NAME));
		r2_1KeysByName.put(IKeyLookup.F15_NAME, lookup
				.formalKeyLookupInteger(IKeyLookup.F15_NAME));
		r2_1KeysByName.put(IKeyLookup.F16_NAME, lookup
				.formalKeyLookupInteger(IKeyLookup.F16_NAME));
		r2_1KeysByName.put(IKeyLookup.F17_NAME, lookup
				.formalKeyLookupInteger(IKeyLookup.F17_NAME));
		r2_1KeysByName.put(IKeyLookup.F18_NAME, lookup
				.formalKeyLookupInteger(IKeyLookup.F18_NAME));
		r2_1KeysByName.put(IKeyLookup.F19_NAME, lookup
				.formalKeyLookupInteger(IKeyLookup.F19_NAME));
		r2_1KeysByName.put(IKeyLookup.F20_NAME, lookup
				.formalKeyLookupInteger(IKeyLookup.F20_NAME));
	}

	private static final KeySequence convert2_1Sequence(int[] r21KeySequence) {
		final int r21KeySequenceLength = r21KeySequence.length;
		final KeyStroke[] keyStrokes = new KeyStroke[r21KeySequenceLength];
		for (int i = 0; i < r21KeySequenceLength; i++) {
			keyStrokes[i] = convert2_1Stroke(r21KeySequence[i]);
		}

		return KeySequence.getInstance(keyStrokes);
	}

	private static final KeyStroke convert2_1Stroke(final int r21Stroke) {
		return SWTKeySupport.convertAcceleratorToKeyStroke(r21Stroke);
	}

	public static final String getDefaultSchemeId() {
		final IPreferenceStore store = PlatformUI.getPreferenceStore();
		return store
				.getDefaultString(IWorkbenchPreferenceConstants.KEY_CONFIGURATION_ID);
	}

	private static final int[] parse2_1Sequence(final String string) {
		final StringTokenizer stringTokenizer = new StringTokenizer(string);
		final int length = stringTokenizer.countTokens();
		final int[] strokes = new int[length];

		for (int i = 0; i < length; i++) {
			strokes[i] = parse2_1Stroke(stringTokenizer.nextToken());
		}

		return strokes;
	}

	private static final int parse2_1Stroke(final String string) {
		final StringTokenizer stringTokenizer = new StringTokenizer(string,
				KeyStroke.KEY_DELIMITER, true);

		final int size = stringTokenizer.countTokens();
		final String[] tokens = new String[size];
		for (int i = 0; stringTokenizer.hasMoreTokens(); i++) {
			tokens[i] = stringTokenizer.nextToken();
		}

		int value = 0;
		if (size % 2 == 1) {
			String token = tokens[size - 1];
			final Integer integer = (Integer) r2_1KeysByName.get(token
					.toUpperCase(Locale.ENGLISH));

			if (integer != null) {
				value = integer.intValue();
			} else if (token.length() == 1) {
				value = token.toUpperCase().charAt(0);
			}

			if (value != 0) {
				for (int i = 0; i < size - 1; i++) {
					token = tokens[i];

					if (i % 2 == 0) {
						if (token.equalsIgnoreCase(IKeyLookup.CTRL_NAME)) {
							if ((value & SWT.CTRL) != 0) {
								return 0;
							}

							value |= SWT.CTRL;

						} else if (token.equalsIgnoreCase(IKeyLookup.ALT_NAME)) {
							if ((value & SWT.ALT) != 0) {
								return 0;
							}

							value |= SWT.ALT;

						} else if (token
								.equalsIgnoreCase(IKeyLookup.SHIFT_NAME)) {
							if ((value & SWT.SHIFT) != 0) {
								return 0;
							}

							value |= SWT.SHIFT;

						} else if (token
								.equalsIgnoreCase(IKeyLookup.COMMAND_NAME)) {
							if ((value & SWT.COMMAND) != 0) {
								return 0;
							}

							value |= SWT.COMMAND;

						} else {
							return 0;

						}

					} else if (!KeyStroke.KEY_DELIMITER.equals(token)) {
						return 0;
					}
				}
			}
		}

		return value;
	}

	private static final void readActiveScheme(
			final IConfigurationElement[] configurationElements,
			final int configurationElementCount, final IMemento preferences,
			final BindingManager bindingManager) {
		final IPreferenceStore store = PlatformUI.getPreferenceStore();
		final String defaultActiveSchemeId = store
				.getDefaultString(IWorkbenchPreferenceConstants.KEY_CONFIGURATION_ID);
		final String preferenceActiveSchemeId = store
				.getString(IWorkbenchPreferenceConstants.KEY_CONFIGURATION_ID);
		if ((preferenceActiveSchemeId != null)
				&& (!preferenceActiveSchemeId.equals(defaultActiveSchemeId))) {
			try {
				bindingManager.setActiveScheme(bindingManager
						.getScheme(preferenceActiveSchemeId));
				return;
			} catch (final NotDefinedException e) {
			}
		}

		if (preferences != null) {
			final IMemento[] preferenceMementos = preferences
					.getChildren(TAG_ACTIVE_KEY_CONFIGURATION);
			int preferenceMementoCount = preferenceMementos.length;
			for (int i = preferenceMementoCount - 1; i >= 0; i--) {
				final IMemento memento = preferenceMementos[i];
				String id = memento.getString(ATT_KEY_CONFIGURATION_ID);
				if (id != null) {
					try {
						bindingManager.setActiveScheme(bindingManager
								.getScheme(id));
						return;
					} catch (final NotDefinedException e) {
					}
				}
			}
		}

		if ((defaultActiveSchemeId != null && defaultActiveSchemeId.length() > 0)
				&& (!defaultActiveSchemeId
						.equals(IBindingService.DEFAULT_DEFAULT_ACTIVE_SCHEME_ID))) {
			try {
				bindingManager.setActiveScheme(bindingManager
						.getScheme(defaultActiveSchemeId));
				return;
			} catch (final NotDefinedException e) {
			}
		}

		for (int i = configurationElementCount - 1; i >= 0; i--) {
			final IConfigurationElement configurationElement = configurationElements[i];

			String id = configurationElement
					.getAttribute(ATT_KEY_CONFIGURATION_ID);
			if (id != null) {
				try {
					bindingManager
							.setActiveScheme(bindingManager.getScheme(id));
					return;
				} catch (final NotDefinedException e) {
				}
			}

			id = configurationElement.getAttribute(ATT_VALUE);
			if (id != null) {
				try {
					bindingManager
							.setActiveScheme(bindingManager.getScheme(id));
					return;
				} catch (final NotDefinedException e) {
				}
			}
		}

		try {
			bindingManager
					.setActiveScheme(bindingManager
							.getScheme(IBindingService.DEFAULT_DEFAULT_ACTIVE_SCHEME_ID));
		} catch (final NotDefinedException e) {
			throw new Error(
					"The default default active scheme id is not defined."); //$NON-NLS-1$
		}
	}

	private static final void readBindingsFromPreferences(final IMemento preferences,
			final BindingManager bindingManager, final CommandManager commandService) {
		List warningsToLog = new ArrayList(1);

		if (preferences != null) {
			final IMemento[] preferenceMementos = preferences
					.getChildren(TAG_KEY_BINDING);
			int preferenceMementoCount = preferenceMementos.length;
			for (int i = preferenceMementoCount - 1; i >= 0; i--) {
				final IMemento memento = preferenceMementos[i];

				String commandId = readOptional(memento, ATT_COMMAND_ID);
				if (commandId == null) {
					commandId = readOptional(memento, ATT_COMMAND);
				}
				final Command command;
				if (commandId != null) {
					command = commandService.getCommand(commandId);
				} else {
					command = null;
				}

				String schemeId = readOptional(memento,
						ATT_KEY_CONFIGURATION_ID);
				if (schemeId == null) {
					schemeId = readRequired(memento, ATT_CONFIGURATION,
							warningsToLog,
							"Key bindings need a scheme or key configuration"); //$NON-NLS-1$
					if (schemeId == null) {
						continue;
					}
				}

				String contextId = readOptional(memento, ATT_CONTEXT_ID);
				if (contextId == null) {
					contextId = readOptional(memento, ATT_SCOPE);
				}
				if (LEGACY_DEFAULT_SCOPE.equals(contextId)) {
					contextId = null;
				}
				if (contextId == null) {
					contextId = IContextIds.CONTEXT_ID_WINDOW;
				}

				String keySequenceText = readOptional(memento, ATT_KEY_SEQUENCE);
				KeySequence keySequence = null;
				if (keySequenceText == null) {
					keySequenceText = readRequired(memento, ATT_STRING,
							warningsToLog,
							"Key bindings need a key sequence or string"); //$NON-NLS-1$
					if (keySequenceText == null) {
						continue;
					}

					keySequence = convert2_1Sequence(parse2_1Sequence(keySequenceText));

				} else {
					try {
						keySequence = KeySequence.getInstance(keySequenceText);
					} catch (final ParseException e) {
						addWarning(warningsToLog, "Could not parse", null, //$NON-NLS-1$
								commandId, "keySequence", keySequenceText); //$NON-NLS-1$
						continue;
					}
					if (keySequence.isEmpty() || !keySequence.isComplete()) {
						addWarning(
								warningsToLog,
								"Key bindings cannot use an empty or incomplete key sequence", //$NON-NLS-1$
								null, commandId, "keySequence", keySequence //$NON-NLS-1$
										.toString());
						continue;
					}

				}

				final String locale = readOptional(memento, ATT_LOCALE);
				final String platform = readOptional(memento, ATT_PLATFORM);

				final ParameterizedCommand parameterizedCommand = command != null ? readParameters(
						memento, warningsToLog, command) : null;

				final Binding binding = new KeyBinding(keySequence,
						parameterizedCommand, schemeId, contextId, locale,
						platform, null, Binding.USER);
				bindingManager.addBinding(binding);
			}
		}

		logWarnings(warningsToLog,
				"Warnings while parsing the key bindings from the preference store"); //$NON-NLS-1$
	}

	private static final void readBindingsFromRegistry(
			final IConfigurationElement[] configurationElements,
			final int configurationElementCount,
			final BindingManager bindingManager,
			final CommandManager commandService) {
		final Collection bindings = new ArrayList(configurationElementCount);
		final List warningsToLog = new ArrayList(1);

		HashSet cocoaTempList = new HashSet();
		
		IConfigurationElement[] sequenceModifiers = new IConfigurationElement[0];
		if(configurationElementCount >0)
			sequenceModifiers =  getSequenceModifierElements(configurationElements[0]);

		for (int i = 0; i < configurationElementCount; i++) {
			final IConfigurationElement configurationElement = configurationElements[i];
			
			if( i>0 && !configurationElement.getDeclaringExtension().equals(configurationElements[i-1].getDeclaringExtension()))
				sequenceModifiers = getSequenceModifierElements(configurationElement);
			
			String commandId = readCommandId(configurationElement);
			
			String viewParameter = null;
			final Command command;
			if (commandId != null) {
				command = commandService.getCommand(commandId);
				if (!command.isDefined()) {
					addWarning(warningsToLog, "Cannot bind to an undefined command", //$NON-NLS-1$
							configurationElement, commandId);
					continue;
				}
			} else {
				command = null;
			}

			String schemeId = readSchemeId(configurationElement, warningsToLog, commandId);
			if(isEmpty(schemeId))
				continue;
				
			String contextId = readContextId(configurationElement);

			String keySequenceText = readKeySequenceText(configurationElement);
			if(isEmpty(keySequenceText)) {
				addWarning(
						warningsToLog,
						"Defining a key binding with no key sequence has no effect", //$NON-NLS-1$
						configurationElement, commandId);
				continue;
			}
			

			
			KeySequence keySequence = readKeySequence(configurationElement, warningsToLog, commandId, keySequenceText);
			if(keySequence == null)
				continue;

			
			String locale = readNonEmptyAttribute(configurationElement, ATT_LOCALE);
			String platform = readNonEmptyAttribute(configurationElement, ATT_PLATFORM);

			ParameterizedCommand parameterizedCommand = 
				readParameterizedCommand(warningsToLog, configurationElement, viewParameter, command);

			List modifiedBindings = applyModifiers(keySequence, keySequenceText, platform, sequenceModifiers, parameterizedCommand, schemeId, contextId, locale, warningsToLog);

			KeyBinding binding = (KeyBinding) modifiedBindings.get(0);
			if(modifiedBindings.size() > 1) {
				for (int j = 1; j < modifiedBindings.size(); j++) {
					bindings.add(modifiedBindings.get(j));
				}
			}

			if (Util.WS_COCOA.equals(platform)) {
				cocoaTempList.add(binding);
			} else if (Util.WS_CARBON.equals(platform)) {
				bindings.add(binding);
				cocoaTempList.add(new KeyBinding(keySequence,
						parameterizedCommand, schemeId, contextId, locale,
						Util.WS_COCOA, null, Binding.SYSTEM));
			} else {
				bindings.add(binding);
			}
		}
		if (cocoaTempList.size() > 0) {
			bindings.addAll(cocoaTempList);
		}

		final Binding[] bindingArray = (Binding[]) bindings
				.toArray(new Binding[bindings.size()]);
		bindingManager.setBindings(bindingArray);

		logWarnings(
				warningsToLog,
				"Warnings while parsing the key bindings from the 'org.eclipse.ui.commands' and 'org.eclipse.ui.bindings' extension point"); //$NON-NLS-1$
	}
	
	private static List applyModifiers(KeySequence keySequence, String keySequenceText,
			String platform, IConfigurationElement[] sequenceModifiers,
			ParameterizedCommand parameterizedCommand, String schemeId,
			String contextId, String locale, List warningsToLog) {

		List bindings = new ArrayList();

		for (int i = 0; i < sequenceModifiers.length; i++) {

			IConfigurationElement sequenceModifier = sequenceModifiers[i];
			String findSequence = sequenceModifier.getAttribute(ATT_FIND);

			if (keySequenceText.startsWith(findSequence)) {
				String replaceSequence = sequenceModifier.getAttribute(ATT_REPLACE);
				String modifiedSequence = replaceSequence + keySequenceText.substring(findSequence.length());
				String platformsString = sequenceModifier.getAttribute(ATT_PLATFORMS);

				String[] platforms = parseCommaSeparatedString(platformsString);
				
				try {
					if (platform == null) {
						addGenericBindings(keySequence, parameterizedCommand, schemeId, contextId, locale,
								bindings, modifiedSequence, platforms);
	
					} else {
						getBindingForPlatform(keySequence, platform,
								parameterizedCommand, schemeId, contextId, locale,
								bindings, modifiedSequence, platforms);
					}
				}catch(ParseException e) {
					bindings.clear();
					addWarning(
							warningsToLog,
							"Cannot create modified sequence for key binding", //$NON-NLS-1$
							sequenceModifier, parameterizedCommand.getId(), ATT_REPLACE,
							replaceSequence);

				}
				break;
			}
		}
		
		if(bindings.size() == 0) {
			KeyBinding binding = new KeyBinding(keySequence,
					parameterizedCommand, schemeId, contextId, locale,
					platform, null, Binding.SYSTEM);
			bindings.add(binding);
		}

		return bindings;
	}

	private static void getBindingForPlatform(KeySequence keySequence,
			String platform, ParameterizedCommand parameterizedCommand,
			String schemeId, String contextId, String locale, List bindings,
			String modifiedSequence, String[] platforms) throws ParseException {
		
		int j = 0;
		for (; j < platforms.length; j++) {
			if(platforms[j].equals(SWT.getPlatform())) {
				KeyBinding newBinding = new KeyBinding(KeySequence
						.getInstance(modifiedSequence),
						parameterizedCommand, schemeId, contextId,
						locale, platforms[j], null, Binding.SYSTEM);
				bindings.add(newBinding);
				break;
			}
		}
		if(j == platforms.length) {
			KeyBinding newBinding = new KeyBinding(keySequence,
					parameterizedCommand, schemeId, contextId,
					locale, null, null, Binding.SYSTEM);
			bindings.add(newBinding);
		}
	}

	private static void addGenericBindings(KeySequence keySequence, ParameterizedCommand parameterizedCommand,
			String schemeId, String contextId, String locale, List bindings,
			String modifiedSequence, String[] platforms) throws ParseException {
		

		KeyBinding originalBinding = new KeyBinding(keySequence,
				parameterizedCommand, schemeId, contextId, locale, null, null,
				Binding.SYSTEM);
		bindings.add(originalBinding);
		
		String platform = SWT.getPlatform();
		boolean modifierExists = false;
		for (int i = 0; i < platforms.length; i++) {
			if(platforms[i].equals(platform)) {
				modifierExists = true;
				break;
			}
		}
		
		if(modifierExists) {
			KeyBinding newBinding = new KeyBinding(KeySequence.getInstance(modifiedSequence),
					parameterizedCommand, schemeId, contextId,
					locale, SWT.getPlatform(), null, Binding.SYSTEM);
	
			KeyBinding deleteBinding = new KeyBinding(keySequence,
					null, schemeId, contextId,
					locale, SWT.getPlatform(), null, Binding.SYSTEM);

			bindings.add(newBinding);
			bindings.add(deleteBinding);
		}

	}

	private static IConfigurationElement[] getSequenceModifierElements(IConfigurationElement configurationElement) {
		
		IExtension extension = configurationElement.getDeclaringExtension();
		IConfigurationElement[] configurationElements = extension.getConfigurationElements();
		List modifierElements = new ArrayList();
		for (int i = 0; i < configurationElements.length; i++) {
			final IConfigurationElement anElement = configurationElements[i];
			if(TAG_SEQUENCE_MODIFIER.equals(anElement.getName()))
				modifierElements.add(anElement);
		}
		return (IConfigurationElement[]) modifierElements.toArray(new IConfigurationElement[modifierElements.size()]);
	}

	public static String[] parseCommaSeparatedString(String commaSeparatedString) {
			StringTokenizer tokenizer = new StringTokenizer(commaSeparatedString, ", "); //$NON-NLS-1$
			int count = tokenizer.countTokens();
			String[] tokens = new String[count];
			for (int i = 0; i < tokens.length; i++) {
				tokens[i] = tokenizer.nextToken();
			}
			return tokens;
	}


	private static String readKeySequenceText(IConfigurationElement configurationElement) {
		
		String keySequenceText = configurationElement.getAttribute(ATT_SEQUENCE);
		if (isEmpty(keySequenceText)) {
			keySequenceText = configurationElement.getAttribute(ATT_KEY_SEQUENCE);
		}
		if (isEmpty(keySequenceText))
			keySequenceText = configurationElement.getAttribute(ATT_STRING);

		return keySequenceText;

	}
	
	private static KeySequence readKeySequence(IConfigurationElement configurationElement, List warningsToLog, String commandId, String keySequenceText) {

		KeySequence keySequence = null;
		if(keySequenceText.equals(configurationElement.getAttribute(ATT_STRING))){
			try {
				keySequence = convert2_1Sequence(parse2_1Sequence(keySequenceText));
			} catch (final IllegalArgumentException e) {
				addWarning(warningsToLog, "Could not parse key sequence", //$NON-NLS-1$
						configurationElement, commandId, "keySequence", //$NON-NLS-1$
						keySequenceText);
				return null;
			}
		} else {
			try {
				keySequence = KeySequence.getInstance(keySequenceText);
			} catch (final ParseException e) {
				addWarning(warningsToLog, "Could not parse key sequence", //$NON-NLS-1$
						configurationElement, commandId, "keySequence", //$NON-NLS-1$
						keySequenceText);
				return null;
			}
			if (keySequence.isEmpty() || !keySequence.isComplete()) {
				addWarning(
						warningsToLog,
						"Key bindings should not have an empty or incomplete key sequence", //$NON-NLS-1$
						configurationElement, commandId, "keySequence", //$NON-NLS-1$
						keySequence.toString());
				return null;
			}

		}
		return keySequence;
	}

	private static ParameterizedCommand readParameterizedCommand(
			final List warningsToLog,
			final IConfigurationElement configurationElement,
			String viewParameter, final Command command) {
		final ParameterizedCommand parameterizedCommand;
		if (command == null) {
			parameterizedCommand = null;
		} else if (viewParameter != null) { 
			HashMap parms = new HashMap();
			parms.put(ShowViewMenu.VIEW_ID_PARM, viewParameter);
			parameterizedCommand = ParameterizedCommand.generateCommand(command, parms);
		} else {
			parameterizedCommand = readParameters(configurationElement,
					warningsToLog, command);
		}
		return parameterizedCommand;
	}

	private static String readNonEmptyAttribute(IConfigurationElement configurationElement, String attribute) {
		String attributeValue = configurationElement.getAttribute(attribute);
		if ((attributeValue != null) && (attributeValue.length() == 0)) {
			attributeValue = null;
		}
		return attributeValue;
	}
	
	
	private static String readContextId(
			final IConfigurationElement configurationElement) {
		String contextId = configurationElement
				.getAttribute(ATT_CONTEXT_ID);
		if (LEGACY_DEFAULT_SCOPE.equals(contextId)) {
			contextId = null;
		} else if ((contextId == null) || (contextId.length() == 0)) {
			contextId = configurationElement.getAttribute(ATT_SCOPE);
			if (LEGACY_DEFAULT_SCOPE.equals(contextId)) {
				contextId = null;
			}
		}
		if ((contextId == null) || (contextId.length() == 0)) {
			contextId = IContextIds.CONTEXT_ID_WINDOW;
		}
		return contextId;
	}

	
	private static String readSchemeId(IConfigurationElement configurationElement, List warningsToLog, String commandId) {
		
		String schemeId = configurationElement.getAttribute(ATT_SCHEME_ID);
		if ((schemeId == null) || (schemeId.length() == 0)) {
			schemeId = configurationElement
					.getAttribute(ATT_KEY_CONFIGURATION_ID);
			if ((schemeId == null) || (schemeId.length() == 0)) {
				schemeId = configurationElement
						.getAttribute(ATT_CONFIGURATION);
				if ((schemeId == null) || (schemeId.length() == 0)) {
					addWarning(warningsToLog, "Key bindings need a scheme", //$NON-NLS-1$
							configurationElement, commandId);
				}
			}
		}
		return schemeId;
	}
	
	private static String readCommandId(
			final IConfigurationElement configurationElement) {
		String commandId = configurationElement
				.getAttribute(ATT_COMMAND_ID);
		if ((commandId == null) || (commandId.length() == 0)) {
			commandId = configurationElement.getAttribute(ATT_COMMAND);
		}
		if ((commandId != null) && (commandId.length() == 0)) {
			commandId = null;
		}
		return commandId;
	}
	
	private static boolean isEmpty(String string) {
		return string == null || string.length() == 0;
	}

	private static final void readSchemesFromRegistry(
			final IConfigurationElement[] configurationElements,
			final int configurationElementCount,
			final BindingManager bindingManager) {
		final HandleObject[] handleObjects = bindingManager.getDefinedSchemes();
		if (handleObjects != null) {
			for (int i = 0; i < handleObjects.length; i++) {
				handleObjects[i].undefine();
			}
		}

		final List warningsToLog = new ArrayList(1);

		for (int i = 0; i < configurationElementCount; i++) {
			final IConfigurationElement configurationElement = configurationElements[i];

			final String id = readRequired(configurationElement, ATT_ID,
					warningsToLog, "Schemes need an id"); //$NON-NLS-1$
			if (id == null) {
				continue;
			}
			final String name = readRequired(configurationElement, ATT_NAME,
					warningsToLog, "A scheme needs a name", id); //$NON-NLS-1$
			if (name == null) {
				continue;
			}
			final String description = readOptional(configurationElement,
					ATT_DESCRIPTION);

			String parentId = configurationElement.getAttribute(ATT_PARENT_ID);
			if ((parentId != null) && (parentId.length() == 0)) {
				parentId = configurationElement.getAttribute(ATT_PARENT);
				if ((parentId != null) && (parentId.length() == 0)) {
					parentId = null;
				}
			}

			final Scheme scheme = bindingManager.getScheme(id);
			scheme.define(name, description, parentId);
		}

		logWarnings(
				warningsToLog,
				"Warnings while parsing the key bindings from the 'org.eclipse.ui.bindings', 'org.eclipse.ui.acceleratorConfigurations' and 'org.eclipse.ui.commands' extension point"); //$NON-NLS-1$
	}

	static final void write(final Scheme activeScheme, final Binding[] bindings)
			throws IOException {
		if (DEBUG) {
			Tracing.printTrace("BINDINGS", "Persisting active scheme '" //$NON-NLS-1$ //$NON-NLS-2$
					+ activeScheme.getId() + '\'');
			Tracing.printTrace("BINDINGS", "Persisting bindings"); //$NON-NLS-1$ //$NON-NLS-2$
		}

		writeActiveScheme(activeScheme);

		final XMLMemento xmlMemento = XMLMemento
				.createWriteRoot(EXTENSION_COMMANDS);
		if (activeScheme != null) {
			writeActiveSchemeToPreferences(xmlMemento, activeScheme);
		}
		if (bindings != null) {
			final int bindingsLength = bindings.length;
			for (int i = 0; i < bindingsLength; i++) {
				final Binding binding = bindings[i];
				if (binding.getType() == Binding.USER) {
					writeBindingToPreferences(xmlMemento, binding);
				}
			}
		}

		final IPreferenceStore preferenceStore = WorkbenchPlugin.getDefault()
				.getPreferenceStore();
		final Writer writer = new StringWriter();
		try {
			xmlMemento.save(writer);
			preferenceStore.setValue(EXTENSION_COMMANDS, writer.toString());
		} finally {
			writer.close();
		}
	}

	private static final void writeActiveScheme(final Scheme scheme) {
		final IPreferenceStore store = PlatformUI.getPreferenceStore();
		final String schemeId = (scheme == null) ? null : scheme.getId();
		final String defaultSchemeId = store
				.getDefaultString(IWorkbenchPreferenceConstants.KEY_CONFIGURATION_ID);
		if ((defaultSchemeId == null) ? (scheme != null) : (!defaultSchemeId
				.equals(schemeId))) {
			store.setValue(IWorkbenchPreferenceConstants.KEY_CONFIGURATION_ID,
					scheme.getId());
		} else {
			store
					.setToDefault(IWorkbenchPreferenceConstants.KEY_CONFIGURATION_ID);
		}
	}

	private static final void writeActiveSchemeToPreferences(
			final IMemento memento, final Scheme scheme) {
		final IPreferenceStore store = PlatformUI.getPreferenceStore();
		final String schemeId = scheme.getId();
		final String defaultSchemeId = store
				.getDefaultString(IWorkbenchPreferenceConstants.KEY_CONFIGURATION_ID);
		if ((defaultSchemeId == null) ? (schemeId != null) : (!defaultSchemeId
				.equals(schemeId))) {
			final IMemento child = memento
					.createChild(TAG_ACTIVE_KEY_CONFIGURATION);
			child.putString(ATT_KEY_CONFIGURATION_ID, schemeId);
		}
	}

	private static final void writeBindingToPreferences(final IMemento parent,
			final Binding binding) {
		final IMemento element = parent.createChild(TAG_KEY_BINDING);
		element.putString(ATT_CONTEXT_ID, binding.getContextId());
		final ParameterizedCommand parameterizedCommand = binding
				.getParameterizedCommand();
		final String commandId = (parameterizedCommand == null) ? null
				: parameterizedCommand.getId();
		element.putString(ATT_COMMAND_ID, commandId);
		element.putString(ATT_KEY_CONFIGURATION_ID, binding.getSchemeId());
		element.putString(ATT_KEY_SEQUENCE, binding.getTriggerSequence()
				.toString());
		element.putString(ATT_LOCALE, binding.getLocale());
		element.putString(ATT_PLATFORM, binding.getPlatform());
		if (parameterizedCommand != null) {
			final Map parameterizations = parameterizedCommand
					.getParameterMap();
			final Iterator parameterizationItr = parameterizations.entrySet()
					.iterator();
			while (parameterizationItr.hasNext()) {
				final Map.Entry entry = (Map.Entry) parameterizationItr.next();
				final String id = (String) entry.getKey();
				final String value = (String) entry.getValue();
				final IMemento parameterElement = element
						.createChild(TAG_PARAMETER);
				parameterElement.putString(ATT_ID, id);
				parameterElement.putString(ATT_VALUE, value);
			}
		}
	}

	private final BindingManager bindingManager;

	private final CommandManager commandManager;

	public BindingPersistence(final BindingManager bindingManager,
			final CommandManager commandManager) {
		this.bindingManager = bindingManager;
		this.commandManager = commandManager;
		super.read();
	}

	@Override
	protected final boolean isChangeImportant(final IRegistryChangeEvent event) {
		return false;
	}

	public boolean bindingsNeedUpdating(final IRegistryChangeEvent event) {
		final IExtensionDelta[] acceleratorConfigurationDeltas = event
				.getExtensionDeltas(
						PlatformUI.PLUGIN_ID,
						IWorkbenchRegistryConstants.PL_ACCELERATOR_CONFIGURATIONS);
		if (acceleratorConfigurationDeltas.length == 0) {
			final IExtensionDelta[] bindingDeltas = event.getExtensionDeltas(
					PlatformUI.PLUGIN_ID,
					IWorkbenchRegistryConstants.PL_BINDINGS);
			if (bindingDeltas.length == 0) {
				final IExtensionDelta[] commandDeltas = event
						.getExtensionDeltas(PlatformUI.PLUGIN_ID,
								IWorkbenchRegistryConstants.PL_COMMANDS);
				if (commandDeltas.length == 0) {
					final IExtensionDelta[] acceleratorScopeDeltas = event
							.getExtensionDeltas(
									PlatformUI.PLUGIN_ID,
									IWorkbenchRegistryConstants.PL_ACCELERATOR_SCOPES);
					if (acceleratorScopeDeltas.length == 0) {
						final IExtensionDelta[] contextDeltas = event
								.getExtensionDeltas(PlatformUI.PLUGIN_ID,
										IWorkbenchRegistryConstants.PL_CONTEXTS);
						if (contextDeltas.length == 0) {
							final IExtensionDelta[] actionDefinitionDeltas = event
									.getExtensionDeltas(
											PlatformUI.PLUGIN_ID,
											IWorkbenchRegistryConstants.PL_ACTION_DEFINITIONS);
							if (actionDefinitionDeltas.length == 0) {
								return false;
							}
						}
					}
				}
			}
		}
		
		return true;
	}
	
	@Override
	protected final boolean isChangeImportant(final PropertyChangeEvent event) {
		return EXTENSION_COMMANDS.equals(event.getProperty());
	}

	@Override
	public final void read() {
		super.read();
		reRead();
	}
	
	public void reRead() {
		final IExtensionRegistry registry = Platform.getExtensionRegistry();
		int activeSchemeElementCount = 0;
		int bindingDefinitionCount = 0;
		int schemeDefinitionCount = 0;
		final IConfigurationElement[][] indexedConfigurationElements = new IConfigurationElement[3][];

		final IConfigurationElement[] bindingsExtensionPoint = registry
				.getConfigurationElementsFor(EXTENSION_BINDINGS);
		for (int i = 0; i < bindingsExtensionPoint.length; i++) {
			final IConfigurationElement configurationElement = bindingsExtensionPoint[i];
			final String name = configurationElement.getName();

			if (TAG_KEY.equals(name)) {
				addElementToIndexedArray(configurationElement,
						indexedConfigurationElements,
						INDEX_BINDING_DEFINITIONS, bindingDefinitionCount++);
			} else
			if (TAG_SCHEME.equals(name)) {
				addElementToIndexedArray(configurationElement,
						indexedConfigurationElements, INDEX_SCHEME_DEFINITIONS,
						schemeDefinitionCount++);
			}

		}

		final IConfigurationElement[] commandsExtensionPoint = registry
				.getConfigurationElementsFor(EXTENSION_COMMANDS);
		for (int i = 0; i < commandsExtensionPoint.length; i++) {
			final IConfigurationElement configurationElement = commandsExtensionPoint[i];
			final String name = configurationElement.getName();

			if (TAG_KEY_BINDING.equals(name)) {
				addElementToIndexedArray(configurationElement,
						indexedConfigurationElements,
						INDEX_BINDING_DEFINITIONS, bindingDefinitionCount++);

			} else if (TAG_KEY_CONFIGURATION.equals(name)) {
				addElementToIndexedArray(configurationElement,
						indexedConfigurationElements, INDEX_SCHEME_DEFINITIONS,
						schemeDefinitionCount++);

			} else if (TAG_ACTIVE_KEY_CONFIGURATION.equals(name)) {
				addElementToIndexedArray(configurationElement,
						indexedConfigurationElements, INDEX_ACTIVE_SCHEME,
						activeSchemeElementCount++);
			}
		}

		final IConfigurationElement[] acceleratorConfigurationsExtensionPoint = registry
				.getConfigurationElementsFor(EXTENSION_ACCELERATOR_CONFIGURATIONS);
		for (int i = 0; i < acceleratorConfigurationsExtensionPoint.length; i++) {
			final IConfigurationElement configurationElement = acceleratorConfigurationsExtensionPoint[i];
			final String name = configurationElement.getName();

			if (TAG_ACCELERATOR_CONFIGURATION.equals(name)) {
				addElementToIndexedArray(configurationElement,
						indexedConfigurationElements, INDEX_SCHEME_DEFINITIONS,
						schemeDefinitionCount++);
			}
		}

		final IPreferenceStore store = WorkbenchPlugin.getDefault()
				.getPreferenceStore();
		final String preferenceString = store.getString(EXTENSION_COMMANDS);
		IMemento preferenceMemento = null;
		if ((preferenceString != null) && (preferenceString.length() > 0)) {
			final Reader reader = new StringReader(preferenceString);
			try {
				preferenceMemento = XMLMemento.createReadRoot(reader);
			} catch (final WorkbenchException e) {
			}
		}

		readSchemesFromRegistry(
				indexedConfigurationElements[INDEX_SCHEME_DEFINITIONS],
				schemeDefinitionCount, bindingManager);
		readActiveScheme(indexedConfigurationElements[INDEX_ACTIVE_SCHEME],
				activeSchemeElementCount, preferenceMemento, bindingManager);
		readBindingsFromRegistry(
				indexedConfigurationElements[INDEX_BINDING_DEFINITIONS],
				bindingDefinitionCount, bindingManager, commandManager);
		readBindingsFromPreferences(preferenceMemento, bindingManager,
				commandManager);
	}
}
