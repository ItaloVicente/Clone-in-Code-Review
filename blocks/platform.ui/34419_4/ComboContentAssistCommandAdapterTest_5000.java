package org.eclipse.ui.tests.fieldassist;

import org.eclipse.jface.fieldassist.ContentProposalAdapter;
import org.eclipse.jface.tests.fieldassist.ComboFieldAssistWindow;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.fieldassist.ContentAssistCommandAdapter;

public class ComboCommandFieldAssistWindow extends ComboFieldAssistWindow {

	@Override
	protected ContentProposalAdapter createContentProposalAdapter(
			Control control) {
		return new ContentAssistCommandAdapter(control,
				getControlContentAdapter(), getContentProposalProvider(), null,
				getAutoActivationCharacters());
	}

}
