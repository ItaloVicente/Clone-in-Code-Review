package org.eclipse.jgit.transport;

import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class RefCollisionCheckerTest {
	RefCollisionChecker refCollisionChecker;

	private final String REF_NAME_1 = "refs/a/b";

	private final String REF_NAME_2 = "refs/z/x";

	private final String REF_NAME_3 = "refs/g/h/i";

	@Test
	public void testNoCurRef() {
		Set<String> curRefs = Collections.EMPTY_SET;
		refCollisionChecker = new RefCollisionChecker(curRefs);
		assertFalse(refCollisionChecker.existRefCollision(REF_NAME_1));
		assertFalse(refCollisionChecker.existRefCollision(REF_NAME_2));
		assertFalse(refCollisionChecker.existRefCollision(REF_NAME_3));
	}

	@Test
	public void testNoRefCollision() {
		Set<String> curRefs = new HashSet<>(Arrays.asList("refs/d"
		refCollisionChecker = new RefCollisionChecker(curRefs);
		assertFalse(refCollisionChecker.existRefCollision(REF_NAME_1));
		assertFalse(refCollisionChecker.existRefCollision(REF_NAME_2));
		assertFalse(refCollisionChecker.existRefCollision(REF_NAME_3));
	}

	@Test
	public void testAdvRefContainingCurRef() {
		Set<String> curRefs = new HashSet<>(Arrays.asList("refs/a"
		refCollisionChecker = new RefCollisionChecker(curRefs);
		assertTrue(refCollisionChecker.existRefCollision(REF_NAME_1));
		assertTrue(refCollisionChecker.existRefCollision(REF_NAME_2));
		assertTrue(refCollisionChecker.existRefCollision(REF_NAME_3));
	}

	@Test
	public void testIncRefContainingCurRef() {
		Set<String> curRefs = new HashSet<>(Arrays.asList("refs/a/b/c"
		refCollisionChecker = new RefCollisionChecker(curRefs);
		assertTrue(refCollisionChecker.existRefCollision(REF_NAME_1));
		assertTrue(refCollisionChecker.existRefCollision(REF_NAME_2));
		assertTrue(refCollisionChecker.existRefCollision(REF_NAME_3));
	}

	@Test
	public void testIncRefAsCurRef() {
		Set<String> curRefs = new HashSet<>(Arrays.asList("refs/a/b"
		refCollisionChecker = new RefCollisionChecker(curRefs);
		assertTrue(refCollisionChecker.existRefCollision(REF_NAME_1));
		assertTrue(refCollisionChecker.existRefCollision(REF_NAME_2));
		assertTrue(refCollisionChecker.existRefCollision(REF_NAME_3));
	}
}
