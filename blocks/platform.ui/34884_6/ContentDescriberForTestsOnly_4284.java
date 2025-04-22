package org.eclipse.ui.tests.performance;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import org.eclipse.core.commands.Command;
import org.eclipse.core.commands.CommandManager;
import org.eclipse.core.commands.ParameterizedCommand;
import org.eclipse.core.commands.common.NotDefinedException;
import org.eclipse.core.commands.contexts.Context;
import org.eclipse.core.commands.contexts.ContextManager;
import org.eclipse.jface.bindings.Binding;
import org.eclipse.jface.bindings.BindingManager;
import org.eclipse.jface.bindings.Scheme;
import org.eclipse.jface.bindings.keys.IKeyLookup;
import org.eclipse.jface.bindings.keys.KeyBinding;
import org.eclipse.jface.bindings.keys.KeyLookupFactory;
import org.eclipse.jface.bindings.keys.KeySequence;
import org.eclipse.jface.bindings.keys.KeyStroke;
import org.eclipse.jface.bindings.keys.ParseException;
import org.eclipse.jface.util.Util;

public final class CommandsPerformanceTest extends BasicPerformanceTest {

	private static final void createContext(
			final ContextManager contextManager, final String parent,
			final int successors, final List activeContextIds) {
		final int count = activeContextIds.size();
		final String contextString = "context" + count;
		final Context context = contextManager.getContext(contextString);
		context.define(contextString, contextString, parent);
		activeContextIds.add(contextString);

		if (successors == 0) {
			return;
		}

		createContext(contextManager, contextString, successors - 1,
				activeContextIds);
	}

	private static final void createScheme(final BindingManager bindingManager,
			final String parent, final int successors, final List schemes) {
		final int count = schemes.size();
		final String schemeString = "scheme" + count;
		final Scheme scheme = bindingManager.getScheme(schemeString);
		scheme.define(schemeString, schemeString, parent);
		schemes.add(scheme);

		if (successors == 0) {
			return;
		}

		createScheme(bindingManager, schemeString, successors - 1, schemes);
	}

	private BindingManager bindingManager = null;

	private CommandManager commandManager = null;

	private ContextManager contextManager = null;

	public CommandsPerformanceTest(final String name) {
		super(name);
	}

	protected final void doSetUp() throws NotDefinedException, Exception {
		super.doSetUp();

		final int contextTreeDepth = 40;
		final int schemeDepth = 20;
		final int bindingCount = 5000;
		final int platformLocaleCount = 1400;
		final int deletionMarkers = 500;
		final String currentLocale = Locale.getDefault().toString();
		final String currentPlatform = Util.getWS();

		final IKeyLookup lookup = KeyLookupFactory.getDefault();
		final int modifierKeys0 = 0;
		final int modifierKeys1 = lookup.getAlt();
		final int modifierKeys2 = lookup.getCommand();
		final int modifierKeys3 = lookup.getCtrl();
		final int modifierKeys4 = lookup.getShift();
		final int modifierKeys5 = lookup.getAlt() | lookup.getCommand();
		final int modifierKeys6 = lookup.getAlt() | lookup.getCtrl();
		final int modifierKeys7 = lookup.getAlt() | lookup.getShift();
		final int modifierKeys8 = lookup.getCommand() | lookup.getCtrl();
		final int modifierKeys9 = lookup.getCommand() | lookup.getShift();
		final int modifierKeys10 = lookup.getCtrl() | lookup.getShift();
		final int modifierKeys11 = lookup.getAlt() | lookup.getCommand()
				| lookup.getCtrl();
		final int modifierKeys12 = lookup.getAlt() | lookup.getCommand()
				| lookup.getShift();
		final int modifierKeys13 = lookup.getAlt() | lookup.getCtrl()
				| lookup.getShift();
		final int modifierKeys14 = lookup.getCommand() | lookup.getCtrl()
				| lookup.getShift();
		final int modifierKeys15 = lookup.getAlt() | lookup.getCommand()
				| lookup.getCtrl() | lookup.getShift();
		final int[] modifierKeyTable = { modifierKeys0, modifierKeys1,
				modifierKeys2, modifierKeys3, modifierKeys4, modifierKeys5,
				modifierKeys6, modifierKeys7, modifierKeys8, modifierKeys9,
				modifierKeys10, modifierKeys11, modifierKeys12, modifierKeys13,
				modifierKeys14, modifierKeys15 };

		commandManager = new CommandManager();

		contextManager = new ContextManager();
		final List activeContextIds = new ArrayList();
		createContext(contextManager, null, contextTreeDepth, activeContextIds);
		contextManager.setActiveContextIds(new HashSet(activeContextIds));

		bindingManager = new BindingManager(contextManager, commandManager);
		final List schemes = new ArrayList();
		createScheme(bindingManager, null, schemeDepth, schemes);
		bindingManager
				.setActiveScheme((Scheme) schemes.get(schemes.size() - 1));

		final Binding[] bindings = new Binding[bindingCount];
		for (int i = 0; i < deletionMarkers; i++) {
			String locale = null;
			String platform = null;

			if (i < platformLocaleCount) {
				switch (i % 4) {
				case 0:
					locale = currentLocale;
					break;
				case 1:
					platform = currentPlatform;
					break;
				case 2:
					locale = "gibberish";
					break;
				case 3:
					platform = "gibberish";
					break;
				}
			}

			final char character = (char) ('A' + (i % 26));
			final int modifierKeys = modifierKeyTable[(i / 26)
					% modifierKeyTable.length];
			final KeyStroke keyStroke = KeyStroke.getInstance(modifierKeys,
					character);
			final KeySequence keySequence = KeySequence.getInstance(keyStroke);

			final String schemeId = ((Scheme) schemes.get(i % schemes.size()))
					.getId();
			final String contextId = (String) activeContextIds.get(i
					% activeContextIds.size());
			final int type = (i % 2);

			final Binding binding = new KeyBinding(keySequence, null, schemeId,
					contextId, locale, platform, null, type);
			bindings[i] = binding;
		}

		for (int i = 0; i < bindingCount - deletionMarkers; i++) {
			String locale = null;
			String platform = null;

			if ((i > deletionMarkers) && (i < platformLocaleCount)) {
				switch (i % 4) {
				case 0:
					locale = currentLocale;
					break;
				case 1:
					platform = currentPlatform;
					break;
				case 2:
					locale = "gibberish";
					break;
				case 3:
					platform = "gibberish";
					break;
				}
			}

			final char character = (char) ('A' + (i % 26));
			final int modifierKeys = modifierKeyTable[(i / 26)
					% modifierKeyTable.length];
			final KeyStroke keyStroke = KeyStroke.getInstance(modifierKeys,
					character);
			final KeySequence keySequence = KeySequence.getInstance(keyStroke);

			final String commandId = "command" + i;
			final String schemeId = ((Scheme) schemes.get(i % schemes.size()))
					.getId();
			final String contextId = (String) activeContextIds.get(i
					% activeContextIds.size());
			final int type = (i % 2);

			final Command command = commandManager.getCommand(commandId);
			final ParameterizedCommand parameterizedCommand = new ParameterizedCommand(
					command, null);
			final Binding binding = new KeyBinding(keySequence,
					parameterizedCommand, schemeId, contextId, locale,
					platform, null, type);
			bindings[i + deletionMarkers] = binding;
		}
		bindingManager.setBindings(bindings);
	}

	protected final void doTearDown() throws Exception {
		bindingManager = null;
		commandManager = null;
		contextManager = null;
		super.doTearDown();
	}

	public final void testBindingCacheHitHard() throws ParseException {
		final int cacheHits = 1000000;
		final KeySequence keySequence = KeySequence.getInstance("CTRL+F");

		bindingManager.getPartialMatches(keySequence);

		startMeasuring();
		for (int i = 0; i < cacheHits; i++) {
			bindingManager.getPartialMatches(keySequence);
		}
		stopMeasuring();
		commitMeasurements();
		assertPerformance();
	}

	public final void testBindingCacheHitHardReverse() throws ParseException {
		final int cacheHits = 1000000;
		final KeySequence keySequence = KeySequence.getInstance("CTRL+F");

		bindingManager.getPartialMatches(keySequence);

		startMeasuring();
		for (int i = 0; i < cacheHits; i++) {
			bindingManager.getActiveBindingsFor((ParameterizedCommand) null);
		}
		stopMeasuring();
		commitMeasurements();
		assertPerformance();
	}

	public final void testBindingCacheHitSoft() throws ParseException {
		final int cacheHits = 10000;
		final KeySequence keySequence = KeySequence.getInstance("CTRL+F");

		final Set contextSet1 = contextManager.getActiveContextIds();
		bindingManager.getPartialMatches(keySequence);
		final List contextList = new ArrayList(contextSet1);
		contextList.remove(contextList.size() - 1);
		final Set contextSet2 = new HashSet(contextList);
		contextManager.setActiveContextIds(contextSet2);
		bindingManager.getPartialMatches(keySequence);

		startMeasuring();
		for (int i = 0; i < cacheHits; i++) {
			if ((i % 2) == 0) {
				contextManager.setActiveContextIds(contextSet1);
			} else {
				contextManager.setActiveContextIds(contextSet2);
			}
			bindingManager.getPartialMatches(keySequence);
		}
		stopMeasuring();
		commitMeasurements();
		assertPerformance();
	}

	public final void testBindingCacheMissLarge() throws ParseException {
		final KeySequence keySequence = KeySequence.getInstance("CTRL+F");

		startMeasuring();
		bindingManager.getPartialMatches(keySequence);
		stopMeasuring();
		commitMeasurements();
		assertPerformance();
	}
}
