package org.eclipse.jgit.internal;

import java.util.Locale;
import org.eclipse.jgit.nls.NLS;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class JGitTextTest {

	@BeforeClass
	public static void setUpBeforeClass() {
		NLS.setLocale(Locale.ENGLISH);
	}

	@Test
	public void abbreviationLengthMustBeNonNegative() {
		assertEquals("Abbreviation length must not be negative."
				JGitText.abbreviationLengthMustBeNonNegative());
	}

	@Test
	public void abortingRebase() {
		assertEquals(
				"Aborting rebase: resetting to 16beb10ce1e456e26675d736cceabb8cc2c58c09"
				JGitText.abortingRebase(
						"16beb10ce1e456e26675d736cceabb8cc2c58c09"));
	}

	@Test
	public void abortingRebaseFailed() {
		assertEquals("Could not abort rebase"
	}

	@Test
	public void abortingRebaseFailedNoOrigHead() {
		assertEquals("Could not abort rebase since ORIG_HEAD is null"
				JGitText.abortingRebaseFailedNoOrigHead());
	}

	@Test
	public void advertisementCameBefore() {
		assertEquals("advertisement of name1^{} came before name2"
				JGitText.advertisementCameBefore("name1"
	}

	@Test
	public void advertisementOfCameBefore() {
		assertEquals("advertisement of name1^{} came before name2"
				JGitText.advertisementOfCameBefore("name1"
	}

	@Test
	public void amazonS3ActionFailed() {
		assertEquals("anAction of 'aKey' failed: 1 anErrorMessage"
				.amazonS3ActionFailed("anAction"
	}

	@Test
	public void amazonS3ActionFailedGivingUp() {
		assertEquals("anAction of 'aKey' failed: Giving up after 1 attempts."
				JGitText.amazonS3ActionFailedGivingUp("anAction"
	}

	@Test
	public void ambiguousObjectAbbreviation() {
		assertEquals("Object abbreviation aName is ambiguous"
				JGitText.ambiguousObjectAbbreviation("aName"));
	}

	@Test
	public void aNewObjectIdIsRequired() {
		assertEquals("A NewObjectId is required."
				JGitText.aNewObjectIdIsRequired());
	}

	@Test
	public void anExceptionOccurredWhileTryingToAddTheIdOfHEAD() {
		assertEquals("An exception occurred while trying to add the Id of HEAD"
				JGitText.anExceptionOccurredWhileTryingToAddTheIdOfHEAD());
	}
}
