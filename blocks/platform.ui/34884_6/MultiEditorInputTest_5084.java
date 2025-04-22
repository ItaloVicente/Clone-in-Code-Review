
package org.eclipse.ui.tests.multipageeditor;

import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.contexts.IContextService;
import org.eclipse.ui.editors.text.TextEditor;
import org.eclipse.ui.services.IServiceLocator;

public class ContextTextEditor extends TextEditor {
	public static final String TEXT_CONTEXT_ID = "org.eclipse.ui.textEditorScope";

	public static final String CONTEXT_ID = "org.eclipse.ui.tests.multipageeditor.contextEditor";

	@Override
	public void init(IEditorSite site, IEditorInput input)
			throws PartInitException {
		super.init(site, input);
		IServiceLocator locator = getSite();
		IContextService contextService = locator
				.getService(IContextService.class);

		contextService.activateContext(CONTEXT_ID);
	}
}
