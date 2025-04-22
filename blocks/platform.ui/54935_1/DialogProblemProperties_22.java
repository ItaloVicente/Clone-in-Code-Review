
package org.eclipse.ui.views.markers.internal;

import java.util.Iterator;
import java.util.List;

import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.viewers.CheckboxTableViewer;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.osgi.util.NLS;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.events.TraverseEvent;
import org.eclipse.swt.events.TraverseListener;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

public class DialogProblemFilter extends DialogMarkerFilter {

	private DescriptionGroup descriptionGroup;

	private SeverityGroup severityGroup;

	private Composite userFilterComposite;

	private Label systemSettingsLabel;

	private CheckboxTableViewer definedList;

	private class DescriptionGroup {
		private Label descriptionLabel;

		private Combo combo;

		private Text description;

		private String contains = MarkerMessages.filtersDialog_contains;

		private String doesNotContain = MarkerMessages.filtersDialog_doesNotContain;

		public DescriptionGroup(Composite parent) {

			Composite descriptionComposite = new Composite(parent, SWT.NONE);
			descriptionComposite.setLayout(new GridLayout(2, false));
			descriptionComposite.setLayoutData(new GridData(
					GridData.FILL_HORIZONTAL | GridData.GRAB_HORIZONTAL));

			descriptionLabel = new Label(descriptionComposite, SWT.NONE);
			descriptionLabel.setFont(parent.getFont());
			descriptionLabel
					.setText(MarkerMessages.filtersDialog_descriptionLabel);

			combo = new Combo(descriptionComposite, SWT.READ_ONLY);
			combo.setFont(parent.getFont());
			combo.add(contains);
			combo.add(doesNotContain);
			combo.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent e) {
					updateForSelection();
				}
			});
			combo.addTraverseListener(new TraverseListener() {
				@Override
				public void keyTraversed(TraverseEvent e) {
					if (e.detail == SWT.TRAVERSE_ESCAPE
							|| e.detail == SWT.TRAVERSE_RETURN) {
						e.doit = false;
					}
				}
			});

			description = new Text(descriptionComposite, SWT.SINGLE
					| SWT.BORDER);
			description.setFont(parent.getFont());
			GridData data = new GridData(GridData.FILL_HORIZONTAL);
			data.horizontalSpan = 2;

			description.setLayoutData(data);
			description.addModifyListener(new ModifyListener() {
				@Override
				public void modifyText(ModifyEvent e) {
					DialogProblemFilter.this.markDirty();
				}
			});
		}

		public boolean getContains() {
			return combo.getSelectionIndex() == combo.indexOf(contains);
		}

		public void setContains(boolean value) {
			if (value) {
				combo.select(combo.indexOf(contains));
			} else {
				combo.select(combo.indexOf(doesNotContain));
			}
		}

		public void setDescription(String text) {
			if (text == null) {
				description.setText(""); //$NON-NLS-1$ 
			} else {
				description.setText(text);
			}
		}

		public String getDescription() {
			return description.getText();
		}

		public void updateEnablement(boolean enabled) {
			descriptionLabel.setEnabled(enabled);
			combo.setEnabled(enabled);
			description.setEnabled(enabled);
		}
	}

	private class SeverityGroup {
		private Button enablementButton;

		private Button errorButton;

		private Button warningButton;

		private Button infoButton;

		public SeverityGroup(Composite parent) {

			Composite severityComposite = new Composite(parent, SWT.NONE);
			severityComposite.setLayout(new GridLayout(4, false));
			severityComposite.setLayoutData(new GridData(
					GridData.FILL_HORIZONTAL | GridData.GRAB_HORIZONTAL));

			SelectionListener listener = new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent e) {
					updateEnablement(true);
					DialogProblemFilter.this.markDirty();
				}
			};

			enablementButton = new Button(severityComposite, SWT.CHECK);
			GridData data = new GridData(GridData.FILL_HORIZONTAL);
			enablementButton.setLayoutData(data);
			enablementButton.setFont(parent.getFont());
			enablementButton
					.setText(MarkerMessages.filtersDialog_severityLabel);
			enablementButton.addSelectionListener(listener);

			errorButton = new Button(severityComposite, SWT.CHECK);
			errorButton.setFont(parent.getFont());
			errorButton.setText(MarkerMessages.filtersDialog_severityError);
			errorButton.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
			errorButton.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent e) {
					updateForSelection();
				}
			});

			warningButton = new Button(severityComposite, SWT.CHECK);
			warningButton.setFont(parent.getFont());
			warningButton.setText(MarkerMessages.filtersDialog_severityWarning);
			warningButton.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
			warningButton.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent e) {
					updateForSelection();
				}
			});

			infoButton = new Button(severityComposite, SWT.CHECK);
			infoButton.setFont(parent.getFont());
			infoButton.setText(MarkerMessages.filtersDialog_severityInfo);
			infoButton.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
			infoButton.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent e) {
					updateForSelection();
				}
			});
		}

		public boolean isSeveritySelected() {
			return enablementButton.getSelection();
		}

		public void setEnabled(boolean enabled) {
			enablementButton.setSelection(enabled);
		}

		public boolean isErrorSelected() {
			return errorButton.getSelection();
		}

		public void setErrorSelected(boolean selected) {
			errorButton.setSelection(selected);
		}

		public boolean isWarningSelected() {
			return warningButton.getSelection();
		}

		public void setWarningSelected(boolean selected) {
			warningButton.setSelection(selected);
		}

		public boolean isInfoSelected() {
			return infoButton.getSelection();
		}

		public void setInfoSelected(boolean selected) {
			infoButton.setSelection(selected);
		}

		public void updateEnablement(boolean enabled) {

			boolean showingSeverity = isSeveritySelected();
			enablementButton.setEnabled(enabled);
			errorButton.setEnabled(showingSeverity && enabled);
			warningButton.setEnabled(showingSeverity && enabled);
			infoButton.setEnabled(showingSeverity && enabled);

		}
	}

	public DialogProblemFilter(Shell parentShell, ProblemFilter[] filters) {
		super(parentShell, filters);
	}

	@Override
	protected void createAttributesArea(Composite parent) {
		Composite composite = new Composite(parent, SWT.NONE);
		composite.setFont(parent.getFont());
		composite.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		GridLayout layout = new GridLayout();
		composite.setLayout(layout);

		descriptionGroup = new DescriptionGroup(composite);
		severityGroup = new SeverityGroup(composite);
	}

	@Override
	protected void updateFilterFromUI(MarkerFilter filter) {
		super.updateFilterFromUI(filter);

		ProblemFilter problemFilter = (ProblemFilter) filter;
		problemFilter.setContains(descriptionGroup.getContains());
		problemFilter.setDescription(descriptionGroup.getDescription().trim());

		problemFilter.setSelectBySeverity(severityGroup.isSeveritySelected());
		int severity = 0;
		if (severityGroup.isErrorSelected()) {
			severity = severity | ProblemFilter.SEVERITY_ERROR;
		}
		if (severityGroup.isWarningSelected()) {
			severity = severity | ProblemFilter.SEVERITY_WARNING;
		}
		if (severityGroup.isInfoSelected()) {
			severity = severity | ProblemFilter.SEVERITY_INFO;
		}
		problemFilter.setSeverity(severity);

	}

	@Override
	protected void updateUIWithFilter(MarkerFilter filter) {

		ProblemFilter problemFilter = (ProblemFilter) filter;
		descriptionGroup.setContains(problemFilter.getContains());
		descriptionGroup.setDescription(problemFilter.getDescription());

		severityGroup.setEnabled(problemFilter.getSelectBySeverity());
		int severity = problemFilter.getSeverity();

		severityGroup
				.setErrorSelected((severity & ProblemFilter.SEVERITY_ERROR) > 0);
		severityGroup
				.setWarningSelected((severity & ProblemFilter.SEVERITY_WARNING) > 0);
		severityGroup
				.setInfoSelected((severity & ProblemFilter.SEVERITY_INFO) > 0);

		super.updateUIWithFilter(filter);

	}

	@Override
	protected void updateEnabledState(boolean enabled) {
		super.updateEnabledState(enabled);
		descriptionGroup.updateEnablement(enabled);
		severityGroup.updateEnablement(enabled);
	}

	@Override
	protected void resetPressed() {
		descriptionGroup.setContains(ProblemFilter.DEFAULT_CONTAINS);
		descriptionGroup.setDescription(ProblemFilter.DEFAULT_DESCRIPTION);

		severityGroup.setEnabled(ProblemFilter.DEFAULT_SELECT_BY_SEVERITY);
		severityGroup
				.setErrorSelected((ProblemFilter.DEFAULT_SEVERITY & ProblemFilter.SEVERITY_ERROR) > 0);
		severityGroup
				.setWarningSelected((ProblemFilter.DEFAULT_SEVERITY & ProblemFilter.SEVERITY_WARNING) > 0);
		severityGroup
				.setInfoSelected((ProblemFilter.DEFAULT_SEVERITY & ProblemFilter.SEVERITY_INFO) > 0);

		super.resetPressed();
	}

	@Override
	protected MarkerFilter newFilter(String newName) {
		return new ProblemFilter(newName);
	}

	@Override
	void createFiltersArea(Composite dialogArea) {

		if (MarkerSupportRegistry.getInstance().getRegisteredFilters().size() == 0) {
			super.createFiltersArea(dialogArea);
			return;
		}

		Composite mainComposite = new Composite(dialogArea, SWT.NONE);
		mainComposite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false,
				true));

		mainComposite.setLayout(new FormLayout());

		Composite topComposite = new Composite(mainComposite, SWT.NONE);
		FormData topData = new FormData();
		topData.top = new FormAttachment(0);
		topData.left = new FormAttachment(0);
		topData.right = new FormAttachment(100);
		topData.bottom = new FormAttachment(50);

		topComposite.setLayoutData(topData);
		topComposite.setLayout(new GridLayout());

		createUserFiltersArea(topComposite);

		Composite bottomComposite = new Composite(mainComposite, SWT.NONE);
		FormData bottomData = new FormData();
		bottomData.top = new FormAttachment(50);
		bottomData.left = new FormAttachment(0);
		bottomData.right = new FormAttachment(100);
		bottomData.bottom = new FormAttachment(100);

		bottomComposite.setLayoutData(bottomData);
		bottomComposite.setLayout(new GridLayout());

		createRegisteredFilters(bottomComposite);
		createFilterSelectButtons(bottomComposite);

	}

	private void createRegisteredFilters(Composite bottomComposite) {

		Composite listArea = new Composite(bottomComposite, SWT.NONE);
		listArea.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		listArea.setLayout(new GridLayout());

		Label title = new Label(listArea, SWT.NONE);
		title.setText(MarkerMessages.ProblemFilterDialog_System_Filters_Title);
		definedList = CheckboxTableViewer.newCheckList(listArea, SWT.BORDER);
		definedList.setContentProvider(new IStructuredContentProvider() {
			@Override
			public Object[] getElements(Object inputElement) {
				return MarkerSupportRegistry.getInstance()
						.getRegisteredFilters().toArray();
			}

			@Override
			public void dispose() {
			}

			@Override
			public void inputChanged(Viewer viewer, Object oldInput,
					Object newInput) {
			}
		});

		definedList.setLabelProvider(new LabelProvider() {
			@Override
			public String getText(Object element) {
				return ((MarkerFilter) element).getName();
			}
		});

		definedList
				.addSelectionChangedListener(new ISelectionChangedListener() {

					@Override
					public void selectionChanged(SelectionChangedEvent event) {

						ISelection selection = event.getSelection();
						if (selection instanceof IStructuredSelection) {
							Object selected = ((IStructuredSelection) selection)
									.getFirstElement();
							if (selected == null) {
								systemSettingsLabel.setText(Util.EMPTY_STRING);
							} else {
								systemSettingsLabel
										.setText(getSystemFilterString((ProblemFilter) selected));
							}
						} else {
							systemSettingsLabel.setText(Util.EMPTY_STRING);
						}
						showSystemLabel(true);

					}
				});

		Iterator definedFilters = MarkerSupportRegistry.getInstance()
				.getRegisteredFilters().iterator();
		definedList.setInput(this);
		while (definedFilters.hasNext()) {
			MarkerFilter next = (MarkerFilter) definedFilters.next();
			definedList.setChecked(next, next.isEnabled());
		}

		definedList.getControl().setLayoutData(
				new GridData(SWT.FILL, SWT.FILL, true, true));

	}

	protected String getSystemFilterString(ProblemFilter filter) {
		StringBuffer filterBuffer = new StringBuffer();

		String scopeString = getScopeString(filter);
		if (scopeString != null) {
			filterBuffer.append(scopeString);
		}

		String descriptionString = getDescriptionString(filter);
		if (descriptionString != null) {
			filterBuffer.append(Util.TWO_LINE_FEED);
			filterBuffer.append(descriptionString);
		}

		String severityString = getSeverityString(filter);
		if (severityString != null) {
			filterBuffer.append(Util.TWO_LINE_FEED);
			filterBuffer.append(severityString);
		}

		String typesString = getProblemTypesString(filter);
		filterBuffer.append(Util.TWO_LINE_FEED);
		filterBuffer.append(typesString);

		return filterBuffer.toString();
	}

	private String getProblemTypesString(ProblemFilter filter) {
		List types = filter.getSelectedTypes();
		if (types.size() == getRootEntries(filter).length) {
			return MarkerMessages.ProblemFilterDialog_All_Problems;
		}
		StringBuffer typesBuffer = new StringBuffer();
		Iterator typesIterator = types.iterator();
		typesBuffer.append(MarkerMessages.ProblemFilterDialog_Selected_Types);

		while (typesIterator.hasNext()) {
			typesBuffer.append(Util.LINE_FEED_AND_TAB);
			typesBuffer.append(((MarkerType) typesIterator.next()).getLabel());

		}
		return typesBuffer.toString();
	}

	private String getSeverityString(ProblemFilter filter) {
		if (filter.getSelectBySeverity()) {
			switch (filter.getSeverity()) {
			case ProblemFilter.SEVERITY_INFO:
				return MarkerMessages.ProblemFilterDialog_Info_Severity;
			case ProblemFilter.SEVERITY_WARNING:
				return MarkerMessages.ProblemFilterDialog_Warning_Severity;
			case ProblemFilter.SEVERITY_ERROR:
				return MarkerMessages.ProblemFilterDialog_Error_Severity;
			default:
				return null;
			}
		}
		return null;
	}

	private String getDescriptionString(ProblemFilter filter) {
		if (filter.getDescription().length() == 0) {
			return null;
		}
		if (filter.getContains()) {
			return NLS.bind(
					MarkerMessages.ProblemFilterDialog_Contains_Description,
					filter.getDescription());
		}
		return NLS
				.bind(
						MarkerMessages.ProblemFilterDialog_Does_Not_Contain_Description,
						filter.getDescription());

	}

	private String getScopeString(ProblemFilter filter) {

		switch (filter.onResource) {
		case MarkerFilter.ON_ANY:
			return MarkerMessages.ProblemFilterDialog_any;
		case MarkerFilter.ON_ANY_IN_SAME_CONTAINER:
			return MarkerMessages.ProblemFilterDialog_sameContainer;
		case MarkerFilter.ON_SELECTED_AND_CHILDREN:
			return MarkerMessages.ProblemFilterDialog_selectedAndChildren;
		case MarkerFilter.ON_SELECTED_ONLY:
			return MarkerMessages.ProblemFilterDialog_selected;
		case MarkerFilter.ON_WORKING_SET:
			return NLS.bind(MarkerMessages.ProblemFilterDialog_workingSet,
					filter.getWorkingSet());

		default:
			return null;

		}
	}

	@Override
	protected void setSelectedFilter(SelectionChangedEvent event) {
		showSystemLabel(false);
		super.setSelectedFilter(event);
	}

	protected void showSystemLabel(boolean systemLabelShowing) {

		systemSettingsLabel.setVisible(systemLabelShowing);
		userFilterComposite.setVisible(!systemLabelShowing);
		userFilterComposite.getParent().layout();
	}

	@Override
	Composite createSelectedFilterArea(Composite composite) {

		Composite wrapper = new Composite(composite, SWT.NONE);
		FormLayout wrapperLayout = new FormLayout();
		wrapperLayout.marginHeight = 0;
		wrapperLayout.marginWidth = 0;
		wrapper.setLayout(wrapperLayout);

		systemSettingsLabel = createSystemSettingsLabel(wrapper);
		systemSettingsLabel.setVisible(false);

		FormData systemData = new FormData();
		systemData.top = new FormAttachment(0, IDialogConstants.VERTICAL_MARGIN);
		systemData.left = new FormAttachment(0,
				IDialogConstants.HORIZONTAL_MARGIN);
		systemData.right = new FormAttachment(100, -1
		systemData.bottom = new FormAttachment(100, -1

		systemSettingsLabel.setLayoutData(systemData);

		userFilterComposite = super.createSelectedFilterArea(wrapper);

		FormData userData = new FormData();
		userData.top = new FormAttachment(0);
		userData.left = new FormAttachment(0);
		userData.right = new FormAttachment(100);
		userData.bottom = new FormAttachment(100);

		userFilterComposite.setLayoutData(userData);

		return wrapper;
	}

	private Label createSystemSettingsLabel(Composite wrapper) {

		return new Label(wrapper, SWT.NONE);
	}

	@Override
	protected void buttonPressed(int buttonId) {
		if (definedList != null) {
			if (buttonId == SELECT_ALL_FILTERS_ID) {
				definedList.setAllChecked(true);
			} else if (buttonId == DESELECT_ALL_FILTERS_ID) {
				definedList.setAllChecked(false);
			}
		}

		super.buttonPressed(buttonId);
	}

	@Override
	protected void okPressed() {

		Iterator registered = MarkerSupportRegistry.getInstance()
				.getRegisteredFilters().iterator();
		while (registered.hasNext()) {
			ProblemFilter next = (ProblemFilter) registered.next();
			next.setEnabled(definedList.getChecked(next));

		}
		super.okPressed();
	}
}
