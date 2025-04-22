package org.eclipse.ui.tests.session;

import java.util.Locale;

import org.eclipse.ui.IPerspectiveDescriptor;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.tests.navigator.AbstractNavigatorTest;

public class LocaleTest extends AbstractNavigatorTest {


	public LocaleTest(String testName) {
		super(testName);

	}

	@Override
	protected void doSetUp() throws Exception {
		super.doSetUp();
		createTestFile();
	}


	public void testLocales() {
		Locale[] locales = Locale.getAvailableLocales();
		Locale oldLocale = Locale.getDefault();

		switchLocale(new Locale("sv"));
		for (Locale locale : locales) {
			switchLocale(locale);
		}

		Locale.setDefault(oldLocale);
	}

	private void switchLocale(Locale locale) {
		Locale.setDefault(locale);
		System.out.println(locale.toString());
		IWorkbenchPage page = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
		IPerspectiveDescriptor[] perspectives = PlatformUI.getWorkbench()
				.getPerspectiveRegistry().getPerspectives();
		for (IPerspectiveDescriptor perspective : perspectives) {
			page.setPerspective(perspective);
		}
		page.closeAllPerspectives(false, false);
	}

}
