
package org.eclipse.core.tests.internal.databinding.beans;

import java.beans.PropertyDescriptor;

import junit.framework.TestCase;

import org.eclipse.core.internal.databinding.beans.BeanPropertyHelper;

public class BeanPropertyHelperTest extends TestCase {
	public void testGetPropertyDescriptor_ClassProperty()
			throws SecurityException, NoSuchMethodException {
		PropertyDescriptor pd = BeanPropertyHelper.getPropertyDescriptor(
				Bean.class, "value");
		assertEquals(Bean.class.getMethod("getValue"), pd.getReadMethod());
		assertEquals(
				Bean.class.getMethod("setValue", new Class[] { String.class }),
				pd.getWriteMethod());
	}

	public void testGetPropertyDescriptor_InterfaceProperty()
			throws SecurityException, NoSuchMethodException {
		PropertyDescriptor pd = BeanPropertyHelper.getPropertyDescriptor(
				IBean.class, "value");
		assertEquals(IBean.class.getMethod("getValue"), pd.getReadMethod());
		assertEquals(
				IBean.class.getMethod("setValue", new Class[] { String.class }),
				pd.getWriteMethod());
	}

	public void testGetPropertyDescriptor_SuperInterfaceProperty()
			throws SecurityException, NoSuchMethodException {
		PropertyDescriptor pd = BeanPropertyHelper.getPropertyDescriptor(
				IBeanExtension.class, "value");
		assertEquals(IBean.class.getMethod("getValue"), pd.getReadMethod());
		assertEquals(
				IBean.class.getMethod("setValue", new Class[] { String.class }),
				pd.getWriteMethod());
	}

}
