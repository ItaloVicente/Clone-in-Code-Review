package org.eclipse.ui.internal.dialogs;

import java.util.ArrayList;
import java.util.List;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.wizard.IWizardNode;
import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchWizard;
import org.eclipse.ui.activities.ITriggerPoint;
import org.eclipse.ui.activities.WorkbenchActivityHelper;
import org.eclipse.ui.dialogs.FilteredTree;
import org.eclipse.ui.internal.WorkbenchMessages;
import org.eclipse.ui.model.AdaptableList;
import org.eclipse.ui.model.WorkbenchLabelProvider;
import org.eclipse.ui.wizards.IWizardCategory;
import org.eclipse.ui.wizards.IWizardDescriptor;

public abstract class ImportExportPage extends WorkbenchWizardSelectionPage{
    protected static final String DIALOG_SETTING_SECTION_NAME = "ImportExportPage."; //$NON-NLS-1$
	
    private TreeViewer treeViewer;
    
	protected class CategorizedWizardSelectionTree {
		private final static int SIZING_LISTS_HEIGHT = 200;
		
		private IWizardCategory wizardCategories;
		private String message;
		private TreeViewer viewer;

		protected CategorizedWizardSelectionTree(IWizardCategory categories, String msg){
			this.wizardCategories = categories;
			this.message = msg;
		}
		
		protected Composite createControl(Composite parent){
	        Font font = parent.getFont();

	        Composite outerContainer = new Composite(parent, SWT.NONE);
	        outerContainer.setLayout(new GridLayout());
	        outerContainer.setLayoutData(new GridData(GridData.FILL_BOTH));
	        outerContainer.setFont(font);

	        Label messageLabel = new Label(outerContainer, SWT.NONE);
	        if (message != null) {
				messageLabel.setText(message);
			}
	        messageLabel.setFont(font);

	        createFilteredTree(outerContainer);
	        layoutTopControl(viewer.getControl());

	        return outerContainer;
		}
		
		private void createFilteredTree(Composite parent){        
			FilteredTree filteredTree = new FilteredTree(parent, SWT.SINGLE | SWT.H_SCROLL
	                | SWT.V_SCROLL | SWT.BORDER, new WizardPatternFilter(), true);
	        viewer = filteredTree.getViewer();
	        filteredTree.setFont(parent.getFont());
			filteredTree.setQuickSelectionMode(true);

	        viewer.setContentProvider(new WizardContentProvider());
			viewer.setLabelProvider(new WorkbenchLabelProvider());
	        viewer.setComparator(DataTransferWizardCollectionComparator.INSTANCE);
	        
	        ArrayList inputArray = new ArrayList();
	        boolean expandTop = false;

	        if (wizardCategories != null) {
	            if (wizardCategories.getParent() == null) {
	                IWizardCategory [] children = wizardCategories.getCategories();
	                for (int i = 0; i < children.length; i++) {
                		inputArray.add(children[i]);
	                }
	            } else {
	                expandTop = true;
	                inputArray.add(wizardCategories);
	            }
	        }

	        if (expandTop) {
				viewer.setAutoExpandLevel(2);
			}

	        AdaptableList input = new AdaptableList(inputArray);
	        
	        viewer.addFilter(new WizardActivityFilter());
	        
	        viewer.setInput(input);
		}

		protected TreeViewer getViewer(){
			return viewer;
		}

	    private void layoutTopControl(Control control) {
	        GridData data = new GridData(GridData.FILL_BOTH);

	        int availableRows = DialogUtil.availableRows(control.getParent());

	        if (availableRows > 50) {
	            data.heightHint = SIZING_LISTS_HEIGHT;
	        } else {
	            data.heightHint = availableRows * 3;
	        }

	        control.setLayoutData(data);
	    }
	}
	
	protected ImportExportPage(IWorkbench aWorkbench, IStructuredSelection currentSelection){
		super("importExportPage", aWorkbench, currentSelection, null, null);	//$NON-NLS-1$
		setTitle(WorkbenchMessages.Select);
	}
	
	@Override
	public void createControl(Composite parent) {
	    Font font = parent.getFont();
	
	    Composite outerContainer = new Composite(parent, SWT.NONE);
	    outerContainer.setLayout(new GridLayout());
	    outerContainer.setLayoutData(new GridData(GridData.FILL_BOTH));
	    outerContainer.setFont(font);

	    Composite comp = createTreeViewer(outerContainer);
	    
		Dialog.applyDialogFont(comp);
		
	    restoreWidgetValues();
	
	    setControl(outerContainer);	
	    
	   initialize();
	}

    protected abstract Composite createTreeViewer(Composite parent);
    
    protected void treeDoubleClicked(DoubleClickEvent event){
    	ISelection selection = event.getViewer().getSelection();
	    IStructuredSelection ss = (IStructuredSelection) selection;
    	listSelectionChanged(ss);
		
		Object element = ss.getFirstElement();
		TreeViewer v = (TreeViewer)event.getViewer();
		if (v.isExpandable(element)) {
		    v.setExpandedState(element, !v.getExpandedState(element));
		} else if (element instanceof WorkbenchWizardElement) {
			if (canFlipToNextPage()) {
				getContainer().showPage(getNextPage());
				return;
			}
		}    	
        getContainer().showPage(getNextPage());   			
    }
    
    private void updateSelectedNode(WorkbenchWizardElement wizardElement){
        setErrorMessage(null);
        if (wizardElement == null) {
        	updateMessage();
            setSelectedNode(null);
            return;
        }

        setSelectedNode(createWizardNode(wizardElement));
        setMessage(wizardElement.getDescription()); 
    }
    
    protected void updateMessage(){
    	TreeViewer viewer = getTreeViewer();
    	if (viewer != null){
    		ISelection selection = viewer.getSelection();
            IStructuredSelection ss = (IStructuredSelection) selection;
            Object sel = ss.getFirstElement();
            if (sel instanceof WorkbenchWizardElement){
               	updateSelectedNode((WorkbenchWizardElement)sel);
            }
            else{
            	setSelectedNode(null);
            }
    	} else {
			setMessage(null);
		}
    }
    
    protected void listSelectionChanged(ISelection selection){
        setErrorMessage(null);
        IStructuredSelection ss = (IStructuredSelection) selection;
        Object sel = ss.getFirstElement();
        if (sel instanceof WorkbenchWizardElement){
	        WorkbenchWizardElement currentWizardSelection = (WorkbenchWizardElement) sel;        
	        updateSelectedNode(currentWizardSelection);
        } else {
			updateSelectedNode(null);
		}
    }

	private IWizardNode createWizardNode(IWizardDescriptor element) {
        return new WorkbenchWizardNode(this, element) {
            @Override
			public IWorkbenchWizard createWizard() throws CoreException {
                return wizardElement.createWizard();
            }
        };
    }
    
    protected void restoreWidgetValues() {
        updateMessage();
    }

    protected void expandPreviouslyExpandedCategories(String setting, IWizardCategory wizardCategories, TreeViewer viewer) {
        String[] expandedCategoryPaths =  getDialogSettings()
                .getArray(setting);
        if (expandedCategoryPaths == null || expandedCategoryPaths.length == 0) {
			return;
		}

        List categoriesToExpand = new ArrayList(expandedCategoryPaths.length);

        if (wizardCategories != null) {
            for (int i = 0; i < expandedCategoryPaths.length; i++) {
                IWizardCategory category = wizardCategories
                        .findCategory(new Path(expandedCategoryPaths[i]));
                if (category != null) {
					categoriesToExpand.add(category);
				}
            }
        }

        if (!categoriesToExpand.isEmpty()) {
			viewer.setExpandedElements(categoriesToExpand.toArray());
		}

    }

    protected void selectPreviouslySelected(String setting, IWizardCategory wizardCategories, final TreeViewer viewer) {
        String selectedId = getDialogSettings().get(setting);
        if (selectedId == null) {
			return;
		}

        if (wizardCategories == null) {
			return;
		}

        Object selected = wizardCategories.findCategory(new Path(
                selectedId));

        if (selected == null) {
            selected = wizardCategories.findWizard(selectedId);

            if (selected == null) {
                return;
			}
        }

        viewer.setSelection(new StructuredSelection(selected), true);
    }
 
    protected void storeExpandedCategories(String setting, TreeViewer viewer) {
        Object[] expandedElements = viewer.getExpandedElements();
        List expandedElementPaths = new ArrayList(expandedElements.length);
        for (int i = 0; i < expandedElements.length; ++i) {
            if (expandedElements[i] instanceof IWizardCategory) {
				expandedElementPaths
                        .add(((IWizardCategory) expandedElements[i])
                                .getPath().toString());
			}
        }
        getDialogSettings().put(setting,
                (String[]) expandedElementPaths
                        .toArray(new String[expandedElementPaths.size()]));
    }

    protected void storeSelectedCategoryAndWizard(String setting, TreeViewer viewer) {
        Object selected = ((IStructuredSelection) viewer
                .getSelection()).getFirstElement();

        if (selected != null) {
            if (selected instanceof IWizardCategory) {
				getDialogSettings().put(setting,
                        ((IWizardCategory) selected).getPath()
                                .toString());
			} else {
            	getDialogSettings().put(setting,
                        ((IWizardDescriptor) selected).getId());
			}
        }
    }
    
    public void saveWidgetValues(){
    }
    
    @Override
	public IWizardPage getNextPage() { 
    	ITriggerPoint triggerPoint = getTriggerPoint();
        
        if (triggerPoint == null || WorkbenchActivityHelper.allowUseOf(triggerPoint, getSelectedNode())) {
			return super.getNextPage();
		}
        return null;
    }

    protected ITriggerPoint getTriggerPoint(){
    	return null;	// default implementation
    }
    
    protected void setTreeViewer(TreeViewer viewer){
    	treeViewer = viewer;
    }
    
    protected TreeViewer getTreeViewer(){
    	return treeViewer;
    }
    
    protected void initialize(){
    }
}
