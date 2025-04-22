/*******************************************************************************
 * Copyright (c) 2009 Matthew Hall and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 *
 * Contributors:
 *     Matthew Hall - initial API and implementation (bug 270461)
 ******************************************************************************/

package org.eclipse.core.tests.databinding;

import org.eclipse.core.databinding.BindingException;
import org.eclipse.core.databinding.UpdateListStrategy;
import org.eclipse.core.databinding.observable.list.IObservableList;
import org.eclipse.core.databinding.observable.list.WritableList;
import org.eclipse.core.internal.databinding.conversion.IdentityConverter;
import org.eclipse.jface.tests.databinding.AbstractDefaultRealmTestCase;

/**
 * @since 1.1
 */
public class UpdateListStrategyTest extends AbstractDefaultRealmTestCase {
	public void testFillDefaults_AssertSourceTypeExtendsConverterFromType() {
		UpdateListStrategyStub strategy = new UpdateListStrategyStub();
		strategy
				.setConverter(new IdentityConverter(Object.class, Object.class));
		strategy.fillDefaults(WritableList.withElementType(String.class),
				WritableList.withElementType(Object.class));

		strategy = new UpdateListStrategyStub();
		strategy
				.setConverter(new IdentityConverter(String.class, Object.class));
		try {
			strategy.fillDefaults(WritableList.withElementType(Object.class),
					WritableList.withElementType(Object.class));
			fail("Expected BindingException since Object does not extend String");
		} catch (BindingException expected) {
		}
	}

	public void testFillDefaults_AssertConverterToTypeExtendsDestinationType() {
		UpdateListStrategyStub strategy = new UpdateListStrategyStub();
		strategy
				.setConverter(new IdentityConverter(Object.class, String.class));
		strategy.fillDefaults(WritableList.withElementType(Object.class),
				WritableList.withElementType(Object.class));

		strategy = new UpdateListStrategyStub();
		strategy
				.setConverter(new IdentityConverter(Object.class, Object.class));
		try {
			strategy.fillDefaults(WritableList.withElementType(Object.class),
					WritableList.withElementType(String.class));
			fail("Expected BindingException since Object does not extend String");
		} catch (BindingException expected) {
		}
	}

	class UpdateListStrategyStub extends UpdateListStrategy {
		@Override
		protected void fillDefaults(IObservableList source,
				IObservableList destination) {
			super.fillDefaults(source, destination);
		}
	}
}
