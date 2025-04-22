
package org.eclipse.core.tests.databinding.observable.value;

import java.util.ArrayList;
import java.util.Collection;

import org.eclipse.core.databinding.observable.list.IObservableList;
import org.eclipse.core.databinding.observable.list.WritableList;
import org.eclipse.core.databinding.observable.value.DuplexingObservableValue;
import org.eclipse.jface.tests.databinding.AbstractDefaultRealmTestCase;

public class DuplexingObservableValueTest extends AbstractDefaultRealmTestCase {
	private IObservableList list;
	private DuplexingObservableValue observable;

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		list = new WritableList(new ArrayList(), String.class);
	}

	public void testValueType_InheritFromTargetList() throws Exception {
		observable = new DuplexingObservableValue(list) {
			@Override
			protected Object coalesceElements(Collection elements) {
				return null;
			}
		};
		assertEquals(
				"value type should be the element type of the target list",
				String.class, observable.getValueType());
	}

	public void testValueType_ProvidedInConstructor() throws Exception {
		observable = new DuplexingObservableValue(list, Object.class) {
			@Override
			protected Object coalesceElements(Collection elements) {
				return null;
			}
		};
		assertEquals("value type should be the type passed to constructor",
				Object.class, observable.getValueType());
	}

	public void test_getValue() throws Exception {
		observable = DuplexingObservableValue.withDefaults(list, null,
				"<Multiple Values>");
		assertNull(observable.getValue());
		list.add("42");
		assertEquals("Value should be \"42\"", "42", observable.getValue());
		list.add("42");
		assertEquals("Value should be \"42\"", "42", observable.getValue());
		list.add("watermelon");
		assertEquals("<Multiple Values>", observable.getValue());
		list.remove(2);
		assertEquals("Value should be \"42\"", "42", observable.getValue());
		list.clear();
		assertNull(observable.getValue());
	}
}
