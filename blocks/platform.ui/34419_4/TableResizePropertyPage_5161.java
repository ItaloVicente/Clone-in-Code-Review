
package org.eclipse.ui.tests.propertyPages;

import java.util.Collection;
import java.util.Iterator;

import org.eclipse.ui.internal.dialogs.PropertyPageContributorManager;
import org.eclipse.ui.internal.dialogs.RegistryPageContributor;
import org.eclipse.ui.tests.navigator.AbstractNavigatorTest;

public class PropertyPageEnablementTest extends AbstractNavigatorTest {

	public PropertyPageEnablementTest(String testName) {
		super(testName);
	}

	@Override
	protected void doSetUp() throws Exception {
		super.doSetUp();
		createTestFile();
	}

	public void testAndPage() {

		Collection contributors = PropertyPageContributorManager.getManager()
				.getApplicableContributors(testFile);
		assertFalse("Has no file pages", contributors.isEmpty());
		for (Iterator iter = contributors.iterator(); iter.hasNext();) {
			RegistryPageContributor element = (RegistryPageContributor) iter
					.next();
			if (element.getPageId().equals("org.eclipse.ui.tests.and")) {
				return;
			}
		}
		assertTrue("And property page for file not found", false);

		contributors = PropertyPageContributorManager.getManager()
				.getApplicableContributors(testFolder);
		for (Iterator iter = contributors.iterator(); iter.hasNext();) {
			RegistryPageContributor element = (RegistryPageContributor) iter
					.next();
			assertFalse("Matching folder for AND", element.getPageId().equals(
					"org.eclipse.ui.tests.and"));

		}

		contributors = PropertyPageContributorManager.getManager()
				.getApplicableContributors(testProject);
		for (Iterator iter = contributors.iterator(); iter.hasNext();) {
			RegistryPageContributor element = (RegistryPageContributor) iter
					.next();
			assertFalse("Matching project for AND", element.getPageId().equals(
					"org.eclipse.ui.tests.and"));

		}

	}

	public void testOrPage() {

		boolean found = false;
		Collection contributors = PropertyPageContributorManager.getManager()
				.getApplicableContributors(testFile);
		assertFalse("Has no file pages", contributors.isEmpty());
		for (Iterator iter = contributors.iterator(); iter.hasNext();) {
			RegistryPageContributor element = (RegistryPageContributor) iter
					.next();
			if (element.getPageId().equals("org.eclipse.ui.tests.or")) {
				found = true;
			}
		}
		assertTrue("OR property page for file not found", found);

		found = false;
		contributors = PropertyPageContributorManager.getManager()
				.getApplicableContributors(testFolder);
		assertFalse("Has no folder pages", contributors.isEmpty());
		for (Iterator iter = contributors.iterator(); iter.hasNext();) {
			RegistryPageContributor element = (RegistryPageContributor) iter
					.next();
			if (element.getPageId().equals("org.eclipse.ui.tests.or")) {
				found = true;
			}
		}
		assertTrue("OR property page for file not found", found);

		contributors = PropertyPageContributorManager.getManager()
				.getApplicableContributors(testProject);
		for (Iterator iter = contributors.iterator(); iter.hasNext();) {
			RegistryPageContributor element = (RegistryPageContributor) iter
					.next();
			assertFalse("Matching project for OR", element.getPageId().equals(
					"org.eclipse.ui.tests.or"));

		}

	}

	public void testInstanceOfPage() {

		Collection contributors = PropertyPageContributorManager.getManager()
				.getApplicableContributors(testFile);
		for (Iterator iter = contributors.iterator(); iter.hasNext();) {
			RegistryPageContributor element = (RegistryPageContributor) iter
					.next();
			assertFalse("Matching file for instanceof", element.getPageId()
					.equals("org.eclipse.ui.tests.instanceof"));
		}

		contributors = PropertyPageContributorManager.getManager()
				.getApplicableContributors(testFolder);
		for (Iterator iter = contributors.iterator(); iter.hasNext();) {
			RegistryPageContributor element = (RegistryPageContributor) iter
					.next();
			assertFalse("Matching folder for instanceof", element.getPageId()
					.equals("org.eclipse.ui.tests.instanceof"));

		}

		boolean found = false;
		contributors = PropertyPageContributorManager.getManager()
				.getApplicableContributors(testProject);
		assertFalse("Has no project pages", contributors.isEmpty());
		for (Iterator iter = contributors.iterator(); iter.hasNext();) {
			RegistryPageContributor element = (RegistryPageContributor) iter
					.next();
			if (element.getPageId().equals("org.eclipse.ui.tests.instanceof")) {
				found = true;
			}
		}
		assertTrue("instanceof property page for project not found", found);

	}

}
