package org.eclipse.ui.tests.fieldassist;

import org.eclipse.jface.tests.fieldassist.AbstractFieldAssistWindow;
import org.eclipse.swt.widgets.Combo;

public class ComboContentAssistCommandAdapterTest extends
		AbstractContentAssistCommandAdapterTest {

	@Override
	protected AbstractFieldAssistWindow createFieldAssistWindow() {
		return new ComboCommandFieldAssistWindow();
	}
	
	private Combo getCombo() {
		return (Combo)getFieldAssistWindow().getFieldAssistControl();
	}
	
	public void testBug243612() throws Exception {
		getFieldAssistWindow().open();

		sendFocusInToControl();
		executeContentAssistHandler();

		assertTwoShellsUp();
		
		assertFalse(getCombo().getListVisible());
	}

}
