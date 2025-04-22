package org.eclipse.ui.tests.multipageeditor;

import org.eclipse.core.resources.IMarker;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.part.MultiPageEditorPart;

public final class TestMultiPageEditor extends MultiPageEditorPart {

    @Override
	protected void createPages() {
        try {
            IEditorPart part1 = new TestKeyBindingMultiPageEditorPart(0);
            addPage(part1, getEditorInput());

            IEditorPart part2 = new TestKeyBindingMultiPageEditorPart(1);
            addPage(part2, getEditorInput());
        } catch (PartInitException e) {
            throw new RuntimeException(e);
        }
    }

	@Override
	public void doSave(IProgressMonitor monitor) {
    }

	@Override
	public void doSaveAs() {
        throw new UnsupportedOperationException("Not implemented in this test."); //$NON-NLS-1$
    }

    public void gotoMarker(IMarker marker) {

    }

	@Override
	public boolean isSaveAsAllowed() {
        return false;
    }

    public void setPage(int page) {
        setActivePage(page);
    }

}
