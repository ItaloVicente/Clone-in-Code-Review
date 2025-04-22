package org.eclipse.core.tests.databinding.bind;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.eclipse.core.databinding.Binding;
import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.core.databinding.bind.Bind;
import org.eclipse.core.databinding.conversion.IConverter;
import org.eclipse.core.databinding.observable.set.IObservableSet;
import org.eclipse.core.databinding.observable.set.WritableSet;
import org.eclipse.jface.tests.databinding.AbstractDefaultRealmTestCase;
import org.junit.Test;

public class BindSetTest extends AbstractDefaultRealmTestCase {

	@Test
	public void oneWayBindingsCreated() {
		var model = new WritableSet<String>();
		var target = new WritableSet<String>();
		var context = new DataBindingContext();

		Binding binding = Bind.oneWay().modelToTarget().from(model).to(target).bind(context);

		assertTrue(context.getBindings().contains(binding));
		assertSame(model, binding.getModel());
		assertSame(target, binding.getTarget());
	}

	@Test
	public void twoWayBindingsCreated() {
		var model = new WritableSet<String>();
		var target = new WritableSet<String>();
		var context = new DataBindingContext();

		Binding binding = Bind.twoWay().targetToModel().from(target).to(model).bind(context);

		assertTrue(context.getBindings().contains(binding));
		assertSame(model, binding.getModel());
		assertSame(target, binding.getTarget());
	}

	@Test
	public void defaultDirectionIsModelToTarget() {
		var model = new WritableSet<String>();
		var target = new WritableSet<String>();

		Binding binding = Bind.oneWay().from(model).to(target).bind();
		assertSame(model, binding.getModel());
		assertSame(target, binding.getTarget());
	}

	@Test
	public void oneWayUpdate() {
		var model = new WritableSet<String>();
		var target = new WritableSet<String>();

		Bind.oneWay().from(model).to(target).bind();

		model.add("test1");
		assertTrue(target.contains("test1"));

		target.add("test2");
		assertFalse(model.contains("test2"));
	}

	@Test
	public void twoWayUpdate() {
		var model = new WritableSet<String>();
		var target = new WritableSet<String>();

		Bind.twoWay().from(model).to(target).bind();

		model.add("test1");
		assertTrue(target.contains("test1"));

		target.add("test2");
		assertTrue(model.contains("test2"));
	}

	@Test
	public void setSetTwice() {
		var model = new WritableSet<String>();
		var target = new WritableSet<String>();

		try {
			Bind.twoWay().from(target).to(model).updateOnlyOnRequest().updateOnlyOnRequest();
			fail();
		} catch (IllegalStateException e) {
		}
	}

	@Test
	public void setNull() {
		try {
			Bind.twoWay().from((IObservableSet<Object>) null);
			fail();
		} catch (NullPointerException e) {
		}
	}

	@Test
	public void oneWayConverter() {
		var model = new WritableSet<String>();
		var target = new WritableSet<Integer>();

		Bind.oneWay().from(model).convertTo(IConverter.create(Integer::parseInt)).to(target).bind();

		model.add("1");
		assertTrue(target.contains(1));
	}

	@Test
	public void twoWayConverter() {
		var model = new WritableSet<String>();
		var target = new WritableSet<Integer>();

		Bind.twoWay() //
				.from(model) //
				.convertTo(IConverter.create(Integer::parseInt)) //
				.convertFrom(IConverter.create((Integer i) -> i.toString())) //
				.to(target) //
				.bind();

		model.add("1");
		assertTrue(target.contains(1));

		target.add(2);
		assertTrue(model.contains("2"));
	}

	@Test
	public void twoWayDefaultConverter() {
		var model = WritableSet.withElementType(String.class);
		var target = WritableSet.withElementType(Integer.class);

		Bind.twoWay().from(model).defaultConvertTo(target).bind();

		model.add("1");
		assertTrue(target.contains(1));

		target.add(2);
		assertTrue(model.contains("2"));
	}

	@Test
	public void oneWayDefaultConverter() {
		var model = WritableSet.withElementType(String.class);
		var target = WritableSet.withElementType(Integer.class);

		Bind.oneWay().from(model).defaultConvertTo(target).bind();

		model.add("1");
		assertTrue(target.contains(1));

		target.add(2);
		assertFalse(model.contains("2"));
	}

	@Test
	public void updateOnlyOnRequest() {
		var model = new WritableSet<String>();
		var target = new WritableSet<String>();
		var context = new DataBindingContext();

		Bind.twoWay().from(model).to(target).updateOnlyOnRequest().bind(context);

		model.add("test");
		assertFalse(target.contains("test"));

		context.updateTargets();

		assertTrue(target.contains("test"));
	}
}


