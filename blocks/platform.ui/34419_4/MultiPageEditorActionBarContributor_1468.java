package org.eclipse.ui.part;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.TabFolder;

@Deprecated
public abstract class MultiPageEditor extends EditorPart {
    private List syncVector;

    private TabFolder tabFolder;

    @Deprecated
	public MultiPageEditor() {
        super();
    }

    protected void addSyncroPageBook(PageBook pageBook) {
        if (syncVector == null) {
			syncVector = new ArrayList(1);
		}
        syncVector.add(pageBook);

        syncPageBook(pageBook);
    }

    @Override
	public void createPartControl(Composite parent) {
        tabFolder = new TabFolder(parent, SWT.NONE);
        tabFolder.addSelectionListener(new SelectionAdapter() {
            @Override
			public void widgetSelected(SelectionEvent e) {
                sync();
            }
        });
    }

    protected TabFolder getFolder() {
        return tabFolder;
    }

    protected void onPageChange() {
        if (syncVector != null) {
            Iterator itr = syncVector.iterator();
            while (itr.hasNext()) {
                PageBook pageBook = (PageBook) itr.next();
                syncPageBook(pageBook);
            }
        }
    }

    protected void removeSyncroPageBook(PageBook pageBook) {
        if (syncVector != null) {
			syncVector.remove(pageBook);
		}
        pageBook.dispose();
    }

    protected void sync() {
        if (syncVector != null) {
            Iterator itr = syncVector.iterator();
            while (itr.hasNext()) {
                PageBook pageBook = (PageBook) itr.next();
                syncPageBook(pageBook);
            }
        }
    }

    protected void syncPageBook(PageBook pageBook) {
        int pos = tabFolder.getSelectionIndex();
        Control children[] = pageBook.getChildren();
        int size = children.length;
        if (pos < size) {
			pageBook.showPage(children[pos]);
		}
    }
}
