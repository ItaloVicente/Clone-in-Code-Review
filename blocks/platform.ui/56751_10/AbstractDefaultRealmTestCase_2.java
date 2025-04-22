package org.eclipse.core.tests.databinding;

import java.util.concurrent.atomic.AtomicBoolean;

import org.eclipse.core.databinding.observable.ISideEffect;
import org.eclipse.core.databinding.observable.value.WritableValue;
import org.eclipse.jface.tests.databinding.AbstractDefaultRealmTestCase;

public class SideEffectTest extends AbstractDefaultRealmTestCase {

	private ISideEffect sideEffect;
	private int sideEffectInvocations;

	private WritableValue<String> defaultDependency;
	private WritableValue<String> alternateDependency;
	private WritableValue<Boolean> useDefaultDependency;

	@Override
	protected void setUp() throws Exception {
		super.setUp();

		defaultDependency = new WritableValue<>("", null);
		alternateDependency = new WritableValue<>("", null);
		useDefaultDependency = new WritableValue<>(true, null);

		sideEffect = ISideEffect.createPaused(() -> {
			if (useDefaultDependency.getValue()) {
				defaultDependency.getValue();
			} else {
				alternateDependency.getValue();
			}
			sideEffectInvocations++;
		});
	}

	public void testSideEffectDoesntRunUntilResumed() throws Exception {
		runAsync();
		assertEquals(0, sideEffectInvocations);
	}

	public void testSideEffectRunsWhenResumed() throws Exception {
		sideEffect.resume();
		runAsync();
		assertEquals(1, sideEffectInvocations);
	}

	public void testResumingSideEffectMultipleTimesHasNoEffect() throws Exception {
		sideEffect.resume();
		sideEffect.resume();
		runAsync();
		assertEquals(1, sideEffectInvocations);
	}

	public void testSideEffectSelectsCorrectDependency() throws Exception {
		sideEffect.resume();
		runAsync();
		assertEquals(1, sideEffectInvocations);

		defaultDependency.setValue("foo");
		runAsync();
		assertEquals(2, sideEffectInvocations);

		alternateDependency.setValue("foo");
		runAsync();
		assertEquals(2, sideEffectInvocations);

		useDefaultDependency.setValue(false);
		runAsync();
		assertEquals(3, sideEffectInvocations);

		defaultDependency.setValue("bar");
		runAsync();
		assertEquals(3, sideEffectInvocations);

		alternateDependency.setValue("bar");
		runAsync();
		assertEquals(4, sideEffectInvocations);
	}

	public void testChangingMultipleDependenciesOnlyRunsTheSideEffectOnce() throws Exception {
		sideEffect.resume();
		runAsync();
		assertEquals(1, sideEffectInvocations);

		defaultDependency.setValue("Foo");
		alternateDependency.setValue("Foo");
		useDefaultDependency.setValue(false);

		runAsync();
		assertEquals(2, sideEffectInvocations);
	}

	public void testChangingDependencyRerunsSideEffect() throws Exception {
		sideEffect.resume();
		runAsync();

		assertEquals(1, sideEffectInvocations);
		defaultDependency.setValue("Foo");
		runAsync();

		assertEquals(2, sideEffectInvocations);
	}

	public void testChangingUnrelatedNodeDoesntRunSideEffect() throws Exception {
		sideEffect.resume();
		runAsync();

		assertEquals(1, sideEffectInvocations);
		alternateDependency.setValue("Bar");
		runAsync();

		assertEquals(1, sideEffectInvocations);
	}

	public void testDeactivatedSideEffectWontRunWhenTriggeredByDependency() throws Exception {
		sideEffect.resume();
		runAsync();

		assertEquals(1, sideEffectInvocations);
		defaultDependency.setValue("Foo");
		sideEffect.dispose();
		runAsync();

		assertEquals(1, sideEffectInvocations);
	}

	public void testDeactivatedSideEffectWontRunWhenRunIfDirtyInvoked() throws Exception {
		sideEffect.resume();
		runAsync();

		assertEquals(1, sideEffectInvocations);
		sideEffect.pause();
		defaultDependency.setValue("MakeItDirty");
		sideEffect.runIfDirty();
		runAsync();

		assertEquals(1, sideEffectInvocations);
	}

	public void testRunIfDirtyDoesNothingIfSideEffectNotDirty() throws Exception {
		sideEffect.resume();
		runAsync();

		assertEquals(1, sideEffectInvocations);
		sideEffect.runIfDirty();

		assertEquals(1, sideEffectInvocations);
	}

	public void testRunIfDirty() throws Exception {
		sideEffect.resume();
		runAsync();
		assertEquals(1, sideEffectInvocations);
		defaultDependency.setValue("Foo");
		sideEffect.runIfDirty();
		assertEquals(2, sideEffectInvocations);
	}

	public void testNestedDependencyChangeAndRunIfDirtyCompletes() throws Exception {
		AtomicBoolean hasRun = new AtomicBoolean();
		WritableValue<Object> invalidator = new WritableValue<Object>(new Object(), null);
		ISideEffect innerSideEffect = ISideEffect.create(() -> {
			invalidator.getValue();
		});

		ISideEffect.createPaused(() -> {
			assertFalse(hasRun.get());
			hasRun.set(true);
			invalidator.setValue(new Object());
			innerSideEffect.runIfDirty();
		}).resume();

		runAsync();
		assertTrue(hasRun.get());
	}

	public void testNestedInvalidateAndRunIfDirtyCompletes() throws Exception {
		AtomicBoolean hasRun = new AtomicBoolean();
		final WritableValue<Object> makesThingsDirty = new WritableValue<>(null, null);
		ISideEffect innerSideEffect = ISideEffect.createPaused(() -> {
			makesThingsDirty.getValue();
		});

		innerSideEffect.resume();

		ISideEffect.createPaused(() -> {
			assertFalse(hasRun.get());
			hasRun.set(true);
			makesThingsDirty.setValue(new Object());
			innerSideEffect.runIfDirty();
		}).resume();

		runAsync();
		assertTrue(hasRun.get());
	}

	public void testNestedSideEffectCreation() throws Exception {
		AtomicBoolean hasRun = new AtomicBoolean();

		ISideEffect.createPaused(() -> {
			ISideEffect.createPaused(() -> {
				assertFalse(hasRun.get());
				hasRun.set(true);
			}).resume();
		}).resume();
		runAsync();
		assertTrue(hasRun.get());
	}

}
