package org.eclipse.jgit.attributes;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.eclipse.jgit.attributes.Attribute.State;
import org.junit.Test;


public class AttributeTest {

	@Test
	public void testBasic() {
		Attribute a = new Attribute("delta"
		assertEquals(a.getKey()
		assertEquals(a.getState()
		assertNull(a.getValue());
		assertEquals(a.toString()

		a = new Attribute("delta"
		assertEquals(a.getKey()
		assertEquals(a.getState()
		assertNull(a.getValue());
		assertEquals(a.toString()

		a = new Attribute("delta"
		assertEquals(a.getKey()
		assertEquals(a.getState()
		assertEquals(a.getValue()
		assertEquals(a.toString()
	}
}
