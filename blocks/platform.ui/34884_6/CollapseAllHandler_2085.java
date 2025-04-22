
package org.eclipse.ui.fieldassist;

import org.eclipse.jface.fieldassist.DecoratedField;
import org.eclipse.jface.fieldassist.FieldDecoration;
import org.eclipse.jface.fieldassist.FieldDecorationRegistry;
import org.eclipse.jface.fieldassist.IContentProposalProvider;
import org.eclipse.jface.fieldassist.IControlContentAdapter;
import org.eclipse.jface.fieldassist.IControlCreator;
import org.eclipse.osgi.util.NLS;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.internal.WorkbenchMessages;
import org.eclipse.ui.keys.IBindingService;

@Deprecated
public class ContentAssistField extends DecoratedField {

	private ContentAssistCommandAdapter adapter;

	private static final String CONTENT_ASSIST_DECORATION_ID = "org.eclipse.ui.fieldAssist.ContentAssistField"; //$NON-NLS-1$

	public ContentAssistField(Composite parent, int style,
			IControlCreator controlCreator,
			IControlContentAdapter controlContentAdapter,
			IContentProposalProvider proposalProvider, String commandId,
			char[] autoActivationCharacters) {

		super(parent, style, controlCreator);
		adapter = new ContentAssistCommandAdapter(getControl(),
				controlContentAdapter, proposalProvider, commandId,
				autoActivationCharacters);

		addFieldDecoration(getFieldDecoration(), SWT.LEFT | SWT.TOP, true);

	}

	public void setEnabled(boolean enabled) {
		adapter.setEnabled(enabled);
		if (enabled) {
			showDecoration(getFieldDecoration());
		} else {
			hideDecoration(getFieldDecoration());
		}
	}

	private FieldDecoration getFieldDecoration() {
		FieldDecorationRegistry registry = FieldDecorationRegistry.getDefault();
		String decId = CONTENT_ASSIST_DECORATION_ID + adapter.getCommandId();
		FieldDecoration dec = registry.getFieldDecoration(decId);

		if (dec == null) {
			FieldDecoration originalDec = registry
					.getFieldDecoration(FieldDecorationRegistry.DEC_CONTENT_PROPOSAL);

			registry.registerFieldDecoration(decId, null, originalDec
					.getImage());
			dec = registry.getFieldDecoration(decId);
		}
		IBindingService bindingService = PlatformUI
				.getWorkbench().getService(IBindingService.class);
		dec.setDescription(NLS.bind(
				WorkbenchMessages.ContentAssist_Cue_Description_Key,
				bindingService.getBestActiveBindingFormattedFor(adapter
						.getCommandId())));

		return dec;
	}

	public ContentAssistCommandAdapter getContentAssistCommandAdapter() {
		return adapter;
	}
}
