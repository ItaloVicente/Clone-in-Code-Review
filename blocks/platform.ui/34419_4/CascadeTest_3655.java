package org.eclipse.e4.ui.tests.css.core;

import org.eclipse.e4.ui.tests.css.core.parser.CascadeTest;
import org.eclipse.e4.ui.tests.css.core.parser.FontFaceRulesTest;
import org.eclipse.e4.ui.tests.css.core.parser.ImportTest;
import org.eclipse.e4.ui.tests.css.core.parser.InheritTest;
import org.eclipse.e4.ui.tests.css.core.parser.MediaRulesTest;
import org.eclipse.e4.ui.tests.css.core.parser.RGBColorImplTest;
import org.eclipse.e4.ui.tests.css.core.parser.SelectorTest;
import org.eclipse.e4.ui.tests.css.core.parser.StyleRuleTest;
import org.eclipse.e4.ui.tests.css.core.parser.ValueTest;
import org.eclipse.e4.ui.tests.css.core.parser.ViewCSSTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({
	CascadeTest.class,
	FontFaceRulesTest.class,
	MediaRulesTest.class,
	RGBColorImplTest.class,
	StyleRuleTest.class,
	ViewCSSTest.class,
	ValueTest.class,
	SelectorTest.class,
	CSSEngineTest.class,
	ImportTest.class,
	InheritTest.class
})
public class CssCoreTestSuite {
}
