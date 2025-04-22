
package org.eclipse.jgit.diff;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class EditTest {
	@Test
	public void testCreate() {
		final Edit e = new Edit(1
		assertEquals(1
		assertEquals(2
		assertEquals(3
		assertEquals(4
	}

	@Test
	public void testCreateEmpty() {
		final Edit e = new Edit(1
		assertEquals(1
		assertEquals(1
		assertEquals(3
		assertEquals(3
		assertTrue("is empty"
		assertSame(Edit.Type.EMPTY
	}

	@Test
	public void testSwap() {
		final Edit e = new Edit(1
		e.swap();
		assertEquals(3
		assertEquals(4
		assertEquals(1
		assertEquals(2
	}

	@Test
	public void testType_Insert() {
		final Edit e = new Edit(1
		assertSame(Edit.Type.INSERT
		assertFalse("not empty"
		assertEquals(0
		assertEquals(1
	}

	@Test
	public void testType_Delete() {
		final Edit e = new Edit(1
		assertSame(Edit.Type.DELETE
		assertFalse("not empty"
		assertEquals(1
		assertEquals(0
	}

	@Test
	public void testType_Replace() {
		final Edit e = new Edit(1
		assertSame(Edit.Type.REPLACE
		assertFalse("not empty"
		assertEquals(1
		assertEquals(3
	}

	@Test
	public void testType_Empty() {
		final Edit e = new Edit(1
		assertSame(Edit.Type.EMPTY
		assertSame(Edit.Type.EMPTY
		assertTrue("is empty"
		assertEquals(0
		assertEquals(0
	}

	@Test
	public void testToString() {
		final Edit e = new Edit(1
		assertEquals("REPLACE(1-2
	}

	@SuppressWarnings("unlikely-arg-type")
	@Test
	public void testEquals1() {
		final Edit e1 = new Edit(1
		final Edit e2 = new Edit(1

		assertEquals(e1
		assertEquals(e2
		assertEquals(e1
		assertEquals(e1.hashCode()
		assertFalse(e1.equals(""));
	}

	@Test
	public void testNotEquals1() {
		assertFalse(new Edit(1
	}

	@Test
	public void testNotEquals2() {
		assertFalse(new Edit(1
	}

	@Test
	public void testNotEquals3() {
		assertFalse(new Edit(1
	}

	@Test
	public void testNotEquals4() {
		assertFalse(new Edit(1
	}

	@Test
	public void testExtendA() {
		final Edit e = new Edit(1

		e.extendA();
		assertEquals(new Edit(1

		e.extendA();
		assertEquals(new Edit(1
	}

	@Test
	public void testExtendB() {
		final Edit e = new Edit(1

		e.extendB();
		assertEquals(new Edit(1

		e.extendB();
		assertEquals(new Edit(1
	}

	@Test
	public void testBeforeAfterCuts() {
		final Edit whole = new Edit(1
		final Edit mid = new Edit(4

		assertEquals(new Edit(1
		assertEquals(new Edit(5
	}
}
