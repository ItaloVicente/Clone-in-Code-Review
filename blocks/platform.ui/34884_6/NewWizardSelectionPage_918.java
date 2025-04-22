package org.eclipse.ui.internal.dialogs;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogSettings;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.jface.wizard.IWizard;
import org.eclipse.jface.wizard.IWizardContainer;
import org.eclipse.jface.wizard.IWizardContainer2;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.IWorkbenchWizard;
import org.eclipse.ui.activities.WorkbenchActivityHelper;
import org.eclipse.ui.dialogs.FilteredTree;
import org.eclipse.ui.internal.WorkbenchMessages;
import org.eclipse.ui.internal.registry.WizardsRegistryReader;
import org.eclipse.ui.model.AdaptableList;
import org.eclipse.ui.model.WorkbenchLabelProvider;
import org.eclipse.ui.wizards.IWizardCategory;
import org.eclipse.ui.wizards.IWizardDescriptor;

class NewWizardNewPage implements ISelectionChangedListener {

    private static final String DIALOG_SETTING_SECTION_NAME = "NewWizardSelectionPage."; //$NON-NLS-1$

    private final static int SIZING_LISTS_HEIGHT = 200;

    private final static int SIZING_VIEWER_WIDTH = 300;

    private final static String STORE_EXPANDED_CATEGORIES_ID = DIALOG_SETTING_SECTION_NAME
            + "STORE_EXPANDED_CATEGORIES_ID"; //$NON-NLS-1$

    private final static String STORE_SELECTED_ID = DIALOG_SETTING_SECTION_NAME
            + "STORE_SELECTED_ID"; //$NON-NLS-1$

    private NewWizardSelectionPage page;
    
    private FilteredTree filteredTree;
    
    private WizardPatternFilter filteredTreeFilter;

    private Hashtable selectedWizards = new Hashtable();

    private IDialogSettings settings;

    private Button showAllCheck;

    private IWizardCategory wizardCategories;

    private IWizardDescriptor [] primaryWizards;

    private CLabel descImageCanvas;

    private Map imageTable = new HashMap();

    private IWizardDescriptor selectedElement;

    private WizardActivityFilter filter = new WizardActivityFilter();

    private boolean needShowAll;

	private boolean projectsOnly;

	private ViewerFilter projectFilter = new WizardTagFilter(new String[] {WorkbenchWizardElement.TAG_PROJECT});

    public NewWizardNewPage(NewWizardSelectionPage mainPage,
			IWizardCategory wizardCategories,
			IWizardDescriptor[] primaryWizards, boolean projectsOnly) {
        this.page = mainPage;
        this.wizardCategories = wizardCategories;
        this.primaryWizards = primaryWizards;
        this.projectsOnly = projectsOnly;

        trimPrimaryWizards();

        if (this.primaryWizards.length > 0) {
            if (allPrimary(wizardCategories)) {
                this.wizardCategories = null; // dont bother considering the categories as all wizards are primary
                needShowAll = false;
            } else {
                needShowAll = !allActivityEnabled(wizardCategories);
            }
        } else {
            needShowAll = !allActivityEnabled(wizardCategories);
        }

		IWizard wizard = mainPage.getWizard();
		if (wizard instanceof NewWizard) {
			if (WizardsRegistryReader.FULL_EXAMPLES_WIZARD_CATEGORY.equals(((NewWizard) wizard)
					.getCategoryId())) {
				filter.setFilterPrimaryWizards(true);
			}
		}
    }

    private boolean allActivityEnabled(IWizardCategory category) {
        IWizardDescriptor [] wizards = category.getWizards();
        for (int i = 0; i < wizards.length; i++) {
            IWizardDescriptor wizard = wizards[i];
            if (WorkbenchActivityHelper.filterItem(wizard)) {
				return false;
			}
        }

        IWizardCategory [] children = category.getCategories();
        for (int i = 0; i < children.length; i++) {
            if (!allActivityEnabled(children[i])) {
				return false;
			}
        }

        return true;
    }

    private void trimPrimaryWizards() {
        ArrayList newPrimaryWizards = new ArrayList(primaryWizards.length);

        if (wizardCategories == null) {
			return;//No categories so nothing to trim
		}

        for (int i = 0; i < primaryWizards.length; i++) {
            if (wizardCategories.findWizard(primaryWizards[i].getId()) != null) {
				newPrimaryWizards.add(primaryWizards[i]);
			}
        }

        primaryWizards = (WorkbenchWizardElement[]) newPrimaryWizards
                .toArray(new WorkbenchWizardElement[newPrimaryWizards.size()]);
    }

    private boolean allPrimary(IWizardCategory category) {
        IWizardDescriptor [] wizards = category.getWizards();
        for (int i = 0; i < wizards.length; i++) {
        	IWizardDescriptor wizard = wizards[i];
            if (!isPrimary(wizard)) {
				return false;
			}
        }

        IWizardCategory [] children = category.getCategories();
        for (int i = 0; i < children.length; i++) {
            if (!allPrimary(children[i])) {
				return false;
			}
        }

        return true;
    }

    private boolean isPrimary(IWizardDescriptor wizard) {
        for (int j = 0; j < primaryWizards.length; j++) {
            if (primaryWizards[j].equals(wizard)) {
				return true;
			}
        }

        return false;
    }

    public void activate() {
        page.setDescription(WorkbenchMessages.NewWizardNewPage_description);
    }

    protected Control createControl(Composite parent) {

        Font wizardFont = parent.getFont();
        Composite outerContainer = new Composite(parent, SWT.NONE);
        GridLayout layout = new GridLayout();
        outerContainer.setLayout(layout);

        Label wizardLabel = new Label(outerContainer, SWT.NONE);
        GridData data = new GridData(SWT.BEGINNING, SWT.FILL, false, true);
        outerContainer.setLayoutData(data);
        wizardLabel.setFont(wizardFont);
        wizardLabel.setText(WorkbenchMessages.NewWizardNewPage_wizardsLabel);    

        Composite innerContainer = new Composite(outerContainer, SWT.NONE);
        layout = new GridLayout(2, false);
        layout.marginHeight = 0;
        layout.marginWidth = 0;
        innerContainer.setLayout(layout);
        innerContainer.setFont(wizardFont);
        data = new GridData(SWT.FILL, SWT.FILL, true, true);	
        innerContainer.setLayoutData(data);

        filteredTree = createFilteredTree(innerContainer);
        createOptionsButtons(innerContainer);
        
        createImage(innerContainer);

        updateDescription(null);

        restoreWidgetValues();

        return outerContainer;
    }

    protected FilteredTree createFilteredTree(Composite parent){
        Composite composite = new Composite(parent, SWT.NONE);
        GridLayout layout = new GridLayout();
        layout.marginHeight = 0;
        layout.marginWidth = 0;
        composite.setLayout(layout);
        
        GridData data = new GridData(SWT.FILL, SWT.FILL, true, true);
        data.widthHint = SIZING_VIEWER_WIDTH;
        data.horizontalSpan = 2;	 
        data.grabExcessHorizontalSpace = true;
        data.grabExcessVerticalSpace = true;

        boolean needsHint = DialogUtil.inRegularFontMode(parent);

        if (needsHint) {
            data.heightHint = SIZING_LISTS_HEIGHT;
        }
        composite.setLayoutData(data);

        filteredTreeFilter = new WizardPatternFilter();
    	FilteredTree filterTree = new FilteredTree(composite, 
    			SWT.SINGLE | SWT.H_SCROLL | SWT.V_SCROLL | SWT.BORDER, filteredTreeFilter, true);
		filterTree.setQuickSelectionMode(true);
  	
		final TreeViewer treeViewer = filterTree.getViewer();
		treeViewer.setContentProvider(new WizardContentProvider());
		treeViewer.setLabelProvider(new WorkbenchLabelProvider());
		treeViewer.setComparator(NewWizardCollectionComparator.INSTANCE);
		treeViewer.addSelectionChangedListener(this);

        ArrayList inputArray = new ArrayList();

        for (int i = 0; i < primaryWizards.length; i++) {
            inputArray.add(primaryWizards[i]);
        }

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
			treeViewer.setAutoExpandLevel(2);
		}

        AdaptableList input = new AdaptableList(inputArray);

        treeViewer.setInput(input);

		filterTree.setBackground(parent.getDisplay().getSystemColor(
				SWT.COLOR_WIDGET_BACKGROUND));

        treeViewer.getTree().setFont(parent.getFont());

        treeViewer.addDoubleClickListener(new IDoubleClickListener() {
            @Override
			public void doubleClick(DoubleClickEvent event) {
            	    IStructuredSelection s = (IStructuredSelection) event
						.getSelection();
				selectionChanged(new SelectionChangedEvent(event.getViewer(), s));
				
				Object element = s.getFirstElement();
                if (treeViewer.isExpandable(element)) {
                	treeViewer.setExpandedState(element, !treeViewer
                            .getExpandedState(element));
                } else if (element instanceof WorkbenchWizardElement) {
                    page.advanceToNextPageOrFinish();
                }
            }
        });
        
        treeViewer.addFilter(filter);
        
        if (projectsOnly) {
			treeViewer.addFilter(projectFilter);
		}

		Dialog.applyDialogFont(filterTree);
		return filterTree;
    }
    
    private void createOptionsButtons(Composite parent){
        if (needShowAll) {
            showAllCheck = new Button(parent, SWT.CHECK);
            GridData data = new GridData();
            showAllCheck.setLayoutData(data);
            showAllCheck.setFont(parent.getFont());
            showAllCheck.setText(WorkbenchMessages.NewWizardNewPage_showAll); 
            showAllCheck.setSelection(false);

            showAllCheck.addSelectionListener(new SelectionAdapter() {

                private Object[] delta = new Object[0];

                @Override
				public void widgetSelected(SelectionEvent e) {
                    boolean showAll = showAllCheck.getSelection();

                    if (showAll) {
                    	filteredTree.getViewer().getControl().setRedraw(false);
                    } else {
                        delta = filteredTree.getViewer().getExpandedElements();
                    }

                    try {
                        if (showAll) {
                        	filteredTree.getViewer().resetFilters();
                        	filteredTree.getViewer().addFilter(filteredTreeFilter);
                            if (projectsOnly) {
								filteredTree.getViewer().addFilter(projectFilter);
							}

                            Object[] currentExpanded = filteredTree.getViewer()
                                    .getExpandedElements();
                            Object[] expanded = new Object[delta.length
                                    + currentExpanded.length];
                            System.arraycopy(currentExpanded, 0, expanded, 0,
                                    currentExpanded.length);
                            System.arraycopy(delta, 0, expanded,
                                    currentExpanded.length, delta.length);
                            filteredTree.getViewer().setExpandedElements(expanded);
                        } else {
                        	filteredTree.getViewer().addFilter(filter);
                            if (projectsOnly) {
								filteredTree.getViewer().addFilter(projectFilter);
							}
                        }
                        filteredTree.getViewer().refresh(false);

                        if (!showAll) {
                            Object[] newExpanded = filteredTree.getViewer().getExpandedElements();
                            List deltaList = new ArrayList(Arrays.asList(delta));
                            deltaList.removeAll(Arrays.asList(newExpanded));
                        }
                    } finally {
                        if (showAll) {
							filteredTree.getViewer().getControl().setRedraw(true);
						}
                    }
                }
            });
        }
    }
    
    private void createImage(Composite parent) {
        descImageCanvas = new CLabel(parent, SWT.NONE);
        GridData data = new GridData(GridData.HORIZONTAL_ALIGN_BEGINNING
                | GridData.VERTICAL_ALIGN_BEGINNING);
        data.widthHint = 0;
        data.heightHint = 0;
        descImageCanvas.setLayoutData(data);

        descImageCanvas.addDisposeListener(new DisposeListener() {

            @Override
			public void widgetDisposed(DisposeEvent e) {
                for (Iterator i = imageTable.values().iterator(); i.hasNext();) {
                    ((Image) i.next()).dispose();
                }
                imageTable.clear();
            }
        });
    }

    protected void expandPreviouslyExpandedCategories() {
        String[] expandedCategoryPaths = settings
                .getArray(STORE_EXPANDED_CATEGORIES_ID);
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
			filteredTree.getViewer().setExpandedElements(categoriesToExpand.toArray());
		}

    }

    protected Object getSingleSelection(IStructuredSelection selection) {
        return selection.size() == 1 ? selection.getFirstElement() : null;
    }

    protected void restoreWidgetValues() {
        expandPreviouslyExpandedCategories();
        selectPreviouslySelected();
    }

    public void saveWidgetValues() {
        storeExpandedCategories();
        storeSelectedCategoryAndWizard();
    }

    @Override
	public void selectionChanged(SelectionChangedEvent selectionEvent) {
        page.setErrorMessage(null);
        page.setMessage(null);

        Object selectedObject = getSingleSelection((IStructuredSelection) selectionEvent
                .getSelection());

        if (selectedObject instanceof IWizardDescriptor) {
            if (selectedObject == selectedElement) {
				return;
			}
            updateWizardSelection((IWizardDescriptor) selectedObject);
        } else {
            selectedElement = null;
            page.setHasPages(false);
            page.setCanFinishEarly(false);
            page.selectWizardNode(null);
            updateDescription(null);
        }
    }

    protected void selectPreviouslySelected() {
        String selectedId = settings.get(STORE_SELECTED_ID);
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

        final StructuredSelection selection = new StructuredSelection(selected);
        filteredTree.getViewer().getControl().getDisplay().asyncExec(new Runnable() {
            @Override
			public void run() {
            	filteredTree.getViewer().setSelection(selection, true);
            }
        });
    }

    public void setDialogSettings(IDialogSettings settings) {
        this.settings = settings;
    }

    protected void storeExpandedCategories() {
        Object[] expandedElements = filteredTree.getViewer().getExpandedElements();
        List expandedElementPaths = new ArrayList(expandedElements.length);
        for (int i = 0; i < expandedElements.length; ++i) {
            if (expandedElements[i] instanceof IWizardCategory) {
				expandedElementPaths
                        .add(((IWizardCategory) expandedElements[i])
                                .getPath().toString());
			}
        }
        settings.put(STORE_EXPANDED_CATEGORIES_ID,
                (String[]) expandedElementPaths
                        .toArray(new String[expandedElementPaths.size()]));
    }

    protected void storeSelectedCategoryAndWizard() {
        Object selected = getSingleSelection((IStructuredSelection) filteredTree
        		.getViewer().getSelection());

        if (selected != null) {
            if (selected instanceof IWizardCategory) {
				settings.put(STORE_SELECTED_ID,
                        ((IWizardCategory) selected).getPath()
                                .toString());
			} else {
                settings.put(STORE_SELECTED_ID,
                        ((IWizardDescriptor) selected).getId());
			}
        }
    }

    private void updateDescription(IWizardDescriptor selectedObject) {
        String string = ""; //$NON-NLS-1$
        if (selectedObject != null) {
			string = selectedObject.getDescription();
		}

        page.setDescription(string);

        if (hasImage(selectedObject)) {
            ImageDescriptor descriptor = null;
            if (selectedObject != null) {
                descriptor = selectedObject.getDescriptionImage();
            }

            if (descriptor != null) {
            	GridData data = (GridData)descImageCanvas.getLayoutData();
            	data.widthHint = SWT.DEFAULT;
            	data.heightHint = SWT.DEFAULT;
                Image image = (Image) imageTable.get(descriptor);
                if (image == null) {
                    image = descriptor.createImage(false);
                    imageTable.put(descriptor, image);
                }
                descImageCanvas.setImage(image);
            }
        } else {
        	GridData data = (GridData)descImageCanvas.getLayoutData();
        	data.widthHint = 0;
        	data.heightHint = 0;
            descImageCanvas.setImage(null);
        }

        descImageCanvas.getParent().layout(true);
        filteredTree.getViewer().getTree().showSelection();

        IWizardContainer container = page.getWizard().getContainer();
        if (container instanceof IWizardContainer2) {
            ((IWizardContainer2) container).updateSize();
        }
    }

    private boolean hasImage(IWizardDescriptor selectedObject) {
        if (selectedObject == null) {
			return false;
		}

        if (selectedObject.getDescriptionImage() != null) {
			return true;
		}

        return false;
    }

    private void updateWizardSelection(IWizardDescriptor selectedObject) {
        selectedElement = selectedObject;
        WorkbenchWizardNode selectedNode;
        if (selectedWizards.containsKey(selectedObject)) {
            selectedNode = (WorkbenchWizardNode) selectedWizards
                    .get(selectedObject);
        } else {
            selectedNode = new WorkbenchWizardNode(page, selectedObject) {
                @Override
				public IWorkbenchWizard createWizard() throws CoreException {
                    return wizardElement.createWizard();
                }
            };
            selectedWizards.put(selectedObject, selectedNode);
        }

        page.setCanFinishEarly(selectedObject.canFinishEarly());
        page.setHasPages(selectedObject.hasPages());
        page.selectWizardNode(selectedNode);

        updateDescription(selectedObject);
    }
}
