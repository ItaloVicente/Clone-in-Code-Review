/*******************************************************************************
 * Copyright (c) 2010, 2015 Ovidio Mallo and others.
 *
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     Ovidio Mallo - initial API and implementation (bug 306611)
 ******************************************************************************/

package org.eclipse.core.internal.databinding;

import org.eclipse.core.databinding.conversion.IConverter;
import org.eclipse.core.databinding.observable.value.ValueDiff;
import org.eclipse.core.databinding.property.INativePropertyListener;
import org.eclipse.core.databinding.property.ISimplePropertyListener;
import org.eclipse.core.databinding.property.value.SimpleValueProperty;

/**
 * Simple value property which applies a given converter on a source object in
 * order to produce the property's value.
 *
 * @param <S>
 *            type of the source object
 * @param <T>
 *            type of the value of the property (after conversion)
 */
public class ConverterValueProperty<S, T> extends SimpleValueProperty<S, T> {

	private final IConverter<S, T> converter;

	/**
	 * Creates a new value property which applies the given converter on the
	 * source object in order to produce the property's value.
	 *
	 * @param converter
	 *            The converter to apply to the source object.
	 */
	public ConverterValueProperty(IConverter<S, T> converter) {
		this.converter = converter;
	}

	@Override
	public Object getValueType() {
		return converter.getToType();
	}

	@Override
	public T getValue(S source) {
		return doGetValue(source);
	}

	@Override
	protected T doGetValue(S source) {
		return converter.convert(source);
	}

	@Override
	protected void doSetValue(S source, T value) {
		throw new UnsupportedOperationException(this
	}

	@Override
	public INativePropertyListener<S> adaptListener(
			ISimplePropertyListener<S, ValueDiff<? extends T>> listener) {
		return null;
	}

	@Override
	public String toString() {
	}
}
