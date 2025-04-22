package org.eclipse.ui.dialogs;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.CheckboxTableViewer;
import org.eclipse.jface.viewers.ViewerComparator;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IFileEditorMapping;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.internal.IWorkbenchHelpContextIds;
import org.eclipse.ui.internal.WorkbenchMessages;
import org.eclipse.ui.internal.registry.EditorRegistry;

public class TypeFilteringDialog extends SelectionDialog {
    Button addTypesButton;

    Collection initialSelections;

    CheckboxTableViewer listViewer;

    private final static int SIZING_SELECTION_WIDGET_HEIGHT = 250;

    private final static int SIZING_SELECTION_WIDGET_WIDTH = 300;

    private final static String TYPE_DELIMITER = WorkbenchMessages.TypesFiltering_typeDelimiter; 

    private String filterTitle = WorkbenchMessages.TypesFiltering_otherExtensions; 

    Text userDefinedText;

    IFileEditorMapping[] currentInput;

    public TypeFilteringDialog(Shell parentShell, Collection preselections) {
        super(parentShell);
        setTitle(WorkbenchMessages.TypesFiltering_title); 
        this.initialSelections = preselections;
        setMessage(WorkbenchMessages.TypesFiltering_message); 
		setShellStyle(getShellStyle() | SWT.SHEET);
    }

    public TypeFilteringDialog(Shell parentShell, Collection preselections,
            String filterText) {
        this(parentShell, preselections);
        this.filterTitle = filterText;
    }

    private void addSelectionButtons(Composite composite) {
        Composite buttonComposite = new Composite(composite, SWT.RIGHT);
        GridLayout layout = new GridLayout();
        layout.numColumns = 2;
        buttonComposite.setLayout(layout);
        GridData data = new GridData(GridData.HORIZONTAL_ALIGN_END
                | GridData.GRAB_HORIZONTAL);
        data.grabExcessHorizontalSpace = true;
        composite.setData(data);
        Button selectButton = createButton(buttonComposite,
                IDialogConstants.SELECT_ALL_ID, WorkbenchMessages.WizardTransferPage_selectAll, false);
        SelectionListener listener = new SelectionAdapter() {
            @Override
			public void widgetSelected(SelectionEvent e) {
                listViewer.setAllChecked(true);
            }
        };
        selectButton.addSelectionListener(listener);
        Button deselectButton = createButton(buttonComposite,
                IDialogConstants.DESELECT_ALL_ID, WorkbenchMessages.WizardTransferPage_deselectAll, false); 
        listener = new SelectionAdapter() {
            @Override
			public void widgetSelected(SelectionEvent e) {
                listViewer.setAllChecked(false);
            }
        };
        deselectButton.addSelectionListener(listener);
    }

    private void addUserDefinedEntries(List result) {
        StringTokenizer tokenizer = new StringTokenizer(userDefinedText
                .getText(), TYPE_DELIMITER);
        while (tokenizer.hasMoreTokens()) {
            String currentExtension = tokenizer.nextToken().trim();
            if (!currentExtension.equals("")) { //$NON-NLS-1$
                if (currentExtension.startsWith("*.")) { //$NON-NLS-1$
					result.add(currentExtension.substring(2));
				} else {
                    if (currentExtension.startsWith(".")) { //$NON-NLS-1$
						result.add(currentExtension.substring(1));
					} else {
						result.add(currentExtension);
					}
                }
            }
        }
    }

    private void checkInitialSelections() {
        IFileEditorMapping editorMappings[] = ((EditorRegistry) PlatformUI
				.getWorkbench().getEditorRegistry()).getUnifiedMappings();
        ArrayList selectedMappings = new ArrayList();
        for (int i = 0; i < editorMappings.length; i++) {
            IFileEditorMapping mapping = editorMappings[i];
            if (this.initialSelections.contains(mapping.getExtension())) {
                listViewer.setChecked(mapping, true);
                selectedMappings.add(mapping.getExtension());
            } else {
                if (this.initialSelections.contains(mapping.getLabel())) {
                    listViewer.setChecked(mapping, true);
                    selectedMappings.add(mapping.getLabel());
                }
            }
        }
        Iterator initialIterator = this.initialSelections.iterator();
        StringBuffer entries = new StringBuffer();
        while (initialIterator.hasNext()) {
            String nextExtension = (String) initialIterator.next();
            if (!selectedMappings.contains(nextExtension)) {
            	if (entries.length() != 0) {
					entries.append(',');
            	}            		
                entries.append(nextExtension);
            }
        }
        this.userDefinedText.setText(entries.toString());
    }

    @Override
	protected void configureShell(Shell shell) {
        super.configureShell(shell);
        PlatformUI.getWorkbench().getHelpSystem().setHelp(shell,
				IWorkbenchHelpContextIds.TYPE_FILTERING_DIALOG);
    }

    @Override
	protected Control createDialogArea(Composite parent) {
        Composite composite = (Composite) super.createDialogArea(parent);
        createMessageArea(composite);
        listViewer = CheckboxTableViewer.newCheckList(composite, SWT.BORDER);
        GridData data = new GridData(GridData.FILL_BOTH);
        data.heightHint = SIZING_SELECTION_WIDGET_HEIGHT;
        data.widthHint = SIZING_SELECTION_WIDGET_WIDTH;
        listViewer.getTable().setLayoutData(data);
        listViewer.getTable().setFont(parent.getFont());
        listViewer.setLabelProvider(FileEditorMappingLabelProvider.INSTANCE);
		listViewer.setContentProvider(ArrayContentProvider.getInstance());
        listViewer.setComparator(new ViewerComparator());
        addSelectionButtons(composite);
        createUserEntryGroup(composite);
        initializeViewer();
        if (this.initialSelections != null && !this.initialSelections.isEmpty()) {
			checkInitialSelections();
		}
        return composite;
    }

    private void createUserEntryGroup(Composite parent) {
        Font font = parent.getFont();
        Composite userDefinedGroup = new Composite(parent, SWT.NONE);
        GridLayout layout = new GridLayout();
        layout.numColumns = 2;
        userDefinedGroup.setLayout(layout);
        userDefinedGroup.setLayoutData(new GridData(
                GridData.HORIZONTAL_ALIGN_FILL | GridData.VERTICAL_ALIGN_FILL));
        Label fTitle = new Label(userDefinedGroup, SWT.NONE);
        fTitle.setFont(font);
        fTitle.setText(filterTitle);
        userDefinedText = new Text(userDefinedGroup, SWT.SINGLE | SWT.BORDER);
        userDefinedText.setFont(font);
        GridData data = new GridData(GridData.HORIZONTAL_ALIGN_FILL
                | GridData.GRAB_HORIZONTAL);
        userDefinedText.setLayoutData(data);
    }

    private IFileEditorMapping[] getInput() {
        if (currentInput == null) {
            List wildcardEditors = new ArrayList();
            IFileEditorMapping[] allMappings = ((EditorRegistry)PlatformUI.getWorkbench()
                    .getEditorRegistry()).getUnifiedMappings();
            for (int i = 0; i < allMappings.length; i++) {
                if (allMappings[i].getName().equals("*")) { //$NON-NLS-1$
					wildcardEditors.add(allMappings[i]);
				}
            }
            currentInput = new IFileEditorMapping[wildcardEditors.size()];
            wildcardEditors.toArray(currentInput);
        }
        return currentInput;
    }

    private void initializeViewer() {
        listViewer.setInput(getInput());
    }

    @Override
	protected void okPressed() {
        IFileEditorMapping[] children = getInput();
        List list = new ArrayList();
        for (int i = 0; i < children.length; ++i) {
            IFileEditorMapping element = children[i];
            if (listViewer.getChecked(element)) {
				list.add(element.getExtension());
			}
        }
        addUserDefinedEntries(list);
        setResult(list);
        super.okPressed();
    }
}
