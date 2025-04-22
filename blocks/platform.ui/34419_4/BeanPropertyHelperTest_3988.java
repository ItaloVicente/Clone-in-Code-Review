
package org.eclipse.core.tests.internal.databinding.beans;

import java.beans.PropertyDescriptor;

import org.eclipse.core.databinding.beans.BeansObservables;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.core.internal.databinding.beans.BeanObservableValueDecorator;
import org.eclipse.jface.databinding.swt.DisplayRealm;
import org.eclipse.jface.tests.databinding.AbstractDefaultRealmTestCase;
import org.eclipse.swt.widgets.Display;

public class BeanObservableValueDecoratorTest extends AbstractDefaultRealmTestCase {
	private Bean bean;
	private IObservableValue observableValue;
	private BeanObservableValueDecorator decorator;
	private PropertyDescriptor propertyDescriptor;

	@Override
	protected void setUp() throws Exception {
		super.setUp();

		bean = new Bean();
		propertyDescriptor = new PropertyDescriptor("value", Bean.class);
		observableValue = BeansObservables.observeValue(DisplayRealm
				.getRealm(Display.getDefault()), bean, "value");
		decorator = new BeanObservableValueDecorator(observableValue,
				propertyDescriptor);
	}

	public void testGetDelegate() throws Exception {
		assertSame(observableValue, decorator.getDecorated());
	}

	public void testGetObserved() throws Exception {
		assertSame(bean, decorator.getObserved());
	}

	public void testGetPropertyDescriptor() throws Exception {
		assertSame(propertyDescriptor, decorator.getPropertyDescriptor());
	}
}
