
package org.eclipse.core.tests.databinding;

import org.eclipse.core.databinding.AggregateValidationStatus;
import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.core.databinding.observable.Realm;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.jface.tests.databinding.AbstractSWTTestCase;

public class AggregateValidationStatusTest extends AbstractSWTTestCase {
	public void testAggregateValidationStatusValueType() throws Exception {
		DataBindingContext dbc = new DataBindingContext();
		AggregateValidationStatus status = new AggregateValidationStatus(dbc
				.getBindings(), AggregateValidationStatus.MAX_SEVERITY);
		assertEquals(IStatus.class, status.getValueType());
	}

	public void testConstructor_DefaultRealm() throws Exception {
		DataBindingContext dbc = new DataBindingContext();
		AggregateValidationStatus status = new AggregateValidationStatus(dbc
				.getBindings(), AggregateValidationStatus.MAX_SEVERITY);
		assertEquals(Realm.getDefault(), status.getRealm());
	}
}
