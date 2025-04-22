package org.eclipse.ui.internal.dialogs;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.content.IContentType;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.preference.PreferencePage;
import org.eclipse.jface.window.Window;
import org.eclipse.osgi.util.NLS;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.ui.IEditorDescriptor;
import org.eclipse.ui.IFileEditorMapping;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;
import org.eclipse.ui.dialogs.EditorSelectionDialog;
import org.eclipse.ui.dialogs.PreferenceLinkArea;
import org.eclipse.ui.internal.IWorkbenchHelpContextIds;
import org.eclipse.ui.internal.WorkbenchMessages;
import org.eclipse.ui.internal.WorkbenchPlugin;
import org.eclipse.ui.internal.registry.EditorDescriptor;
import org.eclipse.ui.internal.registry.EditorRegistry;
import org.eclipse.ui.internal.registry.FileEditorMapping;
import org.eclipse.ui.internal.util.PrefUtil;
import org.eclipse.ui.preferences.IWorkbenchPreferenceContainer;

public class FileEditorsPreferencePage extends PreferencePage implements
        IWorkbenchPreferencePage, Listener {
	
    private static final String DATA_EDITOR = "editor"; //$NON-NLS-1$

    private static final String DATA_FROM_CONTENT_TYPE = "type"; //$NON-NLS-1$
    
	protected Table resourceTypeTable;

    protected Button addResourceTypeButton;

    protected Button removeResourceTypeButton;

    protected Table editorTable;

    protected Button addEditorButton;

    protected Button removeEditorButton;

    protected Button defaultEditorButton;

    protected Label editorLabel;

    protected IWorkbench workbench;

    protected List imagesToDispose;

    protected Map editorsToImages;

    public void addResourceType(String newName, String newExtension) {
        Assert.isTrue((newName != null && newName.length() != 0)
                || (newExtension != null && newExtension.length() != 0));

        int index = newName.indexOf('*');
        if (index > -1) {
            Assert.isTrue(index == 0 && newName.length() == 1);
            Assert.isTrue(newExtension != null && newExtension.length() != 0);
        }

        String newFilename = (newName + (newExtension == null
                || newExtension.length() == 0 ? "" : "." + newExtension)).toUpperCase();//$NON-NLS-1$ //$NON-NLS-2$
        IFileEditorMapping resourceType;
        TableItem[] items = resourceTypeTable.getItems();
        boolean found = false;
        int i = 0;

        while (i < items.length && !found) {
            resourceType = (IFileEditorMapping) items[i].getData();
            int result = newFilename.compareToIgnoreCase(resourceType
                    .getLabel());
            if (result == 0) {
                MessageDialog
                        .openInformation(
                                getControl().getShell(),
                                WorkbenchMessages.FileEditorPreference_existsTitle,
                                WorkbenchMessages.FileEditorPreference_existsMessage);
                return;
            }

            if (result < 0) {
				found = true;
			} else {
				i++;
			}
        }

        resourceType = new FileEditorMapping(newName, newExtension);
        TableItem item = newResourceTableItem(resourceType, i, true);
        resourceTypeTable.setFocus();
        resourceTypeTable.showItem(item);
        fillEditorTable();
    }

    @Override
	protected Control createContents(Composite parent) {
        imagesToDispose = new ArrayList();
        editorsToImages = new HashMap(50);

        Composite pageComponent = new Composite(parent, SWT.NULL);
        GridLayout layout = new GridLayout();
        layout.numColumns = 2;
        layout.marginWidth = 0;
        layout.marginHeight = 0;
        pageComponent.setLayout(layout);
        GridData data = new GridData();
        data.verticalAlignment = GridData.FILL;
        data.horizontalAlignment = GridData.FILL;
        pageComponent.setLayoutData(data);


        PreferenceLinkArea contentTypeArea = new PreferenceLinkArea(pageComponent, SWT.NONE,
                "org.eclipse.ui.preferencePages.ContentTypes", WorkbenchMessages.FileEditorPreference_contentTypesRelatedLink,//$NON-NLS-1$
                (IWorkbenchPreferenceContainer) getContainer(),null);
        
        data = new GridData(GridData.FILL_HORIZONTAL | GridData.GRAB_HORIZONTAL);
        contentTypeArea.getControl().setLayoutData(data);

        Label label = new Label(pageComponent, SWT.LEFT);
        label.setText(WorkbenchMessages.FileEditorPreference_fileTypes); 
        data = new GridData();
        data.horizontalAlignment = GridData.FILL;
        data.horizontalSpan = 2;
        label.setLayoutData(data);

        resourceTypeTable = new Table(pageComponent, SWT.MULTI | SWT.BORDER
                | SWT.FULL_SELECTION);
        resourceTypeTable.addListener(SWT.Selection, this);
        resourceTypeTable.addListener(SWT.DefaultSelection, this);
        data = new GridData(GridData.FILL_HORIZONTAL);

        int availableRows = DialogUtil.availableRows(pageComponent);

        data.heightHint = resourceTypeTable.getItemHeight()
        resourceTypeTable.setLayoutData(data);

        Composite groupComponent = new Composite(pageComponent, SWT.NULL);
        GridLayout groupLayout = new GridLayout();
        groupLayout.marginWidth = 0;
        groupLayout.marginHeight = 0;
        groupComponent.setLayout(groupLayout);
        data = new GridData();
        data.verticalAlignment = GridData.FILL;
        data.horizontalAlignment = GridData.FILL;
        groupComponent.setLayoutData(data);

        addResourceTypeButton = new Button(groupComponent, SWT.PUSH);
        addResourceTypeButton.setText(WorkbenchMessages.FileEditorPreference_add); 
        addResourceTypeButton.addListener(SWT.Selection, this);
        setButtonLayoutData(addResourceTypeButton);

        removeResourceTypeButton = new Button(groupComponent, SWT.PUSH);
        removeResourceTypeButton.setText(WorkbenchMessages.FileEditorPreference_remove);
        removeResourceTypeButton.addListener(SWT.Selection, this);
        setButtonLayoutData(removeResourceTypeButton);

        label = new Label(pageComponent, SWT.LEFT);
        data = new GridData();
        data.horizontalAlignment = GridData.FILL;
        data.horizontalSpan = 2;
        label.setLayoutData(data);

        editorLabel = new Label(pageComponent, SWT.LEFT);
        editorLabel.setText(WorkbenchMessages.FileEditorPreference_associatedEditors);
        data = new GridData();
        data.horizontalAlignment = GridData.FILL;
        data.horizontalSpan = 2;
        editorLabel.setLayoutData(data);

        editorTable = new Table(pageComponent, SWT.MULTI | SWT.BORDER);
        editorTable.addListener(SWT.Selection, this);
        editorTable.addListener(SWT.DefaultSelection, this);
        data = new GridData(GridData.FILL_BOTH);
        data.heightHint = editorTable.getItemHeight() * 7;
        editorTable.setLayoutData(data);

        groupComponent = new Composite(pageComponent, SWT.NULL);
        groupLayout = new GridLayout();
        groupLayout.marginWidth = 0;
        groupLayout.marginHeight = 0;
        groupComponent.setLayout(groupLayout);
        data = new GridData();
        data.verticalAlignment = GridData.FILL;
        data.horizontalAlignment = GridData.FILL;
        groupComponent.setLayoutData(data);

        addEditorButton = new Button(groupComponent, SWT.PUSH);
        addEditorButton.setText(WorkbenchMessages.FileEditorPreference_addEditor);
        addEditorButton.addListener(SWT.Selection, this);
        addEditorButton.setLayoutData(data);
        setButtonLayoutData(addEditorButton);

        removeEditorButton = new Button(groupComponent, SWT.PUSH);
        removeEditorButton.setText(WorkbenchMessages.FileEditorPreference_removeEditor); 
        removeEditorButton.addListener(SWT.Selection, this);
        setButtonLayoutData(removeEditorButton);

        defaultEditorButton = new Button(groupComponent, SWT.PUSH);
        defaultEditorButton.setText(WorkbenchMessages.FileEditorPreference_default);
        defaultEditorButton.addListener(SWT.Selection, this);
        setButtonLayoutData(defaultEditorButton);

        fillResourceTypeTable();
        if (resourceTypeTable.getItemCount() > 0) {
            resourceTypeTable.setSelection(0);
        }
        fillEditorTable();
        updateEnabledState();

        workbench.getHelpSystem().setHelp(parent,
				IWorkbenchHelpContextIds.FILE_EDITORS_PREFERENCE_PAGE);
        applyDialogFont(pageComponent);

        return pageComponent;
    }

    @Override
	public void dispose() {
        super.dispose();
        if (imagesToDispose != null) {
            for (Iterator e = imagesToDispose.iterator(); e.hasNext();) {
                ((Image) e.next()).dispose();
            }
            imagesToDispose = null;
        }
        if (editorsToImages != null) {
            for (Iterator e = editorsToImages.values().iterator(); e.hasNext();) {
                ((Image) e.next()).dispose();
            }
            editorsToImages = null;
        }
    }

    @Override
	protected IPreferenceStore doGetPreferenceStore() {
        return WorkbenchPlugin.getDefault().getPreferenceStore();
    }

    protected void fillEditorTable() {
        editorTable.removeAll();
        FileEditorMapping resourceType = getSelectedResourceType();
        if (resourceType != null) {
            IEditorDescriptor[] array = resourceType.getEditors();
            for (int i = 0; i < array.length; i++) {
                IEditorDescriptor editor = array[i];
                TableItem item = new TableItem(editorTable, SWT.NULL);
                item.setData(DATA_EDITOR, editor);
                String defaultString = null;
                if (resourceType != null) {
                    if (resourceType.getDefaultEditor() == editor && resourceType.isDeclaredDefaultEditor(editor)) {
						defaultString = WorkbenchMessages.FileEditorPreference_defaultLabel;
					}
                }

                if (defaultString != null) {
                    item.setText(editor.getLabel() + " " + defaultString); //$NON-NLS-1$
                } else {
                    item.setText(editor.getLabel());
                }
                item.setImage(getImage(editor));
            }
            
			EditorRegistry registry = (EditorRegistry) WorkbenchPlugin
					.getDefault().getEditorRegistry();
			IContentType[] contentTypes = Platform.getContentTypeManager()
					.findContentTypesFor(resourceType.getLabel());
			for (int i = 0; i < contentTypes.length; i++) {
				array = registry.getEditorsForContentType(contentTypes[i]);
				for (int j = 0; j < array.length; j++) {
					IEditorDescriptor editor = array[j];
					TableItem[] items = editorTable.getItems();
					TableItem foundItem = null;
					for (int k = 0; k < items.length; k++) {
						if (items[k].getData(DATA_EDITOR).equals(editor)) {
							foundItem = items[k];
							break;
						}
					}
					if (foundItem == null) {
						TableItem item = new TableItem(editorTable, SWT.NULL);
						item.setData(DATA_EDITOR, editor);
						item.setData(DATA_FROM_CONTENT_TYPE, contentTypes[i]);
						setLockedItemText(item, editor.getLabel());
						item.setImage(getImage(editor));
					} else { // update the item to reflect its origin
						foundItem.setData(DATA_FROM_CONTENT_TYPE, contentTypes[i]);
						setLockedItemText(foundItem, foundItem.getText());
					}
				}
			}
            
        }
    }

	private void setLockedItemText(TableItem item, String baseLabel) {
		item.setText(NLS
				.bind(WorkbenchMessages.FileEditorPreference_isLocked,
						baseLabel, ((IContentType) item
								.getData(DATA_FROM_CONTENT_TYPE)).getName()));
	}
    
    protected void fillResourceTypeTable() {
        IFileEditorMapping[] array = WorkbenchPlugin.getDefault()
                .getEditorRegistry().getFileEditorMappings();
        for (int i = 0; i < array.length; i++) {
            FileEditorMapping mapping = (FileEditorMapping) array[i];
            mapping = (FileEditorMapping) mapping.clone(); // want a copy
            newResourceTableItem(mapping, i, false);
        }
    }

    protected Image getImage(IEditorDescriptor editor) {
        Image image = (Image) editorsToImages.get(editor);
        if (image == null) {
            image = editor.getImageDescriptor().createImage();
            editorsToImages.put(editor, image);
        }
        return image;
    }

    protected FileEditorMapping getSelectedResourceType() {
        TableItem[] items = resourceTypeTable.getSelection();
        if (items.length == 1) {
            return (FileEditorMapping) items[0].getData();
        }
        return null;        
    }

    protected IEditorDescriptor[] getAssociatedEditors() {
        if (getSelectedResourceType() == null) {
			return null;
		}
        if (editorTable.getItemCount() > 0) {
            ArrayList editorList = new ArrayList();
            for (int i = 0; i < editorTable.getItemCount(); i++) {
				editorList.add(editorTable.getItem(i).getData(DATA_EDITOR));
			}

            return (IEditorDescriptor[]) editorList
                    .toArray(new IEditorDescriptor[editorList.size()]);
        } 
        return null;
    }

    @Override
	public void handleEvent(Event event) {
        if (event.widget == addResourceTypeButton) {
            promptForResourceType();
        } else if (event.widget == removeResourceTypeButton) {
            removeSelectedResourceType();
        } else if (event.widget == addEditorButton) {
            promptForEditor();
        } else if (event.widget == removeEditorButton) {
            removeSelectedEditor();
        } else if (event.widget == defaultEditorButton) {
            setSelectedEditorAsDefault();
        } else if (event.widget == resourceTypeTable) {
            fillEditorTable();
        }

        updateEnabledState();

    }

    @Override
	public void init(IWorkbench aWorkbench) {
        this.workbench = aWorkbench;
        noDefaultAndApplyButton();
    }

    protected TableItem newResourceTableItem(IFileEditorMapping mapping,
            int index, boolean selected) {
        Image image = mapping.getImageDescriptor().createImage(false);
        if (image != null) {
			imagesToDispose.add(image);
		}

        TableItem item = new TableItem(resourceTypeTable, SWT.NULL, index);
        if (image != null) {
            item.setImage(image);
        }
        item.setText(mapping.getLabel());
        item.setData(mapping);
        if (selected) {
            resourceTypeTable.setSelection(index);
        }

        return item;
    }

    @Override
	public boolean performOk() {
        TableItem[] items = resourceTypeTable.getItems();
        FileEditorMapping[] resourceTypes = new FileEditorMapping[items.length];
        for (int i = 0; i < items.length; i++) {
            resourceTypes[i] = (FileEditorMapping) (items[i].getData());
        }
        EditorRegistry registry = (EditorRegistry) WorkbenchPlugin.getDefault()
                .getEditorRegistry(); // cast to allow save to be called
        registry.setFileEditorMappings(resourceTypes);
        registry.saveAssociations();
        
        PrefUtil.savePrefs();
        return true;
    }

    public void promptForEditor() {
        EditorSelectionDialog dialog = new EditorSelectionDialog(getControl()
                .getShell());
        dialog.setEditorsToFilter(getAssociatedEditors());
        dialog
                .setMessage(NLS.bind(WorkbenchMessages.Choose_the_editor_for_file,getSelectedResourceType().getLabel() ));
        if (dialog.open() == Window.OK) {
            EditorDescriptor editor = (EditorDescriptor) dialog
                    .getSelectedEditor();
            if (editor != null) {
                int i = editorTable.getItemCount();
                boolean isEmpty = i < 1;
                TableItem item = new TableItem(editorTable, SWT.NULL, i);
                item.setData(DATA_EDITOR, editor);
                if (isEmpty) {
					item
                            .setText(editor.getLabel()
                                    + " " + WorkbenchMessages.FileEditorPreference_defaultLabel); //$NON-NLS-1$
				} else {
					item.setText(editor.getLabel());
				}
                item.setImage(getImage(editor));
                editorTable.setSelection(i);
                editorTable.setFocus();
                getSelectedResourceType().addEditor(editor);
				if (isEmpty) {
					getSelectedResourceType().setDefaultEditor(editor);
				}
                updateSelectedResourceType(); //in case of new default
            }
        }
    }

    public void promptForResourceType() {
        FileExtensionDialog dialog = new FileExtensionDialog(getControl()
				.getShell(), WorkbenchMessages.FileExtension_shellTitle,
				IWorkbenchHelpContextIds.FILE_EXTENSION_DIALOG,
				WorkbenchMessages.FileExtension_dialogTitle,
				WorkbenchMessages.FileExtension_fileTypeMessage,
				WorkbenchMessages.FileExtension_fileTypeLabel);
        if (dialog.open() == Window.OK) {
            String name = dialog.getName();
            String extension = dialog.getExtension();
            addResourceType(name, extension);
        }
    }

    public void removeSelectedEditor() {
        TableItem[] items = editorTable.getSelection();
        boolean defaultEditor = editorTable.getSelectionIndex() == 0;
        if (items.length > 0) {
        	for (int i = 0; i < items.length; i++) {
                getSelectedResourceType().removeEditor(
                        (EditorDescriptor) items[i].getData(DATA_EDITOR));
                items[i].dispose();	
        	}
        }
        if (defaultEditor && editorTable.getItemCount() > 0) {
            TableItem item = editorTable.getItem(0);
            getSelectedResourceType().setDefaultEditor(
					(EditorDescriptor) item.getData(DATA_EDITOR));
			item.setText(((EditorDescriptor) (item.getData(DATA_EDITOR))).getLabel()
					+ " " + WorkbenchMessages.FileEditorPreference_defaultLabel); //$NON-NLS-1$
			if (!isEditorRemovable(item)) {
				setLockedItemText(item, item.getText());
			}
		}

    }

    public void removeSelectedResourceType() {
        TableItem[] items = resourceTypeTable.getSelection();
        for (int i = 0; i < items.length; i++) {
        	items[i].dispose();
        }
        editorTable.removeAll();
    }

    public void setSelectedEditorAsDefault() {
        TableItem[] items = editorTable.getSelection();
        if (items.length > 0) {
            TableItem oldDefaultItem = editorTable.getItem(0);
            oldDefaultItem
                    .setText(((EditorDescriptor) oldDefaultItem.getData(DATA_EDITOR))
                            .getLabel());
            if (!isEditorRemovable(oldDefaultItem)) {
				setLockedItemText(oldDefaultItem, oldDefaultItem.getText());
			}
            EditorDescriptor editor = (EditorDescriptor) items[0].getData(DATA_EDITOR);
            getSelectedResourceType().setDefaultEditor(editor);
            IContentType fromContentType = (IContentType) items[0].getData(DATA_FROM_CONTENT_TYPE);
            items[0].dispose(); //Table is single selection
            TableItem item = new TableItem(editorTable, SWT.NULL, 0);
            item.setData(DATA_EDITOR, editor);
            if (fromContentType != null) {
				item.setData(DATA_FROM_CONTENT_TYPE, fromContentType);
			}
            item
                    .setText(editor.getLabel()
                            + " " + WorkbenchMessages.FileEditorPreference_defaultLabel); //$NON-NLS-1$
            item.setImage(getImage(editor));
            if (!isEditorRemovable(item)) {
				setLockedItemText(item, item.getText());
			}
			editorTable.setSelection(new TableItem[] { item });
        }
    }

    public void updateEnabledState() {
    	int selectedResources = resourceTypeTable.getSelectionCount();
        int selectedEditors = editorTable.getSelectionCount();

        removeResourceTypeButton.setEnabled(selectedResources != 0);
		editorLabel.setEnabled(selectedResources == 1);
        addEditorButton.setEnabled(selectedResources == 1);
		removeEditorButton.setEnabled(areEditorsRemovable());
        defaultEditorButton.setEnabled(selectedEditors == 1);
    }
    
	private boolean areEditorsRemovable() {
		TableItem[] items = editorTable.getSelection();
		if (items.length == 0) {
			return false;
		}

		for (int i = 0; i < items.length; i++) {
			if (!isEditorRemovable(items[i])) {
				return false;
			}
		}
		return true;
	}
    
    private boolean isEditorRemovable(TableItem item) {
		IContentType fromContentType = (IContentType) item.getData(DATA_FROM_CONTENT_TYPE);
		return fromContentType == null;
	}

    public void updateSelectedResourceType() {
    }
}
