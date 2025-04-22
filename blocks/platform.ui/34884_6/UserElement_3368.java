package org.eclipse.ui.examples.propertysheet;

import org.eclipse.jface.text.Document;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.editors.text.TextEditor;
import org.eclipse.ui.views.contentoutline.ContentOutlinePage;
import org.eclipse.ui.views.contentoutline.IContentOutlinePage;
import org.eclipse.ui.views.properties.IPropertySheetPage;
import org.eclipse.ui.views.properties.PropertySheetPage;

public class UserEditor extends TextEditor {
    private ContentOutlinePage userContentOutline;

    public UserEditor() {
        super();
    }

    public void createPartControl(Composite parent) {
        super.createPartControl(parent);
        getSourceViewer().setDocument(
                new Document(MessageUtil.getString("Editor_instructions"))); //$NON-NLS-1$
    }

    public Object getAdapter(Class adapter) {
        if (adapter.equals(IContentOutlinePage.class)) {
            return getContentOutline();
        }
        if (adapter.equals(IPropertySheetPage.class)) {
            return getPropertySheet();
        }
        return super.getAdapter(adapter);
    }

    protected ContentOutlinePage getContentOutline() {
        if (userContentOutline == null) {
            userContentOutline = new PropertySheetContentOutlinePage(
                    new UserFileParser().parse(getDocumentProvider()));
        }
        return userContentOutline;
    }

    protected IPropertySheetPage getPropertySheet() {
        return new PropertySheetPage();
    }
}
