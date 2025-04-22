package org.eclipse.ui.tests.dynamicplugins;

import org.eclipse.ui.WorkbenchEncoding;
import org.eclipse.ui.ide.IDEEncoding;
import org.eclipse.ui.internal.registry.IWorkbenchRegistryConstants;

public class EncodingTests extends DynamicTestCase {

	private static final String ENCODING = "Cp1257";
	public EncodingTests(String testName) {
		super(testName);
	}

	public void testWorkbenchEncodings() {
		assertFalse(WorkbenchEncoding.getDefinedEncodings().contains(ENCODING));
		getBundle();
		assertTrue(WorkbenchEncoding.getDefinedEncodings().contains(ENCODING));
		removeBundle();
		assertFalse(WorkbenchEncoding.getDefinedEncodings().contains(ENCODING));
	}
	
	public void testIDEEncodings() {
		assertFalse(IDEEncoding.getIDEEncodings().contains(ENCODING));
		getBundle();
		assertTrue(IDEEncoding.getIDEEncodings().contains(ENCODING));
		removeBundle();
		assertFalse(IDEEncoding.getIDEEncodings().contains(ENCODING));
	}
	
	@Override
	protected String getExtensionId() {
		return "newEncoding1.testDynamicEncodingAddition";
	}

	@Override
	protected String getExtensionPoint() {
		return IWorkbenchRegistryConstants.PL_ENCODINGS;
	}

	@Override
	protected String getInstallLocation() {
		return "data/org.eclipse.newEncoding1";
	}
}
