
package org.eclipse.core.tests.databinding.observable.map;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import junit.framework.TestCase;

import org.eclipse.core.databinding.observable.map.MapDiff;
import org.eclipse.core.databinding.observable.map.ObservableMap;
import org.eclipse.jface.databinding.conformance.util.CurrentRealm;
import org.eclipse.jface.databinding.conformance.util.MapChangeEventTracker;
import org.eclipse.jface.databinding.conformance.util.RealmTester;

public class ObservableMapTest extends TestCase {
	ObservableMapStub map;

	@Override
	protected void setUp() throws Exception {
		RealmTester.setDefault(new CurrentRealm(true));
		map = new ObservableMapStub(new HashMap());
	}

	@Override
	protected void tearDown() throws Exception {
		RealmTester.setDefault(null);
	}

	public void testDisposeMapChangeListeners() throws Exception {
		MapChangeEventTracker listener = MapChangeEventTracker.observe(map);

		assertEquals(0, listener.count);
		map.fireMapChange(null);
		assertEquals(1, listener.count);

		map.dispose();
		try {
			map.fireMapChange(null);
		} catch (Exception e) {
		}

		assertEquals("listener should not have been notified", 1,
				listener.count);
	}

	public void testIsStaleRealmChecks() throws Exception {
		RealmTester.exerciseCurrent(new Runnable() {
			@Override
			public void run() {
				map.isStale();
			}
		});
	}

	public void testSetStaleRealmChecks() throws Exception {
		RealmTester.exerciseCurrent(new Runnable() {
			@Override
			public void run() {
				map.setStale(true);
			}
		});
	}

	public void testFireMapChangeRealmChecks() throws Exception {
		RealmTester.exerciseCurrent(new Runnable() {
			@Override
			public void run() {
				map.fireMapChange(null);
			}
		});
	}

	public void testEquals() {
		assertTrue(map.equals(Collections.EMPTY_MAP));
	}

	static class ObservableMapStub extends ObservableMap {
		public ObservableMapStub(Map wrappedMap) {
			super(wrappedMap);
		}

		@Override
		protected void fireMapChange(MapDiff diff) {
			super.fireMapChange(diff);
		}
	}
}
