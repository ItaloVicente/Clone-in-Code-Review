package org.eclipse.ui.internal.ide;

import java.io.File;

import org.junit.Test;

public class DirectoryProposalContentAssistTest extends DirectoryProposalContentAssistTestCase {

	@Test
	public void fileSeparatorOpensProposalPopup() throws Exception {
		getFieldAssistWindow().open();
		sendFocusInToControl();

		sendKeyEventToControl(File.separatorChar);
		waitForDirectoryContentAssist();

		assertPopUpIsOpen();
	}

}
