package org.eclipse.ui.views.properties;

import org.eclipse.jface.viewers.LabelProvider;

public class ComboBoxLabelProvider extends LabelProvider {

    private String[] values;

    public ComboBoxLabelProvider(String[] values) {
        this.values = values;
    }

    public String[] getValues() {
        return values;
    }

    public void setValues(String[] values) {
        this.values = values;
    }

    @Override
	public String getText(Object element) {
        if (element == null) {
            return ""; //$NON-NLS-1$
        }

        if (element instanceof Integer) {
            int index = ((Integer) element).intValue();
            if (index >= 0 && index < values.length) {
                return values[index];
            }
			return ""; //$NON-NLS-1$
        }

        return ""; //$NON-NLS-1$
    }
}
