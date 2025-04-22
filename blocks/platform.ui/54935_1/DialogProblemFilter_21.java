
package org.eclipse.ui.views.markers.internal;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.IInputValidator;
import org.eclipse.jface.dialogs.InputDialog;
import org.eclipse.jface.dialogs.TrayDialog;
import org.eclipse.jface.viewers.CheckStateChangedEvent;
import org.eclipse.jface.viewers.CheckboxTableViewer;
import org.eclipse.jface.viewers.CheckboxTreeViewer;
import org.eclipse.jface.viewers.ColumnWeightData;
import org.eclipse.jface.viewers.ICheckStateListener;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TableLayout;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerComparator;
import org.eclipse.jface.window.Window;
import org.eclipse.osgi.util.NLS;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeColumn;
import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.ui.IWorkingSet;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.dialogs.IWorkingSetSelectionDialog;

public abstract class DialogMarkerFilter extends TrayDialog {

	static final int SELECT_ALL_FILTERS_ID = IDialogConstants.CLIENT_ID + 4;

	static final int DESELECT_ALL_FILTERS_ID = IDialogConstants.CLIENT_ID + 5;

	static final int RESET_ID = IDialogConstants.CLIENT_ID;

	static final int SELECT_WORKING_SET_ID = IDialogConstants.CLIENT_ID + 1;

	static final int SELECT_ALL_ID = IDialogConstants.CLIENT_ID + 2;

	static final int DESELECT_ALL_ID = IDialogConstants.CLIENT_ID + 3;

	private class TypesLabelProvider extends LabelProvider implements
			ITableLabelProvider {

		@Override
		public Image getColumnImage(Object element, int columnIndex) {
			return null;
		}

		@Override
		public String getColumnText(Object element, int columnIndex) {
			return ((AbstractNode) element).getName();
		}
	}

	private class WorkingSetGroup {

		private Button button;

		private Button selectButton;

		WorkingSetGroup(Composite parent) {
			button = createRadioButton(parent,
					MarkerMessages.filtersDialog_noWorkingSet);
			GridData data = new GridData(GridData.FILL_HORIZONTAL);
			button.setLayoutData(data);

			Composite composite = new Composite(parent, SWT.NONE);
			composite.setFont(parent.getFont());
			GridLayout layout = new GridLayout();
			Button radio = new Button(parent, SWT.RADIO);
			layout.marginWidth = radio.computeSize(SWT.DEFAULT, SWT.DEFAULT).x;
			layout.marginHeight = 0;
			radio.dispose();
			composite.setLayout(layout);
			selectButton = createButton(composite, SELECT_WORKING_SET_ID,
					MarkerMessages.filtersDialog_workingSetSelect, false);
		}

		boolean getSelection() {
			return button.getSelection();
		}

		IWorkingSet getWorkingSet() {
			return (IWorkingSet) button.getData();
		}

		void setSelection(boolean selected) {
			button.setSelection(selected);
			if (selected) {
				anyResourceButton.setSelection(false);
				anyResourceInSameProjectButton.setSelection(false);
				selectedResourceButton.setSelection(false);
				selectedResourceAndChildrenButton.setSelection(false);
			}
		}

		void selectPressed() {
			IWorkingSetSelectionDialog dialog = PlatformUI.getWorkbench()
					.getWorkingSetManager().createWorkingSetSelectionDialog(
							getShell(), false);
			IWorkingSet workingSet = getWorkingSet();

			if (workingSet != null) {
				dialog.setSelection(new IWorkingSet[] { workingSet });
			}
			if (dialog.open() == Window.OK) {
				markDirty();
				IWorkingSet[] result = dialog.getSelection();
				if (result != null && result.length > 0) {
					setWorkingSet(result[0]);
				} else {
					setWorkingSet(null);
				}
				if (getSelection() == false) {
					setSelection(true);
				}
			}
		}

		void setWorkingSet(IWorkingSet workingSet) {
			button.setData(workingSet);
			if (workingSet != null) {
				button.setText(NLS.bind(
						MarkerMessages.filtersDialog_workingSet, workingSet
								.getLabel()));
			} else {
				button.setText(MarkerMessages.filtersDialog_noWorkingSet);
			}
		}

		void setEnabled(boolean enabled) {
			button.setEnabled(enabled);
			selectButton.setEnabled(enabled);
		}
	}

	private abstract class AbstractNode {

		public abstract Object getParent();

		public abstract String getName();

		public abstract boolean hasChildren();

		public abstract Object[] getChildren();

		public abstract boolean isCategory();

	}

	private class MarkerTypeNode extends AbstractNode {

		MarkerType type;

		MarkerCategory category;

		public MarkerTypeNode(MarkerType markerType) {
			type = markerType;
			nodeToTypeMapping.put(markerType.getId(), this);
		}

		public void setCategory(MarkerCategory category) {
			this.category = category;
		}

		@Override
		public Object[] getChildren() {
			return new Object[0];
		}

		@Override
		public Object getParent() {
			return category;
		}

		@Override
		public boolean hasChildren() {
			return false;
		}

		@Override
		public String getName() {
			return type.getLabel();
		}

		@Override
		public boolean isCategory() {
			return false;
		}

		public Object getMarkerType() {
			return type;
		}
	}

	private class MarkerCategory extends AbstractNode {

		String name;

		Collection types = new ArrayList();

		public MarkerCategory(String categoryName) {
			name = categoryName;
		}

		@Override
		public String getName() {
			return name;
		}

		public void add(MarkerTypeNode markerType) {
			types.add(markerType);
			markerType.setCategory(this);
		}

		public Object[] getMarkerTypes() {
			return types.toArray();
		}

		@Override
		public Object[] getChildren() {
			return getMarkerTypes();
		}

		@Override
		public Object getParent() {
			return null;
		}

		@Override
		public boolean hasChildren() {
			return true;
		}

		@Override
		public boolean isCategory() {
			return true;
		}

	}

	private MarkerFilter[] filters;

	private CheckboxTreeViewer typesViewer;

	private Button anyResourceButton;

	private Button anyResourceInSameProjectButton;

	private Button selectedResourceButton;

	private Button selectedResourceAndChildrenButton;

	private Button selectAllButton;

	private Button deselectAllButton;

	private WorkingSetGroup workingSetGroup;

	private boolean dirty = false;

	private CheckboxTableViewer filtersList;

	private MarkerFilter[] selectedFilters;

	private HashMap nodeToTypeMapping = new HashMap();

	private ITreeContentProvider typesContentProvider;

	DialogMarkerFilter(Shell parentShell, MarkerFilter[] filtersList) {
		super(parentShell);
		setFilters(filtersList);
	}

	private void setFilters(MarkerFilter[] initialFilters) {
		MarkerFilter[] newMarkers = new MarkerFilter[initialFilters.length];
		for (int i = 0; i < initialFilters.length; i++) {
			MarkerFilter newFilter;
			try {
				newFilter = initialFilters[i].makeClone();
			} catch (CloneNotSupportedException exception) {
				ErrorDialog.openError(getShell(),
						MarkerMessages.MarkerFilterDialog_errorTitle,
						MarkerMessages.MarkerFilterDialog_failedFilterMessage,
						Util.errorStatus(exception));
				return;
			}

			newMarkers[i] = newFilter;

		}
		filters = newMarkers;

	}

	@Override
	protected void buttonPressed(int buttonId) {

		switch (buttonId) {
		case RESET_ID:
			resetPressed();
			markDirty();
			break;
		case SELECT_WORKING_SET_ID:
			workingSetGroup.selectPressed();
			break;
		case SELECT_ALL_ID:
			setAllTypesChecked(true);
			break;
		case DESELECT_ALL_ID:
			setAllTypesChecked(false);
			break;
		case SELECT_ALL_FILTERS_ID:
			filtersList.setAllChecked(true);
			break;
		case DESELECT_ALL_FILTERS_ID:
			filtersList.setAllChecked(false);
			break;
		default:
			break;
		}
		super.buttonPressed(buttonId);
	}

	private void setAllTypesChecked(boolean checked) {
		TreeItem[] items = typesViewer.getTree().getItems();
		for (int i = 0; i < items.length; i++) {
			Object element = items[i].getData();
			typesViewer.setSubtreeChecked(element, checked);
		}
		
	}

	@Override
	protected void configureShell(Shell newShell) {
		super.configureShell(newShell);
		newShell.setText(MarkerMessages.filtersDialog_title);
	}

	protected void createResetArea(Composite parent) {

		Button reset = new Button(parent, SWT.PUSH);
		reset.setText(MarkerMessages.restoreDefaults_text);
		reset.setData(new Integer(RESET_ID));

		reset.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent event) {
				buttonPressed(((Integer) event.widget.getData()).intValue());
			}
		});

		GridData data = new GridData(GridData.HORIZONTAL_ALIGN_END);
		int widthHint = convertHorizontalDLUsToPixels(IDialogConstants.BUTTON_WIDTH);
		Point minSize = reset.computeSize(SWT.DEFAULT, SWT.DEFAULT, true);
		data.widthHint = Math.max(widthHint, minSize.x);
		data.horizontalSpan = 2;
		reset.setLayoutData(data);
	}

	protected Button createCheckbox(Composite parent, String text,
			boolean grabRow) {
		Button button = new Button(parent, SWT.CHECK);
		if (grabRow) {
			GridData gridData = new GridData(GridData.FILL_HORIZONTAL);
			button.setLayoutData(gridData);
		}
		button.setText(text);
		button.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				updateForSelection();
			}
		});
		button.setFont(parent.getFont());
		return button;
	}

	protected Combo createCombo(Composite parent, String[] items,
			int selectionIndex) {
		Combo combo = new Combo(parent, SWT.DROP_DOWN | SWT.READ_ONLY);
		combo.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		combo.setFont(parent.getFont());
		combo.setItems(items);
		combo.select(selectionIndex);
		combo.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				updateForSelection();
			}
		});
		return combo;
	}

	@Override
	protected Control createDialogArea(Composite parent) {
		Composite dialogArea = (Composite) super.createDialogArea(parent);

		dialogArea.setLayout(new GridLayout(2, false));

		createFiltersArea(dialogArea);

		Composite selectedComposite = createSelectedFilterArea(dialogArea);
		selectedComposite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true,
				true));
		updateUIFromFilter();

		filtersList.setSelection(new StructuredSelection(filters[0]));

		createResetArea(dialogArea);
		createSeparatorLine(dialogArea);

		applyDialogFont(dialogArea);
		return dialogArea;
	}

	void createFiltersArea(Composite dialogArea) {

		Composite listArea = new Composite(dialogArea, SWT.NONE);
		listArea.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		listArea.setLayout(new GridLayout());

		createUserFiltersArea(listArea);
		createFilterSelectButtons(listArea);
	}

	void createUserFiltersArea(Composite listArea) {

		Composite userComposite = new Composite(listArea, SWT.NONE);
		userComposite.setLayout(new GridLayout(2, false));
		userComposite
				.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

		Label title = new Label(userComposite, SWT.NONE);
		title.setText(MarkerMessages.MarkerFilter_filtersTitle);
		GridData titleData = new GridData();
		titleData.horizontalSpan = 2;
		title.setLayoutData(titleData);

		filtersList = CheckboxTableViewer.newCheckList(userComposite,
				SWT.BORDER);
		filtersList.setContentProvider(new IStructuredContentProvider() {
			@Override
			public Object[] getElements(Object inputElement) {
				return filters;
			}

			@Override
			public void dispose() {
			}

			@Override
			public void inputChanged(Viewer viewer, Object oldInput,
					Object newInput) {
			}
		});

		filtersList.setLabelProvider(new LabelProvider() {
			@Override
			public String getText(Object element) {
				return ((MarkerFilter) element).getName();
			}
		});

		selectedFilters = new MarkerFilter[] { filters[0] };
		filtersList.setSelection(new StructuredSelection(selectedFilters));

		filtersList
				.addSelectionChangedListener(new ISelectionChangedListener() {

					@Override
					public void selectionChanged(SelectionChangedEvent event) {
						updateFilterFromUI();
						setSelectedFilter(event);

					}
				});

		filtersList.setInput(this);
		for (int i = 0; i < filters.length; i++) {
			filtersList.setChecked(filters[i], filters[i].isEnabled());
		}

		GridData listData = new GridData(SWT.FILL, SWT.FILL, true, true);
		listData.widthHint = convertHorizontalDLUsToPixels(100);
		filtersList.getControl().setLayoutData(listData);

		Composite buttons = new Composite(userComposite, SWT.NONE);
		GridLayout buttonLayout = new GridLayout();
		buttonLayout.marginWidth = 0;
		buttons.setLayout(buttonLayout);
		GridData buttonsData = new GridData();
		buttonsData.verticalAlignment = GridData.BEGINNING;
		buttons.setLayoutData(buttonsData);

		Button addNew = new Button(buttons, SWT.PUSH);
		addNew.setText(MarkerMessages.MarkerFilter_addFilterName);
		addNew.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				InputDialog newDialog = new InputDialog(getShell(),
						MarkerMessages.MarkerFilterDialog_title,
						MarkerMessages.MarkerFilterDialog_message,
						MarkerMessages.MarkerFilter_newFilterName,
						new IInputValidator() {
							@Override
							public String isValid(String newText) {
								if (newText.length() == 0)
									return MarkerMessages.MarkerFilterDialog_emptyMessage;
								for (int i = 0; i < filters.length; i++) {
									if (filters[i].getName().equals(newText))
										return NLS
												.bind(
														MarkerMessages.filtersDialog_conflictingName,
														newText);

								}
								return null;
							}
						});
				newDialog.open();
				String newName = newDialog.getValue();
				if (newName != null) {
					createNewFilter(newName);
				}
			}
		});
		setButtonLayoutData(addNew);

		Button remove = new Button(buttons, SWT.PUSH);
		remove.setText(MarkerMessages.MarkerFilter_deleteSelectedName);
		remove.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				removeFilters(filtersList.getSelection());
			}
		});
		setButtonLayoutData(remove);
	}

	protected void setSelectedFilter(SelectionChangedEvent event) {

		ISelection selection = event.getSelection();
		if (selection instanceof IStructuredSelection) {
			Collection list = ((IStructuredSelection) selection).toList();
			MarkerFilter[] selected = new MarkerFilter[list.size()];
			list.toArray(selected);
			selectedFilters = selected;

		} else {
			selectedFilters = new MarkerFilter[0];
		}
		updateUIFromFilter();

	}

	protected void removeFilters(ISelection selection) {
		if (selection instanceof IStructuredSelection) {
			List toRemove = ((IStructuredSelection) selection).toList();
			MarkerFilter[] newFilters = new MarkerFilter[filters.length
					- toRemove.size()];
			int index = 0;
			for (int i = 0; i < filters.length; i++) {
				if (toRemove.contains(filters[i])) {
					continue;
				}
				newFilters[index] = filters[i];
				index++;
			}

			filters = newFilters;
			filtersList.refresh();
			updateUIFromFilter();
		}
	}

	private void createNewFilter(String newName) {
		MarkerFilter[] newFilters = new MarkerFilter[filters.length + 1];
		System.arraycopy(filters, 0, newFilters, 0, filters.length);
		MarkerFilter filter = newFilter(newName);
		newFilters[filters.length] = filter;
		filters = newFilters;
		filtersList.refresh();
		filtersList.setSelection(new StructuredSelection(filter), true);
		filtersList.getControl().setFocus();
	}

	protected abstract MarkerFilter newFilter(String newName);

	Composite createSelectedFilterArea(Composite composite) {

		Composite selectedComposite = new Composite(composite, SWT.NONE);
		selectedComposite.setLayout(new GridLayout(2, false));

		Composite leftComposite = new Composite(selectedComposite, SWT.NONE);
		leftComposite.setLayout(new GridLayout());
		leftComposite
				.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		createResourceArea(leftComposite);
		createAttributesArea(leftComposite);

		Composite rightComposite = new Composite(selectedComposite, SWT.NONE);
		createTypesArea(rightComposite);
		rightComposite.setLayout(new GridLayout());
		rightComposite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true,
				true));

		return selectedComposite;
	}

	protected void createSeparatorLine(Composite parent) {
		Label separator = new Label(parent, SWT.HORIZONTAL | SWT.SEPARATOR);
		GridData gd = new GridData(GridData.FILL_HORIZONTAL);
		gd.horizontalSpan = 2;
		separator.setLayoutData(gd);
	}

	protected Button createRadioButton(Composite parent, String text) {
		Button button = new Button(parent, SWT.RADIO);
		button.setText(text);
		button.setFont(parent.getFont());
		button.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				updateForSelection();
			}
		});
		return button;
	}

	protected void createResourceArea(Composite parent) {
		Composite group = new Composite(parent, SWT.NONE);
		group.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		group.setLayout(new GridLayout());
		group.setFont(parent.getFont());
		anyResourceButton = createRadioButton(group,
				MarkerMessages.filtersDialog_anyResource);
		anyResourceInSameProjectButton = createRadioButton(group,
				MarkerMessages.filtersDialog_anyResourceInSameProject); // added
		selectedResourceButton = createRadioButton(group,
				MarkerMessages.filtersDialog_selectedResource);
		selectedResourceAndChildrenButton = createRadioButton(group,
				MarkerMessages.filtersDialog_selectedAndChildren);
		workingSetGroup = new WorkingSetGroup(group);
	}

	protected void createTypesArea(Composite parent) {

		Composite composite = new Composite(parent, SWT.NONE);
		composite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		GridLayout layout = new GridLayout();
		composite.setLayout(layout);

		Label label = new Label(composite, SWT.NONE);
		label.setText(MarkerMessages.filtersDialog_showItemsOfType);

		Tree tree = new Tree(composite, SWT.CHECK | SWT.H_SCROLL | SWT.V_SCROLL
				| SWT.MULTI | SWT.FULL_SELECTION | SWT.BORDER);
		tree.setLinesVisible(true);
		tree.setHeaderVisible(false);
		TableLayout tableLayout = new TableLayout();
		tree.setLayout(tableLayout);
		tableLayout.addColumnData(new ColumnWeightData(100, true));
		new TreeColumn(tree, SWT.NONE, 0);

		typesViewer = new CheckboxTreeViewer(tree);
		GridData gridData = new GridData(SWT.FILL, SWT.FILL, true, true);
		gridData.widthHint = convertVerticalDLUsToPixels(100);
		gridData.heightHint = convertVerticalDLUsToPixels(125);

		typesContentProvider = getTypesContentProvider();
		typesViewer.getControl().setLayoutData(gridData);
		typesViewer.setContentProvider(typesContentProvider);
		typesViewer.setLabelProvider(getLabelProvider());
		typesViewer.setComparator(getComparator());
		typesViewer.addCheckStateListener(new ICheckStateListener() {
			@Override
			public void checkStateChanged(CheckStateChangedEvent event) {
				markDirty();
				Object element = event.getElement();
				boolean checked = event.getChecked();
				setChildrenChecked(element, checked);
				setParentCheckState(element, checked);
			}
		});
		typesViewer.setInput(getSelectedFilter().getRootTypes().toArray());

		Composite buttonComposite = new Composite(composite, SWT.NONE);
		GridLayout buttonLayout = new GridLayout();
		buttonLayout.marginWidth = 0;
		buttonComposite.setLayout(buttonLayout);
		selectAllButton = createButton(buttonComposite, SELECT_ALL_ID,
				MarkerMessages.filtersDialog_selectAllTypes, false);
		deselectAllButton = createButton(buttonComposite, DESELECT_ALL_ID,
				MarkerMessages.filtersDialog_deselectAllTypes, false);
	}

	protected MarkerFilter getSelectedFilter() {

		if (selectedFilters.length == 1) {
			return selectedFilters[0];
		}
		return null;
	}

	private ITreeContentProvider getTypesContentProvider() {
		return new ITreeContentProvider() {
			@Override
			public Object[] getElements(Object inputElement) {
				MarkerFilter selected = getSelectedFilter();
				if (selected == null) {
					return new Object[0];
				}

				return getRootEntries(selected);
			}

			@Override
			public void dispose() {
			}

			@Override
			public void inputChanged(Viewer viewer, Object oldInput,
					Object newInput) {
			}

			@Override
			public Object[] getChildren(Object parentElement) {
				return ((AbstractNode) parentElement).getChildren();
			}

			@Override
			public Object getParent(Object element) {
				return ((AbstractNode) element).getParent();
			}

			@Override
			public boolean hasChildren(Object element) {
				return ((AbstractNode) element).hasChildren();
			}
		};
	}

	abstract void createAttributesArea(Composite parent);

	private ILabelProvider getLabelProvider() {
		return new TypesLabelProvider();
	}

	protected List getSelectedTypes() {
		Object[] checkElements = typesViewer.getCheckedElements();
		List selected = new ArrayList();
		for (int i = 0; i < checkElements.length; i++) {
			AbstractNode node = (AbstractNode) checkElements[i];
			if (!node.isCategory()) {
				selected.add(((MarkerTypeNode) node).getMarkerType());
			}

		}
		return selected;
	}

	protected ViewerComparator getComparator() {
		return new ViewerComparator() {
			@Override
			public int compare(Viewer viewer, Object e1, Object e2) {
				return getComparator().compare(((AbstractNode) e1).getName(),
						((AbstractNode) e2).getName());
			}
		};
	}

	@Override
	protected void okPressed() {
		updateFilterFromUI();

		for (int i = 0; i < filters.length; i++) {
			filters[i].setEnabled(filtersList.getChecked(filters[i]));

		}
		super.okPressed();
	}

	protected void resetPressed() {
		setAllTypesChecked(true);
		int onResource = MarkerFilter.DEFAULT_ON_RESOURCE;
		anyResourceButton.setSelection(onResource == MarkerFilter.ON_ANY);
		anyResourceInSameProjectButton
				.setSelection(onResource == MarkerFilter.ON_ANY_IN_SAME_CONTAINER);
		selectedResourceButton
				.setSelection(onResource == MarkerFilter.ON_SELECTED_ONLY);
		selectedResourceAndChildrenButton
				.setSelection(onResource == MarkerFilter.ON_SELECTED_AND_CHILDREN);
		workingSetGroup.setSelection(onResource == MarkerFilter.ON_WORKING_SET);
		updateEnabledState(true);
	}

	void setSelectedTypes(List markerTypes) {
		typesViewer.setCheckedElements(new Object[0]);
		for (int i = 0; i < markerTypes.size(); i++) {
			Object obj = markerTypes.get(i);
			if (obj instanceof MarkerType) {

				Object mapping = nodeToTypeMapping.get(((MarkerType) obj)
						.getId());
				if (mapping != null) {
					typesViewer.setChecked(mapping, true);
					setParentCheckState(mapping, true);
				}
			}
		}
	}

	protected void updateEnabledState(boolean enabled) {

		typesViewer.getTree().setEnabled(enabled);
		selectAllButton.setEnabled(enabled
				&& typesViewer.getTree().getItemCount() > 0);
		deselectAllButton.setEnabled(enabled
				&& typesViewer.getTree().getItemCount() > 0);

		anyResourceButton.setEnabled(enabled);
		anyResourceInSameProjectButton.setEnabled(enabled);
		selectedResourceButton.setEnabled(enabled);
		selectedResourceAndChildrenButton.setEnabled(enabled);
		workingSetGroup.setEnabled(enabled);
	}

	protected final void updateFilterFromUI() {

		MarkerFilter filter = getSelectedFilter();

		if (filter == null) {
			updateEnabledState(false);
			return;
		}

		updateFilterFromUI(filter);
	}

	protected void updateFilterFromUI(MarkerFilter filter) {

		filter.setSelectedTypes(getSelectedTypes());

		if (selectedResourceButton.getSelection()) {
			filter.setOnResource(MarkerFilter.ON_SELECTED_ONLY);
		} else if (selectedResourceAndChildrenButton.getSelection()) {
			filter.setOnResource(MarkerFilter.ON_SELECTED_AND_CHILDREN);
		} else if (anyResourceInSameProjectButton.getSelection()) {
			filter.setOnResource(MarkerFilter.ON_ANY_IN_SAME_CONTAINER);
		} else if (workingSetGroup.getSelection()) {
			filter.setOnResource(MarkerFilter.ON_WORKING_SET);
		} else {
			filter.setOnResource(MarkerFilter.ON_ANY);
		}

		filter.setWorkingSet(workingSetGroup.getWorkingSet());
	}

	protected final void updateUIFromFilter() {

		MarkerFilter filter = getSelectedFilter();

		if (filter == null) {
			updateEnabledState(false);
			return;
		}

		updateUIWithFilter(filter);
	}

	protected void updateUIWithFilter(MarkerFilter filter) {
		setSelectedTypes(filter.getSelectedTypes());

		int on = filter.getOnResource();
		anyResourceButton.setSelection(on == MarkerFilter.ON_ANY);
		anyResourceInSameProjectButton
				.setSelection(on == MarkerFilter.ON_ANY_IN_SAME_CONTAINER);
		selectedResourceButton
				.setSelection(on == MarkerFilter.ON_SELECTED_ONLY);
		selectedResourceAndChildrenButton
				.setSelection(on == MarkerFilter.ON_SELECTED_AND_CHILDREN);
		workingSetGroup.setSelection(on == MarkerFilter.ON_WORKING_SET);
		workingSetGroup.setWorkingSet(filter.getWorkingSet());

		updateEnabledState(true);
	}

	boolean isDirty() {
		return dirty;
	}

	void markDirty() {
		dirty = true;
	}

	public void setFilter(MarkerFilter newFilter) {
		setFilters(new MarkerFilter[] { newFilter });
		updateUIFromFilter();
	}

	public MarkerFilter[] getFilters() {
		return filters;
	}

	protected void updateForSelection() {
		updateEnabledState(true);
		markDirty();
	}

	Object[] getRootEntries(MarkerFilter selected) {

		List roots = selected.getRootTypes();
		List markerNodes = new ArrayList();
		HashMap categories = new HashMap();
		for (int i = 0; i < roots.size(); i++) {
			Object obj = roots.get(i);
			buildTypeTree(markerNodes, obj, categories);
		}
		return markerNodes.toArray();
	}

	private void buildTypeTree(List elements, Object obj, HashMap categories) {
		if (obj instanceof MarkerType) {

			MarkerType markerType = (MarkerType) obj;

			String categoryName = MarkerSupportRegistry.getInstance()
					.getCategory(markerType.getId());

			if (categoryName == null) {
				elements.add(new MarkerTypeNode(markerType));
			} else {
				MarkerCategory category;
				if (categories.containsKey(categoryName)) {
					category = (MarkerCategory) categories.get(categoryName);
				} else {
					category = new MarkerCategory(categoryName);
					categories.put(categoryName, category);
					elements.add(category);
				}
				MarkerTypeNode node = new MarkerTypeNode(markerType);
				category.add(node);
			}

			MarkerType[] subTypes = ((MarkerType) obj).getSubtypes();
			for (int j = 0; j < subTypes.length; j++) {
				buildTypeTree(elements, subTypes[j], categories);
			}
		}
	}

	private void setParentCheckState(Object element, boolean checked) {
		Object parent = typesContentProvider.getParent(element);
		if (parent == null) {
			return;
		}
		Object[] children = typesContentProvider.getChildren(parent);
		if (children.length == 0) {
			return;
		}
		if (checked) {// at least one is checked
			for (int i = 0; i < children.length; i++) {
				Object object = children[i];
				if (!typesViewer.getChecked(object)) {
					typesViewer.setGrayChecked(parent, true);
					return;
				}
			}
			typesViewer.setChecked(parent, true);
		} else {
			for (int i = 0; i < children.length; i++) {
				Object object = children[i];
				if (typesViewer.getChecked(object)) {
					typesViewer.setGrayChecked(parent, true);
					return;
				}
			}
			typesViewer.setChecked(parent, false);
		}

	}

	private void setChildrenChecked(Object element, boolean checked) {
		Object[] children = typesContentProvider.getChildren(element);
		if (children.length > 0) {
			for (int i = 0; i < children.length; i++) {
				typesViewer.setChecked(children[i], checked);
			}
		}
	}

	protected void createFilterSelectButtons(Composite listArea) {
		Composite buttons = new Composite(listArea, SWT.NONE);
		GridLayout buttonLayout = new GridLayout(2, false);
		buttonLayout.marginWidth = 0;
		buttons.setLayout(buttonLayout);

		createButton(buttons, SELECT_ALL_FILTERS_ID,
				MarkerMessages.filtersDialog_selectAll, false);
		createButton(buttons, DESELECT_ALL_FILTERS_ID,
				MarkerMessages.filtersDialog_deselectAll, false);
	}
	
    @Override
	protected boolean isResizable() {
    	return true;
    }
}
