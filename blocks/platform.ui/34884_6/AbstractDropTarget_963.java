package org.eclipse.ui.internal.dialogs;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.jface.resource.LocalResourceManager;
import org.eclipse.jface.resource.ResourceManager;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.internal.IWorkbenchGraphicConstants;
import org.eclipse.ui.internal.IWorkbenchHelpContextIds;
import org.eclipse.ui.internal.WorkbenchImages;
import org.eclipse.ui.internal.WorkbenchMessages;
import org.eclipse.ui.internal.WorkbenchPlugin;
import org.eclipse.ui.internal.registry.WorkingSetDescriptor;

public class WorkingSetTypePage extends WizardPage {
    private final static int SIZING_SELECTION_WIDGET_WIDTH = 50;

    private final static int SIZING_SELECTION_WIDGET_HEIGHT = 200;

    private TableViewer typesListViewer;

	private WorkingSetDescriptor[] descriptors;

    public WorkingSetTypePage() {
        this(WorkbenchPlugin.getDefault().getWorkingSetRegistry().getNewPageWorkingSetDescriptors());
    }

	public WorkingSetTypePage(WorkingSetDescriptor[] descriptors) {
		super(
				"workingSetTypeSelectionPage", WorkbenchMessages.WorkingSetTypePage_description, WorkbenchImages.getImageDescriptor(IWorkbenchGraphicConstants.IMG_WIZBAN_WORKINGSET_WIZ)); //$NON-NLS-1$ 
		this.descriptors = descriptors;
	}

    @Override
	public boolean canFlipToNextPage() {
        return isPageComplete();
    }

    @Override
	public void createControl(Composite parent) {
        Font font = parent.getFont();
        Composite composite = new Composite(parent, SWT.NULL);
        composite.setLayout(new GridLayout());
        composite.setLayoutData(new GridData(GridData.HORIZONTAL_ALIGN_FILL));
        setControl(composite);

        PlatformUI.getWorkbench().getHelpSystem().setHelp(composite,
				IWorkbenchHelpContextIds.WORKING_SET_TYPE_PAGE);
        Label typesLabel = new Label(composite, SWT.NONE);
        typesLabel.setText(WorkbenchMessages.WorkingSetTypePage_typesLabel);
        GridData data = new GridData(GridData.FILL_HORIZONTAL);
        typesLabel.setLayoutData(data);
        typesLabel.setFont(font);

        typesListViewer = new TableViewer(composite, SWT.BORDER | SWT.SINGLE);
        data = new GridData(GridData.FILL_BOTH);
        data.heightHint = SIZING_SELECTION_WIDGET_HEIGHT;
        data.widthHint = SIZING_SELECTION_WIDGET_WIDTH;
        typesListViewer.getTable().setLayoutData(data);
        typesListViewer.getTable().setFont(font);
        typesListViewer
                .addSelectionChangedListener(new ISelectionChangedListener() {
                    @Override
					public void selectionChanged(SelectionChangedEvent event) {
                        handleSelectionChanged();
                    }
                });
        typesListViewer.addDoubleClickListener(new IDoubleClickListener() {
            @Override
			public void doubleClick(DoubleClickEvent event) {
                handleDoubleClick();
            }
        });
        typesListViewer.setContentProvider(new ArrayContentProvider());
        typesListViewer.setLabelProvider(new LabelProvider() {
        	private ResourceManager images = new LocalResourceManager(
					JFaceResources.getResources());
			@Override
			public String getText(Object element) {
				return ((WorkingSetDescriptor)element).getName();
			}
			
			@Override
			public void dispose() {
				images.dispose();
				super.dispose();
			}

			@Override
			public Image getImage(Object element) {
				ImageDescriptor imageDescriptor = ((WorkingSetDescriptor) element)
						.getIcon();
				return imageDescriptor == null ? null : (Image) images
						.get(imageDescriptor);
			}
		});
        typesListViewer.setInput(descriptors);
        setPageComplete(false);
    }

    @Override
	public void dispose() {
        super.dispose();
    }

    public String getSelection() {
        WorkingSetDescriptor descriptor = getSelectedWorkingSet();
        if (descriptor != null)
        	return descriptor.getId();
        
        return null;
    }

	private WorkingSetDescriptor getSelectedWorkingSet() {
		ISelection selection = typesListViewer.getSelection();
        boolean hasSelection = selection != null
                && selection.isEmpty() == false;

        WorkingSetDescriptor descriptor = null;
        if (hasSelection && selection instanceof IStructuredSelection) {
            descriptor = (WorkingSetDescriptor) ((IStructuredSelection) selection)
                    .getFirstElement();
        }
		return descriptor;
	}

    private void handleDoubleClick() {
        handleSelectionChanged();
        getContainer().showPage(getNextPage());
    }

    private void handleSelectionChanged() {
        ISelection selection = typesListViewer.getSelection();
        boolean hasSelection = selection != null
                && selection.isEmpty() == false;

        WorkingSetDescriptor descriptor = getSelectedWorkingSet();
		setDescription(descriptor == null ? "" : descriptor.getDescription()); //$NON-NLS-1$
        
        setPageComplete(hasSelection);
    }
}
