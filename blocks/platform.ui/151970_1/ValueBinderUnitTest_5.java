package org.eclipse.core.databinding.fluent;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.eclipse.core.databinding.Binding;
import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.core.databinding.UpdateValueStrategy;
import org.eclipse.core.databinding.conversion.Converter;
import org.eclipse.core.databinding.observable.Realm;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.core.databinding.observable.value.WritableValue;
import org.junit.Before;
import org.junit.Test;

public class ValueBinderUnitTest {

	private Realm realm;

	private DataBindingContext dbc;

	private IObservableValue<String> model;
	private IObservableValue<Integer> target;

	private UpdateValueStrategy<String, Integer> model2Target;
	private UpdateValueStrategy<Integer, String> target2Model;

	private boolean model2TargetCalled = false;
	private boolean target2ModelCalled = false;

	@Before
	public void setup() {
		realm = new Realm() {

			@Override
			public boolean isCurrent() {
				return true;
			}
		};

		dbc = new DataBindingContext(realm);

		model = new WritableValue<String>(realm);
		target = new WritableValue<Integer>(realm);

		class StringToIntRecordingConverter extends Converter<String, Integer> {

			public StringToIntRecordingConverter() {
				super(String.class, Integer.class);
			}

			@Override
			public Integer convert(String fromObject) {
				model2TargetCalled = true;
				return 42;
			}
		}

		class IntToStringRecordingConverter extends Converter<Integer, String> {

			public IntToStringRecordingConverter() {
				super(Integer.class, String.class);
			}

			@Override
			public String convert(Integer fromObject) {
				target2ModelCalled = true;
				return "42"; //$NON-NLS-1$
			}
		}

		model2Target = new UpdateValueStrategy<String, Integer>();
		model2Target.setConverter(new StringToIntRecordingConverter());

		target2Model = new UpdateValueStrategy<Integer, String>();
		target2Model.setConverter(new IntToStringRecordingConverter());
	}

	@Test
	public void testFromToTarget() {
		Binding b = ValueBinder.from(model).to(target).bind(dbc);

		assertEquals(model, b.getModel());
		assertEquals(target, b.getTarget());
	}

	@Test
	public void testFromByTo() {
		Binding b = ValueBinder.from(model).by(model2Target).to(target).bind(dbc);

		assertEquals(model, b.getModel());
		assertEquals(target, b.getTarget());
		assertTrue(model2TargetCalled);
	}

	@Test
	public void testFromToBy() {
		Binding b = ValueBinder.from(model).to(target).by(target2Model).bind(dbc);

		assertEquals(model, b.getModel());
		assertEquals(target, b.getTarget());
		assertTrue(target2ModelCalled);
	}

	@Test
	public void testFromByToBy() {
		Binding b = ValueBinder.from(model).by(model2Target).to(target).by(target2Model).bind(dbc);

		assertEquals(model, b.getModel());
		assertEquals(target, b.getTarget());
		assertTrue(model2TargetCalled);
		assertTrue(target2ModelCalled);
	}
}
