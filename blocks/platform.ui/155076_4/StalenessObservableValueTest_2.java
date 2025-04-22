public class StalenessObservableValueTest extends AbstractDefaultRealmTestCase {

	@Test
	public void valueDuringListenerCallback() {
		IObservableValue<String> source = new WritableValue<String>("a", String.class);
		IObservableValue<String> delayed = Observables.observeDelayedValue(1, source);
		IObservableValue<Boolean> stale = Observables.observeStale(delayed);

		AtomicInteger nrStaleEvents = new AtomicInteger();

		stale.addValueChangeListener(e -> {
			if (nrStaleEvents.incrementAndGet() == 1) {
				assertTrue(stale.getValue());
				assertTrue(e.diff.getNewValue());
				assertFalse(e.diff.getOldValue());
			} else {
				assertFalse(stale.getValue());
				assertFalse(e.diff.getNewValue());
				assertTrue(e.diff.getOldValue());
			}
		});

		source.setValue("b");

		delayed.getValue();

		assertEquals(2, nrStaleEvents.get());
	}

