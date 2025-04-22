package org.eclipse.ui.tests.keys;

import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

import org.eclipse.core.commands.CommandManager;
import org.eclipse.core.commands.common.NotDefinedException;
import org.eclipse.core.commands.contexts.Context;
import org.eclipse.core.commands.contexts.ContextManager;
import org.eclipse.jface.bindings.Binding;
import org.eclipse.jface.bindings.BindingManager;
import org.eclipse.jface.bindings.Scheme;
import org.eclipse.jface.bindings.TriggerSequence;
import org.eclipse.jface.util.Util;
import org.eclipse.ui.tests.harness.util.UITestCase;

public final class BindingInteractionsTest extends UITestCase {

	private BindingManager bindingManager = null;

	private ContextManager contextManager = null;

	public BindingInteractionsTest(final String name) {
		super(name);
	}

	@Override
	protected void doSetUp() {
		contextManager = new ContextManager();
		bindingManager = new BindingManager(contextManager,
				new CommandManager());
	}

	@Override
	protected void doTearDown() {
		contextManager = null;
		bindingManager = null;
	}

	public void testConflict() throws NotDefinedException {
		final Context context = contextManager.getContext("na");
		context.define("name", "description", null);

		final Scheme scheme = bindingManager.getScheme("na");
		scheme.define("name", "description", null);

		bindingManager.setActiveScheme(scheme);
		final Set activeContextIds = new HashSet();
		activeContextIds.add("na");
		contextManager.setActiveContextIds(activeContextIds);

		final Binding binding1 = new TestBinding("conflict1", "na", "na", null,
				null, Binding.SYSTEM, null);
		bindingManager.addBinding(binding1);
		final Binding binding2 = new TestBinding("conflict2", "na", "na", null,
				null, Binding.SYSTEM, null);
		bindingManager.addBinding(binding2);

		TriggerSequence[] activeBindings = bindingManager
				.getActiveBindingsFor(binding1.getParameterizedCommand());
		assertFalse(binding1.equals(binding2));
		assertTrue("Neither binding should be active",
				activeBindings.length == 0);
		activeBindings = bindingManager.getActiveBindingsFor(binding2
				.getParameterizedCommand());
		assertTrue("Neither binding should be active",
				activeBindings.length == 0);
	}

	public void testContextOverride() throws NotDefinedException {
		final Context parentContext = contextManager.getContext("parent");
		parentContext.define("parent", "parent context", null);

		final Context childContext = contextManager.getContext("child");
		childContext.define("child", "child context", "parent");

		final Scheme scheme = bindingManager.getScheme("na");
		scheme.define("name", "description", null);

		bindingManager.setActiveScheme(scheme);

		final Binding binding1 = new TestBinding("parent", "na", "parent",
				null, null, Binding.SYSTEM, null);
		bindingManager.addBinding(binding1);
		final Binding binding2 = new TestBinding("child", "na", "child", null,
				null, Binding.SYSTEM, null);
		bindingManager.addBinding(binding2);

		final Set activeContextIds = new HashSet();
		activeContextIds.add("parent");
		contextManager.setActiveContextIds(activeContextIds);
		assertEquals(
				"When only the parent context is active, only the parent binding is active.",
				binding1, bindingManager
						.getPerfectMatch(TestBinding.TRIGGER_SEQUENCE));

		activeContextIds.clear();
		activeContextIds.add("child");
		contextManager.setActiveContextIds(activeContextIds);
		assertEquals(
				"When only the child context is active, only the child binding is active.",
				binding2, bindingManager
						.getPerfectMatch(TestBinding.TRIGGER_SEQUENCE));

		activeContextIds.add("parent");
		contextManager.setActiveContextIds(activeContextIds);
		assertEquals(
				"When both contexts are active, only the child binding is active.",
				binding2, bindingManager
						.getPerfectMatch(TestBinding.TRIGGER_SEQUENCE));
	}

	public void testDeletedBinding() throws NotDefinedException {
		final Context context = contextManager.getContext("na");
		context.define("name", "description", null);

		final Scheme scheme = bindingManager.getScheme("na");
		scheme.define("name", "description", null);

		bindingManager.setActiveScheme(scheme);
		final Set activeContextIds = new HashSet();
		activeContextIds.add("na");
		contextManager.setActiveContextIds(activeContextIds);

		final Binding binding1 = new TestBinding(null, "na", "na", null, null,
				Binding.USER, null);
		bindingManager.addBinding(binding1);
		final Binding binding2 = new TestBinding("system", "na", "na", null,
				null, Binding.SYSTEM, null);
		bindingManager.addBinding(binding2);
		assertEquals("The user should be able to remove bindings", null,
				bindingManager.getPerfectMatch(TestBinding.TRIGGER_SEQUENCE));
	}

	public void testDeletedBindingAllowsParent() throws NotDefinedException {
		final Context parentContext = contextManager.getContext("parent");
		parentContext.define("name", "description", null);
		final Context childContext = contextManager.getContext("child");
		childContext.define("name", "description", "parent");

		final Scheme scheme = bindingManager.getScheme("na");
		scheme.define("name", "description", null);

		bindingManager.setActiveScheme(scheme);
		final Set activeContextIds = new HashSet();
		activeContextIds.add("parent");
		activeContextIds.add("child");
		contextManager.setActiveContextIds(activeContextIds);

		final Binding childBinding = new TestBinding("childCommand", "na",
				"child", null, null, Binding.SYSTEM, null);
		bindingManager.addBinding(childBinding);
		final Binding deletion = new TestBinding(null, "na", "child", null,
				null, Binding.USER, null);
		bindingManager.addBinding(deletion);
		final Binding parentBinding = new TestBinding("parentCommand", "na",
				"parent", null, null, Binding.SYSTEM, null);
		bindingManager.addBinding(parentBinding);
		assertEquals(
				"The user should be able to remove bindings to allow a parent binding",
				"parentCommand", bindingManager.getPerfectMatch(
						TestBinding.TRIGGER_SEQUENCE).getParameterizedCommand()
						.getId());
	}

	public void testDeletedBindingPlatform() throws NotDefinedException {
		final String na = "na";

		final Context context = contextManager.getContext(na);
		context.define("name", "description", null);

		final Scheme scheme = bindingManager.getScheme(na);
		scheme.define("name", "description", null);

		bindingManager.setActiveScheme(scheme);
		final Set activeContextIds = new HashSet();
		activeContextIds.add("na");
		contextManager.setActiveContextIds(activeContextIds);

		final Binding allPlatforms = new TestBinding("allPlatforms", na, na,
				null, null, Binding.SYSTEM, null);
		bindingManager.addBinding(allPlatforms);
		final Binding deletion = new TestBinding(null, na, na, null, Util.getWS(),
				Binding.SYSTEM, null);
		bindingManager.addBinding(deletion);
		final Binding platformSpecific = new TestBinding("platformSpecific",
				na, na, null, Util.getWS(), Binding.SYSTEM, null);
		bindingManager.addBinding(platformSpecific);
		assertEquals(
				"We should be able to change a binding on a particular platform",
				platformSpecific, bindingManager
						.getPerfectMatch(TestBinding.TRIGGER_SEQUENCE));
	}

	public void testDeletedBindingUnnecessarily() throws NotDefinedException {
		final Context context = contextManager.getContext("na");
		context.define("name", "description", null);

		final Scheme scheme = bindingManager.getScheme("na");
		scheme.define("name", "description", null);

		bindingManager.setActiveScheme(scheme);
		final Set activeContextIds = new HashSet();
		activeContextIds.add("na");
		contextManager.setActiveContextIds(activeContextIds);

		final Binding binding1 = new TestBinding(null, "na", "na",
				"not the current locale", null, Binding.SYSTEM, null);
		final Binding binding2 = new TestBinding("system", "na", "na", null,
				null, Binding.SYSTEM, null);
		final Binding binding3 = new TestBinding(null, "na", "na", null,
				"not the current platform", Binding.SYSTEM, null);
		final Binding[] bindings = new Binding[3];
		bindings[0] = binding1;
		bindings[1] = binding2;
		bindings[2] = binding3;
		bindingManager.setBindings(bindings);
		assertEquals(
				"A binding should not cause a deletion if its locale or platform doesn't match",
				binding2, bindingManager
						.getPerfectMatch(TestBinding.TRIGGER_SEQUENCE));
	}

	public void testDeletedBindingWithAddition() throws NotDefinedException {
		final Context context = contextManager.getContext("na");
		context.define("name", "description", null);

		final Scheme scheme = bindingManager.getScheme("na");
		scheme.define("name", "description", null);

		bindingManager.setActiveScheme(scheme);
		final Set activeContextIds = new HashSet();
		activeContextIds.add("na");
		contextManager.setActiveContextIds(activeContextIds);

		final Binding binding1 = new TestBinding(null, "na", "na", null, null,
				Binding.USER, null);
		bindingManager.addBinding(binding1);
		final Binding binding2 = new TestBinding("system", "na", "na", null,
				null, Binding.SYSTEM, null);
		bindingManager.addBinding(binding2);
		final Binding binding3 = new TestBinding("user", "na", "na", null,
				null, Binding.USER, null);
		bindingManager.addBinding(binding3);
		assertEquals("The user redefine a particular binding", binding3,
				bindingManager.getPerfectMatch(TestBinding.TRIGGER_SEQUENCE));
	}

	public void testDeletedBindingWithUserOverride() throws NotDefinedException {
		final Context context = contextManager.getContext("na");
		context.define("name", "description", null);

		final Scheme scheme = bindingManager.getScheme("na");
		scheme.define("name", "description", null);

		bindingManager.setActiveScheme(scheme);
		final Set activeContextIds = new HashSet();
		activeContextIds.add("na");
		contextManager.setActiveContextIds(activeContextIds);

		final Binding generalBinding = new TestBinding("general", scheme
				.getId(), context.getId(), null, null, Binding.SYSTEM, null);
		bindingManager.addBinding(generalBinding);
		final Binding cancelOnPlatform = new TestBinding(null, scheme.getId(),
				context.getId(), null, bindingManager.getPlatform(),
				Binding.SYSTEM, null);
		bindingManager.addBinding(cancelOnPlatform);
		final Binding userOverride = new TestBinding("user", scheme.getId(),
				context.getId(), null, bindingManager.getPlatform(),
				Binding.USER, null);
		bindingManager.addBinding(userOverride);
		assertEquals("The user redefine a binding deleted in the system",
				userOverride, bindingManager
						.getPerfectMatch(TestBinding.TRIGGER_SEQUENCE));

	}

	public void testDoubleDeletedBinding() {
		final String parent = "parent";
		final String child = "child";

		final Context parentContext = contextManager.getContext(parent);
		parentContext.define(parent, parent, null);
		final Context childContext = contextManager.getContext(child);
		childContext.define(child, child, parent);
		final Set activeContextIds = new HashSet();
		activeContextIds.add(parent);
		activeContextIds.add(child);
		contextManager.setActiveContextIds(activeContextIds);

		final Scheme scheme = bindingManager.getScheme("na");
		scheme.define(parent, parent, null);

		final Binding binding = new TestBinding("command", "na", parent, null,
				null, Binding.SYSTEM, null);
		bindingManager.addBinding(binding);
		final Binding correctDeletion = new TestBinding(null, "na", parent,
				null, null, Binding.USER, null);
		bindingManager.addBinding(correctDeletion);
		final Binding wrongDeletion = new TestBinding(null, "na", child, null,
				null, Binding.USER, null);
		bindingManager.addBinding(wrongDeletion);

		assertEquals("The parent should not be active", null, bindingManager
				.getPerfectMatch(binding.getTriggerSequence()));
	}

	public void testDoubleParent() throws NotDefinedException {
		final String parent = "parent";
		final String child = "child";

		final Context parentContext = contextManager.getContext(parent);
		parentContext.define(parent, parent, null);
		final Context childContext = contextManager.getContext(child);
		childContext.define(child, child, parent);
		final Set activeContextIds = new HashSet();
		activeContextIds.add(parent);
		activeContextIds.add(child);
		contextManager.setActiveContextIds(activeContextIds);

		final Scheme parentScheme = bindingManager.getScheme(parent);
		parentScheme.define(parent, parent, null);
		final Scheme childScheme = bindingManager.getScheme(child);
		childScheme.define(child, child, parent);
		bindingManager.setActiveScheme(childScheme);

		final Binding parentBinding = new TestBinding(parent, parent, parent,
				null, null, Binding.SYSTEM, null);
		final Binding childBinding = new TestBinding(child, child, child, null,
				null, Binding.SYSTEM, null);
		bindingManager.addBinding(parentBinding);
		bindingManager.addBinding(childBinding);

		assertTrue("The parent should not be active",
				bindingManager.getActiveBindingsFor(parentBinding
						.getParameterizedCommand()).length == 0);
		assertTrue("The child should be active",
				bindingManager.getActiveBindingsFor(childBinding
						.getParameterizedCommand()).length != 0);
	}

	public void testLocaleOverride() throws NotDefinedException {
		final Context context = contextManager.getContext("na");
		context.define("name", "description", null);

		final Scheme scheme = bindingManager.getScheme("na");
		scheme.define("name", "description", null);

		bindingManager.setActiveScheme(scheme);
		final Set activeContextIds = new HashSet();
		activeContextIds.add("na");
		contextManager.setActiveContextIds(activeContextIds);

		final Binding binding1 = new TestBinding("base", "na", "na", null,
				null, Binding.SYSTEM, null);
		bindingManager.addBinding(binding1);
		final Binding binding2 = new TestBinding(null, "na", "na", Locale
				.getDefault().toString(), null, Binding.SYSTEM, null);
		bindingManager.addBinding(binding2);
		final Binding binding3 = new TestBinding("locale-specific", "na", "na",
				Locale.getDefault().toString(), null, Binding.SYSTEM, null);
		bindingManager.addBinding(binding3);
		assertEquals(
				"A plug-in developer should be able to change a binding for a locale",
				binding3, bindingManager
						.getPerfectMatch(TestBinding.TRIGGER_SEQUENCE));
	}

	public void testPlatformOverride() throws NotDefinedException {
		final Context context = contextManager.getContext("na");
		context.define("name", "description", null);

		final Scheme scheme = bindingManager.getScheme("na");
		scheme.define("name", "description", null);

		bindingManager.setActiveScheme(scheme);
		final Set activeContextIds = new HashSet();
		activeContextIds.add("na");
		contextManager.setActiveContextIds(activeContextIds);

		final Binding binding1 = new TestBinding("base", "na", "na", null,
				null, Binding.SYSTEM, null);
		bindingManager.addBinding(binding1);
		final Binding binding2 = new TestBinding(null, "na", "na", null, Util.getWS(),
				Binding.SYSTEM, null);
		bindingManager.addBinding(binding2);
		final Binding binding3 = new TestBinding("platform-specific", "na",
				"na", null, Util.getWS(), Binding.SYSTEM, null);
		bindingManager.addBinding(binding3);
		assertEquals(
				"A plug-in developer should be able to change a binding for a platform",
				binding3, bindingManager
						.getPerfectMatch(TestBinding.TRIGGER_SEQUENCE));
	}

	public void testSchemeOverride() throws NotDefinedException {
		final Context context = contextManager.getContext("na");
		context.define("name", "description", null);

		final Scheme parentScheme = bindingManager.getScheme("parent");
		parentScheme.define("parent", "parent scheme", null);

		final Scheme childScheme = bindingManager.getScheme("child");
		childScheme.define("child", "child scheme", "parent");

		bindingManager.setActiveScheme(childScheme);
		final Set activeContextIds = new HashSet();
		activeContextIds.add("na");
		contextManager.setActiveContextIds(activeContextIds);

		final Binding binding1 = new TestBinding("child", "child", "na", null,
				null, Binding.SYSTEM, null);
		bindingManager.addBinding(binding1);
		final Binding binding2 = new TestBinding("parent", "parent", "na",
				null, null, Binding.SYSTEM, null);
		bindingManager.addBinding(binding2);
		assertEquals("The binding from the child scheme should be active",
				binding1, bindingManager
						.getPerfectMatch(TestBinding.TRIGGER_SEQUENCE));
	}

	public void testSchemeOverrideDifferentContexts()
			throws NotDefinedException {
		final String parent = "parent";
		final String child = "child";

		final Context parentContext = contextManager.getContext(parent);
		parentContext.define("parent", "description", null);
		final Context childContext = contextManager.getContext(child);
		childContext.define("child", "description", parent);

		final Scheme parentScheme = bindingManager.getScheme(parent);
		parentScheme.define("parent", "parent scheme", null);
		final Scheme childScheme = bindingManager.getScheme(child);
		childScheme.define("child", "child scheme", parent);

		bindingManager.setActiveScheme(childScheme);
		final Set activeContextIds = new HashSet();
		activeContextIds.add(parentContext.getId());
		activeContextIds.add(childContext.getId());
		contextManager.setActiveContextIds(activeContextIds);

		final Binding binding1 = new TestBinding("parent",
				parentScheme.getId(), childContext.getId(), null, null,
				Binding.SYSTEM, null);
		bindingManager.addBinding(binding1);
		final Binding binding2 = new TestBinding("child", childScheme.getId(),
				parentContext.getId(), null, null, Binding.SYSTEM, null);
		bindingManager.addBinding(binding2);
		assertEquals("The binding from the child scheme should be active",
				binding2, bindingManager
						.getPerfectMatch(TestBinding.TRIGGER_SEQUENCE));
	}

	public void testSchemeOverrideDifferentTypes() throws NotDefinedException {
		final Context context = contextManager.getContext("na");
		context.define("name", "description", null);

		final Scheme parentScheme = bindingManager.getScheme("parent");
		parentScheme.define("parent", "parent scheme", null);

		final Scheme childScheme = bindingManager.getScheme("child");
		childScheme.define("child", "child scheme", "parent");

		bindingManager.setActiveScheme(childScheme);
		final Set activeContextIds = new HashSet();
		activeContextIds.add("na");
		contextManager.setActiveContextIds(activeContextIds);

		final Binding binding1 = new TestBinding("child", "child", "na", null,
				null, Binding.SYSTEM, null);
		bindingManager.addBinding(binding1);
		final Binding binding2 = new TestBinding("parent", "parent", "na",
				null, null, Binding.USER, null);
		bindingManager.addBinding(binding2);
		assertEquals("The binding from the child scheme should be active",
				binding1, bindingManager
						.getPerfectMatch(TestBinding.TRIGGER_SEQUENCE));
	}

	public void testSiblingContextConflict() throws NotDefinedException {
		final Context context1 = contextManager.getContext("sibling1");
		context1.define("sibling1", "first sibling context", null);

		final Context context2 = contextManager.getContext("sibling2");
		context2.define("sibling2", "second sibling context", null);

		final Scheme scheme = bindingManager.getScheme("na");
		scheme.define("name", "description", null);

		bindingManager.setActiveScheme(scheme);

		final Binding binding1 = new TestBinding("sibling1", "na", "sibling1",
				null, null, Binding.SYSTEM, null);
		bindingManager.addBinding(binding1);
		final Binding binding2 = new TestBinding("sibling2", "na", "sibling2",
				null, null, Binding.SYSTEM, null);
		bindingManager.addBinding(binding2);

		final Set activeContextIds = new HashSet();
		activeContextIds.add("sibling1");
		contextManager.setActiveContextIds(activeContextIds);
		assertEquals(
				"When only the first sibling is active, only the first binding is active",
				binding1, bindingManager
						.getPerfectMatch(TestBinding.TRIGGER_SEQUENCE));

		activeContextIds.clear();
		activeContextIds.add("sibling2");
		contextManager.setActiveContextIds(activeContextIds);
		assertEquals(
				"When only the second sibling is active, only the second binding is active",
				binding2, bindingManager
						.getPerfectMatch(TestBinding.TRIGGER_SEQUENCE));

		activeContextIds.add("sibling1");
		contextManager.setActiveContextIds(activeContextIds);
		assertEquals("When both contexts are active, a conflict should occur",
				null, bindingManager
						.getPerfectMatch(TestBinding.TRIGGER_SEQUENCE));
	}

	public void testUserOverride() throws NotDefinedException {
		final Context context = contextManager.getContext("na");
		context.define("name", "description", null);

		final Scheme scheme = bindingManager.getScheme("na");
		scheme.define("name", "description", null);

		bindingManager.setActiveScheme(scheme);
		final Set activeContextIds = new HashSet();
		activeContextIds.add("na");
		contextManager.setActiveContextIds(activeContextIds);

		final Binding binding1 = new TestBinding("user", "na", "na", null,
				null, Binding.USER, null);
		bindingManager.addBinding(binding1);
		final Binding binding2 = new TestBinding("system", "na", "na", null,
				null, Binding.SYSTEM, null);
		bindingManager.addBinding(binding2);
		assertEquals("The user-defined binding should be active", binding1,
				bindingManager.getPerfectMatch(TestBinding.TRIGGER_SEQUENCE));
	}
}
