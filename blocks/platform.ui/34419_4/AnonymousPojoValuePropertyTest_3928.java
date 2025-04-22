
package org.eclipse.core.tests.databinding.beans;

import org.eclipse.core.databinding.beans.BeanProperties;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.core.databinding.observable.value.WritableValue;
import org.eclipse.core.databinding.property.value.IValueProperty;
import org.eclipse.jface.tests.databinding.AbstractDefaultRealmTestCase;

public class AnonymousBeanValuePropertyTest extends
		AbstractDefaultRealmTestCase {
	public void testObserveDetailHavingNullValueType_UseExplicitValueType() {
		IObservableValue master = WritableValue.withValueType(null);
		IValueProperty prop = BeanProperties.value("value", String.class);

		IObservableValue detail = prop.observeDetail(master);

		assertEquals(String.class, detail.getValueType());
	}
}
