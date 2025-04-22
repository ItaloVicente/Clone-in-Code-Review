
package org.eclipse.core.tests.databinding;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

import org.eclipse.core.databinding.observable.SideEffect;
import org.eclipse.core.databinding.observable.value.WritableValue;
import org.eclipse.jface.tests.databinding.AbstractDefaultRealmTestCase;

public class SideEffectTest extends AbstractDefaultRealmTestCase {

	private SideEffect sideEffect;
	private int sideEffectInvocations = 0;

	private WritableValue<String> defaultDependency;
	private WritableValue<String> alternateDependency;
	private WritableValue<Boolean> useDefaultDependency;

	@Override
	protected void setUp() throws Exception {
		super.setUp();

		defaultDependency = new WritableValue<String>("", null);
		alternateDependency = new WritableValue<String>("", null);
		useDefaultDependency = new WritableValue<Boolean>(true, null);

		sideEffect = SideEffect.create(() -> {
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
		sideEffect.activate();
		runAsync();
		assertEquals(1, sideEffectInvocations);
	}

	public void testActivatingSideEffectMultipleTimesHasNoEffect() throws Exception {
		sideEffect.activate();
		sideEffect.activate();
		runAsync();
		assertEquals(1, sideEffectInvocations);
	}

	public void testSideEffectSelectsCorrectDependency() throws Exception {
		sideEffect.activate();
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
		sideEffect.activate();
		runAsync();
		assertEquals(1, sideEffectInvocations);

		defaultDependency.setValue("Foo");
		alternateDependency.setValue("Foo");
		useDefaultDependency.setValue(false);

		runAsync();
		assertEquals(2, sideEffectInvocations);
	}

	public void testChangingDependencyRerunsSideEffect() throws Exception {
		sideEffect.activate();
		runAsync();

		assertEquals(1, sideEffectInvocations);
		defaultDependency.setValue("Foo");
		runAsync();

		assertEquals(2, sideEffectInvocations);
	}

	public void testChangingUnrelatedNodeDoesntRunSideEffect() throws Exception {
		sideEffect.activate();
		runAsync();

		assertEquals(1, sideEffectInvocations);
		alternateDependency.setValue("Bar");
		runAsync();

		assertEquals(1, sideEffectInvocations);
	}

	public void testDeactivatedSideEffectWontRunWhenTriggeredByDependency() throws Exception {
		sideEffect.activate();
		runAsync();

		assertEquals(1, sideEffectInvocations);
		defaultDependency.setValue("Foo");
		sideEffect.dispose();
		runAsync();

		assertEquals(1, sideEffectInvocations);
	}

	public void testDeactivatedSideEffectWontRunWhenApplyInvoked() throws Exception {
		sideEffect.activate();
		runAsync();

		assertEquals(1, sideEffectInvocations);
		sideEffect.dispose();
		sideEffect.apply();
		runAsync();

		assertEquals(1, sideEffectInvocations);
	}

	public void testApplyDoesNothingIfSideEffectNotDirty() throws Exception {
		sideEffect.activate();
		runAsync();

		assertEquals(1, sideEffectInvocations);
		sideEffect.apply();

		assertEquals(1, sideEffectInvocations);
	}

	public void testApplyRunsSynchronously() throws Exception {
		sideEffect.activate();
		sideEffect.invalidate();
		assertEquals(1, sideEffectInvocations);
		sideEffect.apply();
		assertEquals(2, sideEffectInvocations);
	}

	public void testApplyRunsIfDirty() throws Exception {
		sideEffect.activate();
		runAsync();
		assertEquals(1, sideEffectInvocations);
		defaultDependency.setValue("Foo");
		sideEffect.apply();
		assertEquals(2, sideEffectInvocations);
	}

	public void testNestedDependencyChangeAndApplyCompletes() throws Exception {
		final AtomicBoolean hasRun = new AtomicBoolean();
		WritableValue<Object> invalidator = new WritableValue<Object>(new Object(), null);
		final SideEffect innerSideEffect = SideEffect.create(() -> {
			invalidator.getValue();
		}).activate();

		SideEffect.create(() -> {
			assertFalse(hasRun.get());
			hasRun.set(true);
			invalidator.setValue(new Object());
			innerSideEffect.apply();
		}).activate();

		runAsync();
		assertTrue(hasRun.get());
	}

	public void testNestedInvalidateAndApplyCompletes() throws Exception {
		final AtomicBoolean hasRun = new AtomicBoolean();
		final SideEffect innerSideEffect = SideEffect.create(() -> {
		}).activate();

		SideEffect.create(() -> {
			assertFalse(hasRun.get());
			hasRun.set(true);
			innerSideEffect.invalidate();
			innerSideEffect.apply();
		}).activate();

		runAsync();
		assertTrue(hasRun.get());
	}

	public void testNestedSideEffectCreation() throws Exception {
		final AtomicBoolean hasRun = new AtomicBoolean();

		SideEffect.create(() -> {
			SideEffect.create(() -> {
				assertFalse(hasRun.get());
				hasRun.set(true);
			}).activate();
		}).activate();
		runAsync();
		assertTrue(hasRun.get());
	}

	public void testInvalidateSelf() throws Exception {
		final AtomicInteger runCount = new AtomicInteger();
		final SideEffect[] sideEffect = new SideEffect[1];
		sideEffect[0] = SideEffect.create(() -> {
			assertTrue(runCount.get() < 2);
			int count = runCount.incrementAndGet();
			if (count == 1) {
				sideEffect[0].invalidate();
			}
		});
		sideEffect[0].activate();
		runAsync();
		assertEquals(2, runCount.get());
	}

}
