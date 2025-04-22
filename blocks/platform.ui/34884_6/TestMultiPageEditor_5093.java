package org.eclipse.ui.tests.multipageeditor;

import org.eclipse.core.resources.IMarker;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.IKeyBindingService;
import org.eclipse.ui.part.EditorPart;

public final class TestKeyBindingMultiPageEditorPart extends EditorPart {

    private final int number;

    public TestKeyBindingMultiPageEditorPart(int number) {
        super();
        this.number = number;
    }

	@Override
	public void createPartControl(Composite parent) {
        Composite composite = new Composite(parent, SWT.NONE);
        composite.setLayout(new RowLayout());
        Text text1 = new Text(composite, SWT.NONE);
        text1.setText("Blue"); //$NON-NLS-1$
        Text text2 = new Text(composite, SWT.NONE);
        text2.setText("Red"); //$NON-NLS-1$
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
	public void init(IEditorSite site, IEditorInput input) {
        setInput(input);
        setSite(site);
        setPartName("Editor"); //$NON-NLS-1$
        setTitleImage(input.getImageDescriptor().createImage());
        setTitleToolTip("Moooooo"); //$NON-NLS-1$
    }

	@Override
	public boolean isDirty() {
        return false;
    }

	@Override
	public boolean isSaveAsAllowed() {
        return false;
    }

	@Override
	public void setFocus() {
        final boolean odds = (number % 2) > 0;
        final String scope1 = "org.eclipse.ui.tests.scope1"; //$NON-NLS-1$
        final String scope2 = "org.eclipse.ui.tests.scope2"; //$NON-NLS-1$
        IKeyBindingService keyBindingService = getEditorSite()
                .getKeyBindingService();
        keyBindingService.setScopes(new String[] { (odds) ? scope1 : scope2 });
    }
}
