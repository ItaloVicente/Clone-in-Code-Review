
package org.eclipse.core.tests.internal.databinding;

import org.eclipse.core.databinding.BindingProperties;
import org.eclipse.core.databinding.conversion.IConverter;
import org.eclipse.core.databinding.property.value.IValueProperty;
import org.eclipse.core.internal.databinding.ConverterValueProperty;
import org.eclipse.core.internal.databinding.conversion.ObjectToStringConverter;
import org.eclipse.jface.tests.databinding.AbstractDefaultRealmTestCase;

public class ConverterValuePropertyTest extends AbstractDefaultRealmTestCase {

	private IConverter converter;

	@Override
	protected void setUp() throws Exception {
		super.setUp();

		converter = new ObjectToStringConverter(Integer.class);
	}

	public void testGetValue() {
		IValueProperty property = BindingProperties.convertedValue(converter);

		assertEquals("123", property.getValue(new Integer(123)));
	}

	public void testGetValueForNullSource() {
		IValueProperty property = BindingProperties.convertedValue(converter);

		assertEquals("", property.getValue(null));
	}

	public void testSetValue() {
		IValueProperty property = BindingProperties.convertedValue(converter);

		try {
			property.setValue(new Integer(123), "123");
			fail("setting a value should trigger an exception!");
		} catch (UnsupportedOperationException e) {
		}
	}

	public void testGetValueType() {
		IValueProperty property = BindingProperties.convertedValue(converter);

		assertEquals(converter.getToType(), property.getValueType());
	}
}
