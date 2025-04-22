
package org.eclipse.core.tests.internal.databinding.beans;

import java.util.ArrayList;
import java.util.Collections;

import org.eclipse.core.databinding.beans.BeansObservables;
import org.eclipse.core.databinding.beans.PojoObservables;
import org.eclipse.core.databinding.observable.map.IObservableMap;
import org.eclipse.jface.databinding.conformance.util.CurrentRealm;
import org.eclipse.jface.databinding.conformance.util.MapChangeEventTracker;
import org.eclipse.jface.tests.databinding.AbstractDefaultRealmTestCase;

public class JavaBeanPropertyObservableMapTest extends
		AbstractDefaultRealmTestCase {
	public void testSetBeanProperty_CorrectForNullOldAndNewValues() {


		Bean bean = new AnnoyingBean();
		bean.setMap(Collections.singletonMap("key", "old"));

		IObservableMap map = BeansObservables.observeMap(
				new CurrentRealm(true), bean, "map");
		MapChangeEventTracker tracker = MapChangeEventTracker.observe(map);

		bean.setMap(Collections.singletonMap("key", "new"));

		assertEquals(1, tracker.count);

		assertEquals(Collections.EMPTY_SET, tracker.event.diff.getAddedKeys());
		assertEquals(Collections.singleton("key"), tracker.event.diff
				.getChangedKeys());
		assertEquals(Collections.EMPTY_SET, tracker.event.diff.getRemovedKeys());

		assertEquals("old", tracker.event.diff.getOldValue("key"));
		assertEquals("new", tracker.event.diff.getNewValue("key"));
	}

	public void testUpdatedBeanMapIsModifiable() {
		Bean bean = new Bean(new ArrayList());
		IObservableMap observable = BeansObservables.observeMap(bean, "map");

		observable.put(new Object(), new Object());
		bean.getMap().clear();
	}

	public void testUpdatedPojoMapIsModifiable() {
		Bean bean = new Bean(new ArrayList());
		IObservableMap observable = PojoObservables.observeMap(bean, "map");

		observable.put(new Object(), new Object());
		bean.getMap().clear();
	}
}
