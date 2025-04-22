
package org.eclipse.core.tests.internal.databinding.beans;

import java.beans.PropertyDescriptor;

import junit.framework.Test;
import junit.framework.TestSuite;

import org.eclipse.core.databinding.beans.BeanProperties;
import org.eclipse.core.databinding.beans.BeansObservables;
import org.eclipse.core.databinding.beans.IBeanObservable;
import org.eclipse.core.databinding.beans.IBeanProperty;
import org.eclipse.core.databinding.beans.PojoObservables;
import org.eclipse.core.databinding.observable.ChangeEvent;
import org.eclipse.core.databinding.observable.Diffs;
import org.eclipse.core.databinding.observable.IChangeListener;
import org.eclipse.core.databinding.observable.IObservable;
import org.eclipse.core.databinding.observable.Realm;
import org.eclipse.core.databinding.observable.value.ComputedValue;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.jface.databinding.conformance.MutableObservableValueContractTest;
import org.eclipse.jface.databinding.conformance.delegate.AbstractObservableValueContractDelegate;
import org.eclipse.jface.databinding.conformance.util.ChangeEventTracker;
import org.eclipse.jface.databinding.conformance.util.CurrentRealm;
import org.eclipse.jface.databinding.conformance.util.ValueChangeEventTracker;
import org.eclipse.jface.examples.databinding.model.SimplePerson;
import org.eclipse.jface.tests.databinding.AbstractDefaultRealmTestCase;

public class JavaBeanObservableValueTest extends AbstractDefaultRealmTestCase {
	private Bean bean;
	private IObservableValue observableValue;
	private IBeanObservable beanObservable;
	private PropertyDescriptor propertyDescriptor;
	private String propertyName;

	@Override
	protected void setUp() throws Exception {
		super.setUp();

		bean = new Bean();
		propertyName = "value";
		propertyDescriptor = ((IBeanProperty) BeanProperties.value(Bean.class,
				propertyName)).getPropertyDescriptor();
		observableValue = BeansObservables.observeValue(bean, propertyName);
		beanObservable = (IBeanObservable) observableValue;
	}

	public void testGetObserved() throws Exception {
		assertEquals(bean, beanObservable.getObserved());
	}

	public void testGetPropertyDescriptor() throws Exception {
		assertEquals(propertyDescriptor, beanObservable.getPropertyDescriptor());
	}

	public void testSetValueThrowsExceptionThrownByBean() throws Exception {
		ThrowsSetException temp = new ThrowsSetException();
		IObservableValue observable = BeansObservables.observeValue(temp,
				"value");

		try {
			observable.setValue("");
			fail("exception should have been thrown");
		} catch (RuntimeException e) {
			assertEquals(temp.thrownException, e.getCause());
		}
	}

	public void testGetValueThrowsExceptionThrownByBean() throws Exception {
		ThrowsGetException temp = new ThrowsGetException();
		IObservableValue observable = BeansObservables.observeValue(temp,
				"value");

		try {
			observable.getValue();
			fail("exception should have been thrown");
		} catch (RuntimeException e) {
			assertEquals(temp.thrownException, e.getCause());
		}
	}

	public void testBug198519() {
		final SimplePerson person = new SimplePerson();
		final ComputedValue cv = new ComputedValue() {
			final IObservableValue name = BeansObservables.observeValue(person,
					"name"); //$NON-NLS-1$

			@Override
			protected Object calculate() {
				return Boolean.valueOf(name.getValue() != null);
			}
		};
		cv.addChangeListener(new IChangeListener() {
			@Override
			public void handleChange(ChangeEvent event) {
				cv.getValue();
			}
		});
		person.setName("foo");
	}

	public void testConstructor_RegistersListeners() throws Exception {
		IObservableValue observable = BeansObservables.observeValue(bean,
				propertyName);
		ChangeEventTracker.observe(observable);

		assertTrue(bean.hasListeners(propertyName));
	}

	public void testConstructor_SkipRegisterListeners() throws Exception {
		IObservableValue observable = PojoObservables.observeValue(bean,
				propertyName);
		ChangeEventTracker.observe(observable);

		assertFalse(bean.hasListeners(propertyName));
	}

	public void testSetBeanProperty_CorrectForNullOldAndNewValues() {


		Bean bean = new AnnoyingBean();
		bean.setValue("old");
		IObservableValue observable = BeansObservables.observeValue(bean,
				"value");
		ValueChangeEventTracker tracker = ValueChangeEventTracker
				.observe(observable);
		bean.setValue("new");
		assertEquals(1, tracker.count);
		assertEquals("old", tracker.event.diff.getOldValue());
		assertEquals("new", tracker.event.diff.getNewValue());
	}

	public void testSetBeanPropertyOutsideRealm_FiresEventInsideRealm() {
		Bean bean = new Bean("old");
		CurrentRealm realm = new CurrentRealm(true);
		IObservableValue observable = BeansObservables.observeValue(realm,
				bean, "value");
		ValueChangeEventTracker tracker = ValueChangeEventTracker
				.observe(observable);

		realm.setCurrent(false);
		bean.setValue("new");
		assertEquals(0, tracker.count);

		realm.setCurrent(true);
		assertEquals(1, tracker.count);
		assertEquals(Diffs.createValueDiff("old", "new"), tracker.event.diff);
	}

	public static Test suite() {
		TestSuite suite = new TestSuite(JavaBeanObservableValueTest.class
				.getName());
		suite.addTestSuite(JavaBeanObservableValueTest.class);
		suite.addTest(MutableObservableValueContractTest.suite(new Delegate()));
		return suite;
	}

			AbstractObservableValueContractDelegate {
		private Bean bean;

		@Override
		public void setUp() {
			super.setUp();

			bean = new Bean("");
		}

		@Override
		public IObservableValue createObservableValue(Realm realm) {
			return BeansObservables.observeValue(realm, bean, "value");
		}

		@Override
		public void change(IObservable observable) {
			IObservableValue observableValue = (IObservableValue) observable;
			observableValue.setValue(createValue(observableValue));
		}

		@Override
		public Object getValueType(IObservableValue observable) {
			return String.class;
		}

		@Override
		public Object createValue(IObservableValue observable) {
			return observable.getValue() + "a";
		}
	}

		private String value;


		public void setValue(String value) {
			throw thrownException = new NullPointerException();
		}

		public String getValue() {
			return value;
		}
	}

		public String value;


		public String getValue() {
			throw thrownException = new NullPointerException();
		}

		public void setValue(String value) {
			this.value = value;
		}
	}
}
