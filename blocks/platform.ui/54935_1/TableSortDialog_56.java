
package org.eclipse.ui.views.markers.internal;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.window.Window;

public class TableSortAction extends Action {

    private TableView view;

    private TableSortDialog dialog;

    public TableSortAction(TableView view, TableSortDialog dialog) {
        super(MarkerMessages.sortAction_title);
        this.view = view;
        this.dialog = dialog;
        setEnabled(true);
    }

    @Override
	public void run() {
        if (dialog.open() == Window.OK && dialog.isDirty()) {
            view.setComparator(dialog.getSorter());
        }
    }
}
