package org.eclipse.egit.ui.internal.repository;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.text.AbstractInformationControlManager;
import org.eclipse.jface.text.AbstractInformationControlManager.IInformationControlCloser;
import org.eclipse.jface.text.DefaultInformationControl;
import org.eclipse.jface.text.IInformationControl;
import org.eclipse.jface.text.IInformationControlCreator;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jgit.util.StringUtils;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.FocusListener;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.contexts.IContextActivation;
import org.eclipse.ui.contexts.IContextService;
import org.eclipse.ui.editors.text.EditorsUI;
import org.eclipse.ui.texteditor.ITextEditorActionConstants;
import org.eclipse.ui.texteditor.ITextEditorActionDefinitionIds;
import org.eclipse.ui.views.properties.PropertyDescriptor;
import org.eclipse.ui.views.properties.PropertySheetPage;

public class MessagePropertyDescriptor extends PropertyDescriptor {

	private final String message;

	private final PropertySheetPage page;

	public MessagePropertyDescriptor(Object id, String label, String message,
			PropertySheetPage page) {
		super(id, label);
		this.message = message;
		this.page = page;
	}

	@Override
	public String getDescription() {
		String description = super.getDescription();
		if (description == null) {
			description = getDisplayName();
		}
		return description;
	}

	@Override
	public CellEditor createPropertyEditor(Composite parent) {
		if (StringUtils.isEmptyOrNull(message)) {
			return null;
		}
		return new PopupCellEditor(parent);
	}

	private class PopupCellEditor extends CellEditor {

		private Label editor;

		private Tree tree;

		private TreeItem mySelf;

		private Object content;

		private AbstractInformationControlManager popupControlManager;

		PopupCellEditor(Composite parent) {
			super(parent);
		}

		@Override
		protected Control createControl(Composite parent) {
			tree = (Tree) parent;
			editor = new Label(parent, SWT.LEFT);
			editor.setBackground(
					parent.getDisplay().getSystemColor(SWT.COLOR_TRANSPARENT));
			return editor;
		}

		@Override
		protected Object doGetValue() {
			return content;
		}

		@Override
		protected void doSetValue(Object value) {
			content = value;
		}

		@Override
		protected void doSetFocus() {
			if (popupControlManager != null) {
				return;
			}
			Rectangle bounds = editor.getBounds();
			FocusPopupAction focusAction;
			IActionBars bars = page.getSite().getActionBars();
			IAction existingAction = bars.getGlobalActionHandler(
					ITextEditorActionConstants.SHOW_INFORMATION);
			if (existingAction == null
					|| !(existingAction instanceof FocusPopupAction)) {
				focusAction = new FocusPopupAction();
				bars.setGlobalActionHandler(focusAction.getId(), focusAction);
				bars.updateActionBars();
			} else {
				existingAction.setEnabled(true);
				focusAction = (FocusPopupAction) existingAction;
			}
			IContextService ctxSrv = page.getSite()
					.getService(IContextService.class);
			IContextActivation[] ctxActivation = { null };
			ctxActivation[0] = ctxSrv
					.activateContext("org.eclipse.ui.textEditorScope"); //$NON-NLS-1$
			IInformationControlCreator popupCreator = parentShell -> new DefaultInformationControl(
					parentShell, EditorsUI.getTooltipAffordanceString(), null);
			class Manager extends AbstractInformationControlManager {

				Manager(IInformationControlCreator creator) {
					super(creator);
				}

				@Override
				protected void setCloser(IInformationControlCloser closer) {
					super.setCloser(closer);
				}

				@Override
				protected void computeInformation() {
					setInformation(message, bounds);
				}

			}
			Manager popupManager = new Manager(popupCreator);
			IInformationControlCloser closer = new IInformationControlCloser() {

				private Control subject;

				private IInformationControl popup;

				private Listener subjectListener;

				private FocusListener focusListener;

				@Override
				public void setSubjectControl(Control subject) {
					this.subject = subject;
				}

				@Override
				public void setInformationControl(IInformationControl control) {
					focusAction.setPopup(control);
					if (control != null) {
						popup = control;
					} else if (ctxActivation[0] != null) {
						ctxSrv.deactivateContext(ctxActivation[0]);
						ctxActivation[0] = null;
					}
				}

				@Override
				public void start(Rectangle subjectArea) {
					if (subject != null && popup != null) {
						hookSubject();
						popup.addDisposeListener(event -> {
							focusAction.setEnabled(false);
							unhookSubject();
							if (popupControlManager != null) {
								popupControlManager.dispose();
								popupControlManager = null;
							}
						});
						focusListener = new FocusListener() {

							@Override
							public void focusGained(FocusEvent e) {
							}

							@Override
							public void focusLost(FocusEvent e) {
								popup.dispose();
							}
						};
						popup.addFocusListener(focusListener);
					}
				}

				@Override
				public void stop() {
					unhookSubject();
					if (popup != null) {
						if (focusListener != null) {
							popup.removeFocusListener(focusListener);
							focusListener = null;
						}
						popup = null;
					}
				}

				private void hookSubject() {
					if (subject == null || popup == null) {
						return;
					}
					subjectListener = event -> {
						switch (event.type) {
						case SWT.Selection:
							if (event.item != mySelf && popup != null) {
								popup.dispose();
								popup = null;
							}
							break;
						case SWT.Dispose:
						case SWT.MouseWheel:
							if (popup != null) {
								popup.dispose();
								popup = null;
							}
							break;
						case SWT.Deactivate:
						case SWT.FocusOut:
							if (popup != null && !popup.isFocusControl()) {
								popup.dispose();
								popup = null;
							}
							break;
						default:
							break;
						}
					};
					subject.addListener(SWT.Selection, subjectListener);
					subject.addListener(SWT.Dispose, subjectListener);
					subject.addListener(SWT.Deactivate, subjectListener);
					subject.addListener(SWT.FocusOut, subjectListener);
					subject.addListener(SWT.MouseWheel, subjectListener);
					subject.getShell().addListener(SWT.Deactivate,
							subjectListener);
				}

				private void unhookSubject() {
					if (subject == null || subjectListener == null) {
						return;
					}
					subject.removeListener(SWT.Selection, subjectListener);
					subject.removeListener(SWT.Dispose, subjectListener);
					subject.removeListener(SWT.Deactivate, subjectListener);
					subject.removeListener(SWT.FocusOut, subjectListener);
					subject.removeListener(SWT.MouseWheel, subjectListener);
					Shell shell = subject.getShell();
					if (shell != null) {
						shell.removeListener(SWT.Deactivate, subjectListener);
					}
					subjectListener = null;
				}
			};
			popupManager.setCloser(closer);
			popupControlManager = popupManager;
			TreeItem[] selection = tree.getSelection();
			mySelf = selection == null || selection.length == 0 ? null
					: selection[0];
			popupManager.install(editor.getParent());
			popupManager.showInformation();
		}
	}

	private static class FocusPopupAction extends Action {

		private IInformationControl popup;

		FocusPopupAction() {
			super();
		}

		void setPopup(IInformationControl control) {
			popup = control;
			setEnabled(control != null);
		}

		@Override
		public String getId() {
			return ITextEditorActionConstants.SHOW_INFORMATION;
		}

		@Override
		public String getActionDefinitionId() {
			return ITextEditorActionDefinitionIds.SHOW_INFORMATION;
		}

		@Override
		public void run() {
			if (popup != null) {
				popup.setFocus();
			}
		}
	}

}
