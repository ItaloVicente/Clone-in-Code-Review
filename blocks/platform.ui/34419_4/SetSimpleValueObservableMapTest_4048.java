
package org.eclipse.core.tests.internal.databinding.property.value;

import org.eclipse.core.databinding.observable.map.WritableMap;
import org.eclipse.core.internal.databinding.property.value.MapSimpleValueObservableMap;
import org.eclipse.core.internal.databinding.property.value.SelfValueProperty;
import org.eclipse.jface.tests.databinding.AbstractDefaultRealmTestCase;

public class MapSimpleValueObservableMapTest extends
		AbstractDefaultRealmTestCase {

	public void testGetKeyValueType() {
		WritableMap masterMap = new WritableMap(String.class, Integer.class);
		SelfValueProperty detailProperty = new SelfValueProperty(Object.class);

		MapSimpleValueObservableMap detailMap = new MapSimpleValueObservableMap(
				masterMap, detailProperty);

		assertEquals(masterMap.getKeyType(), detailMap.getKeyType());
		assertEquals(detailProperty.getValueType(), detailMap.getValueType());
	}

	public void testPut_ReplacedOldValue() {
		WritableMap masterMap = new WritableMap(String.class, Integer.class);
		SelfValueProperty detailProperty = new SelfValueProperty(Integer.class);

		MapSimpleValueObservableMap detailMap = new MapSimpleValueObservableMap(
				masterMap, detailProperty);

		String key = "key";

		Integer oldValue = new Integer(111);
		masterMap.put(key, oldValue);

		Integer newValue = new Integer(777);
		Object returnedOldValue = detailMap.put(key, newValue);

		assertSame(oldValue, returnedOldValue);
	}
}
