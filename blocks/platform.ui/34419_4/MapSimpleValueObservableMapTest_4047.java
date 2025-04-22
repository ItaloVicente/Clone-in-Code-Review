
package org.eclipse.core.tests.internal.databinding.property.value;

import org.eclipse.core.databinding.beans.BeanProperties;
import org.eclipse.core.databinding.observable.list.WritableList;
import org.eclipse.core.tests.internal.databinding.beans.Bean;
import org.eclipse.jface.tests.databinding.AbstractDefaultRealmTestCase;

public class ListSimpleValueObservableListTest extends
		AbstractDefaultRealmTestCase {

	public void testBug301410() {
		BeanProperties.value(Bean.class, "value").observeDetail(
				new WritableList()).dispose();
	}
}
