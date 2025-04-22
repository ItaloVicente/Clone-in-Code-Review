package org.eclipse.egit.core.synchronize;

import static org.eclipse.egit.core.synchronize.GitCommitsModelCache.ZERO_ID;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.eclipse.egit.core.synchronize.GitCommitsModelCache.Change;
import org.eclipse.jgit.lib.AbbreviatedObjectId;
import org.junit.Test;

public class ChangeTest {

	private static final AbbreviatedObjectId MISC_ID = AbbreviatedObjectId
			.fromString("63448b851ae8831a1ad007f588508d3246ec7ace");

	@Test
	public void shouldNotBeEqualWithNullRefference() {
		Change change = new Change();

		boolean result = change.equals(null);

		assertFalse(result);
	}

	@Test
	public void shouldNotBeEqualWithDifferentType() {
		Change change = new Change();

		boolean result = change.equals(new Object());

		assertFalse(result);
	}

	@Test
	public void shouldBeEqualWhenBothIdsAreNull() {
		Change change = new Change();

		boolean result = change.equals(new Change());

		assertTrue(result);
	}

	@Test
	public void shouldNotBeEqualWhenOneObjectIdIsNull() {
		Change change = new Change();
		change.objectId = ZERO_ID;

		boolean result = change.equals(new Change());

		assertFalse(result);
	}

	@Test
	public void shouldBeEqualWhenBothObjectIdsAreTheSame() {
		Change c1 = new Change();
		Change c2 = new Change();
		c1.objectId = c2.objectId = MISC_ID;

		boolean result = c1.equals(c2);

		assertTrue(result);
		assertTrue(c1.hashCode() == c2.hashCode());
	}

	@Test
	public void shouldNotBeEqualWhenObjectIdsAreDifferent() {
		Change c1 = new Change();
		Change c2 = new Change();
		c1.objectId = ZERO_ID;
		c2.objectId = MISC_ID;

		boolean result = c1.equals(c2);

		assertFalse(result);
		assertFalse(c1.hashCode() == c2.hashCode());
	}

	@Test
	public void shouldNotBeEqualWhenOneRemoteObjectIsNull() {
		Change c1 = new Change();
		Change c2 = new Change();
		c1.objectId = c2.commitId = ZERO_ID;
		c1.remoteObjectId = MISC_ID;

		boolean result = c1.equals(c2);

		assertFalse(result);
		assertFalse(c1.hashCode() == c2.hashCode());
	}

	@Test
	public void shouldBeEqualWhenBothObjectIdsAndRemoteIdsAreSame() {
		Change c1 = new Change();
		Change c2 = new Change();
		c1.objectId = c2.objectId = ZERO_ID;
		c1.remoteObjectId = c2.remoteObjectId = MISC_ID;

		boolean result = c1.equals(c2);

		assertTrue(result);
		assertTrue(c1.hashCode() == c2.hashCode());
	}

}
