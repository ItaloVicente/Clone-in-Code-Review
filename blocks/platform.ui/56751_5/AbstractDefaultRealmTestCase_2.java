package org.eclipse.core.tests.databinding;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

import org.eclipse.core.databinding.observable.ISideEffect;
import org.eclipse.core.databinding.observable.SideEffect;
import org.eclipse.core.databinding.observable.value.WritableValue;
import org.eclipse.jface.tests.databinding.AbstractDefaultRealmTestCase;

public class SideEffectTest extends AbstractDefaultRealmTestCase {

	private SideEffect sideEffect;
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

		sideEffect = SideEffect.createPaused(() -> {
			if (useDefaultDependency.getValue()) {
				defaultDependency.getValue();
			} else {
				alternateDependency.getValue();
			}
			sideEffectInvocations++;
		});
	}

	public void testSideEffectDoesntRunUntilActivated() throws Exception {
		runAsync();
		assertEquals(0, sideEffectInvocations);
	}

	public void testSideEffectRunsWhenActivated() throws Exception {
		sideEffect.resume();
		runAsync();
		assertEquals(1, sideEffectInvocations);
	}

	public void testActivatingSideEffectMultipleTimesHasNoEffect() throws Exception {
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

	public void testDeactivatedSideEffectWontRunWhenApplyInvoked() throws Exception {
		sideEffect.resume();
		runAsync();

		assertEquals(1, sideEffectInvocations);
		sideEffect.dispose();
		sideEffect.runIfDirty();
		runAsync();

		assertEquals(1, sideEffectInvocations);
	}

	public void testApplyDoesNothingIfSideEffectNotDirty() throws Exception {
		sideEffect.resume();
		runAsync();

		assertEquals(1, sideEffectInvocations);
		sideEffect.runIfDirty();

		assertEquals(1, sideEffectInvocations);
	}

	public void testApplyRunsSynchronously() throws Exception {
		sideEffect.resume();
		sideEffect.markDirty();
		assertEquals(1, sideEffectInvocations);
		sideEffect.runIfDirty();
		assertEquals(2, sideEffectInvocations);
	}

	public void testApplyRunsIfDirty() throws Exception {
		sideEffect.resume();
		runAsync();
		assertEquals(1, sideEffectInvocations);
		defaultDependency.setValue("Foo");
		sideEffect.runIfDirty();
		assertEquals(2, sideEffectInvocations);
	}

	public void testNestedDependencyChangeAndApplyCompletes() throws Exception {
		AtomicBoolean hasRun = new AtomicBoolean();
		WritableValue<Object> invalidator = new WritableValue<Object>(new Object(), null);
		ISideEffect innerSideEffect = SideEffect.create(() -> {
			invalidator.getValue();
		});

		SideEffect.createPaused(() -> {
			assertFalse(hasRun.get());
			hasRun.set(true);
			invalidator.setValue(new Object());
			innerSideEffect.runIfDirty();
		}).resume();

		runAsync();
		assertTrue(hasRun.get());
	}

	public void testNestedInvalidateAndApplyCompletes() throws Exception {
		AtomicBoolean hasRun = new AtomicBoolean();
		SideEffect innerSideEffect = SideEffect.createPaused(() -> {
		});

		innerSideEffect.resume();

		SideEffect.createPaused(() -> {
			assertFalse(hasRun.get());
			hasRun.set(true);
			innerSideEffect.markDirty();
			innerSideEffect.runIfDirty();
		}).resume();

		runAsync();
		assertTrue(hasRun.get());
	}

	public void testNestedSideEffectCreation() throws Exception {
		AtomicBoolean hasRun = new AtomicBoolean();

		SideEffect.createPaused(() -> {
			SideEffect.createPaused(() -> {
				assertFalse(hasRun.get());
				hasRun.set(true);
			}).resume();
		}).resume();
		runAsync();
		assertTrue(hasRun.get());
	}

	public void testInvalidateSelf() throws Exception {
		AtomicInteger runCount = new AtomicInteger();
		SideEffect[] sideEffect = new SideEffect[1];
		sideEffect[0] = SideEffect.createPaused(() -> {
			assertTrue(runCount.get() < 2);
			int count = runCount.incrementAndGet();
			if (count == 1) {
				sideEffect[0].markDirty();
			}
		});
		sideEffect[0].resume();
		runAsync();
		assertEquals(2, runCount.get());
	}

}
