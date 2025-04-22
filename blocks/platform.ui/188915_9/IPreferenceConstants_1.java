package org.eclipse.ui.internal.ide;

import static org.eclipse.ui.internal.ide.IDEWorkbenchMessages.LargeFileAssociationsPreferencePage_ColumnHeader_Editor;
import static org.eclipse.ui.internal.ide.IDEWorkbenchMessages.LargeFileAssociationsPreferencePage_ColumnHeader_Size;
import static org.eclipse.ui.internal.ide.IDEWorkbenchMessages.LargeFileAssociationsPreferencePage_DefaultPreference_Label;
import static org.eclipse.ui.internal.ide.IDEWorkbenchMessages.LargeFileAssociationsPreferencePage_DisabledPageText;
import static org.eclipse.ui.internal.ide.IDEWorkbenchMessages.LargeFileAssociationsPreferencePage_EditEditorButton_Label;
import static org.eclipse.ui.internal.ide.IDEWorkbenchMessages.LargeFileAssociationsPreferencePage_EditorDialog_Error_DuplicateSize;
import static org.eclipse.ui.internal.ide.IDEWorkbenchMessages.LargeFileAssociationsPreferencePage_EditorDialog_Label_Editor;
import static org.eclipse.ui.internal.ide.IDEWorkbenchMessages.LargeFileAssociationsPreferencePage_EditorDialog_Label_FileSize;
import static org.eclipse.ui.internal.ide.IDEWorkbenchMessages.LargeFileAssociationsPreferencePage_EditorDialog_Message;
import static org.eclipse.ui.internal.ide.IDEWorkbenchMessages.LargeFileAssociationsPreferencePage_EditorDialog_Radio_Prompt;
import static org.eclipse.ui.internal.ide.IDEWorkbenchMessages.LargeFileAssociationsPreferencePage_EditorDialog_Radio_Specific;
import static org.eclipse.ui.internal.ide.IDEWorkbenchMessages.LargeFileAssociationsPreferencePage_EditorDialog_Title;
import static org.eclipse.ui.internal.ide.IDEWorkbenchMessages.LargeFileAssociationsPreferencePage_EditorTable_DialogPrompt;
import static org.eclipse.ui.internal.ide.IDEWorkbenchMessages.LargeFileAssociationsPreferencePage_ExtensionDialog_Error_WildcardAndDot;
import static org.eclipse.ui.internal.ide.IDEWorkbenchMessages.LargeFileAssociationsPreferencePage_ExtensionDialog_Label;
import static org.eclipse.ui.internal.ide.IDEWorkbenchMessages.LargeFileAssociationsPreferencePage_ExtensionDialog_Message;
import static org.eclipse.ui.internal.ide.IDEWorkbenchMessages.LargeFileAssociationsPreferencePage_ExtensionsTable_DisabledClarification;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.core.runtime.Assert;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.PlainMessageDialog;
import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.jface.layout.LayoutConstants;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.preference.PreferencePage;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.window.Window;
import org.eclipse.osgi.util.NLS;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.events.VerifyEvent;
import org.eclipse.swt.events.VerifyListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
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
import org.eclipse.ui.internal.IPreferenceConstants;
import org.eclipse.ui.internal.LargeFileLimitsPreferenceHandler;
import org.eclipse.ui.internal.LargeFileLimitsPreferenceHandler.FileLimit;
import org.eclipse.ui.internal.WorkbenchImages;
import org.eclipse.ui.internal.WorkbenchMessages;
import org.eclipse.ui.internal.WorkbenchPlugin;
import org.eclipse.ui.internal.dialogs.DialogUtil;
import org.eclipse.ui.internal.registry.EditorDescriptor;
import org.eclipse.ui.internal.util.PrefUtil;

public class LargeFileAssociationsPreferencePage extends PreferencePage implements IWorkbenchPreferencePage, Listener {

	private static final String DATA_EDITOR = "editor"; //$NON-NLS-1$
	private static final String DATA_PROMPT = "prompt"; //$NON-NLS-1$

	private static final String DIALOG_PROMPT_EDITOR_TABLE_TEXT = LargeFileAssociationsPreferencePage_EditorTable_DialogPrompt;

	private static final int EDITOR_TABLE_CELL_INDEX_EDITOR = 0;
	private static final int EDITOR_TABLE_CELL_INDEX_SIZE = 1;

	private static final String[] SIZE_UNITS = { "B", "KB", "MB", "GB" }; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
	private static final int BYTE_UNIT_INDEX = 0;
	private static final int MEGABYTE_UNIT_INDEX = 2;
	private static final long B = 1L;
	private static final long KB = 1024L;
	private static final long MB = KB * KB;
	private static final long GB = MB * KB;
	private static final long[] SIZE_MULTIPLIERS = { B, KB, MB, GB };

	private static final String SIZE_TEXT_PREFIX = "> "; //$NON-NLS-1$
	private static final String DEFAULT_SIZE_STRING = "8"; //$NON-NLS-1$
	private static final int DEFAULT_SIZE_UNIT = MEGABYTE_UNIT_INDEX;

	private Table resourceTypeTable;
	private Button addResourceTypeButton;
	private Button removeResourceTypeButton;

	private Table editorTable;
	private Button addEditorButton;
	private Button editEditorButton;
	private Button removeEditorButton;

	private Button enableDefaultPreference;
	private Text defaultPreferenceFileSize;
	private Combo defaultPreferenceUnit;

	private Label editorLabel;

	private IWorkbench workbench;

	private List<Image> imagesToDispose;

	private Map<IEditorDescriptor, Image> editorsToImages;

	public void addResourceType(String newExtension) {
		Assert.isTrue(newExtension != null && newExtension.length() != 0);

		LargeFileAssociation association;
		TableItem[] items = resourceTypeTable.getItems();
		boolean found = false;
		int i = 0;

		while (i < items.length && !found) {
			association = (LargeFileAssociation) items[i].getData();
			int result = newExtension.compareToIgnoreCase(association.extension);
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

		association = new LargeFileAssociation(newExtension);
		association.promptSize = new SizeAndUnit(DEFAULT_SIZE_STRING, DEFAULT_SIZE_UNIT);
		TableItem item = newResourceTableItem(association, i, true);
		resourceTypeTable.setFocus();
		resourceTypeTable.showItem(item);
		fillEditorTable();
	}

	@Override
	protected Control createContents(Composite parent) {
		boolean isLegacyPreferenceSet = LargeFileLimitsPreferenceHandler.isLargeDocumentLegacyPreferenceSet();

		Composite pageComponent = new Composite(parent, SWT.NULL);
		if (isLegacyPreferenceSet) {
			createDisabledPreferencePageContents(pageComponent);
		} else {
			createPreferencePageContents(pageComponent);
		}

		return pageComponent;
	}

	private static void createDisabledPreferencePageContents(Composite pageComponent) {
		pageComponent.setLayout(new FillLayout());
		String disabledPageText = NLS.bind(LargeFileAssociationsPreferencePage_DisabledPageText,
				IPreferenceConstants.LARGE_DOC_SIZE_FOR_EDITORS);
		Label legacyPreferenceSetLabel = new Label(pageComponent, SWT.NONE);
		legacyPreferenceSetLabel.setText(disabledPageText);
	}

	private void createPreferencePageContents(Composite pageComponent) {
		imagesToDispose = new ArrayList<>();
		editorsToImages = new HashMap<>(50);

		GridLayout layout = new GridLayout();
		layout.numColumns = 2;
		layout.marginWidth = 0;
		layout.marginHeight = 0;
		pageComponent.setLayout(layout);
		GridData data = new GridData();
		data.verticalAlignment = GridData.FILL;
		data.horizontalAlignment = GridData.FILL;
		pageComponent.setLayoutData(data);

		Composite groupComponent = new Composite(pageComponent, SWT.NONE);
		GridLayout groupLayout = new GridLayout();
		groupLayout.marginWidth = 0;
		groupLayout.marginHeight = 0;
		groupLayout.numColumns = 4;
		groupComponent.setLayout(groupLayout);
		enableDefaultPreference = new Button(groupComponent, SWT.CHECK);
		enableDefaultPreference.setText(LargeFileAssociationsPreferencePage_DefaultPreference_Label);
		defaultPreferenceFileSize = createFileSizeText(groupComponent);
		defaultPreferenceUnit = createSizeUnitCombo(groupComponent);
		enableDefaultPreference.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				updateDefaultPreferenceWidgetsEnabledState();
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				updateDefaultPreferenceWidgetsEnabledState();
			}
		});
		setDefaultPreferenceWidgetValues();

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

		resourceTypeTable.addListener(SWT.Selection, event -> {
			if (event.detail == SWT.CHECK) {
				TableItem item = (TableItem) event.item;
				LargeFileAssociation association = (LargeFileAssociation) item.getData();
				boolean newEnabled = item.getChecked();
				boolean oldEnabled = association.enabled;
				association.enabled = newEnabled;
				if (oldEnabled != newEnabled) {
					String extensionText = getExtensionText(association);
					item.setText(extensionText);
					fillEditorTable();
				}
			}
		});

		groupComponent = new Composite(pageComponent, SWT.NULL);
		groupLayout = new GridLayout();
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
		editorColumn.setText(LargeFileAssociationsPreferencePage_ColumnHeader_Editor);
		editorColumn.setWidth(300);
		TableColumn sizeColumn = new TableColumn(editorTable, SWT.NONE);
		sizeColumn.setText(LargeFileAssociationsPreferencePage_ColumnHeader_Size);
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

		editEditorButton = new Button(groupComponent, SWT.PUSH);
		editEditorButton.setText(LargeFileAssociationsPreferencePage_EditEditorButton_Label);
		editEditorButton.addListener(SWT.Selection, this);
		editEditorButton.setLayoutData(data);
		setButtonLayoutData(editEditorButton);

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

		applyDialogFont(pageComponent);
	}

	private void updateDefaultPreferenceWidgetsEnabledState() {
		boolean enabled = enableDefaultPreference.getSelection();
		defaultPreferenceFileSize.setEnabled(enabled);
		defaultPreferenceUnit.setEnabled(enabled);
	}

	private void setDefaultPreferenceWidgetValues() {
		Long defaultPreferenceValue = LargeFileLimitsPreferenceHandler.getDefaultLimit();
		if (defaultPreferenceValue == null) {
			enableDefaultPreference.setSelection(false);
		} else {
			enableDefaultPreference.setSelection(true);
			SizeAndUnit sizeAndUnit = getSizeAndUnit(defaultPreferenceValue.longValue());
			defaultPreferenceFileSize.setText(sizeAndUnit.sizeString);
			defaultPreferenceUnit.select(sizeAndUnit.unitIndex);
		}
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
		LargeFileAssociation association = getSelectedLargeFileAssociation();
		if (association != null) {
			editorTable.setEnabled(association.enabled);
			if (association.enabled) {
				for (EditorForSize editorForSize : association.editors) {
					TableItem item = new TableItem(editorTable, SWT.NULL);
					item.setData(DATA_EDITOR, editorForSize.editor);
					String[] text = { editorForSize.editor.getLabel(), getSizeText(editorForSize.size) };
					item.setText(text);
					item.setImage(getImage(editorForSize.editor));
				}
				if (association.promptSize != null) {
					TableItem item = new TableItem(editorTable, SWT.NULL);
					item.setData(DATA_PROMPT, DATA_PROMPT);
					String[] text = { DIALOG_PROMPT_EDITOR_TABLE_TEXT, getSizeText(association.promptSize) };
					item.setText(text); // $NON-NLS-1$
				}
			}
		}
	}

	protected void fillResourceTypeTable() {
		String[] configured = LargeFileLimitsPreferenceHandler.getConfiguredExtensionTypes();
		String[] disabled = LargeFileLimitsPreferenceHandler.getDisabledExtensionTypes();

		List<String> extensions = new ArrayList<>();
		extensions.addAll(Arrays.asList(configured));
		extensions.addAll(Arrays.asList(disabled));
		Set<String> disabledSet = new HashSet<>(Arrays.asList(disabled));

		List<LargeFileAssociation> associations = new ArrayList<>();

		for (String extension : extensions) {
			FileLimit[] fileLimits = LargeFileLimitsPreferenceHandler.getFileLimitsForExtension(extension);
			LargeFileAssociation association = new LargeFileAssociation(extension);

			association.enabled = !disabledSet.contains(extension);
			long lowestPromptSize = Long.MAX_VALUE;

			for (FileLimit fileLimit : fileLimits) {
				long size = fileLimit.fileSize;
				String editorIdString = fileLimit.editorId;

				IEditorDescriptor editorDescriptor = workbench.getEditorRegistry().findEditor(editorIdString);
				if (editorDescriptor != null) {
					SizeAndUnit sizeAndUnit = getSizeAndUnit(size);
					EditorForSize editorForSize = new EditorForSize(editorDescriptor, sizeAndUnit);
					association.editors.add(editorForSize);
				} else if (LargeFileLimitsPreferenceHandler.isPromptPreferenceValue(editorIdString)) {
					long newSize = Long.valueOf(size);
					if (newSize < lowestPromptSize) {
						lowestPromptSize = newSize;
						association.promptSize = getSizeAndUnit(size);
					}
				}
			}

			associations.add(association);
		}

		int index = 0;
		for (LargeFileAssociation association : associations) {
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

	protected LargeFileAssociation getSelectedLargeFileAssociation() {
		TableItem[] items = resourceTypeTable.getSelection();
		if (items.length == 1) {
			return (LargeFileAssociation) items[0].getData();
		}
		return null;
	}

	protected IEditorDescriptor[] getAssociatedEditors() {
		if (getSelectedLargeFileAssociation() == null) {
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
		} else if (event.widget == editEditorButton) {
			editSelectedEditor();
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

	protected TableItem newResourceTableItem(LargeFileAssociation association, int index, boolean selected) {
		Image image = getImageDescriptor().createImage(false);
		if (image != null) {
			imagesToDispose.add(image);
		}

		TableItem item = new TableItem(resourceTypeTable, SWT.NULL, index);
		if (image != null) {
			item.setImage(image);
		}
		String extensionText = getExtensionText(association);
		item.setText(extensionText);
		item.setChecked(association.enabled);
		item.setData(association);
		if (selected) {
			resourceTypeTable.setSelection(index);
		}

		return item;
	}

	@Override
	public boolean performOk() {
		updateDefaultPreference();
		updateExtensionPreferences();
		PrefUtil.savePrefs();
		return true;
	}

	private void updateDefaultPreference() {
		boolean isDefaultPreferenceEnabled = enableDefaultPreference.getSelection();
		if (isDefaultPreferenceEnabled) {
			SizeAndUnit size = getSizeAndUnit(defaultPreferenceFileSize, defaultPreferenceUnit);
			LargeFileLimitsPreferenceHandler.setDefaultLimit(size.bytes);
		} else {
			LargeFileLimitsPreferenceHandler.removeDefaultLimit();
		}
	}

	private void updateExtensionPreferences() {
		String[] previousExtensions = LargeFileLimitsPreferenceHandler.getConfiguredExtensionTypes();
		List<String> configuredExtensions = new ArrayList<>();
		List<String> disabledExtensions = new ArrayList<>();
		List<String> newExtensions = new ArrayList<>();

		TableItem[] items = resourceTypeTable.getItems();
		for (TableItem item : items) {
			LargeFileAssociation association = (LargeFileAssociation) (item.getData());
			String extension = association.extension;
			if (!association.enabled) {
				disabledExtensions.add(extension);
			}
			List<FileLimit> fileLimits = new ArrayList<>();
			for (EditorForSize editorForSize : association.editors) {
				String editorId = editorForSize.editor.getId();
				long fileSize = editorForSize.size.bytes;
				FileLimit fileLimit = new FileLimit(editorId, fileSize);
				fileLimits.add(fileLimit);
			}
			if (association.promptSize != null) {
				String editorId = LargeFileLimitsPreferenceHandler.PROMPT_EDITOR_PREFERENCE_VALUE;
				long fileSize = association.promptSize.bytes;
				FileLimit fileLimit = new FileLimit(editorId, fileSize);
				fileLimits.add(fileLimit);
			}
			if (!fileLimits.isEmpty()) {
				if (association.enabled) {
					configuredExtensions.add(extension);
				}
				newExtensions.add(extension);
				LargeFileLimitsPreferenceHandler.setFileLimitsForExtension(extension,
						fileLimits.toArray(FileLimit[]::new));
			}
		}

		LargeFileLimitsPreferenceHandler.setConfiguredExtensionTypes(configuredExtensions.toArray(String[]::new));
		LargeFileLimitsPreferenceHandler.setDisabledExtensionTypes(disabledExtensions.toArray(String[]::new));

		Set<String> removedExtensions = new HashSet<>(Arrays.asList(previousExtensions));
		removedExtensions.removeAll(newExtensions);
		for (String removedExtension : removedExtensions) {
			LargeFileLimitsPreferenceHandler.removeFileLimitsForExtension(removedExtension);
		}
	}

	public void promptForEditor() {
		long[] configuredFileSizes = getConfiguredFileSizes();
		Shell shell = getControl().getShell();
		EditorForSizeDialog editorForSizeDialog = new EditorForSizeDialog(shell, configuredFileSizes);
		if (editorForSizeDialog.open() == Window.OK) {
			EditorDescriptor editor = null;
			if (!editorForSizeDialog.promptForEditor) {
				editor = promptForEditorWithDialog();
			}
			String editorText = DIALOG_PROMPT_EDITOR_TABLE_TEXT;
			SizeAndUnit size = editorForSizeDialog.size;

			LargeFileAssociation association = getSelectedLargeFileAssociation();

			int i = editorTable.getItemCount();
			TableItem item = null;
			if (editor != null) {
				item = new TableItem(editorTable, SWT.NULL, i);
				item.setData(DATA_EDITOR, editor);
				item.setImage(getImage(editor));
				editorText = editor.getLabel();
				EditorForSize editorForSize = new EditorForSize(editor, size);
				association.editors.add(editorForSize);
			} else {
				int promptIndex = getIndexOfPromptItem();
				if (promptIndex != -1) {
					item = editorTable.getItem(promptIndex);
				} else {
					item = new TableItem(editorTable, SWT.NULL, i);
					item.setData(DATA_PROMPT, DATA_PROMPT);
				}
				editorText = DIALOG_PROMPT_EDITOR_TABLE_TEXT;
				association.promptSize = size;
			}
			String[] text = { editorText, getSizeText(size) };
			item.setText(text);
			editorTable.setSelection(i);
			editorTable.setFocus();
		}
	}

	void editSelectedEditor() {
		LargeFileAssociation association = getSelectedLargeFileAssociation();
		int selectionIndex = editorTable.getSelectionIndex();
		TableItem item = editorTable.getItem(selectionIndex);
		String editorString = item.getText(EDITOR_TABLE_CELL_INDEX_EDITOR);
		boolean promptForEditor = LargeFileLimitsPreferenceHandler.isPromptPreferenceValue(editorString);
		String sizeString = item.getText(EDITOR_TABLE_CELL_INDEX_SIZE);
		SizeAndUnit selectedSize = parseSizeString(sizeString);

		long[] configuredFileSizes = getConfiguredFileSizes();
		long[] fileSizes = Arrays.stream(configuredFileSizes).filter(l -> l != selectedSize.bytes).toArray();
		Shell shell = getControl().getShell();
		EditorForSizeDialog editorForSizeDialog = new EditorForSizeDialog(shell, fileSizes, selectedSize,
				promptForEditor);
		if (editorForSizeDialog.open() == Window.OK) {
			EditorDescriptor editor = null;
			if (!editorForSizeDialog.promptForEditor) {
				editor = promptForEditorWithDialog();
			}
			String editorText = DIALOG_PROMPT_EDITOR_TABLE_TEXT;
			SizeAndUnit size = editorForSizeDialog.size;


			if (editor != null) {
				removeItemEditor(item, association);
				item.setData(DATA_EDITOR, editor);
				item.setData(DATA_PROMPT, null);
				item.setImage(getImage(editor));
				editorText = editor.getLabel();
				EditorForSize editorForSize = new EditorForSize(editor, size);
				association.editors.add(editorForSize);
			} else {
				int promptIndex = getIndexOfPromptItem();
				item = editorTable.getItem(selectionIndex);
				item.setImage((Image) null);
				removeItemEditor(item, association);
				item.setData(DATA_EDITOR, null);
				item.setData(DATA_PROMPT, DATA_PROMPT);
				editorText = DIALOG_PROMPT_EDITOR_TABLE_TEXT;
				association.promptSize = size;
				if (promptIndex != -1 && promptIndex != selectionIndex) {
					editorTable.remove(promptIndex);
				}
			}
			String[] text = { editorText, getSizeText(size) };
			item.setText(text);
			editorTable.setFocus();
		}
	}

	private EditorDescriptor promptForEditorWithDialog() {
		EditorDescriptor selectedEditor = null;
		EditorSelectionDialog editorSelectionDialog = new EditorSelectionDialog(getControl().getShell());
		editorSelectionDialog.setEditorsToFilter(getAssociatedEditors());
		editorSelectionDialog.setMessage(
				NLS.bind(WorkbenchMessages.Choose_the_editor_for_file, getSelectedLargeFileAssociation().extension));
		if (editorSelectionDialog.open() == Window.OK) {
			selectedEditor = (EditorDescriptor) editorSelectionDialog.getSelectedEditor();
		}
		return selectedEditor;
	}

	private void removeItemEditor(TableItem item, LargeFileAssociation association) {
		EditorDescriptor previousEditor = (EditorDescriptor) item.getData(DATA_EDITOR);
		association.removeEditor(previousEditor);
	}

	private int getIndexOfPromptItem() {
		int promptIndex = -1;
		TableItem[] editorTableItems = editorTable.getItems();
		for (int j = 0; j < editorTableItems.length; ++j) {
			TableItem editorTableItem = editorTableItems[j];
			if (editorTableItem.getData(DATA_PROMPT) != null) {
				promptIndex = j;
				break;
			}
		}
		return promptIndex;
	}

	private long[] getConfiguredFileSizes() {
		LargeFileAssociation association = getSelectedLargeFileAssociation();
		int numberOfSizes = association.editors.size();
		if (association.promptSize != null) {
			++numberOfSizes;
		}
		long[] configuredFileSizes = new long[numberOfSizes];
		int index = 0;
		for (EditorForSize editor : association.editors) {
			configuredFileSizes[index] = editor.size.bytes;
			++index;
		}
		if (association.promptSize != null) {
			configuredFileSizes[index] = association.promptSize.bytes;
		}
		return configuredFileSizes;
	}

	public void promptForResourceType() {
		ExtensionDialog dialog = new ExtensionDialog(getControl().getShell());
		if (dialog.open() == Window.OK) {
			String extension = dialog.getExtension();
			addResourceType(extension);
		}
	}

	public void removeSelectedEditor() {
		TableItem[] items = editorTable.getSelection();
		if (items.length > 0) {
			for (TableItem item : items) {
				LargeFileAssociation association = getSelectedLargeFileAssociation();
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

		int selectedEditors = editorTable.getSelectionCount();
		int editorsCount = editorTable.getItemCount();
		boolean removeEditorsEnabled = editorsCount > 1 && selectedEditors > 0;
		editEditorButton.setEnabled(selectedEditors == 1);
		removeEditorButton.setEnabled(removeEditorsEnabled);
	}

	private static ImageDescriptor getImageDescriptor() {
		return WorkbenchImages.getImageDescriptor(ISharedImages.IMG_OBJ_FILE);
	}

	private static String getExtensionText(LargeFileAssociation association) {
		String text = association.extension;
		if (!association.enabled) {
			text += " " + LargeFileAssociationsPreferencePage_ExtensionsTable_DisabledClarification; //$NON-NLS-1$
		}
		return text;
	}

	private static String getSizeText(SizeAndUnit size) {
		String sizeText = SIZE_TEXT_PREFIX + size.sizeString;
		if (size.unitIndex != BYTE_UNIT_INDEX) {
			sizeText += getSizeSuffix(size.unitIndex);
		}
		return sizeText;
	}

	private static Text createFileSizeText(Composite parent) {
		Text text = new Text(parent, SWT.BORDER);
		text.addVerifyListener(new NumberVerifyListener());
		text.addModifyListener(new NumberModifyListener());
		text.setText(DEFAULT_SIZE_STRING);
		return text;
	}

	private static Combo createSizeUnitCombo(Composite parent) {
		Combo sizeUnitCombo = new Combo(parent, SWT.READ_ONLY);
		for (String unit : SIZE_UNITS) {
			sizeUnitCombo.add(unit);
		}
		sizeUnitCombo.select(DEFAULT_SIZE_UNIT);
		return sizeUnitCombo;
	}

	private static SizeAndUnit getSizeAndUnit(Text fileSizeText, Combo sizeUnitCombo) {
		String fileSizeString = fileSizeText.getText();
		int sizeUnitSelectionIndex = sizeUnitCombo.getSelectionIndex();
		SizeAndUnit size = new SizeAndUnit(fileSizeString, sizeUnitSelectionIndex);
		return size;
	}

	private static SizeAndUnit getSizeAndUnit(long bytes) {
		long largestSizeMultiplier = 1L;
		int sizeUnitIndex = 0;
		for (int i = 0; i < SIZE_UNITS.length; ++i) {
			long sizeMultiplier = SIZE_MULTIPLIERS[i];
			if (bytes % sizeMultiplier == 0) {
				if (largestSizeMultiplier < sizeMultiplier) {
					largestSizeMultiplier = sizeMultiplier;
					sizeUnitIndex = i;
				}
			}
		}
		long sizeInUnit = bytes / largestSizeMultiplier;
		String sizeString = String.valueOf(sizeInUnit);
		SizeAndUnit sizeAndUnit = new SizeAndUnit(sizeString, sizeUnitIndex);
		return sizeAndUnit;
	}

	private static long computeSize(String sizeString, int unitIndex) {
		long bytesWithoutUnit = Long.valueOf(sizeString);
		long unitMultiplier = SIZE_MULTIPLIERS[unitIndex];
		long size = bytesWithoutUnit * unitMultiplier;
		return size;
	}

	private static SizeAndUnit parseSizeString(String sizeString) {
		if (sizeString.startsWith(SIZE_TEXT_PREFIX)) {
			sizeString = sizeString.substring(SIZE_TEXT_PREFIX.length());
		}
		long sizeMultiplier = 1L;
		sizeString = sizeString.strip();
		int unitIndex = 0;
		for (int i = 0; i < SIZE_UNITS.length; ++i) {
			String sizeSuffix = getSizeSuffix(i);
			if (sizeString.endsWith(sizeSuffix)) {
				int newLength = sizeString.length() - sizeSuffix.length();
				sizeString = sizeString.substring(0, newLength);
				unitIndex = i;
				break;
			}
		}
		sizeMultiplier = SIZE_MULTIPLIERS[unitIndex];
		sizeString = sizeString.strip();
		long size = Long.valueOf(sizeString);
		size *= sizeMultiplier;
		return new SizeAndUnit(sizeString, unitIndex, size);
	}

	private static String getSizeSuffix(int unitIndex) {
		return " " + SIZE_UNITS[unitIndex]; //$NON-NLS-1$
	}

	private static class NumberVerifyListener implements VerifyListener {
		@Override
		public void verifyText(VerifyEvent e) {
			Text text = (Text) e.getSource();

			String oldText = text.getText();
			String prefix = oldText.substring(0, e.start);
			String suffix = oldText.substring(e.end);
			String newText = prefix + e.text + suffix;

			if (newText.startsWith("0") && newText.length() > 1) { //$NON-NLS-1$
				e.doit = false;
			} else if (newText.startsWith("-") || newText.startsWith("+")) { //$NON-NLS-1$ //$NON-NLS-2$
				e.doit = false;
			} else if (!newText.isEmpty()) {
				try {
					long size = Long.parseLong(newText);
					if (size < 0) {
						e.doit = false;
					}
				} catch (NumberFormatException ex) {
					e.doit = false;
				}
			}
		}
	}

	private static class NumberModifyListener implements ModifyListener {
		@Override
		public void modifyText(ModifyEvent e) {
			Text text = (Text) e.widget;
			String contents = text.getText();
			if (contents.isBlank()) {
				text.setText("0"); //$NON-NLS-1$
			}
		}
	}

	private static class ExtensionDialog extends TitleAreaDialog {

		private static final String ASTERIX_PREFIX = "*."; //$NON-NLS-1$

		private Button okButton;
		private Text extensionText;

		private String extension = ""; //$NON-NLS-1$

		ExtensionDialog(Shell shell) {
			super(shell);
			setShellStyle(getShellStyle() | SWT.SHEET);
		}


		@Override
		protected void configureShell(Shell shell) {
			super.configureShell(shell);
			shell.setText(WorkbenchMessages.FileExtension_shellTitle);
		}

		@Override
		protected Control createDialogArea(Composite parent) {
			Composite parentComposite = (Composite) super.createDialogArea(parent);

			Composite contents = new Composite(parentComposite, SWT.NONE);
			contents.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

			setTitle(WorkbenchMessages.FileExtension_shellTitle);
			setMessage(LargeFileAssociationsPreferencePage_ExtensionDialog_Message);

			new Label(contents, SWT.LEFT).setText(LargeFileAssociationsPreferencePage_ExtensionDialog_Label);

			extensionText = new Text(contents, SWT.SINGLE | SWT.BORDER);

			extensionText.addModifyListener(event -> {
				if (event.widget == extensionText) {
					extension = extensionText.getText().trim();
					okButton.setEnabled(validateExtension());
				}
			});
			extensionText.setFocus();

			Dialog.applyDialogFont(parentComposite);

			Point defaultMargins = LayoutConstants.getMargins();
			GridLayoutFactory.fillDefaults().numColumns(2).margins(defaultMargins.x, defaultMargins.y)
					.generateLayout(contents);

			return contents;
		}

		@Override
		protected void createButtonsForButtonBar(Composite parent) {
			okButton = createButton(parent, IDialogConstants.OK_ID, IDialogConstants.OK_LABEL, true);
			okButton.setEnabled(false);
			createButton(parent, IDialogConstants.CANCEL_ID, IDialogConstants.CANCEL_LABEL, false);
		}

		private boolean validateExtension() {
			if (extension.isEmpty()) {
				setErrorMessage(WorkbenchMessages.FileExtension_extensionEmptyMessage);
				return false;
			}

			int dotIndex = extension.indexOf('.');
			int asterixIndex = extension.indexOf('*');
			if (dotIndex != -1 || asterixIndex != -1) {
				if (!extension.startsWith(ASTERIX_PREFIX) || extension.equals(ASTERIX_PREFIX)) {
					setErrorMessage(LargeFileAssociationsPreferencePage_ExtensionDialog_Error_WildcardAndDot);
					return false;
				}
			}

			setErrorMessage(null);
			return true;
		}

		String getExtension() {
			if (extension.startsWith(ASTERIX_PREFIX)) {
				return extension.substring(ASTERIX_PREFIX.length());
			}
			return extension;
		}

		@Override
		protected boolean isResizable() {
			return true;
		}
	}

	private static class EditorForSizeDialog extends TitleAreaDialog {

		private final long[] configuredFileSizes;

		private Text sizeText;
		private Combo sizeUnitCombo;
		private Button promptEditorOnOpen;
		private Button chooseEditorDialog;
		private Button okButton;

		private SizeAndUnit size;
		private boolean promptForEditor;

		EditorForSizeDialog(Shell shell, long[] configuredFileSizes) {
			this(shell, configuredFileSizes, new SizeAndUnit(DEFAULT_SIZE_STRING, MEGABYTE_UNIT_INDEX), true);
		}

		EditorForSizeDialog(Shell shell, long[] configuredFileSizes, SizeAndUnit size, boolean promptForEditor) {
			super(shell);
			setShellStyle(getShellStyle() | SWT.SHEET);
			this.configuredFileSizes = configuredFileSizes;
			this.size = size;
			this.promptForEditor = promptForEditor;
		}

		@Override
		protected void configureShell(Shell newShell) {
			super.configureShell(newShell);
			newShell.setText(LargeFileAssociationsPreferencePage_EditorDialog_Title);
		}

		@Override
		protected Control createDialogArea(Composite parent) {
			Composite parentComposite = (Composite) super.createDialogArea(parent);

			Composite contents = new Composite(parentComposite, SWT.NONE);
			contents.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

			setTitle(LargeFileAssociationsPreferencePage_EditorDialog_Title);
			setMessage(LargeFileAssociationsPreferencePage_EditorDialog_Message);

			Group editorTypeGroup = new Group(contents, SWT.BORDER);
			editorTypeGroup.setText(LargeFileAssociationsPreferencePage_EditorDialog_Label_Editor);
			editorTypeGroup.setLayout(new FillLayout(SWT.VERTICAL));
			editorTypeGroup.setLayoutData(new GridData(SWT.FILL, SWT.BEGINNING, true, false));
			promptEditorOnOpen = new Button(editorTypeGroup, SWT.RADIO);
			promptEditorOnOpen.setText(LargeFileAssociationsPreferencePage_EditorDialog_Radio_Prompt);
			chooseEditorDialog = new Button(editorTypeGroup, SWT.RADIO);
			chooseEditorDialog.setText(LargeFileAssociationsPreferencePage_EditorDialog_Radio_Specific);
			promptEditorOnOpen.setSelection(promptForEditor);
			chooseEditorDialog.setSelection(!promptForEditor);
			Group fileSizeGroup = new Group(contents, SWT.BORDER);
			fileSizeGroup.setText(LargeFileAssociationsPreferencePage_EditorDialog_Label_FileSize);
			fileSizeGroup.setLayout(new FillLayout(SWT.HORIZONTAL));
			fileSizeGroup.setLayoutData(new GridData(SWT.FILL, SWT.BEGINNING, true, false));
			sizeText = createFileSizeText(fileSizeGroup);
			sizeText.setText(size.sizeString);
			sizeUnitCombo = createSizeUnitCombo(fileSizeGroup);
			sizeUnitCombo.select(size.unitIndex);
			sizeText.setFocus();

			sizeText.addModifyListener(event -> {
				if (event.widget == sizeText) {
					okButton.setEnabled(validateSize());
				}
			});
			sizeUnitCombo.addModifyListener(event -> {
				if (event.widget == sizeUnitCombo) {
					okButton.setEnabled(validateSize());
				}
			});

			Dialog.applyDialogFont(contents);

			Point defaultMargins = LayoutConstants.getMargins();
			GridLayoutFactory.fillDefaults().numColumns(2).margins(defaultMargins.x, defaultMargins.y)
					.generateLayout(contents);

			return contents;
		}

		@Override
		protected void createButtonsForButtonBar(Composite parent) {
			okButton = createButton(parent, IDialogConstants.OK_ID, IDialogConstants.OK_LABEL, true);
			okButton.setEnabled(validateSize());
			createButton(parent, IDialogConstants.CANCEL_ID, IDialogConstants.CANCEL_LABEL, false);
		}

		@Override
		protected void okPressed() {
			promptForEditor = promptEditorOnOpen.getSelection();
			size = getSizeAndUnit(sizeText, sizeUnitCombo);
			super.okPressed();
		}

		@Override
		protected boolean isResizable() {
			return true;
		}

		private boolean validateSize() {
			SizeAndUnit currentSize = getSizeAndUnit(sizeText, sizeUnitCombo);
			long currentFileSize = currentSize.bytes;
			boolean editorConfiguredForBytes = false;
			for (long configuredFileSize : configuredFileSizes) {
				if (currentFileSize == configuredFileSize) {
					editorConfiguredForBytes = true;
					break;
				}
			}
			if (editorConfiguredForBytes) {
				setErrorMessage(LargeFileAssociationsPreferencePage_EditorDialog_Error_DuplicateSize);
				return false;
			}
			setErrorMessage(null);
			return true;
		}
	}

	private static class LargeFileAssociation {
		private final String extension;
		private final List<EditorForSize> editors;
		private SizeAndUnit promptSize;
		private boolean enabled;

		LargeFileAssociation(String extension) {
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
		private final SizeAndUnit size;

		EditorForSize(IEditorDescriptor editor, SizeAndUnit size) {
			this.editor = editor;
			this.size = size;
		}
	}

	private static class SizeAndUnit {
		private final String sizeString;
		private final int unitIndex;
		private final long bytes;

		SizeAndUnit(String sizeString, int unitIndex) {
			this(sizeString, unitIndex, computeSize(sizeString, unitIndex));
		}

		SizeAndUnit(String sizeString, int unitIndex, long bytes) {
			this.sizeString = sizeString;
			this.unitIndex = unitIndex;
			this.bytes = bytes;
		}
	}
}
