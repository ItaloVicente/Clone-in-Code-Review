package org.eclipse.ui.tests.multipageeditor;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.FindReplaceDocumentAdapter;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IRegion;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.editors.text.TextEditor;
import org.eclipse.ui.part.MultiPageEditorPart;
import org.eclipse.ui.texteditor.IDocumentProvider;

public class MultiVariablePageEditor extends MultiPageEditorPart {

	private Composite lastPage;

	@Override
	protected void createPages() {
		try {
			TextEditor section1 = new TextEditor();
			int index = addPage(section1, getEditorInput());
			setPageText(index, section1.getTitle());

			TextEditor section2 = new TextEditor();
			index = addPage(section2, getEditorInput());
			setPageText(index, section2.getTitle());

			ContextTextEditor section3 = new ContextTextEditor();
			index = addPage(section3, getEditorInput());
			setPageText(index, section3.getTitle());

		} catch (PartInitException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void doSave(IProgressMonitor monitor) {
	}

	@Override
	public void doSaveAs() {
		throw new UnsupportedOperationException(
				"doSaveAs should not be called.");
	}

	@Override
	public boolean isSaveAsAllowed() {
		return false;
	}

	@Override
	protected void pageChange(int newPageIndex) {
		super.pageChange(newPageIndex);
		IEditorPart part = getEditor(newPageIndex);
		if (part instanceof TextEditor) {
			TextEditor editor = (TextEditor) part;
			IDocumentProvider provider = editor.getDocumentProvider();
			IDocument doc = provider.getDocument(getEditorInput());
			FindReplaceDocumentAdapter find = new FindReplaceDocumentAdapter(
					doc);
			try {
				IRegion region = find.find(0, "#section0" + (newPageIndex + 1),
						true, true, false, false);
				if (region != null) {
					editor.selectAndReveal(region.getOffset(), region
							.getLength());
				}
			} catch (BadLocationException e) {
				System.err.println("Failed to find a section");
			}
		}
	}

	public void setPage(int index) {
		super.setActivePage(index);
	}

	public void addLastPage() {
		lastPage = new Composite(getContainer(), SWT.NONE);
		Label l = new Label(lastPage, SWT.SHADOW_IN);
		l.setText(getEditorInput().getName());
		addPage(lastPage);
	}

	public void removeLastPage() {
		if (getPageCount() > 0) {
			removePage(getPageCount() - 1);
		}
		lastPage = null;
	}

	public Control getLastPage() {
		return lastPage;
	}
	
	@Override
	public IEditorPart getEditor(int pageIndex) {
		return super.getEditor(pageIndex);
	}

	public Control getTestControl(int index) {
		return getControl(index);
	}
}
