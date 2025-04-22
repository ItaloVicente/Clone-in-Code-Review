package org.eclipse.ui.internal.ide;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.core.runtime.Assert;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.PlainMessageDialog;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.preference.PreferencePage;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.window.Window;
import org.eclipse.osgi.util.NLS;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.VerifyEvent;
import org.eclipse.swt.events.VerifyListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IEditorDescriptor;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;
import org.eclipse.ui.dialogs.EditorSelectionDialog;
import org.eclipse.ui.internal.IWorkbenchHelpContextIds;
import org.eclipse.ui.internal.LargeDocumentPreferenceHandler;
import org.eclipse.ui.internal.WorkbenchImages;
import org.eclipse.ui.internal.WorkbenchMessages;
import org.eclipse.ui.internal.WorkbenchPlugin;
import org.eclipse.ui.internal.dialogs.DialogUtil;
import org.eclipse.ui.internal.dialogs.FileExtensionDialog;
import org.eclipse.ui.internal.registry.EditorDescriptor;
import org.eclipse.ui.internal.util.PrefUtil;

public class LargeDocumentEditorsPreferencePage extends PreferencePage implements IWorkbenchPreferencePage, Listener {

	private static final String DATA_EDITOR = "editor"; //$NON-NLS-1$
	private static final String DATA_PROMPT = "prompt"; //$NON-NLS-1$

	private static final String DIALOG_PROMPT_EDITOR_TABLE_TEXT = "dialog prompt"; //$NON-NLS-1$

	private static final long KB = 1024L;
	private static final long MB = KB * KB;
	private static final long GB = MB * KB;
	private static final String[] SIZE_SUFFIXES = { "KB", "MB", "GB" }; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
	private static final long[] SIZE_MULTIPLIERS = { KB, MB, GB };

	protected Table resourceTypeTable;

	protected Button addResourceTypeButton;

	protected Button removeResourceTypeButton;

	protected Table editorTable;

	protected Button addEditorButton;

	protected Button removeEditorButton;

	protected Label editorLabel;

	protected IWorkbench workbench;

	protected List<Image> imagesToDispose;

	protected Map<IEditorDescriptor, Image> editorsToImages;

	public void addResourceType(String newName, String newExtension) {
		Assert.isTrue(newExtension != null && newExtension.length() != 0);
		Assert.isTrue("*".equals(newName)); //$NON-NLS-1$


		LargeDocumentAssociation resourceType;
		TableItem[] items = resourceTypeTable.getItems();
		boolean found = false;
		int i = 0;

		while (i < items.length && !found) {
			resourceType = (LargeDocumentAssociation) items[i].getData();
			int result = newExtension.compareToIgnoreCase(resourceType.extension);
			if (result == 0) {
				PlainMessageDialog.getBuilder(getShell(), WorkbenchMessages.FileEditorPreference_existsTitle)
						.image(SWT.ICON_INFORMATION).message(WorkbenchMessages.FileEditorPreference_existsMessage)
						.build().open();
				return;
			}

			if (result < 0) {
				found = true;
			} else {
				i++;
			}
		}

		resourceType = new LargeDocumentAssociation(newExtension);
		TableItem item = newResourceTableItem(resourceType, i, true);
		resourceTypeTable.setFocus();
		resourceTypeTable.showItem(item);
		fillEditorTable();
	}

	@Override
	protected Control createContents(Composite parent) {
		imagesToDispose = new ArrayList<>();
		editorsToImages = new HashMap<>(50);

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

		Label label = new Label(pageComponent, SWT.LEFT);
		label.setText(WorkbenchMessages.FileEditorPreference_fileTypes);
		data = new GridData();
		data.horizontalAlignment = GridData.FILL;
		data.horizontalSpan = 2;
		label.setLayoutData(data);

		resourceTypeTable = new Table(pageComponent, SWT.CHECK | SWT.MULTI | SWT.BORDER | SWT.FULL_SELECTION);
		resourceTypeTable.addListener(SWT.Selection, this);
		resourceTypeTable.addListener(SWT.DefaultSelection, this);
		data = new GridData(GridData.FILL_HORIZONTAL);

		int availableRows = DialogUtil.availableRows(pageComponent);

		data.heightHint = resourceTypeTable.getItemHeight() * (availableRows / 16);
		resourceTypeTable.setLayoutData(data);

		resourceTypeTable.addListener(SWT.Selection, new Listener() {
			@Override
			public void handleEvent(Event event) {
				if (event.detail == SWT.CHECK) {
					TableItem item = (TableItem) event.item;
					LargeDocumentAssociation association = (LargeDocumentAssociation) item.getData();
					boolean newEnabled = item.getChecked();
					boolean oldEnabled = association.enabled;
					association.enabled = newEnabled;
					if (oldEnabled != newEnabled) {
						fillEditorTable();
					}
				}
			}
		});

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
		TableColumn editorColumn = new TableColumn(editorTable, SWT.NONE);
		editorColumn.setText("Editor"); //$NON-NLS-1$
		editorColumn.setWidth(300);
		TableColumn sizeColumn = new TableColumn(editorTable, SWT.NONE);
		sizeColumn.setText("Size"); //$NON-NLS-1$
		sizeColumn.setWidth(100);
		editorTable.addListener(SWT.Selection, this);
		editorTable.addListener(SWT.DefaultSelection, this);
		data = new GridData(GridData.FILL_BOTH);
		data.heightHint = editorTable.getItemHeight() * 7;
		editorTable.setLayoutData(data);
		editorTable.setHeaderVisible(true);

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

		fillResourceTypeTable();
		if (resourceTypeTable.getItemCount() > 0) {
			resourceTypeTable.setSelection(0);
		}
		fillEditorTable();
		updateEnabledState();

		workbench.getHelpSystem().setHelp(parent, IWorkbenchHelpContextIds.FILE_EDITORS_PREFERENCE_PAGE);
		applyDialogFont(pageComponent);

		return pageComponent;
	}

	@Override
	public void dispose() {
		super.dispose();
		if (imagesToDispose != null) {
			for (Image image : imagesToDispose) {
				image.dispose();
			}
			imagesToDispose = null;
		}
		if (editorsToImages != null) {
			for (Image image : editorsToImages.values()) {
				image.dispose();
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
		LargeDocumentAssociation association = getSelectedLargeDocumentAssociation();
		if (association != null) {
			editorTable.setEnabled(association.enabled);
			if (association.enabled) {
				for (EditorForSize editorForSize : association.editors) {
					TableItem item = new TableItem(editorTable, SWT.NULL);
					item.setData(DATA_EDITOR, editorForSize.editor);
					String sizeDisplayString = getDisplayString(editorForSize.size);
					String[] text = { editorForSize.editor.getLabel(), sizeDisplayString };
					item.setText(text);
					item.setImage(getImage(editorForSize.editor));
				}
				if (association.promptSize != null) {
					TableItem item = new TableItem(editorTable, SWT.NULL);
					item.setData(DATA_PROMPT, DATA_PROMPT);
					String sizeDisplayString = getDisplayString(association.promptSize);
					String[] text = { DIALOG_PROMPT_EDITOR_TABLE_TEXT, sizeDisplayString };
					item.setText(text);
				}
			}
		}
	}

	protected void fillResourceTypeTable() {
		String[] extensions = LargeDocumentPreferenceHandler.getConfiguredExtensionTypes();
		String[] disabled = LargeDocumentPreferenceHandler.getDisabledExtensionTypes();

		List<LargeDocumentAssociation> associations = new ArrayList<>();

		for (String extension : extensions) {
			String[] preferenceValues = LargeDocumentPreferenceHandler.getExtensionPreferenceValues(extension);
			LargeDocumentAssociation association = new LargeDocumentAssociation(extension);

			for (int i = 0; i < preferenceValues.length; i = i + 2) {
				String size = preferenceValues[i + 0];
				String editorIdString = preferenceValues[i + 1];

				IEditorDescriptor editorDescriptor = workbench.getEditorRegistry().findEditor(editorIdString);
				if (editorDescriptor != null) {
					EditorForSize editorForSize = new EditorForSize(editorDescriptor, size);
					association.editors.add(editorForSize);
				} else if (LargeDocumentPreferenceHandler.isPromptPreferenceValue(editorIdString)) {
					if (association.promptSize != null) {
						long oldSize = Long.valueOf(association.promptSize);
						long newSize = Long.valueOf(size);
						if (newSize < oldSize) {
							association.promptSize = size;
						}
					} else {
						association.promptSize = size;
					}
				}
			}

			associations.add(association);
		}

		for (String extension : disabled) {
			LargeDocumentAssociation association = new LargeDocumentAssociation(extension);
			association.enabled = false;
			associations.add(association);
		}

		int index = 0;
		for (LargeDocumentAssociation association : associations) {
			newResourceTableItem(association, index, false);
			++index;
		}
	}

	protected Image getImage(IEditorDescriptor editor) {
		Image image = editorsToImages.get(editor);
		if (image == null) {
			image = editor.getImageDescriptor().createImage();
			editorsToImages.put(editor, image);
		}
		return image;
	}

	protected LargeDocumentAssociation getSelectedLargeDocumentAssociation() {
		TableItem[] items = resourceTypeTable.getSelection();
		if (items.length == 1) {
			return (LargeDocumentAssociation) items[0].getData();
		}
		return null;
	}

	protected IEditorDescriptor[] getAssociatedEditors() {
		if (getSelectedLargeDocumentAssociation() == null) {
			return null;
		}
		if (editorTable.getItemCount() > 0) {
			List<IEditorDescriptor> editorList = new ArrayList<>();
			for (int i = 0; i < editorTable.getItemCount(); i++) {
				IEditorDescriptor editorDescriptor = (IEditorDescriptor) editorTable.getItem(i).getData(DATA_EDITOR);
				if (editorDescriptor != null) {
					editorList.add(editorDescriptor);
				}
			}

			return editorList.toArray(new IEditorDescriptor[editorList.size()]);
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

	protected TableItem newResourceTableItem(LargeDocumentAssociation association, int index, boolean selected) {
		Image image = getImageDescriptor().createImage(false);
		if (image != null) {
			imagesToDispose.add(image);
		}

		TableItem item = new TableItem(resourceTypeTable, SWT.NULL, index);
		if (image != null) {
			item.setImage(image);
		}
		item.setText(association.extension);
		item.setChecked(association.enabled);
		item.setData(association);
		if (selected) {
			resourceTypeTable.setSelection(index);
		}

		return item;
	}

	@Override
	public boolean performOk() {
		String[] previousExtensions = LargeDocumentPreferenceHandler.getConfiguredExtensionTypes();
		List<String> configuredExtensions = new ArrayList<>();
		List<String> disabledExtensions = new ArrayList<>();
		List<String> newExtensions = new ArrayList<>();

		TableItem[] items = resourceTypeTable.getItems();
		for (TableItem item : items) {
			LargeDocumentAssociation association = (LargeDocumentAssociation) (item.getData());
			String extension = association.extension;
			newExtensions.add(extension);
			if (association.enabled) {
				configuredExtensions.add(extension);
			} else {
				disabledExtensions.add(extension);
			}
			List<String> preferenceValues = new ArrayList<>();
			for (EditorForSize editorForSize : association.editors) {
				String editorId = editorForSize.editor.getId();
				String size = parseSize(editorForSize.size);
				preferenceValues.add(editorId);
				preferenceValues.add(size);
			}
			if (association.promptSize != null) {
				preferenceValues.add(LargeDocumentPreferenceHandler.PROMPT_EDITOR_PREFERENCE_VALUE);
				String size = parseSize(association.promptSize);
				preferenceValues.add(size);
			}
			LargeDocumentPreferenceHandler.setExtensionPreferenceValues(extension,
					preferenceValues.toArray(String[]::new));
		}

		LargeDocumentPreferenceHandler.setConfiguredExtensionTypes(configuredExtensions.toArray(String[]::new));
		LargeDocumentPreferenceHandler.setDisabledExtensionTypes(disabledExtensions.toArray(String[]::new));

		Set<String> removedExtensions = new HashSet<>(Arrays.asList(previousExtensions));
		removedExtensions.removeAll(newExtensions);
		for (String removedExtension : removedExtensions) {
			LargeDocumentPreferenceHandler.removeExtensionPreference(removedExtension);
		}

		PrefUtil.savePrefs();
		return true;
	}

	public void promptForEditor() {
		EditorForSizeDialog editorForSizeDialog = new EditorForSizeDialog(getControl().getShell());
		if (editorForSizeDialog.open() == Window.OK) {
			EditorDescriptor editor = null;
			if (!editorForSizeDialog.getPromptForEditor()) {
				EditorSelectionDialog editorSelectionDialog = new EditorSelectionDialog(getControl().getShell());
				editorSelectionDialog.setEditorsToFilter(getAssociatedEditors());
				editorSelectionDialog.setMessage(NLS.bind(WorkbenchMessages.Choose_the_editor_for_file,
						getSelectedLargeDocumentAssociation().extension));
				if (editorSelectionDialog.open() == Window.OK) {
					editor = (EditorDescriptor) editorSelectionDialog.getSelectedEditor();
				}
			}
			LargeDocumentAssociation association = getSelectedLargeDocumentAssociation();
			String editorText = DIALOG_PROMPT_EDITOR_TABLE_TEXT;
			String sizeText = editorForSizeDialog.getSizeString();

			int i = editorTable.getItemCount();
			TableItem item = null;
			if (editor != null) {
				item = new TableItem(editorTable, SWT.NULL, i);
				item.setData(DATA_EDITOR, editor);
				item.setImage(getImage(editor));
				editorText = editor.getLabel();
				EditorForSize editorForSize = new EditorForSize(editor, sizeText);
				association.editors.add(editorForSize);
			} else {
				TableItem[] editorTableItems = editorTable.getItems();
				for (int j = 0; j < editorTableItems.length; ++j) {
					TableItem editorTableItem = editorTableItems[j];
					if (editorTableItem.getData(DATA_PROMPT) != null) {
						item = editorTableItem;
						i = j;
						break;
					}
				}
				if (item == null) {
					item = new TableItem(editorTable, SWT.NULL, i);
					item.setData(DATA_PROMPT, DATA_PROMPT);
				}
				editorText = DIALOG_PROMPT_EDITOR_TABLE_TEXT;
				association.promptSize = sizeText;
			}
			String[] text = { editorText, sizeText };
			item.setText(text);
			editorTable.setSelection(i);
			editorTable.setFocus();
		}
	}

	public void promptForResourceType() {
		FileExtensionDialog dialog = new FileExtensionDialog(getControl().getShell(),
				WorkbenchMessages.FileExtension_shellTitle, IWorkbenchHelpContextIds.FILE_EXTENSION_DIALOG,
				WorkbenchMessages.FileExtension_dialogTitle, WorkbenchMessages.FileExtension_fileTypeMessage,
				WorkbenchMessages.FileExtension_fileTypeLabel);
		if (dialog.open() == Window.OK) {
			String name = dialog.getName();
			String extension = dialog.getExtension();
			addResourceType(name, extension);
		}
	}

	public void removeSelectedEditor() {
		TableItem[] items = editorTable.getSelection();
		if (items.length > 0) {
			for (TableItem item : items) {
				LargeDocumentAssociation association = getSelectedLargeDocumentAssociation();
				IEditorDescriptor editorDescriptor = (IEditorDescriptor) item.getData(DATA_EDITOR);
				if (editorDescriptor != null) {
					association.removeEditor(editorDescriptor);
				} else if (item.getData(DATA_PROMPT) != null) {
					association.promptSize = null;
				}
				item.dispose();
			}
		}
	}

	public void removeSelectedResourceType() {
		for (TableItem item : resourceTypeTable.getSelection()) {
			item.dispose();
		}
		editorTable.removeAll();
	}

	public void updateEnabledState() {
		int selectedResources = resourceTypeTable.getSelectionCount();

		removeResourceTypeButton.setEnabled(selectedResources != 0);
		editorLabel.setEnabled(selectedResources == 1);
		addEditorButton.setEnabled(selectedResources == 1);
		removeEditorButton.setEnabled(selectedResources != 0);
	}

	private static ImageDescriptor getImageDescriptor() {
		return WorkbenchImages.getImageDescriptor(ISharedImages.IMG_OBJ_FILE);
	}

	private static String getDisplayString(String sizeString) {
		long size = Long.valueOf(sizeString);
		long largestSizeMultiplier = 1L;
		String sizeSuffix = ""; //$NON-NLS-1$
		for (int i = 0; i < SIZE_SUFFIXES.length; ++i) {
			long sizeMultiplier = SIZE_MULTIPLIERS[i];
			if (size % sizeMultiplier == 0) {
				if (largestSizeMultiplier < sizeMultiplier) {
					largestSizeMultiplier = sizeMultiplier;
				}
				sizeSuffix = " " + SIZE_SUFFIXES[i]; //$NON-NLS-1$
			}
		}
		if (!sizeSuffix.isEmpty()) {
			long sizeInUnit = size / largestSizeMultiplier;
			sizeString = String.valueOf(sizeInUnit) + sizeSuffix;
		}
		return sizeString;
	}

	private static String parseSize(String sizeString) {
		long sizeMultiplier = 1L;
		sizeString = sizeString.strip();
		for (int i = 0; i < SIZE_SUFFIXES.length; ++i) {
			String sizeSuffix = SIZE_SUFFIXES[i];
			if (sizeString.endsWith(sizeSuffix)) {
				int newLength = sizeString.length() - sizeSuffix.length();
				sizeString = sizeString.substring(0, newLength);
				sizeMultiplier = SIZE_MULTIPLIERS[i];
				break;
			}
		}
		sizeString = sizeString.strip();
		long size = Long.valueOf(sizeString);
		size *= sizeMultiplier;
		return String.valueOf(size);
	}

	private static class EditorForSizeDialog extends Dialog {

		private static final String BYTE_TEXT = "B"; //$NON-NLS-1$

		private Text documentSize;
		private Combo sizeUnit;
		private Button promptEditorOnOpen;
		private Button chooseEditorDialog;

		private String sizeString;
		private boolean promptForEditor;

		EditorForSizeDialog(Shell shell) {
			super(shell);
			sizeString = "8 MB"; //$NON-NLS-1$
			promptForEditor = true;
		}

		@Override
		protected void configureShell(Shell newShell) {
			super.configureShell(newShell);
			newShell.setText("Add associated editor"); //$NON-NLS-1$
		}

		@Override
		protected Control createDialogArea(Composite parent) {
			Composite contents = (Composite) super.createDialogArea(parent);
			Group editorTypeGroup = new Group(contents, SWT.BORDER);
			editorTypeGroup.setText("Editor"); //$NON-NLS-1$
			editorTypeGroup.setLayout(new FillLayout(SWT.VERTICAL));
			promptEditorOnOpen = new Button(editorTypeGroup, SWT.RADIO);
			promptEditorOnOpen.setText("Prompt on document open"); //$NON-NLS-1$
			promptEditorOnOpen.setSelection(true);
			chooseEditorDialog = new Button(editorTypeGroup, SWT.RADIO);
			chooseEditorDialog.setText("Specific editor"); //$NON-NLS-1$
			chooseEditorDialog.setSelection(false);
			Group documentSizeGroup = new Group(contents, SWT.BORDER);
			documentSizeGroup.setText("Document size"); //$NON-NLS-1$
			documentSizeGroup.setLayout(new FillLayout(SWT.HORIZONTAL));
			documentSize = new Text(documentSizeGroup, SWT.NONE);
			documentSize.addVerifyListener(new NumberVerifyListener());
			documentSize.setText("8"); //$NON-NLS-1$
			sizeUnit = new Combo(documentSizeGroup, SWT.READ_ONLY);
			sizeUnit.add(BYTE_TEXT);
			for (String unit : SIZE_SUFFIXES) {
				sizeUnit.add(unit);
			}
			sizeUnit.select(2); // default to MB
			return contents;
		}

		@Override
		protected void okPressed() {
			promptForEditor = promptEditorOnOpen.getSelection();
			String unitText = BYTE_TEXT;
			int sizeUnitSelectionIndex = sizeUnit.getSelectionIndex();
			if (sizeUnitSelectionIndex > 0) {
				unitText = SIZE_SUFFIXES[sizeUnitSelectionIndex - 1];
			}
			sizeString = documentSize.getText() + " " + unitText; //$NON-NLS-1$
			super.okPressed();
		}

		boolean getPromptForEditor() {
			return promptForEditor;
		}

		String getSizeString() {
			return sizeString;
		}
	}

	private static class NumberVerifyListener implements VerifyListener {
		@Override
		public void verifyText(VerifyEvent e) {
			Text text = (Text) e.getSource();

			String oldText = text.getText();
			String newText = oldText.substring(0, e.start) + e.text + oldText.substring(e.end);

			try {
				Integer.parseInt(newText);
			} catch (NumberFormatException ex) {
				e.doit = false;
			}
		}
	}

	private static class LargeDocumentAssociation {
		private final String extension;
		private final List<EditorForSize> editors;
		private String promptSize;
		private boolean enabled;

		LargeDocumentAssociation(String extension) {
			this.extension = extension;
			editors = new ArrayList<>();
			promptSize = null;
			enabled = true;
		}

		void removeEditor(IEditorDescriptor editor) {
			if (editor != null) {
				editors.removeIf(e -> e.editor.equals(editor));
			}
		}
	}

	private static class EditorForSize {
		private final IEditorDescriptor editor;
		private final String size;

		EditorForSize(IEditorDescriptor editor, String size) {
			this.editor = editor;
			this.size = size;
		}
	}
}
