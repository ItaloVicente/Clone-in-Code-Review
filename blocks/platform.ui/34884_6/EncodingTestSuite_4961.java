package org.eclipse.ui.tests.encoding;

import java.nio.charset.Charset;
import java.nio.charset.IllegalCharsetNameException;
import java.util.Iterator;
import java.util.List;

import org.eclipse.core.runtime.Assert;
import org.eclipse.ui.WorkbenchEncoding;
import org.eclipse.ui.tests.harness.util.UITestCase;

public class EncodingTestCase extends UITestCase {

	public EncodingTestCase(String testName) {
		super(testName);
	}

	public void testWorkbenchEncodings() {
		List encodings = WorkbenchEncoding.getDefinedEncodings();
		Iterator iterator = encodings.iterator();

		while (iterator.hasNext()) {
			String nextEncoding = (String) iterator.next();
			try {
				Assert.isTrue(Charset.isSupported(nextEncoding), "Unsupported charset " + nextEncoding);
				
			} catch (IllegalCharsetNameException e) {
				Assert.isTrue(false, "Unsupported charset " + nextEncoding);
			}
			

		}
	}

}
