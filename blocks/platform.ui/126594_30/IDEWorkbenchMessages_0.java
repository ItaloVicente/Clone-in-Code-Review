package org.eclipse.ui.internal.ide.application.dialogs;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.layout.PixelConverter;
import org.eclipse.jface.layout.TableColumnLayout;
import org.eclipse.jface.preference.PreferencePage;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ColumnWeightData;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.osgi.util.NLS;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;
import org.eclipse.ui.internal.ide.IDEWorkbenchMessages;
import org.eclipse.ui.internal.ide.IDEWorkbenchPlugin;
import org.eclipse.urischeme.IOperatingSystemRegistration;
import org.eclipse.urischeme.ISchemeInformation;
import org.eclipse.urischeme.IUriSchemeExtensionReader;
import org.eclipse.urischeme.IUriSchemeExtensionReader.Scheme;

public class UriSchemeHandlerPreferencePage extends PreferencePage implements IWorkbenchPreferencePage {

	private Label handlerLocation;
	private TableViewer tableViewer;
	private IOperatingSystemRegistration operatingSystemRegistration;
	private Collection<UiSchemeInformation> schemeInformationList = null;
	private String currentLocation = null;

	@SuppressWarnings("javadoc")
	public UriSchemeHandlerPreferencePage() {
		super.setDescription(IDEWorkbenchMessages.UrlHandlerPreferencePage_Page_Description);
	}

	@Override
	protected Control createContents(Composite parent) {
		addFiller(parent, 2);
		createTableViewerForSchemes(parent);
		GridData gridData = new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1);
		handlerLocation = new Label(parent, SWT.FILL);
		handlerLocation.setLayoutData(gridData);
		return parent;
	}

	private void createTableViewerForSchemes(Composite parent) {
		Composite editorComposite = new Composite(parent, SWT.NONE);
		GridData gridData = new GridData(SWT.FILL, SWT.TOP, true, false);
		gridData.horizontalSpan = 2;
		gridData.horizontalIndent = 0;
		editorComposite.setLayoutData(gridData);
		Table schemeTable = new Table(editorComposite,
				SWT.H_SCROLL | SWT.V_SCROLL | SWT.SINGLE | SWT.BORDER | SWT.FULL_SELECTION | SWT.CHECK);
		schemeTable.setHeaderVisible(true);
		schemeTable.setLinesVisible(true);
		schemeTable.setFont(parent.getFont());
		schemeTable.addListener(SWT.Selection, new TableSchemeSelectionListener());

		TableColumnLayout tableColumnlayout = new TableColumnLayout();
		editorComposite.setLayout(tableColumnlayout);

		TableColumn nameColumn = new TableColumn(schemeTable, SWT.NONE, 0);
		nameColumn.setText(IDEWorkbenchMessages.UrlHandlerPreferencePage_ColumnName_SchemeName);

		TableColumn descriptionColumn = new TableColumn(schemeTable, SWT.NONE, 1);
		descriptionColumn.setText(IDEWorkbenchMessages.UrlHandlerPreferencePage_ColumnName_SchemeDescription);

		tableColumnlayout.setColumnData(nameColumn, new ColumnWeightData(20));
		tableColumnlayout.setColumnData(descriptionColumn, new ColumnWeightData(80));

		tableViewer = new TableViewer(schemeTable);
		tableViewer.setContentProvider(ArrayContentProvider.getInstance());
		tableViewer.setLabelProvider(new ItemLabelProvider());

		try {
			schemeInformationList = retrieveSchemeInformationList();
		} catch (Exception e) {
			IStatus status = new Status(IStatus.ERROR, IDEWorkbenchPlugin.IDE_WORKBENCH, 1,
					IDEWorkbenchMessages.UrlHandlerPreferencePage_Error_Reading_Scheme, e);
			IDEWorkbenchPlugin.log(IDEWorkbenchMessages.UrlHandlerPreferencePage_Error_Reading_Scheme, status);
		}
		tableViewer.setInput(schemeInformationList);
		TableItem[] tableSchemes = tableViewer.getTable().getItems();
		if (tableSchemes == null) {
			return;
		}
		for (TableItem tableScheme : tableSchemes) {
			UiSchemeInformation schemeInformation = (UiSchemeInformation) (tableScheme.getData());
			if (schemeInformation != null) {
				tableScheme.setChecked(schemeInformation.checked);
			}
		}
	}

	private void addFiller(Composite composite, int horizontalSpan) {
		PixelConverter pixelConverter = new PixelConverter(composite);
		Label filler = new Label(composite, SWT.LEFT);
		GridData gd = new GridData(GridData.HORIZONTAL_ALIGN_FILL);
		gd.horizontalSpan = horizontalSpan;
		gd.heightHint = pixelConverter.convertHeightInCharsToPixels(1) / 2;
		filler.setLayoutData(gd);
	}

	private Collection<UiSchemeInformation> retrieveSchemeInformationList() throws Exception {
		Collection<UiSchemeInformation> returnList = new ArrayList<>();
		Collection<Scheme> schemes = IUriSchemeExtensionReader.INSTANCE.getSchemes();
		if (operatingSystemRegistration != null) {
			for (ISchemeInformation info : operatingSystemRegistration.getSchemesInformation(schemes)) {
				returnList.add(new UiSchemeInformation(info.isHandled(), info));
			}
		}
		return returnList;
	}

	@Override
	public void init(IWorkbench workbench) {
		operatingSystemRegistration = IOperatingSystemRegistration.getInstance();
		if (operatingSystemRegistration != null) {
			currentLocation = operatingSystemRegistration.getEclipseLauncher();
		}
	}

	@Override
	public boolean performOk() {
		if (operatingSystemRegistration == null) {
			return true;
		}

		List<ISchemeInformation> toAdd = new ArrayList<>();
		List<ISchemeInformation> toRemove = new ArrayList<>();
		for (UiSchemeInformation info : schemeInformationList) {
			if (info.checked && !info.information.isHandled()) {
				toAdd.add(info.information);
			}
			if (!info.checked && info.information.isHandled()) {
				toRemove.add(info.information);
			}
		}

		try {
			operatingSystemRegistration.handleSchemes(toAdd, toRemove);
		} catch (Exception e) {
			IStatus status = new Status(IStatus.ERROR, IDEWorkbenchPlugin.IDE_WORKBENCH, 1,
					IDEWorkbenchMessages.UrlHandlerPreferencePage_Error_Writing_Scheme, e);
			IDEWorkbenchPlugin.log(IDEWorkbenchMessages.UrlHandlerPreferencePage_Error_Writing_Scheme, status);
		}
		return true;
	}

	private class TableSchemeSelectionListener implements Listener {
		@Override
		public void handleEvent(Event event) {
			if (event.detail == SWT.CHECK) {
				handleCheckbox(event);
			} else {
				handleSelection();
			}
		}

		private void handleSelection() {
			IStructuredSelection selection = tableViewer.getStructuredSelection();
			Object firstElement = selection != null ? selection.getFirstElement() : null;
			if (firstElement != null && firstElement instanceof UiSchemeInformation) {
				setSchemeDetails((UiSchemeInformation) firstElement);
			}
		}

		private void setSchemeDetails(UiSchemeInformation schemeInfo) {
			if (schemeInfo.checked) {
				handlerLocation.setText(
						NLS.bind(IDEWorkbenchMessages.UrlHandlerPreferencePage_Label_Message_Current_Application,
								currentLocation));

			} else if (schemeIsHandledByOther(schemeInfo.information)) {
				handlerLocation
						.setText(NLS.bind(IDEWorkbenchMessages.UrlHandlerPreferencePage_Label_Message_Other_Application,
								schemeInfo.information.getHandlerInstanceLocation()));
			} else {
				handlerLocation.setText(IDEWorkbenchMessages.UrlHandlerPreferencePage_Label_Message_No_Application);
			}
		}

		private void handleCheckbox(Event event) {
			TableItem tableItem = (TableItem) event.item;
			if (tableItem != null && tableItem.getData() instanceof UiSchemeInformation) {
				UiSchemeInformation schemeInformation = (UiSchemeInformation) tableItem.getData();

				if (tableItem.getChecked() && schemeIsHandledByOther(schemeInformation.information)) {
					event.doit = false;
					tableItem.setChecked(false);
					MessageDialog.openWarning(getShell(),
							IDEWorkbenchMessages.UriHandlerPreferencePage_Warning_OtherApp,
							NLS.bind(IDEWorkbenchMessages.UriHandlerPreferencePage_Warning_OtherApp_Description,
									schemeInformation.information.getHandlerInstanceLocation(),
									schemeInformation.information.getScheme()));
					return;
				}
				schemeInformation.checked = tableItem.getChecked();
				setSchemeDetails(schemeInformation);
			}
		}
	}

	private boolean schemeIsHandledByOther(ISchemeInformation info) {
		boolean schemeIsNotHandled = !info.isHandled();
		boolean handlerLocationIsSet = info.getHandlerInstanceLocation() != null
				&& !info.getHandlerInstanceLocation().isEmpty();
		return schemeIsNotHandled && handlerLocationIsSet;
	}

	private final class ItemLabelProvider extends LabelProvider implements ITableLabelProvider {

		@Override
		public Image getColumnImage(Object element, int columnIndex) {
			return null;
		}

		@Override
		public String getColumnText(Object element, int columnIndex) {
			if (element instanceof UiSchemeInformation) {
				UiSchemeInformation schemeInfo = (UiSchemeInformation) element;
				switch (columnIndex) {
				case 0:
					return schemeInfo.information.getScheme();
				case 1:
					return schemeInfo.information.getDescription();
				default:
					throw new IllegalArgumentException("Unknown column"); //$NON-NLS-1$
				}
			}
			return null; // cannot happen
		}

		@Override
		public boolean isLabelProperty(Object element, String property) {
			return false;
		}
	}

	private final static class UiSchemeInformation {
		public boolean checked;
		public ISchemeInformation information;

		public UiSchemeInformation(boolean checked, ISchemeInformation information) {
			this.checked = checked;
			this.information = information;
		}

	}
}
