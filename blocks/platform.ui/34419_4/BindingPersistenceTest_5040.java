package org.eclipse.ui.tests.keys;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.eclipse.core.commands.Category;
import org.eclipse.core.commands.Command;
import org.eclipse.core.commands.CommandManager;
import org.eclipse.core.commands.IParameter;
import org.eclipse.core.commands.IParameterValues;
import org.eclipse.core.commands.ParameterizedCommand;
import org.eclipse.core.commands.common.NotDefinedException;
import org.eclipse.core.commands.contexts.Context;
import org.eclipse.core.commands.contexts.ContextManager;
import org.eclipse.jface.bindings.Binding;
import org.eclipse.jface.bindings.BindingManager;
import org.eclipse.jface.bindings.Scheme;
import org.eclipse.jface.bindings.TriggerSequence;
import org.eclipse.jface.bindings.keys.KeyBinding;
import org.eclipse.jface.bindings.keys.KeySequence;
import org.eclipse.jface.bindings.keys.ParseException;
import org.eclipse.ui.tests.harness.util.UITestCase;

public final class BindingManagerTest extends UITestCase {

	private BindingManager bindingManager = null;

	private CommandManager commandManager = null;

	private ContextManager contextManager = null;

	public BindingManagerTest(final String name) {
		super(name);
	}

	@Override
	protected final void doSetUp() {
		commandManager = new CommandManager();
		contextManager = new ContextManager();
		bindingManager = new BindingManager(contextManager, commandManager);
	}

	@Override
	protected final void doTearDown() {
		bindingManager = null;
		contextManager = null;
		commandManager = null;
	}

	public final void testConstructor() {
		try {
			new BindingManager(null, null);
			fail("A binding manager cannot be constructed with a null context manager");
		} catch (final NullPointerException e) {
		}
	}

	public final void testAddBinding() throws NotDefinedException {
		final Context context = contextManager.getContext("na");
		context.define("name", "description", null);
		final Scheme scheme = bindingManager.getScheme("na");
		scheme.define("name", "description", null);
		bindingManager.setActiveScheme(scheme);
		final Set activeContextIds = new HashSet();
		activeContextIds.add("na");
		contextManager.setActiveContextIds(activeContextIds);

		try {
			bindingManager.addBinding(null);
			fail("It should not be possible to add a null binding");
		} catch (final NullPointerException e) {
		}

		final Binding binding = new TestBinding("conflict1", "na", "na", null,
				null, Binding.SYSTEM, null);
		bindingManager.addBinding(binding);
		assertSame("The binding should be active", binding,
				bindingManager.getPerfectMatch(TestBinding.TRIGGER_SEQUENCE));
	}

	public final void testGetActiveBindingsDisregardingContext() {
		final Map activeBindings = bindingManager
				.getActiveBindingsDisregardingContext();
		assertNotNull("The active bindings should never be null",
				activeBindings);
		assertTrue("The active bindings should start empty",
				activeBindings.isEmpty());
	}

	public final void testGetActiveBindingsDisregardingContextFlat() {
		final Collection activeBindings = bindingManager
				.getActiveBindingsDisregardingContextFlat();
		assertNotNull("The active bindings should never be null",
				activeBindings);
		assertTrue("The active bindings should start empty",
				activeBindings.isEmpty());
	}

	public final void testGetActiveBindingsFor() throws NotDefinedException {
		final TriggerSequence[] activeBindingsForNull = bindingManager
				.getActiveBindingsFor((ParameterizedCommand) null);
		assertNotNull("The active bindings for a command should never be null",
				activeBindingsForNull);
		assertTrue(
				"The active binding for a null command should always be empty",
				activeBindingsForNull.length == 0);

		final Context context = contextManager.getContext("na");
		context.define("name", "description", null);

		final Scheme scheme = bindingManager.getScheme("na");
		scheme.define("name", "description", null);

		bindingManager.setActiveScheme(scheme);
		final Set activeContextIds = new HashSet();
		activeContextIds.add("na");
		contextManager.setActiveContextIds(activeContextIds);

		final String commandId = "commandId";
		final Binding binding = new TestBinding(commandId, "na", "na", null,
				null, Binding.SYSTEM, null);
		bindingManager.addBinding(binding);

		final TriggerSequence[] bindings = bindingManager
				.getActiveBindingsFor(binding.getParameterizedCommand());
		assertEquals("There should be one binding", 1, bindings.length);
		assertSame("The binding should match", TestBinding.TRIGGER_SEQUENCE,
				bindings[0]);
	}

	public final void testGetActiveScheme() {
		assertNull("The active scheme should start null",
				bindingManager.getActiveScheme());
	}

	public final void testGetBindings() {
		assertNull("The bindings should start off null",
				bindingManager.getBindings());

		final Binding binding = new TestBinding(null, "schemeId", "contextId",
				null, null, Binding.SYSTEM, null);
		bindingManager.addBinding(binding);
		final Binding[] bindings = bindingManager.getBindings();
		assertEquals("There should be one binding", 1, bindings.length);
		assertSame("The binding should be the same", binding, bindings[0]);

		bindings[0] = null;
		assertNotNull("There should be no change",
				bindingManager.getBindings()[0]);
	}

	public final void testGetDefinedSchemeIds() {
		assertTrue("The set of defined schemes should start empty",
				bindingManager.getDefinedSchemes().length == 0);

		final Scheme scheme = bindingManager.getScheme("schemeId");
		assertTrue(
				"The set of defined schemes should still be empty after a get",
				bindingManager.getDefinedSchemes().length == 0);

		scheme.define("name", "description", null);
		Scheme[] definedSchemes = bindingManager.getDefinedSchemes();
		assertEquals("There should be one defined scheme id", 1,
				definedSchemes.length);
		assertSame("The defined scheme id should match", scheme,
				definedSchemes[0]);

		definedSchemes[0] = null;
		definedSchemes = bindingManager.getDefinedSchemes();
		assertSame("The API should not expose internal collections", scheme,
				definedSchemes[0]);

		scheme.undefine();
		assertTrue(
				"The set of defined schemes should be empty after an undefine",
				bindingManager.getDefinedSchemes().length == 0);
	}

	public final void testGetLocale() {
		assertNotNull("The locale should never be null",
				bindingManager.getLocale());
	}

	public final void testGetPartialMatches() throws NotDefinedException,
			ParseException {
		final Context context = contextManager.getContext("na");
		context.define("name", "description", null);
		final Scheme scheme = bindingManager.getScheme("na");
		scheme.define("name", "description", null);
		bindingManager.setActiveScheme(scheme);
		final Set activeContextIds = new HashSet();
		activeContextIds.add("na");
		contextManager.setActiveContextIds(activeContextIds);

		final KeySequence perfectMatch = KeySequence.getInstance("CTRL+F");
		final Command perfectCommand = commandManager.getCommand("perfect");
		final ParameterizedCommand perfectParameterizedCommand = new ParameterizedCommand(
				perfectCommand, null);
		final Binding perfectMatchBinding = new KeyBinding(perfectMatch,
				perfectParameterizedCommand, "na", "na", null, null, null,
				Binding.SYSTEM);
		final KeySequence partialMatch1 = KeySequence
				.getInstance("CTRL+F CTRL+F");
		final Command partialCommand1 = commandManager.getCommand("partial1");
		final ParameterizedCommand partialParameterizedCommand1 = new ParameterizedCommand(
				partialCommand1, null);
		final Binding partialMatchBinding1 = new KeyBinding(partialMatch1,
				partialParameterizedCommand1, "na", "na", null, null, null,
				Binding.SYSTEM);
		final Binding[] bindings = new Binding[2];
		bindings[0] = perfectMatchBinding;
		bindings[1] = partialMatchBinding1;
		bindingManager.setBindings(bindings);
		Map partialMatches = bindingManager.getPartialMatches(perfectMatch);
		assertTrue("A partial match should override a perfect match",
				!partialMatches.isEmpty());
		assertTrue("A partial match should override a perfect match",
				partialMatches.containsKey(partialMatch1));

		final KeySequence partialMatch2 = KeySequence
				.getInstance("CTRL+F CTRL+F CTRL+F");
		final Command partialCommand2 = commandManager.getCommand("partial2");
		final ParameterizedCommand partialParameterizedCommand2 = new ParameterizedCommand(
				partialCommand2, null);
		final Binding partialMatchBinding2 = new KeyBinding(partialMatch2,
				partialParameterizedCommand2, "na", "na", null, null, null,
				Binding.SYSTEM);
		bindings[0] = partialMatchBinding1;
		bindings[1] = partialMatchBinding2;
		bindingManager.setBindings(bindings);
		partialMatches = bindingManager.getPartialMatches(perfectMatch);
		assertEquals("There should be two partial matches", 2,
				partialMatches.size());
		assertSame("The partial match should be the one defined",
				partialMatchBinding1, partialMatches.get(partialMatch1));
		assertSame("The partial match should be the one defined",
				partialMatchBinding2, partialMatches.get(partialMatch2));

		bindingManager.addBinding(perfectMatchBinding);
		partialMatches = bindingManager.getPartialMatches(KeySequence
				.getInstance());
		assertEquals("There should be three partial matches", 3,
				partialMatches.size());
		assertSame("The partial match should be the one defined",
				perfectMatchBinding, partialMatches.get(perfectMatch));
		assertSame("The partial match should be the one defined",
				partialMatchBinding1, partialMatches.get(partialMatch1));
		assertSame("The partial match should be the one defined",
				partialMatchBinding2, partialMatches.get(partialMatch2));
	}

	public final void testGetPerfectMatch() throws NotDefinedException,
			ParseException {
		final Context context = contextManager.getContext("na");
		context.define("name", "description", null);
		final Scheme scheme = bindingManager.getScheme("na");
		scheme.define("name", "description", null);
		bindingManager.setActiveScheme(scheme);
		final Set activeContextIds = new HashSet();
		activeContextIds.add("na");
		contextManager.setActiveContextIds(activeContextIds);

		final KeySequence perfectMatch = KeySequence.getInstance("CTRL+F");
		final Command perfectCommand = commandManager.getCommand("perfect");
		final ParameterizedCommand perfectParameterizedCommand = new ParameterizedCommand(
				perfectCommand, null);
		final Binding perfectMatchBinding = new KeyBinding(perfectMatch,
				perfectParameterizedCommand, "na", "na", null, null, null,
				Binding.SYSTEM);
		final KeySequence partialMatch1 = KeySequence
				.getInstance("CTRL+F CTRL+F");
		final Command partialCommand1 = commandManager.getCommand("partial1");
		final ParameterizedCommand partialParameterizedCommand1 = new ParameterizedCommand(
				partialCommand1, null);
		final Binding partialMatchBinding1 = new KeyBinding(partialMatch1,
				partialParameterizedCommand1, "na", "na", null, null, null,
				Binding.SYSTEM);
		final Binding[] bindings = new Binding[2];
		bindings[0] = perfectMatchBinding;
		bindings[1] = partialMatchBinding1;
		bindingManager.setBindings(bindings);
		Binding actualBinding = bindingManager.getPerfectMatch(perfectMatch);
		assertSame("This should be a perfect match", perfectMatchBinding,
				actualBinding);

		final KeySequence partialMatch2 = KeySequence
				.getInstance("CTRL+F CTRL+F CTRL+F");
		final Command partialCommand2 = commandManager.getCommand("partial2");
		final ParameterizedCommand partialParameterizedCommand2 = new ParameterizedCommand(
				partialCommand2, null);
		final Binding partialMatchBinding2 = new KeyBinding(partialMatch2,
				partialParameterizedCommand2, "na", "na", null, null, null,
				Binding.SYSTEM);
		bindings[0] = partialMatchBinding1;
		bindings[1] = partialMatchBinding2;
		bindingManager.setBindings(bindings);
		actualBinding = bindingManager.getPerfectMatch(perfectMatch);
		assertNull("There should be no perfect matches", actualBinding);

		bindingManager.addBinding(perfectMatchBinding);
		actualBinding = bindingManager.getPerfectMatch(KeySequence
				.getInstance());
		assertNull("This should be no perfect matches for an empty sequence",
				actualBinding);
	}

	public final void testGetPlatform() {
		assertNotNull("The platform can never be null",
				bindingManager.getPlatform());
	}

	public final void testGetScheme() {
		final String schemeId = "schemeId";
		final Scheme firstScheme = bindingManager.getScheme(schemeId);
		assertTrue("A scheme should start undefined", !firstScheme.isDefined());
		final Scheme secondScheme = bindingManager.getScheme(schemeId);
		assertSame("The two scheme should be the same", firstScheme,
				secondScheme);
	}

	public final void testIsPartialMatch() throws NotDefinedException,
			ParseException {
		final Context context = contextManager.getContext("na");
		context.define("name", "description", null);
		final Scheme scheme = bindingManager.getScheme("na");
		scheme.define("name", "description", null);
		bindingManager.setActiveScheme(scheme);
		final Set activeContextIds = new HashSet();
		activeContextIds.add("na");
		contextManager.setActiveContextIds(activeContextIds);

		final KeySequence perfectMatch = KeySequence.getInstance("CTRL+F");
		final Command perfectCommand = commandManager.getCommand("perfect");
		final ParameterizedCommand perfectParameterizedCommand = new ParameterizedCommand(
				perfectCommand, null);
		final Binding perfectMatchBinding = new KeyBinding(perfectMatch,
				perfectParameterizedCommand, "na", "na", null, null, null,
				Binding.SYSTEM);
		final KeySequence partialMatch1 = KeySequence
				.getInstance("CTRL+F CTRL+F");
		final Command partialCommand1 = commandManager.getCommand("partial1");
		final ParameterizedCommand partialParameterizedCommand1 = new ParameterizedCommand(
				partialCommand1, null);
		final Binding partialMatchBinding1 = new KeyBinding(partialMatch1,
				partialParameterizedCommand1, "na", "na", null, null, null,
				Binding.SYSTEM);
		final Binding[] bindings = new Binding[2];
		bindings[0] = perfectMatchBinding;
		bindings[1] = partialMatchBinding1;
		bindingManager.setBindings(bindings);
		assertTrue("A perfect match should be overridden by a partial",
				bindingManager.isPartialMatch(perfectMatch));

		final KeySequence partialMatch2 = KeySequence
				.getInstance("CTRL+F CTRL+F CTRL+F");
		final Command partialCommand2 = commandManager.getCommand("partial2");
		final ParameterizedCommand partialParameterizedCommand2 = new ParameterizedCommand(
				partialCommand2, null);
		final Binding partialMatchBinding2 = new KeyBinding(partialMatch2,
				partialParameterizedCommand2, "na", "na", null, null, null,
				Binding.SYSTEM);
		bindings[0] = partialMatchBinding1;
		bindings[1] = partialMatchBinding2;
		bindingManager.setBindings(bindings);
		assertTrue("Two partial matches should count as a partial",
				bindingManager.isPartialMatch(perfectMatch));

		bindingManager.addBinding(perfectMatchBinding);
		bindingManager.setBindings(bindings);
		assertTrue("An empty sequence matches everything partially",
				bindingManager.isPartialMatch(KeySequence.getInstance()));
	}

	public final void testIsPerfectMatch() throws NotDefinedException,
			ParseException {
		final Context context = contextManager.getContext("na");
		context.define("name", "description", null);
		final Scheme scheme = bindingManager.getScheme("na");
		scheme.define("name", "description", null);
		bindingManager.setActiveScheme(scheme);
		final Set activeContextIds = new HashSet();
		activeContextIds.add("na");
		contextManager.setActiveContextIds(activeContextIds);

		final KeySequence perfectMatch = KeySequence.getInstance("CTRL+F");
		final Command perfectCommand = commandManager.getCommand("perfect");
		final ParameterizedCommand perfectParameterizedCommand = new ParameterizedCommand(
				perfectCommand, null);
		final Binding perfectMatchBinding = new KeyBinding(perfectMatch,
				perfectParameterizedCommand, "na", "na", null, null, null,
				Binding.SYSTEM);
		final KeySequence partialMatch1 = KeySequence
				.getInstance("CTRL+F CTRL+F");
		final Command partialCommand1 = commandManager.getCommand("partial1");
		final ParameterizedCommand partialParameterizedCommand1 = new ParameterizedCommand(
				partialCommand1, null);
		final Binding partialMatchBinding1 = new KeyBinding(partialMatch1,
				partialParameterizedCommand1, "na", "na", null, null, null,
				Binding.SYSTEM);
		final Binding[] bindings = new Binding[2];
		bindings[0] = perfectMatchBinding;
		bindings[1] = partialMatchBinding1;
		bindingManager.setBindings(bindings);
		assertTrue("This should be a perfect match",
				bindingManager.isPerfectMatch(perfectMatch));

		final KeySequence partialMatch2 = KeySequence
				.getInstance("CTRL+F CTRL+F CTRL+F");
		final Command partialCommand2 = commandManager.getCommand("perfect");
		final ParameterizedCommand partialParameterizedCommand2 = new ParameterizedCommand(
				partialCommand2, null);
		final Binding partialMatchBinding2 = new KeyBinding(partialMatch2,
				partialParameterizedCommand2, "na", "na", null, null, null,
				Binding.SYSTEM);
		bindings[0] = partialMatchBinding1;
		bindings[1] = partialMatchBinding2;
		bindingManager.setBindings(bindings);
		assertTrue("This should be no perfect matches",
				!bindingManager.isPerfectMatch(perfectMatch));

		bindingManager.addBinding(perfectMatchBinding);
		assertTrue("This should be no perfect matches",
				!bindingManager.isPerfectMatch(KeySequence.getInstance()));
	}

	public final void testRemoveBindings() throws NotDefinedException {
		final Context context = contextManager.getContext("na");
		context.define("name", "description", null);
		final Scheme scheme = bindingManager.getScheme("na");
		scheme.define("name", "description", null);
		bindingManager.setActiveScheme(scheme);
		final Set activeContextIds = new HashSet();
		activeContextIds.add("na");
		contextManager.setActiveContextIds(activeContextIds);

		final Binding binding1 = new TestBinding("command1", "na", "na", null,
				null, Binding.SYSTEM, null);
		bindingManager.addBinding(binding1);
		final Binding binding2 = new TestBinding("command2", "na", "na", "zh",
				null, Binding.SYSTEM, null);
		bindingManager.addBinding(binding2);
		final Binding binding3 = new TestBinding("command3", "na", "na", null,
				"gtk", Binding.SYSTEM, null);
		bindingManager.addBinding(binding3);
		final Binding binding4 = new TestBinding("command4", "na", "na", null,
				"gtk", Binding.USER, null);
		bindingManager.addBinding(binding4);
		final Binding binding5 = new TestBinding("command5", "na", "na", "zh",
				"gtk", Binding.USER, null);
		bindingManager.addBinding(binding5);
		assertNotNull("There should be three active bindings",
				bindingManager.getActiveBindingsFor(binding1
						.getParameterizedCommand()));
		assertNotNull("There should be three active bindings",
				bindingManager.getActiveBindingsFor(binding2
						.getParameterizedCommand()));
		assertNotNull("There should be three active bindings",
				bindingManager.getActiveBindingsFor(binding4
						.getParameterizedCommand()));

		bindingManager.removeBindings(TestBinding.TRIGGER_SEQUENCE, "na", "na",
				"zh", "gtk", null, Binding.USER);
		assertEquals("There should be four bindings left", 4,
				bindingManager.getBindings().length);
		assertNotNull("There should be four active bindings",
				bindingManager.getActiveBindingsFor(binding1
						.getParameterizedCommand()));
		assertNotNull("There should be four active bindings",
				bindingManager.getActiveBindingsFor(binding2
						.getParameterizedCommand()));
		assertNotNull("There should be four active bindings",
				bindingManager.getActiveBindingsFor(binding3
						.getParameterizedCommand()));
		assertNotNull("There should be four active bindings",
				bindingManager.getActiveBindingsFor(binding4
						.getParameterizedCommand()));
	}

	public final void testSetActiveScheme() {
		final String schemeId = "schemeId";
		final Scheme scheme = bindingManager.getScheme(schemeId);
		try {
			bindingManager.setActiveScheme(scheme);
			fail("Cannot activate an undefined scheme");
		} catch (final NotDefinedException e) {
		}

		scheme.define("name", "description", null);
		try {
			bindingManager.setActiveScheme(scheme);
			assertSame("The schemes should match", scheme,
					bindingManager.getActiveScheme());
		} catch (final NotDefinedException e) {
			fail("Should be able to activate a scheme");
		}

		scheme.undefine();
		assertNull("The scheme should have become unselected",
				bindingManager.getActiveScheme());
	}

	public void testGetCurrentConflicts() throws NotDefinedException,
			ParseException {

		final Context context = contextManager.getContext("na");
		context.define("name", "description", null);
		final Scheme scheme = bindingManager.getScheme("na");
		scheme.define("name", "description", null);
		bindingManager.setActiveScheme(scheme);
		contextManager.setActiveContextIds(null);

		Command command1 = commandManager.getCommand("conflictCommand1");
		ParameterizedCommand parameterizedCommand1 = ParameterizedCommand
				.generateCommand(command1, null);
		Command command2 = commandManager.getCommand("conflictCommand2");
		ParameterizedCommand parameterizedCommand2 = ParameterizedCommand
				.generateCommand(command2, null);
		Command command3 = commandManager.getCommand("conflictCommand3");
		ParameterizedCommand parameterizedCommand3 = ParameterizedCommand
				.generateCommand(command3, null);
		KeySequence conflict = KeySequence.getInstance("M1+M2+9");
		KeySequence noConflict = KeySequence.getInstance("M1+M2+8");
		Binding binding1 = new KeyBinding(conflict, parameterizedCommand1,
				"na", "na", null, null, null, Binding.SYSTEM);
		Binding binding2 = new KeyBinding(conflict, parameterizedCommand2,
				"na", "na", null, null, null, Binding.SYSTEM);
		Binding binding3 = new KeyBinding(noConflict, parameterizedCommand3,
				"na", "na", null, null, null, Binding.SYSTEM);
		final Binding[] bindings = new Binding[] { binding1, binding2, binding3 };
		bindingManager.setBindings(bindings);

		final Set activeContextIds = new HashSet();
		activeContextIds.add("na");
		contextManager.setActiveContextIds(activeContextIds);

		Map activeBindingsDisregardingContext = bindingManager
				.getActiveBindingsDisregardingContext();// force a recompute
		assertNotNull(activeBindingsDisregardingContext);

		Map currentConflicts = bindingManager.getCurrentConflicts();
		assertEquals(1, currentConflicts.size()); // we have only one conflict

		Collection conflictsCollection = bindingManager
				.getConflictsFor(noConflict);
		assertNull(conflictsCollection); // no conflict for this keybinding

		conflictsCollection = bindingManager.getConflictsFor(conflict);
		assertNotNull(conflictsCollection); // this has one conflict with 2
		assertEquals(2, conflictsCollection.size());

	}

	public final void testSetBindings() throws NotDefinedException {
		final Context context = contextManager.getContext("na");
		context.define("name", "description", null);
		final Scheme scheme = bindingManager.getScheme("na");
		scheme.define("name", "description", null);
		bindingManager.setActiveScheme(scheme);
		final Set activeContextIds = new HashSet();
		activeContextIds.add("na");
		contextManager.setActiveContextIds(activeContextIds);

		bindingManager.setBindings(null);
		assertTrue(
				"There should be no active bindings",
				bindingManager
						.getActiveBindingsFor((ParameterizedCommand) null).length == 0);

		final String commandId = "commandId";
		final Binding binding = new TestBinding(commandId, "na", "na", null,
				null, Binding.SYSTEM, null);
		final Binding[] bindings = new Binding[1];
		bindings[0] = binding;
		bindingManager.setBindings(bindings);
		final TriggerSequence[] activeBindings = bindingManager
				.getActiveBindingsFor(binding.getParameterizedCommand());
		assertEquals("There should be one active binding", 1,
				activeBindings.length);
		assertSame("The binding should be the one we set",
				TestBinding.TRIGGER_SEQUENCE, activeBindings[0]);
	}

	public final void testSetLocale() throws NotDefinedException {
		final Context context = contextManager.getContext("na");
		context.define("name", "description", null);
		final Scheme scheme = bindingManager.getScheme("na");
		scheme.define("name", "description", null);
		bindingManager.setActiveScheme(scheme);
		final Set activeContextIds = new HashSet();
		activeContextIds.add("na");
		contextManager.setActiveContextIds(activeContextIds);

		try {
			bindingManager.setLocale(null);
			fail("Cannot set the locale to null");
		} catch (final NullPointerException e) {
		}

		final String commandId = "commandId";
		final Binding binding = new TestBinding(commandId, "na", "na", "xx",
				null, Binding.SYSTEM, null);
		bindingManager.addBinding(binding);
		assertTrue("The binding shouldn't be active",
				bindingManager.getActiveBindingsFor(binding
						.getParameterizedCommand()).length == 0);
		bindingManager.setLocale("xx_XX");
		final TriggerSequence[] activeBindings = bindingManager
				.getActiveBindingsFor(binding.getParameterizedCommand());
		assertEquals("The binding should become active", 1,
				activeBindings.length);
		assertSame("The binding should be the same",
				TestBinding.TRIGGER_SEQUENCE, activeBindings[0]);
	}

	public final void testSetPlatform() throws NotDefinedException {
		final Context context = contextManager.getContext("na");
		context.define("name", "description", null);
		final Scheme scheme = bindingManager.getScheme("na");
		scheme.define("name", "description", null);
		bindingManager.setActiveScheme(scheme);
		final Set activeContextIds = new HashSet();
		activeContextIds.add("na");
		contextManager.setActiveContextIds(activeContextIds);

		try {
			bindingManager.setPlatform(null);
			fail("Cannot set the platform to null");
		} catch (final NullPointerException e) {
		}

		final String commandId = "commandId";
		final Binding binding = new TestBinding(commandId, "na", "na", null,
				"atari", Binding.SYSTEM, null);
		bindingManager.addBinding(binding);
		assertTrue("The binding shouldn't be active",
				bindingManager.getActiveBindingsFor(binding
						.getParameterizedCommand()).length == 0);
		bindingManager.setPlatform("atari");
		final TriggerSequence[] activeBindings = bindingManager
				.getActiveBindingsFor(binding.getParameterizedCommand());
		assertEquals("The binding should become active", 1,
				activeBindings.length);
		assertSame("The binding should be the same",
				TestBinding.TRIGGER_SEQUENCE, activeBindings[0]);
	}

	public final void testGetBestActiveBindingFor() throws Exception {
		final TriggerSequence[] activeBindingsForNull = bindingManager
				.getActiveBindingsFor((ParameterizedCommand) null);
		assertNotNull("The active bindings for a command should never be null",
				activeBindingsForNull);
		assertTrue(
				"The active binding for a null command should always be empty",
				activeBindingsForNull.length == 0);

		final Context context = contextManager.getContext("na");
		context.define("name", "description", null);

		final Scheme scheme = bindingManager.getScheme("na");
		scheme.define("name", "description", null);

		bindingManager.setActiveScheme(scheme);
		final Set activeContextIds = new HashSet();
		activeContextIds.add("na");
		contextManager.setActiveContextIds(activeContextIds);

		final String commandId = "commandId";
		final String categoryId = "cat";
		Category cat = commandManager.getCategory(categoryId);
		cat.define("cat", "cat");
		Command cmd = commandManager.getCommand(commandId);
		IParameter[] parms = new IParameter[1];
		parms[0] = new IParameter() {
			@Override
			public String getId() {
				return "viewId";
			}

			@Override
			public String getName() {
				return "View Id";
			}

			@Override
			public IParameterValues getValues() {
				return null;
			}

			@Override
			public boolean isOptional() {
				return false;
			}
		};
		cmd.define("na", "NA", cat, parms);
		Map map = new HashMap();
		map.put("viewId", "outline");
		ParameterizedCommand outline = ParameterizedCommand.generateCommand(
				cmd, map);
		map = new HashMap();
		map.put("viewId", "console");
		ParameterizedCommand console = ParameterizedCommand.generateCommand(
				cmd, map);
		assertFalse(outline.equals(console));

		final Binding b2 = new KeyBinding(KeySequence.getInstance("M1+M2+V"),
				outline, "na", "na", null, null, null, Binding.SYSTEM);
		bindingManager.addBinding(b2);

		final Binding binding = new KeyBinding(KeySequence.getInstance("M1+V"),
				outline, "na", "na", null, null, null, Binding.SYSTEM);
		bindingManager.addBinding(binding);

		final Binding b3 = new KeyBinding(KeySequence.getInstance("M1+M2+C"),
				console, "na", "na", null, null, null, Binding.SYSTEM);
		bindingManager.addBinding(b3);


		final TriggerSequence[] bindings = bindingManager
				.getActiveBindingsFor(binding.getParameterizedCommand());
		assertEquals(2, bindings.length);

		final TriggerSequence bestBinding = bindingManager
				.getBestActiveBindingFor(outline);
		assertEquals(binding.getTriggerSequence(), bestBinding);

		final TriggerSequence bestBinding2 = bindingManager
				.getBestActiveBindingFor(console);
		assertEquals(b3.getTriggerSequence(), bestBinding2);
	}
}
