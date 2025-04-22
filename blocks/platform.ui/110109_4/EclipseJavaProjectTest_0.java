package org.eclipse.ui.tests.smartimport;

import org.eclipse.reddeer.junit.runner.RedDeerSuite;
import org.junit.runner.RunWith;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(RedDeerSuite.class)
@SuiteClasses({
	PlainJavaProjectTest.class,
	PlainEclipseProjectTest.class,
	EclipseJavaProjectTest.class,
	FeatureProjectTest.class
	})
public class AllTests {

}
