package org.eclipse.ui.dialogs;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;

import org.eclipse.core.runtime.IStatus;

import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.viewers.ILabelProvider;


public class TwoPaneElementSelector extends AbstractElementListSelectionDialog {
    private String fUpperListLabel;

    private String fLowerListLabel;
    
    private Comparator fLowerListComparator = null;

    private ILabelProvider fQualifierRenderer;

    private Object[] fElements = new Object[0];

    private Table fLowerList;

    private Object[] fQualifierElements;

    public TwoPaneElementSelector(Shell parent, ILabelProvider elementRenderer,
            ILabelProvider qualifierRenderer) {
        super(parent, elementRenderer);
        setSize(50, 15);
        setAllowDuplicates(false);
        fQualifierRenderer = qualifierRenderer;
    }

    public void setUpperListLabel(String label) {
        fUpperListLabel = label;
    }

    public void setLowerListLabel(String label) {
        fLowerListLabel = label;
    }

    public void setLowerListComparator(Comparator comparator) {
    	fLowerListComparator = comparator;
    }

    public void setElements(Object[] elements) {
        fElements = elements;
    }

    @Override
	public Control createDialogArea(Composite parent) {
        Composite contents = (Composite) super.createDialogArea(parent);
        createMessageArea(contents);
        createFilterText(contents);
        createLabel(contents, fUpperListLabel);
        createFilteredList(contents);
        createLabel(contents, fLowerListLabel);
        createLowerList(contents);
        setListElements(fElements);
        List initialSelections = getInitialElementSelections();
        if (!initialSelections.isEmpty()) {
            Object element = initialSelections.get(0);
            setSelection(new Object[] { element });
            setLowerSelectedElement(element);
        }
        return contents;
    }

    protected Label createLabel(Composite parent, String name) {
        if (name == null) {
			return null;
		}
        Label label = new Label(parent, SWT.NONE);
        label.setText(name);
        label.setFont(parent.getFont());
        return label;
    }

    protected Table createLowerList(Composite parent) {
        Table list = new Table(parent, SWT.BORDER | SWT.V_SCROLL | SWT.H_SCROLL);
        list.addListener(SWT.Selection, new Listener() {
            @Override
			public void handleEvent(Event evt) {
                handleLowerSelectionChanged();
            }
        });
        list.addListener(SWT.MouseDoubleClick, new Listener() {
            @Override
			public void handleEvent(Event evt) {
                handleDefaultSelected();
            }
        });
        list.addDisposeListener(new DisposeListener() {
            @Override
			public void widgetDisposed(DisposeEvent e) {
                fQualifierRenderer.dispose();
            }
        });
        GridData data = new GridData();
        data.widthHint = convertWidthInCharsToPixels(50);
        data.heightHint = convertHeightInCharsToPixels(5);
        data.grabExcessVerticalSpace = true;
        data.grabExcessHorizontalSpace = true;
        data.horizontalAlignment = GridData.FILL;
        data.verticalAlignment = GridData.FILL;
        list.setLayoutData(data);
        list.setFont(parent.getFont());
        fLowerList = list;
        return list;
    }

    @Override
	protected void computeResult() {
        Object[] results = new Object[] { getLowerSelectedElement() };
        setResult(Arrays.asList(results));
    }

    @Override
	protected void handleDefaultSelected() {
        if (validateCurrentSelection() && (getLowerSelectedElement() != null)) {
			buttonPressed(IDialogConstants.OK_ID);
		}
    }

    @Override
	protected void handleSelectionChanged() {
        handleUpperSelectionChanged();
    }

    private void handleUpperSelectionChanged() {
        int indices[] = getSelectionIndices();
        fLowerList.removeAll();
		int elementCount = 0;
		List elements= new ArrayList(indices.length * 5);
        for (int i= 0; i < indices.length; i++) {
        	Object[] foldedElements= getFoldedElements(indices[i]);
			if (foldedElements != null) {
				elementCount = elementCount + foldedElements.length;
				elements.add(getFoldedElements(indices[i]));
			}
		}
		if (elementCount > 0) {
			fQualifierElements = new Object[elementCount];
			int destPos = 0;
			Iterator iterator = elements.iterator();
			while (iterator.hasNext()) {
				Object[] objects= (Object[])iterator.next();
				System.arraycopy(objects, 0, fQualifierElements, destPos, objects.length);
				destPos = destPos + objects.length;
			}
			updateLowerListWidget(fQualifierElements);
		} else {
			fQualifierElements = null;
			updateLowerListWidget(new Object[] {});
		}

        validateCurrentSelection();
    }

    private void handleLowerSelectionChanged() {
        validateCurrentSelection();
    }

    protected void setLowerSelectedElement(Object element) {
        if (fQualifierElements == null) {
			return;
		}
        int i;
        for (i = 0; i != fQualifierElements.length; i++) {
			if (fQualifierElements[i].equals(element)) {
				break;
			}
		}
        if (i != fQualifierElements.length) {
			fLowerList.setSelection(i);
		}
    }

    protected Object getLowerSelectedElement() {
        int index = fLowerList.getSelectionIndex();
        if (index >= 0) {
			return fQualifierElements[index];
		}
        return null;
    }

    private void updateLowerListWidget(Object[] elements) {
        int length = elements.length;
        String[] qualifiers = new String[length];
        for (int i = 0; i != length; i++){
        	String text = fQualifierRenderer.getText(elements[i]);
        	if(text == null) {
				text = ""; //$NON-NLS-1$
			}
            qualifiers[i] = text;
        }
        
        TwoArrayQuickSorter sorter;
        if (fLowerListComparator == null) {
        	sorter = new TwoArrayQuickSorter(isCaseIgnored());
        } else {
        	sorter = new TwoArrayQuickSorter(fLowerListComparator);
        }
        
        sorter.sort(qualifiers, elements);
        for (int i = 0; i != length; i++) {
            TableItem item = new TableItem(fLowerList, SWT.NONE);
            item.setText(qualifiers[i]);
            item.setImage(fQualifierRenderer.getImage(elements[i]));
        }
        if (fLowerList.getItemCount() > 0) {
			fLowerList.setSelection(0);
		}
    }

    @Override
	protected void handleEmptyList() {
        super.handleEmptyList();
        fLowerList.setEnabled(false);
    }
    
    @Override
	protected boolean validateCurrentSelection() {
    	ISelectionStatusValidator validator = getValidator();
    	Object lowerSelection = getLowerSelectedElement();
    	
    	if (validator != null && lowerSelection != null) {
    		IStatus status = validator.validate(new Object [] {lowerSelection});
    		updateStatus(status);
    		return status.isOK();
    	}
        return super.validateCurrentSelection();
     }
}
