package org.eclipse.e4.ui.tests.css.swt;

import org.eclipse.e4.ui.css.core.resources.CSSResourcesHelpersTest;
import org.eclipse.e4.ui.css.core.resources.SWTResourceRegistryKeyFactoryTest;
import org.eclipse.e4.ui.css.core.resources.SWTResourcesRegistryTest;
import org.eclipse.e4.ui.css.swt.helpers.CSSSWTColorHelperTest;
import org.eclipse.e4.ui.css.swt.helpers.CSSSWTFontHelperTest;
import org.eclipse.e4.ui.css.swt.helpers.EclipsePreferencesHelperTest;
import org.eclipse.e4.ui.css.swt.helpers.PreferenceOverriddenByCssChangeListenerTest;
import org.eclipse.e4.ui.css.swt.properties.preference.EclipsePreferencesHandlerTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({ CSSSWTFontHelperTest.class, CSSSWTColorHelperTest.class, CSSResourcesHelpersTest.class,
	SWTResourceRegistryKeyFactoryTest.class, SWTResourcesRegistryTest.class, FontDefinitionTest.class,
	ColorDefinitionTest.class, ThemesExtensionTest.class, IEclipsePreferencesTest.class,
	EclipsePreferencesHelperTest.class, CSSSWTWidgetTest.class, LabelTest.class, CTabFolderTest.class,
	CTabItemTest.class, IdClassLabelColorTest.class, ShellTest.class, ButtonTest.class, GradientTest.class,
	MarginTest.class, InnerClassElementTest.class, EclipsePreferencesHandlerTest.class,
	PreferenceOverriddenByCssChangeListenerTest.class, ButtonTextTransformTest.class, LabelTextTransformTest.class,
		TextTextTransformTest.class, DescendentTest.class, ThemeTest.class, Bug419482Test.class, ShellActiveTest.class,
		InheritTest.class })
public class CssSwtTestSuite {

}
