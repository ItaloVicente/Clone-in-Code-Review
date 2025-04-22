
package org.eclipse.core.tests.databinding;

import org.eclipse.core.databinding.BindingException;
import org.eclipse.core.databinding.UpdateSetStrategy;
import org.eclipse.core.databinding.observable.set.IObservableSet;
import org.eclipse.core.databinding.observable.set.WritableSet;
import org.eclipse.core.internal.databinding.conversion.IdentityConverter;
import org.eclipse.jface.tests.databinding.AbstractDefaultRealmTestCase;

public class UpdateSetStrategyTest extends AbstractDefaultRealmTestCase {
	public void testFillDefaults_AssertSourceTypeExtendsConverterFromType() {
		UpdateSetStrategyStub strategy = new UpdateSetStrategyStub();
		strategy
				.setConverter(new IdentityConverter(Object.class, Object.class));
		strategy.fillDefaults(WritableSet.withElementType(String.class),
				WritableSet.withElementType(Object.class));

		strategy = new UpdateSetStrategyStub();
		strategy
				.setConverter(new IdentityConverter(String.class, Object.class));
		try {
			strategy.fillDefaults(WritableSet.withElementType(Object.class),
					WritableSet.withElementType(Object.class));
			fail("Expected BindingException since Object does not extend String");
		} catch (BindingException expected) {
		}
	}

	public void testFillDefaults_AssertConverterToTypeExtendsDestinationType() {
		UpdateSetStrategyStub strategy = new UpdateSetStrategyStub();
		strategy
				.setConverter(new IdentityConverter(Object.class, String.class));
		strategy.fillDefaults(WritableSet.withElementType(Object.class),
				WritableSet.withElementType(Object.class));

		strategy = new UpdateSetStrategyStub();
		strategy
				.setConverter(new IdentityConverter(Object.class, Object.class));
		try {
			strategy.fillDefaults(WritableSet.withElementType(Object.class),
					WritableSet.withElementType(String.class));
			fail("Expected BindingException since Object does not extend String");
		} catch (BindingException expected) {
		}
	}

	class UpdateSetStrategyStub extends UpdateSetStrategy {
		@Override
		protected void fillDefaults(IObservableSet source,
				IObservableSet destination) {
			super.fillDefaults(source, destination);
		}
	}
}
