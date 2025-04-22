package org.eclipse.ui.tests.dialogs;

import junit.framework.Test;
import junit.framework.TestSuite;

import org.eclipse.ui.tests.compare.UIComparePreferencesAuto;

public class UIAutomatedSuite extends TestSuite {

    public static Test suite() {
        return new UIAutomatedSuite();
    }

	public UIAutomatedSuite() {
		addTest(new TestSuite(UIDialogsAuto.class));
		addTest(new TestSuite(DeprecatedUIDialogsAuto.class));
		addTest(new TestSuite(UIWizardsAuto.class));
		addTest(new TestSuite(DeprecatedUIWizardsAuto.class));
		addTest(new TestSuite(UIPreferencesAuto.class));
		addTest(new TestSuite(UIComparePreferencesAuto.class));
		addTest(new TestSuite(DeprecatedUIPreferencesAuto.class));
		addTest(new TestSuite(UIMessageDialogsAuto.class));
		addTest(new TestSuite(UINewWorkingSetWizardAuto.class));
		addTest(new TestSuite(UIEditWorkingSetWizardAuto.class));
		addTest(new TestSuite(SearchPatternAuto.class));
		addTest(new TestSuite(UIFilteredResourcesSelectionDialogAuto.class));
	}
}
