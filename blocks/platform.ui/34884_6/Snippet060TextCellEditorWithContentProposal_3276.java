package org.eclipse.jface.snippets.viewers;

import org.eclipse.jface.bindings.keys.IKeyLookup;
import org.eclipse.jface.bindings.keys.KeyLookupFactory;
import org.eclipse.jface.bindings.keys.KeyStroke;
import org.eclipse.jface.fieldassist.ContentProposalAdapter;
import org.eclipse.jface.fieldassist.IContentProposalListener2;
import org.eclipse.jface.fieldassist.IContentProposalProvider;
import org.eclipse.jface.fieldassist.SimpleContentProposalProvider;
import org.eclipse.jface.fieldassist.TextContentAdapter;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.ColumnViewerEditor;
import org.eclipse.jface.viewers.ColumnViewerEditorActivationEvent;
import org.eclipse.jface.viewers.ColumnViewerEditorActivationStrategy;
import org.eclipse.jface.viewers.EditingSupport;
import org.eclipse.jface.viewers.FocusCellHighlighter;
import org.eclipse.jface.viewers.FocusCellOwnerDrawHighlighter;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.jface.viewers.TableViewerEditor;
import org.eclipse.jface.viewers.TableViewerFocusCellManager;
import org.eclipse.jface.viewers.TextCellEditor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;

public class Snippet060TextCellEditorWithContentProposal {
	private static class Color {
		public String name;

		public Color(String name) {
			this.name = name;
		}

		@Override
		public String toString() {
			return name;
		}
	}

	public static class TextCellEditorWithContentProposal extends TextCellEditor {

		private ContentProposalAdapter contentProposalAdapter;
		private boolean popupOpen = false; // true, iff popup is currently open

		public TextCellEditorWithContentProposal(Composite parent, IContentProposalProvider contentProposalProvider,
				KeyStroke keyStroke, char[] autoActivationCharacters) {
			super(parent);

			enableContentProposal(contentProposalProvider, keyStroke, autoActivationCharacters);
		}

		private void enableContentProposal(IContentProposalProvider contentProposalProvider, KeyStroke keyStroke,
				char[] autoActivationCharacters) {
			contentProposalAdapter = new ContentProposalAdapter(text, new TextContentAdapter(),
					contentProposalProvider, keyStroke, autoActivationCharacters);

			contentProposalAdapter.addContentProposalListener(new IContentProposalListener2() {

				@Override
				public void proposalPopupClosed(ContentProposalAdapter adapter) {
					popupOpen = false;
				}

				@Override
				public void proposalPopupOpened(ContentProposalAdapter adapter) {
					popupOpen = true;
				}
			});
		}

		public ContentProposalAdapter getContentProposalAdapter() {
			return contentProposalAdapter;
		}

		@Override
		protected void focusLost() {
			if (!popupOpen) {
				super.focusLost();
			}
		}

		@Override
		protected boolean dependsOnExternalFocusListener() {
			return false;
		}
	}

	private static class ColorNameEditingSupport extends EditingSupport {
		private TextCellEditorWithContentProposal cellEditor;

		public ColorNameEditingSupport(TableViewer viewer) {
			super(viewer);

			IContentProposalProvider contentProposalProvider = new SimpleContentProposalProvider(new String[] { "red",
					"green", "blue" });
			cellEditor = new TextCellEditorWithContentProposal(viewer.getTable(), contentProposalProvider, null, null);
		}

		@Override
		protected boolean canEdit(Object element) {
			return (element instanceof Color);
		}

		@Override
		protected CellEditor getCellEditor(Object element) {
			return cellEditor;
		}

		@Override
		protected Object getValue(Object element) {
			return ((Color) element).name;
		}

		@Override
		protected void setValue(Object element, Object value) {
			((Color) element).name = value.toString();
			getViewer().update(element, null);
		}

	}

	public Snippet060TextCellEditorWithContentProposal(Shell shell) {
		final TableViewer viewer = new TableViewer(shell, SWT.BORDER | SWT.FULL_SELECTION);
		final Table table = viewer.getTable();
		table.setLinesVisible(true);
		table.setHeaderVisible(true);

		final TableViewerColumn colorColumn = new TableViewerColumn(viewer, SWT.LEFT);
		colorColumn.getColumn().setText("Color name");
		colorColumn.getColumn().setWidth(200);
		colorColumn.setLabelProvider(new ColumnLabelProvider());
		colorColumn.setEditingSupport(new ColorNameEditingSupport(viewer));

		viewer.setContentProvider(new ArrayContentProvider());

		ColumnViewerEditorActivationStrategy activationSupport = new ColumnViewerEditorActivationStrategy(viewer) {
			@Override
			protected boolean isEditorActivationEvent(ColumnViewerEditorActivationEvent event) {
				return event.eventType == ColumnViewerEditorActivationEvent.TRAVERSAL
						|| event.eventType == ColumnViewerEditorActivationEvent.MOUSE_DOUBLE_CLICK_SELECTION
						|| event.eventType == ColumnViewerEditorActivationEvent.PROGRAMMATIC
						|| (event.eventType == ColumnViewerEditorActivationEvent.KEY_PRESSED && event.keyCode == KeyLookupFactory
								.getDefault().formalKeyLookup(IKeyLookup.ENTER_NAME));
			}
		};
		activationSupport.setEnableEditorActivationWithKeyboard(true);

		FocusCellHighlighter focusCellHighlighter = new FocusCellOwnerDrawHighlighter(viewer);
		TableViewerFocusCellManager focusCellManager = new TableViewerFocusCellManager(viewer, focusCellHighlighter);

		TableViewerEditor.create(viewer, focusCellManager, activationSupport, ColumnViewerEditor.TABBING_VERTICAL
				| ColumnViewerEditor.KEYBOARD_ACTIVATION);

		viewer.setInput(createModel());
	}

	private Color[] createModel() {
		return new Color[] { new Color("red"), new Color("green") };
	}

	public static void main(String[] args) {
		Display display = new Display();

		Shell shell = new Shell(display);
		shell.setLayout(new FillLayout());
		new Snippet060TextCellEditorWithContentProposal(shell);
		shell.open();

		while (!shell.isDisposed()) {
			if (!display.readAndDispatch())
				display.sleep();
		}

		display.dispose();
	}
}
