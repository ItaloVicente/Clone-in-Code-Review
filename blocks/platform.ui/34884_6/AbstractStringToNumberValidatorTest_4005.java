
package org.eclipse.core.tests.internal.databinding.property.value;

import org.eclipse.core.databinding.observable.set.WritableSet;
import org.eclipse.core.internal.databinding.property.value.SelfValueProperty;
import org.eclipse.core.internal.databinding.property.value.SetSimpleValueObservableMap;
import org.eclipse.jface.tests.databinding.AbstractDefaultRealmTestCase;

public class SetSimpleValueObservableMapTest extends
		AbstractDefaultRealmTestCase {

	public void testGetKeyValueType() {
		WritableSet masterSet = WritableSet.withElementType(String.class);
		SelfValueProperty detailProperty = new SelfValueProperty(Object.class);

		SetSimpleValueObservableMap detailMap = new SetSimpleValueObservableMap(
				masterSet, detailProperty);

		assertEquals(masterSet.getElementType(), detailMap.getKeyType());
		assertEquals(detailProperty.getValueType(), detailMap.getValueType());
	}
}
