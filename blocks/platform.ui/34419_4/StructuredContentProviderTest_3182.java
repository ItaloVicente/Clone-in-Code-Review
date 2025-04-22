package org.eclipse.jface.examples.databinding.contentprovider.test;

import java.util.Collections;
import java.util.Set;

import org.eclipse.core.databinding.observable.map.ComputedObservableMap;
import org.eclipse.core.databinding.observable.map.MapDiff;
import org.eclipse.core.databinding.observable.set.IObservableSet;

public class SomeMathFunction extends ComputedObservableMap {

	public static final int OP_IDENTITY = 0;

	public static final int OP_MULTIPLY = 1;

	public static final int OP_ROUND = 2;

	private int op = OP_ROUND;

	public SomeMathFunction(IObservableSet domain) {
		super(domain);
		init();
	}

	public void setOperation(final int operation) {
		final int oldOp = this.op;
		this.op = operation;

		fireMapChange(new MapDiff() {

			@Override
			public Set getAddedKeys() {
				return Collections.EMPTY_SET;
			}

			@Override
			public Set getChangedKeys() {
				return keySet();
			}

			@Override
			public Object getNewValue(Object key) {
				return doComputeResult(key, operation);
			}

			@Override
			public Object getOldValue(Object key) {
				return doComputeResult(key, oldOp);
			}

			@Override
			public Set getRemovedKeys() {
				return Collections.EMPTY_SET;
			}
		});
	}

	private Object doComputeResult(Object element, int op) {
		switch (op) {
		case OP_IDENTITY:
			return element;
		case OP_MULTIPLY:
			return new Double((((Double) element).doubleValue() * 2.0));
		case OP_ROUND:
			return new Double(Math.floor((((Double) element).doubleValue())));
		}
		return element;
	}

	@Override
	protected Object doGet(Object key) {
		return doComputeResult(key, this.op);
	}

	@Override
	protected Object doPut(Object key, Object value) {
		throw new UnsupportedOperationException();
	}

	@Override
	protected void hookListener(Object addedKey) {
	}

	@Override
	protected void unhookListener(Object removedKey) {
	}

}
