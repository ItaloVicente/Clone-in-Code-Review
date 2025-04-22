package org.eclipse.ui.dialogs;

import java.util.Arrays;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;

public class ElementListSelectionDialog extends
        AbstractElementListSelectionDialog {

    private Object[] fElements;

    public ElementListSelectionDialog(Shell parent, ILabelProvider renderer) {
        super(parent, renderer);
    }

    public void setElements(Object[] elements) {
        fElements = elements;
    }

    @Override
	protected void computeResult() {
        setResult(Arrays.asList(getSelectedElements()));
    }

    @Override
	protected Control createDialogArea(Composite parent) {
        Composite contents = (Composite) super.createDialogArea(parent);

        createMessageArea(contents);
        createFilterText(contents);
        createFilteredList(contents);

        setListElements(fElements);

        setSelection(getInitialElementSelections().toArray());

        return contents;
    }
}
