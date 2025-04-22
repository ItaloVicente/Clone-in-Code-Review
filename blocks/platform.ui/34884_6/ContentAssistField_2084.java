
package org.eclipse.ui.fieldassist;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.IHandler;
import org.eclipse.jface.fieldassist.ContentProposalAdapter;
import org.eclipse.jface.fieldassist.ControlDecoration;
import org.eclipse.jface.fieldassist.FieldDecoration;
import org.eclipse.jface.fieldassist.FieldDecorationRegistry;
import org.eclipse.jface.fieldassist.IContentProposalProvider;
import org.eclipse.jface.fieldassist.IControlContentAdapter;
import org.eclipse.osgi.util.NLS;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.FocusListener;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.IWorkbenchCommandConstants;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.handlers.IHandlerActivation;
import org.eclipse.ui.handlers.IHandlerService;
import org.eclipse.ui.internal.WorkbenchMessages;
import org.eclipse.ui.keys.IBindingService;

public class ContentAssistCommandAdapter extends ContentProposalAdapter {

	private static final String CONTENT_ASSIST_DECORATION_ID = "org.eclipse.ui.fieldAssist.ContentAssistField"; //$NON-NLS-1$
	private String commandId;

	@Deprecated
	public static final String CONTENT_PROPOSAL_COMMAND= IWorkbenchCommandConstants.EDIT_CONTENT_ASSIST;

	private static final int DEFAULT_AUTO_ACTIVATION_DELAY = 500;

	private IHandlerService handlerService;

	private IHandlerActivation activeHandler;

	private IHandler proposalHandler = new AbstractHandler() {
		@Override
		public Object execute(ExecutionEvent event) {
			openProposalPopup();
			return null;
		}

	};
	private ControlDecoration decoration;

	public ContentAssistCommandAdapter(Control control,
			IControlContentAdapter controlContentAdapter,
			IContentProposalProvider proposalProvider, String commandId,
			char[] autoActivationCharacters) {
		this(control, controlContentAdapter, proposalProvider, commandId,
				autoActivationCharacters, false);
	}

	public ContentAssistCommandAdapter(Control control,
			IControlContentAdapter controlContentAdapter,
			IContentProposalProvider proposalProvider, String commandId,
			char[] autoActivationCharacters, boolean installDecoration) {
		super(control, controlContentAdapter, proposalProvider, null,
				autoActivationCharacters);
		this.commandId = commandId;
		if (commandId == null) {
			this.commandId= IWorkbenchCommandConstants.EDIT_CONTENT_ASSIST;
		}

		if (autoActivationCharacters == null) {
			this.setAutoActivationCharacters(new char[] {});
		}
		setAutoActivationDelay(DEFAULT_AUTO_ACTIVATION_DELAY);

		this.handlerService = PlatformUI.getWorkbench()
				.getService(IHandlerService.class);

		addListeners(control);

		if (control.isFocusControl()) {
			activateHandler();
		}

		if (installDecoration) {
			decoration = new ControlDecoration(control, SWT.TOP | SWT.LEFT);
			decoration.setShowOnlyOnFocus(true);
			FieldDecoration dec = getContentAssistFieldDecoration();
			decoration.setImage(dec.getImage());
			decoration.setDescriptionText(dec.getDescription());
		}

	}

	private void addListeners(Control control) {
		control.addFocusListener(new FocusListener() {
			@Override
			public void focusLost(FocusEvent e) {
				deactivateHandler();
			}

			@Override
			public void focusGained(FocusEvent e) {
				if (isEnabled()) {
					activateHandler();
				} else {
					deactivateHandler();
				}
			}
		});
		control.addDisposeListener(new DisposeListener() {
			@Override
			public void widgetDisposed(DisposeEvent e) {
				deactivateHandler();
			}
		});
	}

	public String getCommandId() {
		return commandId;
	}

	private FieldDecoration getContentAssistFieldDecoration() {
		FieldDecorationRegistry registry = FieldDecorationRegistry.getDefault();
		String decId = CONTENT_ASSIST_DECORATION_ID + getCommandId();
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
		dec
				.setDescription(NLS
						.bind(
								WorkbenchMessages.ContentAssist_Cue_Description_Key,
								bindingService
										.getBestActiveBindingFormattedFor(getCommandId())));

		return dec;
	}

	@Override
	public void setEnabled(boolean enabled) {
		super.setEnabled(enabled);
		if (decoration != null) {
			if (enabled) {
				decoration.show();
			} else {
				decoration.hide();
			}
		}
		if (getControl().isFocusControl()) {
			if (enabled) {
				activateHandler();
			} else {
				deactivateHandler();
			}
		}
	}

	private void activateHandler() {
		if (activeHandler == null) {
			activeHandler = handlerService.activateHandler(commandId,
					proposalHandler);
		}
	}

	private void deactivateHandler() {
		if (activeHandler != null) {
			handlerService.deactivateHandler(activeHandler);
			activeHandler = null;
		}
	}
}
