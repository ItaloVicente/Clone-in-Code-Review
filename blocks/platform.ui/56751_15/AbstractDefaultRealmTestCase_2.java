package org.eclipse.core.tests.databinding;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

import org.eclipse.core.databinding.observable.ISideEffect;
import org.eclipse.core.databinding.observable.value.ComputedValue;
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

	public void testConsumeOnceDoesntPassNullToConsumer() throws Exception {
		AtomicBoolean consumerHasRun = new AtomicBoolean();
		WritableValue<Object> makesThingsDirty = new WritableValue<>(null, null);
		ComputedValue<Object> value = new ComputedValue<Object>() {
			@Override
			protected Object calculate() {
				makesThingsDirty.getValue();
				return null;
			}
		};

		ISideEffect consumeOnce = ISideEffect.consumeOnceAsync(value::getValue, (Object) -> {
			consumerHasRun.set(true);
		});

		makesThingsDirty.setValue(new Object());
		runAsync();
		makesThingsDirty.setValue(new Object());
		runAsync();
		assertFalse(consumerHasRun.get());
		consumeOnce.dispose();
	}

	public void testConsumeOnceDoesntRunTwice() throws Exception {
		AtomicInteger numberOfRuns = new AtomicInteger();
		WritableValue<Object> makesThingsDirty = new WritableValue<>(null, null);
		WritableValue<Object> returnValue = new WritableValue<>(null, null);
		ComputedValue<Object> value = new ComputedValue<Object>() {
			@Override
			protected Object calculate() {
				makesThingsDirty.getValue();
				return returnValue.getValue();
			}
		};

		ISideEffect consumeOnce = ISideEffect.consumeOnceAsync(value::getValue, (Object) -> {
			numberOfRuns.set(numberOfRuns.get() + 1);
		});

		makesThingsDirty.setValue(new Object());
		runAsync();
		assertEquals(0, numberOfRuns.get());

		returnValue.setValue("Foo");
		runAsync();
		assertEquals(1, numberOfRuns.get());

		returnValue.setValue("Bar");
		runAsync();
		assertEquals(1, numberOfRuns.get());
		consumeOnce.dispose();
	}

	public void testConsumeOnceDoesntRunAtAllIfDisposed() throws Exception {
		AtomicInteger numberOfRuns = new AtomicInteger();
		WritableValue<Object> returnValue = new WritableValue<>("foo", null);

		ISideEffect consumeOnce = ISideEffect.consumeOnceAsync(returnValue::getValue, (Object) -> {
			numberOfRuns.set(numberOfRuns.get() + 1);
		});

		consumeOnce.dispose();

		runAsync();
		assertEquals(0, numberOfRuns.get());
	}

	public void testConsumeOnceRunsIfInitialValueNonNull() throws Exception {
		AtomicInteger numberOfRuns = new AtomicInteger();
		WritableValue<Object> returnValue = new WritableValue<>("foo", null);

		ISideEffect consumeOnce = ISideEffect.consumeOnceAsync(returnValue::getValue, (Object) -> {
			numberOfRuns.set(numberOfRuns.get() + 1);
		});

		runAsync();
		assertEquals(1, numberOfRuns.get());

		consumeOnce.dispose();
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
