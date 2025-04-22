
package org.eclipse.ui.views.contentoutline;

import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.ISelectionProvider;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.internal.views.ViewsPlugin;
import org.eclipse.ui.internal.views.contentoutline.ContentOutlineMessages;
import org.eclipse.ui.part.IContributedContentsView;
import org.eclipse.ui.part.IPage;
import org.eclipse.ui.part.IPageBookViewPage;
import org.eclipse.ui.part.IPageSite;
import org.eclipse.ui.part.MessagePage;
import org.eclipse.ui.part.PageBook;
import org.eclipse.ui.part.PageBookView;

public class ContentOutline extends PageBookView implements ISelectionProvider,
        ISelectionChangedListener {



    public static final String PREFIX = PlatformUI.PLUGIN_ID + "."; //$NON-NLS-1$

    public static final String CONTENT_OUTLINE_VIEW_HELP_CONTEXT_ID = PREFIX
            + "content_outline_context";//$NON-NLS-1$

    private String defaultText =ContentOutlineMessages.ContentOutline_noOutline; 

    public ContentOutline() {
        super();
    }

    @Override
	public void addSelectionChangedListener(ISelectionChangedListener listener) {
        getSelectionProvider().addSelectionChangedListener(listener);
    }

    @Override
	protected IPage createDefaultPage(PageBook book) {
        MessagePage page = new MessagePage();
        initPage(page);
        page.createControl(book);
        page.setMessage(defaultText);
        return page;
    }

    @Override
	public void createPartControl(Composite parent) {
        super.createPartControl(parent);
        PlatformUI.getWorkbench().getHelpSystem().setHelp(getPageBook(),
                CONTENT_OUTLINE_VIEW_HELP_CONTEXT_ID);
    }

    @Override
	protected PageRec doCreatePage(IWorkbenchPart part) {
        Object obj = ViewsPlugin.getAdapter(part, IContentOutlinePage.class, false);
        if (obj instanceof IContentOutlinePage) {
            IContentOutlinePage page = (IContentOutlinePage) obj;
            if (page instanceof IPageBookViewPage) {
				initPage((IPageBookViewPage) page);
			}
            page.createControl(getPageBook());
            return new PageRec(part, page);
        }
        return null;
    }

    @Override
	protected void doDestroyPage(IWorkbenchPart part, PageRec rec) {
        IContentOutlinePage page = (IContentOutlinePage) rec.page;
        page.dispose();
        rec.dispose();
    }

    @Override
	public Object getAdapter(Class key) {
        if (key == IContributedContentsView.class) {
			return new IContributedContentsView() {
                @Override
				public IWorkbenchPart getContributingPart() {
                    return getContributingEditor();
                }
            };
		}
        return super.getAdapter(key);
    }

    @Override
	protected IWorkbenchPart getBootstrapPart() {
        IWorkbenchPage page = getSite().getPage();
        if (page != null) {
			return page.getActiveEditor();
		}

        return null;
    }

    private IWorkbenchPart getContributingEditor() {
        return getCurrentContributingPart();
    }

    @Override
	public ISelection getSelection() {
        return getSelectionProvider().getSelection();
    }

    @Override
	protected boolean isImportant(IWorkbenchPart part) {
        return (part instanceof IEditorPart);
    }

    @Override
	public void partBroughtToTop(IWorkbenchPart part) {
        partActivated(part);
    }

    @Override
	public void removeSelectionChangedListener(
            ISelectionChangedListener listener) {
        getSelectionProvider().removeSelectionChangedListener(listener);
    }

    @Override
	public void selectionChanged(SelectionChangedEvent event) {
        getSelectionProvider().selectionChanged(event);
    }

    @Override
	public void setSelection(ISelection selection) {
        getSelectionProvider().setSelection(selection);
    }

    @Override
	protected void showPageRec(PageRec pageRec) {
        IPageSite pageSite = getPageSite(pageRec.page);
        ISelectionProvider provider = pageSite.getSelectionProvider();
        if (provider == null && (pageRec.page instanceof IContentOutlinePage)) {
            pageSite.setSelectionProvider((IContentOutlinePage) pageRec.page);
		}
        super.showPageRec(pageRec);
    }
}
