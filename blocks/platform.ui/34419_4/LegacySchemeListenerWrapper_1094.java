
package org.eclipse.ui.internal.keys;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;

import org.eclipse.core.commands.Category;
import org.eclipse.core.commands.Command;
import org.eclipse.core.commands.CommandManager;
import org.eclipse.core.commands.ParameterizedCommand;
import org.eclipse.core.commands.common.NotDefinedException;
import org.eclipse.core.commands.contexts.Context;
import org.eclipse.core.commands.contexts.ContextManager;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.SafeRunner;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.bindings.Binding;
import org.eclipse.jface.bindings.BindingManager;
import org.eclipse.jface.bindings.Scheme;
import org.eclipse.jface.bindings.TriggerSequence;
import org.eclipse.jface.bindings.keys.KeyBinding;
import org.eclipse.jface.bindings.keys.KeySequence;
import org.eclipse.jface.bindings.keys.KeySequenceText;
import org.eclipse.jface.bindings.keys.KeyStroke;
import org.eclipse.jface.contexts.IContextIds;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.preference.PreferencePage;
import org.eclipse.jface.util.SafeRunnable;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.FocusListener;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.activities.IActivityManager;
import org.eclipse.ui.commands.ICommandService;
import org.eclipse.ui.contexts.IContextService;
import org.eclipse.ui.internal.IPreferenceConstants;
import org.eclipse.ui.internal.IWorkbenchHelpContextIds;
import org.eclipse.ui.internal.WorkbenchPlugin;
import org.eclipse.ui.internal.misc.StatusUtil;
import org.eclipse.ui.internal.util.PrefUtil;
import org.eclipse.ui.internal.util.Util;
import org.eclipse.ui.keys.IBindingService;
import org.eclipse.ui.statushandlers.StatusManager;

import com.ibm.icu.text.Collator;
import com.ibm.icu.text.MessageFormat;

public final class KeysPreferencePage extends PreferencePage implements
		IWorkbenchPreferencePage {

	private class SortOrderSelectionListener extends SelectionAdapter {

		private final int columnSelected;

		private SortOrderSelectionListener(final int columnSelected) {
			this.columnSelected = columnSelected;
		}

		@Override
		public void widgetSelected(SelectionEvent e) {
			final int oldSortIndex = sortOrder[0];
			final TableColumn oldSortColumn = tableBindings
					.getColumn(oldSortIndex);
			oldSortColumn.setText(UNSORTED_COLUMN_NAMES[oldSortIndex]);
			final TableColumn newSortColumn = tableBindings
					.getColumn(columnSelected);
			newSortColumn.setText(SORTED_COLUMN_NAMES[columnSelected]);

			boolean columnPlaced = false;
			boolean enoughRoom = false;
			int bumpedColumn = -1;
			for (int i = 0; i < sortOrder.length; i++) {
				if (sortOrder[i] == columnSelected) {
					enoughRoom = true;
					if (bumpedColumn != -1) {
						sortOrder[i] = bumpedColumn;
					} else {
						columnPlaced = true;
					}
					break;

				} else if (columnPlaced) {
					int temp = sortOrder[i];
					sortOrder[i] = bumpedColumn;
					bumpedColumn = temp;

				} else {
					bumpedColumn = sortOrder[i];
					sortOrder[i] = columnSelected;
					columnPlaced = true;
				}
			}

			if (!enoughRoom) {
				final int[] newSortOrder = new int[sortOrder.length + 1];
				System.arraycopy(sortOrder, 0, newSortOrder, 0,
						sortOrder.length);
				newSortOrder[sortOrder.length] = bumpedColumn;
				sortOrder = newSortOrder;
			}

			updateViewTab();
		}
	}

	private static final String BINDING_KEY = "Binding.bindings.jface.eclipse.org"; //$NON-NLS-1$

	private static final Image IMAGE_BLANK = ImageFactory.getImage("blank"); //$NON-NLS-1$

	private static final Image IMAGE_CHANGE = ImageFactory.getImage("change"); //$NON-NLS-1$

	private static final String ITEM_DATA_KEY = "org.eclipse.jface.bindings"; //$NON-NLS-1$

	private static final int ITEMS_TO_SHOW = 9;

	private static final ResourceBundle RESOURCE_BUNDLE = ResourceBundle
			.getBundle(KeysPreferencePage.class.getName());

	private static final int VIEW_TOTAL_COLUMNS = 4;

	private static final String[] SORTED_COLUMN_NAMES = new String[VIEW_TOTAL_COLUMNS];

	private static final int TAB_INDEX_MODIFY = 1;

	private static final String[] UNSORTED_COLUMN_NAMES = new String[VIEW_TOTAL_COLUMNS];

	private static final int VIEW_CATEGORY_COLUMN_INDEX = 0;

	private static final int VIEW_COMMAND_COLUMN_INDEX = 1;

	private static final int VIEW_CONTEXT_COLUMN_INDEX = 3;

	private static final int VIEW_KEY_SEQUENCE_COLUMN_INDEX = 2;

	static {
		UNSORTED_COLUMN_NAMES[VIEW_CATEGORY_COLUMN_INDEX] = Util
				.translateString(RESOURCE_BUNDLE, "tableColumnCategory"); //$NON-NLS-1$
		UNSORTED_COLUMN_NAMES[VIEW_COMMAND_COLUMN_INDEX] = Util
				.translateString(RESOURCE_BUNDLE, "tableColumnCommand"); //$NON-NLS-1$
		UNSORTED_COLUMN_NAMES[VIEW_KEY_SEQUENCE_COLUMN_INDEX] = Util
				.translateString(RESOURCE_BUNDLE, "tableColumnKeySequence"); //$NON-NLS-1$
		UNSORTED_COLUMN_NAMES[VIEW_CONTEXT_COLUMN_INDEX] = Util
				.translateString(RESOURCE_BUNDLE, "tableColumnContext"); //$NON-NLS-1$

		SORTED_COLUMN_NAMES[VIEW_CATEGORY_COLUMN_INDEX] = Util.translateString(
				RESOURCE_BUNDLE, "tableColumnCategorySorted"); //$NON-NLS-1$
		SORTED_COLUMN_NAMES[VIEW_COMMAND_COLUMN_INDEX] = Util.translateString(
				RESOURCE_BUNDLE, "tableColumnCommandSorted"); //$NON-NLS-1$
		SORTED_COLUMN_NAMES[VIEW_KEY_SEQUENCE_COLUMN_INDEX] = Util
				.translateString(RESOURCE_BUNDLE,
						"tableColumnKeySequenceSorted"); //$NON-NLS-1$
		SORTED_COLUMN_NAMES[VIEW_CONTEXT_COLUMN_INDEX] = Util.translateString(
				RESOURCE_BUNDLE, "tableColumnContextSorted"); //$NON-NLS-1$
	}

	private IActivityManager activityManager;

	private IBindingService bindingService;

	private Button buttonAdd;

	private Button buttonRemove;

	private Button buttonRestore;

	private Map categoryIdsByUniqueName;

	private Map categoryUniqueNamesById;

	private Combo comboCategory;

	private Combo comboCommand;

	private Combo comboContext;

	private Combo comboScheme;

	private Map commandIdsByCategoryId;

	private ParameterizedCommand[] commands = null;

	private ICommandService commandService;

	private Map contextIdsByUniqueName;

	private IContextService contextService;

	private Map contextUniqueNamesById;

	private Label labelBindingsForCommand;

	private Label labelBindingsForTriggerSequence;

	private Label labelContextExtends;

	private Label labelSchemeExtends;

	private final BindingManager localChangeManager = new BindingManager(
			new ContextManager(), new CommandManager());

	private Map schemeIdsByUniqueName;

	private Map schemeUniqueNamesById;

	private int[] sortOrder = { VIEW_CATEGORY_COLUMN_INDEX,
			VIEW_COMMAND_COLUMN_INDEX, VIEW_KEY_SEQUENCE_COLUMN_INDEX,
			VIEW_CONTEXT_COLUMN_INDEX };

	private TabFolder tabFolder;

	private Table tableBindings;

	private Table tableBindingsForCommand;

	private Table tableBindingsForTriggerSequence;

	private Text textTriggerSequence;

	private KeySequenceText textTriggerSequenceManager;

	
	@Override
	public void applyData(Object data) {
		if(data instanceof Binding) {
			editBinding((Binding) data);
		}
	}
	@Override
	protected final Control createContents(final Composite parent) {
		
		PlatformUI.getWorkbench().getHelpSystem()
			.setHelp(parent, IWorkbenchHelpContextIds.KEYS_PREFERENCE_PAGE);
		
		tabFolder = new TabFolder(parent, SWT.NULL);

		final TabItem viewTab = new TabItem(tabFolder, SWT.NULL);
		viewTab.setText(Util.translateString(RESOURCE_BUNDLE, "viewTab.Text")); //$NON-NLS-1$
		viewTab.setControl(createViewTab(tabFolder));

		final TabItem modifyTab = new TabItem(tabFolder, SWT.NULL);
		modifyTab.setText(Util.translateString(RESOURCE_BUNDLE,
				"modifyTab.Text")); //$NON-NLS-1$
		modifyTab.setControl(createModifyTab(tabFolder));

		applyDialogFont(tabFolder);
		final IPreferenceStore store = getPreferenceStore();
		final int selectedTab = store
				.getInt(IPreferenceConstants.KEYS_PREFERENCE_SELECTED_TAB);
		if ((tabFolder.getItemCount() > selectedTab) && (selectedTab > 0)) {
			tabFolder.setSelection(selectedTab);
		}
		
		return tabFolder;
	}

	private final Composite createModifyTab(final TabFolder parent) {
		final Composite composite = new Composite(parent, SWT.NULL);
		composite.setLayout(new GridLayout());
		GridData gridData = new GridData(GridData.FILL_BOTH);
		composite.setLayoutData(gridData);
		final Composite compositeKeyConfiguration = new Composite(composite,
				SWT.NULL);
		GridLayout gridLayout = new GridLayout();
		gridLayout.numColumns = 3;
		compositeKeyConfiguration.setLayout(gridLayout);
		gridData = new GridData(GridData.FILL_HORIZONTAL);
		compositeKeyConfiguration.setLayoutData(gridData);
		final Label labelKeyConfiguration = new Label(
				compositeKeyConfiguration, SWT.LEFT);
		labelKeyConfiguration.setText(Util.translateString(RESOURCE_BUNDLE,
				"labelScheme")); //$NON-NLS-1$
		comboScheme = new Combo(compositeKeyConfiguration, SWT.READ_ONLY);
		gridData = new GridData();
		gridData.widthHint = 200;
		comboScheme.setLayoutData(gridData);
		comboScheme.setVisibleItemCount(ITEMS_TO_SHOW);

		comboScheme.addSelectionListener(new SelectionAdapter() {
			@Override
			public final void widgetSelected(final SelectionEvent e) {
				selectedComboScheme();
			}
		});

		labelSchemeExtends = new Label(compositeKeyConfiguration, SWT.LEFT);
		gridData = new GridData(GridData.FILL_HORIZONTAL);
		labelSchemeExtends.setLayoutData(gridData);
		final Control spacer = new Composite(composite, SWT.NULL);
		gridData = new GridData();
		gridData.heightHint = 10;
		gridData.widthHint = 10;
		spacer.setLayoutData(gridData);
		final Group groupCommand = new Group(composite, SWT.SHADOW_NONE);
		gridLayout = new GridLayout();
		gridLayout.numColumns = 3;
		groupCommand.setLayout(gridLayout);
		gridData = new GridData(GridData.FILL_BOTH);
		groupCommand.setLayoutData(gridData);
		groupCommand.setText(Util.translateString(RESOURCE_BUNDLE,
				"groupCommand")); //$NON-NLS-1$	
		final Label labelCategory = new Label(groupCommand, SWT.LEFT);
		gridData = new GridData();
		labelCategory.setLayoutData(gridData);
		labelCategory.setText(Util.translateString(RESOURCE_BUNDLE,
				"labelCategory")); //$NON-NLS-1$
		comboCategory = new Combo(groupCommand, SWT.READ_ONLY);
		gridData = new GridData();
		gridData.horizontalSpan = 2;
		gridData.widthHint = 200;
		comboCategory.setLayoutData(gridData);
		comboCategory.setVisibleItemCount(ITEMS_TO_SHOW);

		comboCategory.addSelectionListener(new SelectionAdapter() {
			@Override
			public final void widgetSelected(final SelectionEvent e) {
				update();
			}
		});

		final Label labelCommand = new Label(groupCommand, SWT.LEFT);
		gridData = new GridData();
		labelCommand.setLayoutData(gridData);
		labelCommand.setText(Util.translateString(RESOURCE_BUNDLE,
				"labelCommand")); //$NON-NLS-1$
		comboCommand = new Combo(groupCommand, SWT.READ_ONLY);
		gridData = new GridData();
		gridData.horizontalSpan = 2;
		gridData.widthHint = 300;
		comboCommand.setLayoutData(gridData);
		comboCommand.setVisibleItemCount(9);

		comboCommand.addSelectionListener(new SelectionAdapter() {
			@Override
			public final void widgetSelected(final SelectionEvent e) {
				update();
			}
		});

		labelBindingsForCommand = new Label(groupCommand, SWT.LEFT);
		gridData = new GridData(GridData.VERTICAL_ALIGN_BEGINNING);
		gridData.verticalAlignment = GridData.FILL_VERTICAL;
		labelBindingsForCommand.setLayoutData(gridData);
		labelBindingsForCommand.setText(Util.translateString(RESOURCE_BUNDLE,
				"labelAssignmentsForCommand")); //$NON-NLS-1$
		tableBindingsForCommand = new Table(groupCommand, SWT.BORDER
				| SWT.FULL_SELECTION | SWT.H_SCROLL | SWT.V_SCROLL);
		tableBindingsForCommand.setHeaderVisible(true);
		gridData = new GridData(GridData.FILL_BOTH);
		gridData.heightHint = 60;
		gridData.horizontalSpan = 2;
		boolean isMac = org.eclipse.jface.util.Util.isMac();
		gridData.widthHint =  isMac ? 620 : 520;
		tableBindingsForCommand.setLayoutData(gridData);
		TableColumn tableColumnDelta = new TableColumn(tableBindingsForCommand,
				SWT.NULL, 0);
		tableColumnDelta.setResizable(false);
		tableColumnDelta.setText(Util.ZERO_LENGTH_STRING);
		tableColumnDelta.setWidth(20);
		TableColumn tableColumnContext = new TableColumn(
				tableBindingsForCommand, SWT.NULL, 1);
		tableColumnContext.setResizable(true);
		tableColumnContext.setText(Util.translateString(RESOURCE_BUNDLE,
				"tableColumnContext")); //$NON-NLS-1$
		tableColumnContext.pack();
		tableColumnContext.setWidth(200);
		final TableColumn tableColumnKeySequence = new TableColumn(
				tableBindingsForCommand, SWT.NULL, 2);
		tableColumnKeySequence.setResizable(true);
		tableColumnKeySequence.setText(Util.translateString(RESOURCE_BUNDLE,
				"tableColumnKeySequence")); //$NON-NLS-1$
		tableColumnKeySequence.pack();
		tableColumnKeySequence.setWidth(300);

		tableBindingsForCommand.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseDoubleClick(MouseEvent mouseEvent) {
				update();
			}
		});

		tableBindingsForCommand.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent selectionEvent) {
				selectedTableBindingsForCommand();
			}
		});

		final Group groupKeySequence = new Group(composite, SWT.SHADOW_NONE);
		gridLayout = new GridLayout();
		gridLayout.numColumns = 4;
		groupKeySequence.setLayout(gridLayout);
		gridData = new GridData(GridData.FILL_BOTH);
		groupKeySequence.setLayoutData(gridData);
		groupKeySequence.setText(Util.translateString(RESOURCE_BUNDLE,
				"groupKeySequence")); //$NON-NLS-1$	
		final Label labelKeySequence = new Label(groupKeySequence, SWT.LEFT);
		gridData = new GridData();
		labelKeySequence.setLayoutData(gridData);
		labelKeySequence.setText(Util.translateString(RESOURCE_BUNDLE,
				"labelKeySequence")); //$NON-NLS-1$

		textTriggerSequence = new Text(groupKeySequence, SWT.BORDER);
		textTriggerSequence.setFont(groupKeySequence.getFont());
		gridData = new GridData();
		gridData.horizontalSpan = 2;
		gridData.widthHint = 300;
		textTriggerSequence.setLayoutData(gridData);
		textTriggerSequence.addModifyListener(new ModifyListener() {
			@Override
			public void modifyText(ModifyEvent e) {
				update();
			}
		});
		textTriggerSequence.addFocusListener(new FocusListener() {
			@Override
			public void focusGained(FocusEvent e) {
				bindingService.setKeyFilterEnabled(false);
			}

			@Override
			public void focusLost(FocusEvent e) {
				bindingService.setKeyFilterEnabled(true);
			}
		});
		textTriggerSequence.addDisposeListener(new DisposeListener() {
			@Override
			public void widgetDisposed(DisposeEvent e) {
				if (!bindingService.isKeyFilterEnabled()) {
					bindingService.setKeyFilterEnabled(true);
				}
			}
		});

		textTriggerSequenceManager = new KeySequenceText(textTriggerSequence);
		textTriggerSequenceManager.setKeyStrokeLimit(4);

		final Button buttonAddKey = new Button(groupKeySequence, SWT.LEFT
				| SWT.ARROW);
		buttonAddKey.setToolTipText(Util.translateString(RESOURCE_BUNDLE,
				"buttonAddKey.ToolTipText")); //$NON-NLS-1$
		gridData = new GridData();
		gridData.heightHint = comboCategory.getTextHeight();
		buttonAddKey.setLayoutData(gridData);

		final Control[] tabStops = groupKeySequence.getTabList();
		final ArrayList newTabStops = new ArrayList();
		for (int i = 0; i < tabStops.length; i++) {
			Control tabStop = tabStops[i];
			newTabStops.add(tabStop);
			if (textTriggerSequence.equals(tabStop)) {
				newTabStops.add(buttonAddKey);
			}
		}
		final Control[] newTabStopArray = (Control[]) newTabStops
				.toArray(new Control[newTabStops.size()]);
		groupKeySequence.setTabList(newTabStopArray);

		final Menu menuButtonAddKey = new Menu(buttonAddKey);
		final Iterator trappedKeyItr = KeySequenceText.TRAPPED_KEYS.iterator();
		while (trappedKeyItr.hasNext()) {
			final KeyStroke trappedKey = (KeyStroke) trappedKeyItr.next();
			final MenuItem menuItem = new MenuItem(menuButtonAddKey, SWT.PUSH);
			menuItem.setText(trappedKey.format());
			menuItem.addSelectionListener(new SelectionAdapter() {

				@Override
				public void widgetSelected(SelectionEvent e) {
					textTriggerSequenceManager.insert(trappedKey);
					textTriggerSequence.setFocus();
					textTriggerSequence.setSelection(textTriggerSequence
							.getTextLimit());
				}
			});
		}
		buttonAddKey.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent selectionEvent) {
				Point buttonLocation = buttonAddKey.getLocation();
				buttonLocation = groupKeySequence.toDisplay(buttonLocation.x,
						buttonLocation.y);
				Point buttonSize = buttonAddKey.getSize();
				menuButtonAddKey.setLocation(buttonLocation.x, buttonLocation.y
						+ buttonSize.y);
				menuButtonAddKey.setVisible(true);
			}
		});

		labelBindingsForTriggerSequence = new Label(groupKeySequence, SWT.LEFT);
		gridData = new GridData(GridData.VERTICAL_ALIGN_BEGINNING);
		gridData.verticalAlignment = GridData.FILL_VERTICAL;
		labelBindingsForTriggerSequence.setLayoutData(gridData);
		labelBindingsForTriggerSequence.setText(Util.translateString(
				RESOURCE_BUNDLE, "labelAssignmentsForKeySequence")); //$NON-NLS-1$
		tableBindingsForTriggerSequence = new Table(groupKeySequence,
				SWT.BORDER | SWT.FULL_SELECTION | SWT.H_SCROLL | SWT.V_SCROLL);
		tableBindingsForTriggerSequence.setHeaderVisible(true);
		gridData = new GridData(GridData.FILL_BOTH);
		gridData.heightHint = 60;
		gridData.horizontalSpan = 3;
		gridData.widthHint = isMac ? 620 : 520;
		tableBindingsForTriggerSequence.setLayoutData(gridData);
		tableColumnDelta = new TableColumn(tableBindingsForTriggerSequence,
				SWT.NULL, 0);
		tableColumnDelta.setResizable(false);
		tableColumnDelta.setText(Util.ZERO_LENGTH_STRING);
		tableColumnDelta.setWidth(20);
		tableColumnContext = new TableColumn(tableBindingsForTriggerSequence,
				SWT.NULL, 1);
		tableColumnContext.setResizable(true);
		tableColumnContext.setText(Util.translateString(RESOURCE_BUNDLE,
				"tableColumnContext")); //$NON-NLS-1$
		tableColumnContext.pack();
		tableColumnContext.setWidth(200);
		final TableColumn tableColumnCommand = new TableColumn(
				tableBindingsForTriggerSequence, SWT.NULL, 2);
		tableColumnCommand.setResizable(true);
		tableColumnCommand.setText(Util.translateString(RESOURCE_BUNDLE,
				"tableColumnCommand")); //$NON-NLS-1$
		tableColumnCommand.pack();
		tableColumnCommand.setWidth(300);

		tableBindingsForTriggerSequence.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseDoubleClick(MouseEvent mouseEvent) {
				update();
			}
		});

		tableBindingsForTriggerSequence
				.addSelectionListener(new SelectionAdapter() {

					@Override
					public void widgetSelected(SelectionEvent selectionEvent) {
						selectedTableBindingsForTriggerSequence();
					}
				});

		final Composite compositeContext = new Composite(composite, SWT.NULL);
		gridLayout = new GridLayout();
		gridLayout.numColumns = 3;
		compositeContext.setLayout(gridLayout);
		gridData = new GridData(GridData.FILL_HORIZONTAL);
		compositeContext.setLayoutData(gridData);
		final Label labelContext = new Label(compositeContext, SWT.LEFT);
		labelContext.setText(Util.translateString(RESOURCE_BUNDLE,
				"labelContext")); //$NON-NLS-1$
		comboContext = new Combo(compositeContext, SWT.READ_ONLY);
		gridData = new GridData();
		gridData.widthHint = 250;
		comboContext.setLayoutData(gridData);
		comboContext.setVisibleItemCount(ITEMS_TO_SHOW);

		comboContext.addSelectionListener(new SelectionAdapter() {
			@Override
			public final void widgetSelected(final SelectionEvent e) {
				update();
			}
		});

		labelContextExtends = new Label(compositeContext, SWT.LEFT);
		gridData = new GridData(GridData.FILL_HORIZONTAL);
		labelContextExtends.setLayoutData(gridData);
		final Composite compositeButton = new Composite(composite, SWT.NULL);
		gridLayout = new GridLayout();
		gridLayout.marginHeight = 20;
		gridLayout.marginWidth = 0;
		gridLayout.numColumns = 3;
		compositeButton.setLayout(gridLayout);
		gridData = new GridData();
		compositeButton.setLayoutData(gridData);
		buttonAdd = new Button(compositeButton, SWT.CENTER | SWT.PUSH);
		gridData = new GridData();
		int widthHint = convertHorizontalDLUsToPixels(IDialogConstants.BUTTON_WIDTH);
		buttonAdd.setText(Util.translateString(RESOURCE_BUNDLE, "buttonAdd")); //$NON-NLS-1$
		gridData.widthHint = Math.max(widthHint, buttonAdd.computeSize(
				SWT.DEFAULT, SWT.DEFAULT, true).x) + 5;
		buttonAdd.setLayoutData(gridData);

		buttonAdd.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent selectionEvent) {
				selectedButtonAdd();
			}
		});

		buttonRemove = new Button(compositeButton, SWT.CENTER | SWT.PUSH);
		gridData = new GridData();
		widthHint = convertHorizontalDLUsToPixels(IDialogConstants.BUTTON_WIDTH);
		buttonRemove.setText(Util.translateString(RESOURCE_BUNDLE,
				"buttonRemove")); //$NON-NLS-1$
		gridData.widthHint = Math.max(widthHint, buttonRemove.computeSize(
				SWT.DEFAULT, SWT.DEFAULT, true).x) + 5;
		buttonRemove.setLayoutData(gridData);

		buttonRemove.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent selectionEvent) {
				selectedButtonRemove();
			}
		});

		buttonRestore = new Button(compositeButton, SWT.CENTER | SWT.PUSH);
		gridData = new GridData();
		widthHint = convertHorizontalDLUsToPixels(IDialogConstants.BUTTON_WIDTH);
		buttonRestore.setText(Util.translateString(RESOURCE_BUNDLE,
				"buttonRestore")); //$NON-NLS-1$
		gridData.widthHint = Math.max(widthHint, buttonRestore.computeSize(
				SWT.DEFAULT, SWT.DEFAULT, true).x) + 5;
		buttonRestore.setLayoutData(gridData);

		buttonRestore.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent selectionEvent) {
				selectedButtonRestore();
			}
		});

		return composite;
	}

	private final Composite createViewTab(final TabFolder parent) {
		GridData gridData = null;
		int widthHint;

		final Composite composite = new Composite(parent, SWT.NONE);
		composite.setLayoutData(new GridData(GridData.FILL_BOTH));
		composite.setLayout(new GridLayout());

		tableBindings = new Table(composite, SWT.BORDER | SWT.FULL_SELECTION
				| SWT.H_SCROLL | SWT.V_SCROLL);
		tableBindings.setHeaderVisible(true);
		gridData = new GridData(GridData.FILL_BOTH);
		gridData.heightHint = 400;
		gridData.horizontalSpan = 2;
		tableBindings.setLayoutData(gridData);
		final TableColumn tableColumnCategory = new TableColumn(tableBindings,
				SWT.NONE, VIEW_CATEGORY_COLUMN_INDEX);
		tableColumnCategory
				.setText(SORTED_COLUMN_NAMES[VIEW_CATEGORY_COLUMN_INDEX]);
		tableColumnCategory
				.addSelectionListener(new SortOrderSelectionListener(
						VIEW_CATEGORY_COLUMN_INDEX));
		final TableColumn tableColumnCommand = new TableColumn(tableBindings,
				SWT.NONE, VIEW_COMMAND_COLUMN_INDEX);
		tableColumnCommand
				.setText(UNSORTED_COLUMN_NAMES[VIEW_COMMAND_COLUMN_INDEX]);
		tableColumnCommand.addSelectionListener(new SortOrderSelectionListener(
				VIEW_COMMAND_COLUMN_INDEX));
		final TableColumn tableColumnKeySequence = new TableColumn(
				tableBindings, SWT.NONE, VIEW_KEY_SEQUENCE_COLUMN_INDEX);
		tableColumnKeySequence
				.setText(UNSORTED_COLUMN_NAMES[VIEW_KEY_SEQUENCE_COLUMN_INDEX]);
		tableColumnKeySequence
				.addSelectionListener(new SortOrderSelectionListener(
						VIEW_KEY_SEQUENCE_COLUMN_INDEX));
		final TableColumn tableColumnContext = new TableColumn(tableBindings,
				SWT.NONE, VIEW_CONTEXT_COLUMN_INDEX);
		tableColumnContext
				.setText(UNSORTED_COLUMN_NAMES[VIEW_CONTEXT_COLUMN_INDEX]);
		tableColumnContext.addSelectionListener(new SortOrderSelectionListener(
				VIEW_CONTEXT_COLUMN_INDEX));
		tableBindings.addSelectionListener(new SelectionAdapter() {
			@Override
			public final void widgetDefaultSelected(final SelectionEvent e) {
				selectedTableKeyBindings();
			}
		});

		final Composite buttonBar = new Composite(composite, SWT.NONE);
		buttonBar.setLayout(new GridLayout(2, false));
		gridData = new GridData();
		gridData.horizontalAlignment = GridData.END;
		buttonBar.setLayoutData(gridData);

		final Button editButton = new Button(buttonBar, SWT.PUSH);
		gridData = new GridData();
		widthHint = convertHorizontalDLUsToPixels(IDialogConstants.BUTTON_WIDTH);
		editButton.setText(Util.translateString(RESOURCE_BUNDLE, "buttonEdit")); //$NON-NLS-1$
		gridData.widthHint = Math.max(widthHint, editButton.computeSize(
				SWT.DEFAULT, SWT.DEFAULT, true).x) + 5;
		editButton.setLayoutData(gridData);
		editButton.addSelectionListener(new SelectionListener() {

			@Override
			public final void widgetDefaultSelected(final SelectionEvent event) {
				selectedTableKeyBindings();
			}

			@Override
			public void widgetSelected(SelectionEvent e) {
				widgetDefaultSelected(e);
			}
		});

		final Button buttonExport = new Button(buttonBar, SWT.PUSH);
		gridData = new GridData();
		widthHint = convertHorizontalDLUsToPixels(IDialogConstants.BUTTON_WIDTH);
		buttonExport.setText(Util.translateString(RESOURCE_BUNDLE,
				"buttonExport")); //$NON-NLS-1$
		gridData.widthHint = Math.max(widthHint, buttonExport.computeSize(
				SWT.DEFAULT, SWT.DEFAULT, true).x) + 5;
		buttonExport.setLayoutData(gridData);
		buttonExport.addSelectionListener(new SelectionListener() {

			@Override
			public final void widgetDefaultSelected(final SelectionEvent event) {
				selectedButtonExport();
			}

			@Override
			public void widgetSelected(SelectionEvent e) {
				widgetDefaultSelected(e);
			}
		});

		return composite;
	}

	@Override
	protected IPreferenceStore doGetPreferenceStore() {
		return PrefUtil.getInternalPreferenceStore();
	}

	public final void editBinding(final Binding binding) {
		tabFolder.setSelection(TAB_INDEX_MODIFY);

		if (binding == null) {
			return;
		}

		final ParameterizedCommand command = binding.getParameterizedCommand();
		String categoryName = null;
		String commandName = null;
		try {
			categoryName = command.getCommand().getCategory().getName();
			commandName = command.getName();
		} catch (final NotDefinedException e) {
			return; // no name
		}

		final String[] categoryNames = comboCategory.getItems();
		int i = 0;
		for (; i < categoryNames.length; i++) {
			if (categoryName.equals(categoryNames[i])) {
				break;
			}
		}
		if (i >= comboCategory.getItemCount()) {
			return;
		}
		comboCategory.select(i);

		updateComboCommand();

		final String[] commandNames = comboCommand.getItems();
		int j = 0;
		for (; j < commandNames.length; j++) {
			if (commandName.equals(commandNames[j])) {
				if (comboCommand.getSelectionIndex() != j) {
					comboCommand.select(j);
				}
				break;
			}
		}
		if (j >= comboCommand.getItemCount()) {
			if (comboCommand.getSelectionIndex() != 0) {
				comboCommand.select(0);
			}
			update();
			return;
		}

		update();

		final TableItem[] items = tableBindingsForCommand.getItems();
		int k = 0;
		for (; k < items.length; k++) {
			final String currentKeySequence = items[k].getText(2);
			if (binding.getTriggerSequence().format()
					.equals(currentKeySequence)) {
				break;
			}
		}
		if (k < tableBindingsForCommand.getItemCount()) {
			tableBindingsForCommand.select(k);
			tableBindingsForCommand.notifyListeners(SWT.Selection, null);
			textTriggerSequence.setFocus();
		}
	}

	private final String getCategoryId() {
		return !commandIdsByCategoryId.containsKey(null)
				|| comboCategory.getSelectionIndex() > 0 ? (String) categoryIdsByUniqueName
				.get(comboCategory.getText())
				: null;
	}

	private final String getContextId() {
		return comboContext.getSelectionIndex() >= 0 ? (String) contextIdsByUniqueName
				.get(comboContext.getText())
				: null;
	}

	private final KeySequence getKeySequence() {
		return textTriggerSequenceManager.getKeySequence();
	}

	private final ParameterizedCommand getParameterizedCommand() {
		final int selectionIndex = comboCommand.getSelectionIndex();
		if ((selectionIndex >= 0) && (commands != null)
				&& (selectionIndex < commands.length)) {
			return commands[selectionIndex];
		}

		return null;
	}

	private final String getSchemeId() {
		return comboScheme.getSelectionIndex() >= 0 ? (String) schemeIdsByUniqueName
				.get(comboScheme.getText())
				: null;
	}

	@Override
	public final void init(final IWorkbench workbench) {
		activityManager = workbench.getActivitySupport().getActivityManager();
		bindingService = workbench.getService(IBindingService.class);
		commandService = workbench.getService(ICommandService.class);
		contextService = workbench.getService(IContextService.class);
	}

	private final boolean isActive(final Command command) {
		return activityManager.getIdentifier(command.getId()).isEnabled();
	}

	private final void logPreferenceStoreException(final Throwable exception) {
		final String message = Util.translateString(RESOURCE_BUNDLE,
				"PreferenceStoreError.Message"); //$NON-NLS-1$
		String exceptionMessage = exception.getMessage();
		if (exceptionMessage == null) {
			exceptionMessage = message;
		}
		final IStatus status = new Status(IStatus.ERROR,
				WorkbenchPlugin.PI_WORKBENCH, 0, exceptionMessage, exception);
		WorkbenchPlugin.log(message, status);
		StatusUtil.handleStatus(message, exception, StatusManager.SHOW);
	}

	@Override
	public final boolean performCancel() {
		persistSelectedTab();

		return super.performCancel();
	}

	@Override
	protected final void performDefaults() {
		final String title = Util.translateString(RESOURCE_BUNDLE,
				"restoreDefaultsMessageBoxText"); //$NON-NLS-1$
		final String message = Util.translateString(RESOURCE_BUNDLE,
				"restoreDefaultsMessageBoxMessage"); //$NON-NLS-1$
		final boolean confirmed = MessageDialog.open(MessageDialog.CONFIRM,
				getShell(), title, message, SWT.SHEET);

		if (confirmed) {
			final String defaultSchemeId = bindingService.getDefaultSchemeId();
			final Scheme defaultScheme = localChangeManager
					.getScheme(defaultSchemeId);
			try {
				localChangeManager.setActiveScheme(defaultScheme);
			} catch (final NotDefinedException e) {
			}

			final Binding[] currentBindings = localChangeManager.getBindings();
			final int currentBindingsLength = currentBindings.length;
			final Set trimmedBindings = new HashSet();
			for (int i = 0; i < currentBindingsLength; i++) {
				final Binding binding = currentBindings[i];
				if (binding.getType() != Binding.USER) {
					trimmedBindings.add(binding);
				}
			}
			final Binding[] trimmedBindingArray = (Binding[]) trimmedBindings
					.toArray(new Binding[trimmedBindings.size()]);
			localChangeManager.setBindings(trimmedBindingArray);

			try {
				bindingService.savePreferences(defaultScheme,
						trimmedBindingArray);
			} catch (final IOException e) {
				logPreferenceStoreException(e);
			}
		}

		setScheme(localChangeManager.getActiveScheme()); // update the scheme
		update(true);
		super.performDefaults();
	}

	@Override
	public final boolean performOk() {
		try {
			bindingService.savePreferences(
					localChangeManager.getActiveScheme(), localChangeManager
							.getBindings());
		} catch (final IOException e) {
			logPreferenceStoreException(e);
		}

		persistSelectedTab();

		return super.performOk();
	}

	private final void persistSelectedTab() {
		final IPreferenceStore store = getPreferenceStore();
		store.setValue(IPreferenceConstants.KEYS_PREFERENCE_SELECTED_TAB,
				tabFolder.getSelectionIndex());
	}

	private final void selectedButtonAdd() {
		final ParameterizedCommand command = getParameterizedCommand();
		final String contextId = getContextId();
		final String schemeId = getSchemeId();
		final KeySequence keySequence = getKeySequence();
		localChangeManager.removeBindings(keySequence, schemeId, contextId,
				null, null, null, Binding.USER);
		localChangeManager.addBinding(new KeyBinding(keySequence, command,
				schemeId, contextId, null, null, null, Binding.USER));
		update(true);
	}

	private final void selectedButtonExport() {
		final FileDialog fileDialog = new FileDialog(getShell(), SWT.SAVE
				| SWT.SHEET);
		fileDialog.setFilterExtensions(new String[] { "*.csv" }); //$NON-NLS-1$
		fileDialog.setFilterNames(new String[] { Util.translateString(
				RESOURCE_BUNDLE, "csvFilterName") }); //$NON-NLS-1$
		final String filePath = fileDialog.open();
		if (filePath == null) {
			return;
		}

		final SafeRunnable runnable = new SafeRunnable() {
			@Override
			public final void run() throws IOException {
				Writer fileWriter = null;
				try {
					fileWriter = new BufferedWriter(new FileWriter(filePath));
					final TableItem[] items = tableBindings.getItems();
					final int numColumns = tableBindings.getColumnCount();
					for (int i = 0; i < items.length; i++) {
						final TableItem item = items[i];
						for (int j = 0; j < numColumns; j++) {
							String buf = Util.replaceAll(item.getText(j), "\"", //$NON-NLS-1$
									"\"\""); //$NON-NLS-1$
							fileWriter.write("\"" + buf + "\"");  //$NON-NLS-1$//$NON-NLS-2$
							if (j < numColumns - 1) {
								fileWriter.write(',');
							}
						}
						fileWriter.write(System.getProperty("line.separator")); //$NON-NLS-1$
					}

				} finally {
					if (fileWriter != null) {
						try {
							fileWriter.close();
						} catch (final IOException e) {
						}
					}

				}
			}
		};
		SafeRunner.run(runnable);
	}
	
	private final void selectedButtonRemove() {
		final String contextId = getContextId();
		final String schemeId = getSchemeId();
		final KeySequence keySequence = getKeySequence();
		localChangeManager.removeBindings(keySequence, schemeId, contextId,
				null, null, null, Binding.USER);
		localChangeManager.addBinding(new KeyBinding(keySequence, null,
				schemeId, contextId, null, null, null, Binding.USER));
		update(true);
	}

	private final void selectedButtonRestore() {
		String contextId = getContextId();
		String schemeId = getSchemeId();
		KeySequence keySequence = getKeySequence();
		localChangeManager.removeBindings(keySequence, schemeId, contextId,
				null, null, null, Binding.USER);
		update(true);
	}

	private final void selectedComboScheme() {
		final String activeSchemeId = getSchemeId();
		final Scheme activeScheme = localChangeManager
				.getScheme(activeSchemeId);
		try {
			localChangeManager.setActiveScheme(activeScheme);
		} catch (final NotDefinedException e) {
		}
		update(true);
	}

	private final void selectedTableBindingsForCommand() {
		final int selection = tableBindingsForCommand.getSelectionIndex();
		if ((selection >= 0)
				&& (selection < tableBindingsForCommand.getItemCount())) {
			final TableItem item = tableBindingsForCommand.getItem(selection);
			final KeyBinding binding = (KeyBinding) item.getData(ITEM_DATA_KEY);
			setContextId(binding.getContextId());
			setKeySequence(binding.getKeySequence());
		}

		update();
	}

	private final void selectedTableBindingsForTriggerSequence() {
		final int selection = tableBindingsForTriggerSequence
				.getSelectionIndex();
		if ((selection >= 0)
				&& (selection < tableBindingsForTriggerSequence.getItemCount())) {
			final TableItem item = tableBindingsForTriggerSequence
					.getItem(selection);
			final Binding binding = (Binding) item.getData(ITEM_DATA_KEY);
			setContextId(binding.getContextId());
		}

		update();
	}

	private final void selectedTableKeyBindings() {
		final int selectionIndex = tableBindings.getSelectionIndex();
		if (selectionIndex != -1) {
			final TableItem item = tableBindings.getItem(selectionIndex);
			final Binding binding = (Binding) item.getData(BINDING_KEY);
			editBinding(binding);

		} else {
			editBinding(null);
		}
	}

	private final void setContextId(final String contextId) {
		comboContext.clearSelection();
		comboContext.deselectAll();

		String contextName = (String) contextUniqueNamesById.get(contextId);
		if (contextName == null) {
			contextName = (String) contextUniqueNamesById
					.get(IContextIds.CONTEXT_ID_WINDOW);
		}
		if (contextName == null) {
			contextName = Util.ZERO_LENGTH_STRING;
		}

		final String[] items = comboContext.getItems();
		boolean found = false;
		for (int i = 0; i < items.length; i++) {
			if (contextName.equals(items[i])) {
				comboContext.select(i);
				found = true;
				break;
			}
		}

		if ((!found) && (items.length > 0)) {
			comboContext.select(0);
		}
	}

	private final void setKeySequence(final KeySequence keySequence) {
		textTriggerSequenceManager.setKeySequence(keySequence);
	}

	private final void setParameterizedCommand(
			final ParameterizedCommand command) {
		int i = 0;
		if (commands != null) {
			final int commandCount = commands.length;
			for (; i < commandCount; i++) {
				if (commands[i].equals(command)) {
					if ((comboCommand.getSelectionIndex() != i)
							&& (i < comboCommand.getItemCount())) {
						comboCommand.select(i);
					}
					break;
				}
			}
			if ((i >= comboCommand.getItemCount())
					&& (comboCommand.getSelectionIndex() != 0)) {
				comboCommand.select(0);
			}
		}
	}

	private final void setScheme(final Scheme scheme) {
		comboScheme.clearSelection();
		comboScheme.deselectAll();
		final String schemeUniqueName = (String) schemeUniqueNamesById
				.get(scheme.getId());

		if (schemeUniqueName != null) {
			final String items[] = comboScheme.getItems();

			for (int i = 0; i < items.length; i++) {
				if (schemeUniqueName.equals(items[i])) {
					comboScheme.select(i);
					break;
				}
			}
		}
	}

	@Override
	public final void setVisible(final boolean visible) {
		if (visible == true) {
			Map contextsByName = new HashMap();

			for (Iterator iterator = contextService.getDefinedContextIds()
					.iterator(); iterator.hasNext();) {
				Context context = contextService.getContext((String) iterator
						.next());
				try {
					String name = context.getName();
					Collection contexts = (Collection) contextsByName.get(name);

					if (contexts == null) {
						contexts = new HashSet();
						contextsByName.put(name, contexts);
					}

					contexts.add(context);
				} catch (final NotDefinedException e) {
				}
			}
			
			Map commandsByName = new HashMap();

			for (Iterator iterator = commandService.getDefinedCommandIds()
					.iterator(); iterator.hasNext();) {
				Command command = commandService.getCommand((String) iterator
						.next());
				if (!isActive(command)) {
					continue;
				}

				try {
					String name = command.getName();
					Collection commands = (Collection) commandsByName.get(name);

					if (commands == null) {
						commands = new HashSet();
						commandsByName.put(name, commands);
					}

					commands.add(command);
				} catch (NotDefinedException eNotDefined) {
				}
			}
			
			commandIdsByCategoryId = new HashMap();

			for (Iterator iterator = commandService.getDefinedCommandIds()
					.iterator(); iterator.hasNext();) {
				final Command command = commandService
						.getCommand((String) iterator.next());
				if (!isActive(command)) {
					continue;
				}

				try {
					String categoryId = command.getCategory().getId();
					Collection commandIds = (Collection) commandIdsByCategoryId
							.get(categoryId);

					if (commandIds == null) {
						commandIds = new HashSet();
						commandIdsByCategoryId.put(categoryId, commandIds);
					}

					commandIds.add(command.getId());
				} catch (NotDefinedException eNotDefined) {
				}
			}

			Map categoriesByName = new HashMap();

			for (Iterator iterator = commandService.getDefinedCategoryIds()
					.iterator(); iterator.hasNext();) {
				Category category = commandService
						.getCategory((String) iterator.next());

				try {
					if (commandIdsByCategoryId.containsKey(category.getId())) {
						String name = category.getName();
						Collection categories = (Collection) categoriesByName
								.get(name);

						if (categories == null) {
							categories = new HashSet();
							categoriesByName.put(name, categories);
						}

						categories.add(category);
					}
				} catch (NotDefinedException eNotDefined) {
				}
			}

			Map schemesByName = new HashMap();

			final Scheme[] definedSchemes = bindingService.getDefinedSchemes();
			for (int i = 0; i < definedSchemes.length; i++) {
				final Scheme scheme = definedSchemes[i];
				try {
					String name = scheme.getName();
					Collection schemes = (Collection) schemesByName.get(name);

					if (schemes == null) {
						schemes = new HashSet();
						schemesByName.put(name, schemes);
					}

					schemes.add(scheme);
				} catch (final NotDefinedException e) {
				}
			}

			contextIdsByUniqueName = new HashMap();
			contextUniqueNamesById = new HashMap();

			for (Iterator iterator = contextsByName.entrySet().iterator(); iterator
					.hasNext();) {
				Map.Entry entry = (Map.Entry) iterator.next();
				String name = (String) entry.getKey();
				Set contexts = (Set) entry.getValue();
				Iterator iterator2 = contexts.iterator();

				if (contexts.size() == 1) {
					Context context = (Context) iterator2.next();
					contextIdsByUniqueName.put(name, context.getId());
					contextUniqueNamesById.put(context.getId(), name);
				} else {
					while (iterator2.hasNext()) {
						Context context = (Context) iterator2.next();
						String uniqueName = MessageFormat.format(
								Util.translateString(RESOURCE_BUNDLE,
										"uniqueName"), new Object[] { name, //$NON-NLS-1$
										context.getId() });
						contextIdsByUniqueName.put(uniqueName, context.getId());
						contextUniqueNamesById.put(context.getId(), uniqueName);
					}
				}
			}

			categoryIdsByUniqueName = new HashMap();
			categoryUniqueNamesById = new HashMap();

			for (Iterator iterator = categoriesByName.entrySet().iterator(); iterator
					.hasNext();) {
				Map.Entry entry = (Map.Entry) iterator.next();
				String name = (String) entry.getKey();
				Set categories = (Set) entry.getValue();
				Iterator iterator2 = categories.iterator();

				if (categories.size() == 1) {
					Category category = (Category) iterator2.next();
					categoryIdsByUniqueName.put(name, category.getId());
					categoryUniqueNamesById.put(category.getId(), name);
				} else {
					while (iterator2.hasNext()) {
						Category category = (Category) iterator2.next();
						String uniqueName = MessageFormat.format(
								Util.translateString(RESOURCE_BUNDLE,
										"uniqueName"), new Object[] { name, //$NON-NLS-1$
										category.getId() });
						categoryIdsByUniqueName.put(uniqueName, category
								.getId());
						categoryUniqueNamesById.put(category.getId(),
								uniqueName);
					}
				}
			}

			schemeIdsByUniqueName = new HashMap();
			schemeUniqueNamesById = new HashMap();

			for (Iterator iterator = schemesByName.entrySet().iterator(); iterator
					.hasNext();) {
				Map.Entry entry = (Map.Entry) iterator.next();
				String name = (String) entry.getKey();
				Set keyConfigurations = (Set) entry.getValue();
				Iterator iterator2 = keyConfigurations.iterator();

				if (keyConfigurations.size() == 1) {
					Scheme scheme = (Scheme) iterator2.next();
					schemeIdsByUniqueName.put(name, scheme.getId());
					schemeUniqueNamesById.put(scheme.getId(), name);
				} else {
					while (iterator2.hasNext()) {
						Scheme scheme = (Scheme) iterator2.next();
						String uniqueName = MessageFormat.format(
								Util.translateString(RESOURCE_BUNDLE,
										"uniqueName"), new Object[] { name, //$NON-NLS-1$
										scheme.getId() });
						schemeIdsByUniqueName.put(uniqueName, scheme.getId());
						schemeUniqueNamesById.put(scheme.getId(), uniqueName);
					}
				}
			}

			Scheme activeScheme = bindingService.getActiveScheme();

			try {
				for (int i = 0; i < definedSchemes.length; i++) {
					final Scheme scheme = definedSchemes[i];
					final Scheme copy = localChangeManager.getScheme(scheme
							.getId());
					copy.define(scheme.getName(), scheme.getDescription(),
							scheme.getParentId());
				}
				localChangeManager.setActiveScheme(bindingService
						.getActiveScheme());
			} catch (final NotDefinedException e) {
				throw new Error(
						"There is a programmer error in the keys preference page"); //$NON-NLS-1$
			}
			localChangeManager.setLocale(bindingService.getLocale());
			localChangeManager.setPlatform(bindingService.getPlatform());
			localChangeManager.setBindings(bindingService.getBindings());

			List categoryNames = new ArrayList(categoryIdsByUniqueName.keySet());
			Collections.sort(categoryNames, Collator.getInstance());
			if (commandIdsByCategoryId.containsKey(null)) {
				categoryNames.add(0, Util.translateString(RESOURCE_BUNDLE,
						"other")); //$NON-NLS-1$
			}
			comboCategory.setItems((String[]) categoryNames
					.toArray(new String[categoryNames.size()]));
			comboCategory.clearSelection();
			comboCategory.deselectAll();
			if (commandIdsByCategoryId.containsKey(null)
					|| !categoryNames.isEmpty()) {
				comboCategory.select(0);
			}

			List schemeNames = new ArrayList(schemeIdsByUniqueName.keySet());
			Collections.sort(schemeNames, Collator.getInstance());
			comboScheme.setItems((String[]) schemeNames
					.toArray(new String[schemeNames.size()]));
			setScheme(activeScheme);

			update(true);
		}

		super.setVisible(visible);
	}

	private final void update() {
		update(false);
	}

	private final void update(final boolean updateViewTab) {
		if (updateViewTab) {
			updateViewTab();
		}
		updateComboCommand();
		updateComboContext();
		final TriggerSequence triggerSequence = getKeySequence();
		updateTableBindingsForTriggerSequence(triggerSequence);
		final ParameterizedCommand command = getParameterizedCommand();
		updateTableBindingsForCommand(command);
		final String contextId = getContextId();
		updateSelection(tableBindingsForTriggerSequence, contextId,
				triggerSequence);
		updateSelection(tableBindingsForCommand, contextId, triggerSequence);
		updateLabelSchemeExtends();
		updateLabelContextExtends();
		updateEnabled(triggerSequence, command);
	}

	private final void updateComboCommand() {
		final ParameterizedCommand command = getParameterizedCommand();

		final String categoryId = getCategoryId();
		Set commandIds = (Set) commandIdsByCategoryId.get(categoryId);
		if (commandIds==null) {
			commandIds = Collections.EMPTY_SET;
		}

		List commands = new ArrayList();
		final Iterator commandIdItr = commandIds.iterator();
		while (commandIdItr.hasNext()) {
			final String currentCommandId = (String) commandIdItr.next();
			final Command currentCommand = commandService
					.getCommand(currentCommandId);
			try {
				commands.addAll(ParameterizedCommand
						.generateCombinations(currentCommand));
			} catch (final NotDefinedException e) {
			}
		}
		
		commands = sortParameterizedCommands(commands);
		
		final int commandCount = commands.size();
		this.commands = (ParameterizedCommand[]) commands
				.toArray(new ParameterizedCommand[commandCount]);

		final String[] commandNames = new String[commandCount];
		for (int i = 0; i < commandCount; i++) {
			try {
				commandNames[i] = this.commands[i].getName();
			} catch (final NotDefinedException e) {
				throw new Error(
						"Concurrent modification of the command's defined state"); //$NON-NLS-1$
			}
		}

		final String[] currentItems = comboCommand.getItems();
		if (!Arrays.equals(currentItems, commandNames)) {
			comboCommand.setItems(commandNames);
		}

		setParameterizedCommand(command);

		if ((comboCommand.getSelectionIndex() == -1) && (commandCount > 0)) {
			comboCommand.select(0);
		}
	}
	
	private List sortParameterizedCommands(List commands) {
		final Collator collator = Collator.getInstance();
		
		Comparator comparator = new Comparator() {
			@Override
			public int compare(Object o1, Object o2) {
				String name1 = null;
				String name2 = null;
				try {
					name1 = ((ParameterizedCommand) o1).getName();
				} catch (NotDefinedException e) {
					return -1;
				}
				try {
					name2 = ((ParameterizedCommand) o2).getName();
				} catch (NotDefinedException e) {
					return 1;
				}
				int rc = collator.compare(name1, name2);
				if (rc != 0) {
					return rc;
				}

				String id1 = ((ParameterizedCommand) o1).getId();
				String id2 = ((ParameterizedCommand) o2).getId();
				return collator.compare(id1, id2);
			}
		};
		Collections.sort(commands, comparator);
		return commands;
	}

	private final void updateComboContext() {
		final String contextId = getContextId();
		final Map contextIdsByName = new HashMap(contextIdsByUniqueName);

		final List contextNames = new ArrayList(contextIdsByName.keySet());
		Collections.sort(contextNames, Collator.getInstance());

		comboContext.setItems((String[]) contextNames
				.toArray(new String[contextNames.size()]));
		setContextId(contextId);

		if (comboContext.getSelectionIndex() == -1 && !contextNames.isEmpty()) {
			comboContext.select(0);
		}
	}

	private final void updateEnabled(final TriggerSequence triggerSequence,
			final ParameterizedCommand command) {
		final boolean commandSelected = command != null;
		labelBindingsForCommand.setEnabled(commandSelected);
		tableBindingsForCommand.setEnabled(commandSelected);

		final boolean triggerSequenceSelected = !triggerSequence.isEmpty();
		labelBindingsForTriggerSequence.setEnabled(triggerSequenceSelected);
		tableBindingsForTriggerSequence.setEnabled(triggerSequenceSelected);

		final boolean buttonsEnabled = commandSelected
				&& triggerSequenceSelected;
		buttonAdd.setEnabled(buttonsEnabled);
		buttonRemove.setEnabled(buttonsEnabled);
		buttonRestore.setEnabled(buttonsEnabled);
	}

	private final void updateLabelContextExtends() {
		final String contextId = getContextId();

		if (contextId != null) {
			final Context context = contextService.getContext(getContextId());
			if (context.isDefined()) {
				try {
					final String parentId = context.getParentId();
					if (parentId != null) {
						final String name = (String) contextUniqueNamesById
								.get(parentId);
						if (name != null) {
							labelContextExtends.setText(MessageFormat.format(
									Util.translateString(RESOURCE_BUNDLE,
											"extends"), //$NON-NLS-1$
									new Object[] { name }));
							return;
						}
					}
				} catch (final NotDefinedException e) {
				}
			}
		}

		labelContextExtends.setText(Util.ZERO_LENGTH_STRING);
	}

	private final void updateLabelSchemeExtends() {
		final String schemeId = getSchemeId();

		if (schemeId != null) {
			final Scheme scheme = bindingService.getScheme(schemeId);
			try {
				final String name = (String) schemeUniqueNamesById.get(scheme
						.getParentId());
				if (name != null) {
					labelSchemeExtends.setText(MessageFormat.format(Util
							.translateString(RESOURCE_BUNDLE, "extends"), //$NON-NLS-1$
							new Object[] { name }));
					return;
				}
			} catch (final NotDefinedException e) {
			}
		}

		labelSchemeExtends.setText(Util.ZERO_LENGTH_STRING);
	}

	private final void updateSelection(final Table table,
			final String contextId, final TriggerSequence triggerSequence) {
		if (table.getSelectionCount() > 1) {
			table.deselectAll();
		}

		final TableItem[] items = table.getItems();
		int selection = -1;
		for (int i = 0; i < items.length; i++) {
			final Binding binding = (Binding) items[i].getData(ITEM_DATA_KEY);
			if ((Util.equals(contextId, binding.getContextId()))
					&& (Util.equals(triggerSequence, binding
							.getTriggerSequence()))) {
				selection = i;
				break;
			}
		}

		if (selection != -1) {
			table.select(selection);
		}
	}

	private final void updateTableBindingsForCommand(
			final ParameterizedCommand parameterizedCommand) {
		tableBindingsForCommand.removeAll();

		final Collection bindings = localChangeManager
				.getActiveBindingsDisregardingContextFlat();
		final Iterator bindingItr = bindings.iterator();
		while (bindingItr.hasNext()) {
			final Binding binding = (Binding) bindingItr.next();
			if (!Util.equals(parameterizedCommand, binding
					.getParameterizedCommand())) {
				continue; // binding does not match
			}

			final TableItem tableItem = new TableItem(tableBindingsForCommand,
					SWT.NULL);
			tableItem.setData(ITEM_DATA_KEY, binding);

			if (binding.getType() == Binding.SYSTEM) {
				tableItem.setImage(0, IMAGE_BLANK);
			} else {
				tableItem.setImage(0, IMAGE_CHANGE);
			}

			String contextName = (String) contextUniqueNamesById.get(binding
					.getContextId());
			if (contextName == null) {
				contextName = Util.ZERO_LENGTH_STRING;
			}
			tableItem.setText(1, contextName);
			tableItem.setText(2, binding.getTriggerSequence().format());
		}
	}

	private final void updateTableBindingsForTriggerSequence(
			final TriggerSequence triggerSequence) {
		tableBindingsForTriggerSequence.removeAll();

		final Map activeBindings = localChangeManager
				.getActiveBindingsDisregardingContext();
		final Collection bindings = (Collection) activeBindings
				.get(triggerSequence);
		if (bindings == null) {
			return;
		}

		final Iterator bindingItr = bindings.iterator();
		while (bindingItr.hasNext()) {
			final Binding binding = (Binding) bindingItr.next();
			final Context context = contextService.getContext(binding
					.getContextId());
			final ParameterizedCommand parameterizedCommand = binding
					.getParameterizedCommand();
			final Command command = parameterizedCommand.getCommand();
			if ((!context.isDefined()) && (!command.isDefined())) {
				continue;
			}

			final TableItem tableItem = new TableItem(
					tableBindingsForTriggerSequence, SWT.NULL);
			tableItem.setData(ITEM_DATA_KEY, binding);

			if (binding.getType() == Binding.SYSTEM) {
				tableItem.setImage(0, IMAGE_BLANK);
			} else {
				tableItem.setImage(0, IMAGE_CHANGE);
			}

			try {
				tableItem.setText(1, context.getName());
				tableItem.setText(2, parameterizedCommand.getName());
			} catch (final NotDefinedException e) {
				throw new Error(
						"Context or command became undefined on a non-UI thread while the UI thread was processing."); //$NON-NLS-1$
			}
		}
	}

	private final void updateViewTab() {
		tableBindings.removeAll();

		final List bindings = new ArrayList(localChangeManager
				.getActiveBindingsDisregardingContextFlat());
		Collections.sort(bindings, new Comparator() {
			@Override
			public final int compare(final Object object1, final Object object2) {
				final Binding binding1 = (Binding) object1;
				final Binding binding2 = (Binding) object2;

				final Command command1 = binding1.getParameterizedCommand()
						.getCommand();
				String categoryName1 = Util.ZERO_LENGTH_STRING;
				String commandName1 = Util.ZERO_LENGTH_STRING;
				try {
					commandName1 = command1.getName();
					categoryName1 = command1.getCategory().getName();
				} catch (final NotDefinedException e) {
				}
				final String triggerSequence1 = binding1.getTriggerSequence()
						.format();
				final String contextId1 = binding1.getContextId();
				String contextName1 = Util.ZERO_LENGTH_STRING;
				if (contextId1 != null) {
					final Context context = contextService
							.getContext(contextId1);
					try {
						contextName1 = context.getName();
					} catch (final org.eclipse.core.commands.common.NotDefinedException e) {
					}
				}

				final Command command2 = binding2.getParameterizedCommand()
						.getCommand();
				String categoryName2 = Util.ZERO_LENGTH_STRING;
				String commandName2 = Util.ZERO_LENGTH_STRING;
				try {
					commandName2 = command2.getName();
					categoryName2 = command2.getCategory().getName();
				} catch (final org.eclipse.core.commands.common.NotDefinedException e) {
				}
				final String keySequence2 = binding2.getTriggerSequence()
						.format();
				final String contextId2 = binding2.getContextId();
				String contextName2 = Util.ZERO_LENGTH_STRING;
				if (contextId2 != null) {
					final Context context = contextService
							.getContext(contextId2);
					try {
						contextName2 = context.getName();
					} catch (final org.eclipse.core.commands.common.NotDefinedException e) {
					}
				}

				int compare = 0;
				for (int i = 0; i < sortOrder.length; i++) {
					switch (sortOrder[i]) {
					case VIEW_CATEGORY_COLUMN_INDEX:
						compare = Util.compare(categoryName1, categoryName2);
						if (compare != 0) {
							return compare;
						}
						break;
					case VIEW_COMMAND_COLUMN_INDEX:
						compare = Util.compare(commandName1, commandName2);
						if (compare != 0) {
							return compare;
						}
						break;
					case VIEW_KEY_SEQUENCE_COLUMN_INDEX:
						compare = Util.compare(triggerSequence1, keySequence2);
						if (compare != 0) {
							return compare;
						}
						break;
					case VIEW_CONTEXT_COLUMN_INDEX:
						compare = Util.compare(contextName1, contextName2);
						if (compare != 0) {
							return compare;
						}
						break;
					default:
						throw new Error(
								"Programmer error: added another sort column without modifying the comparator."); //$NON-NLS-1$
					}
				}

				return compare;
			}

			@Override
			public final boolean equals(final Object object) {
				return super.equals(object);
			}
		});

		final Iterator keyBindingItr = bindings.iterator();
		while (keyBindingItr.hasNext()) {
			final Binding binding = (Binding) keyBindingItr.next();

			final ParameterizedCommand command = binding
					.getParameterizedCommand();
			String commandName = Util.ZERO_LENGTH_STRING;
			String categoryName = Util.ZERO_LENGTH_STRING;
			try {
				commandName = command.getName();
				categoryName = command.getCommand().getCategory().getName();
			} catch (final org.eclipse.core.commands.common.NotDefinedException e) {
			}

			if ((commandName == null) || (commandName.length() == 0)) {
				continue;
			}

			final String contextId = binding.getContextId();
			String contextName = Util.ZERO_LENGTH_STRING;
			if (contextId != null) {
				final Context context = contextService.getContext(contextId);
				try {
					contextName = context.getName();
				} catch (final org.eclipse.core.commands.common.NotDefinedException e) {
				}
			}

			final TableItem item = new TableItem(tableBindings, SWT.NONE);
			item.setText(VIEW_CATEGORY_COLUMN_INDEX, categoryName);
			item.setText(VIEW_COMMAND_COLUMN_INDEX, commandName);
			item.setText(VIEW_KEY_SEQUENCE_COLUMN_INDEX, binding
					.getTriggerSequence().format());
			item.setText(VIEW_CONTEXT_COLUMN_INDEX, contextName);
			item.setData(BINDING_KEY, binding);
		}

		for (int i = 0; i < tableBindings.getColumnCount(); i++) {
			tableBindings.getColumn(i).pack();
		}
	}
	
	
}
