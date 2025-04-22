
package org.eclipse.ui.tests.keys;

import junit.framework.TestCase;

import org.eclipse.jface.bindings.Binding;

public class Bug189167Test extends TestCase {

	private Binding createTestBinding() {
		return new TestBinding("commandId", "schemeId", "contextId", "locale", //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
				"platform", 0, null); //$NON-NLS-1$
	}

	public void testBindingsEqual() {
		Binding one = createTestBinding();
		Binding two = createTestBinding();
		assertEquals(one, two);
	}

	public void testHashCodeEquals(){
		Binding one = createTestBinding();
		Binding two = createTestBinding();
		Binding b3  = new TestBinding("commandID", "schemeID", "contextID", "locale", "platform", 1, null);
		assertEquals(one, two);
		assertEquals(one.hashCode(), two.hashCode());
		
		assertFalse(one.equals(b3));
	}
}
