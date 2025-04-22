package org.eclipse.ui.internal.ide.application.dialogs;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.layout.PixelConverter;
import org.eclipse.jface.layout.TableColumnLayout;
import org.eclipse.jface.preference.PreferencePage;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ColumnLayoutData;
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
	private Collection<ISchemeInformation> schemeInformationList = null;
	private Collection<ISchemeInformation> schemesToRegister;
	private Collection<ISchemeInformation> schemesToDeregister;
	private String currentLocation;

	@SuppressWarnings("javadoc")
	public UriSchemeHandlerPreferencePage() {
		super.setDescription(IDEWorkbenchMessages.UrlHandlerPreferencePage_Page_Description);
		schemesToRegister = new ArrayList<>();
		schemesToDeregister = new ArrayList<>();
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
		gridData.horizontalIndent = 20;
		editorComposite.setLayoutData(gridData);
		Table schemeTable = new Table(editorComposite,
				SWT.H_SCROLL | SWT.V_SCROLL | SWT.SINGLE | SWT.BORDER | SWT.FULL_SELECTION | SWT.CHECK);
		schemeTable.setHeaderVisible(true);
		schemeTable.setLinesVisible(true);
		schemeTable.setFont(parent.getFont());
		schemeTable.addListener(SWT.Selection, new TableSchemeSelectionListener());
		TableColumnLayout tableColumnlayout = new TableColumnLayout();
		editorComposite.setLayout(tableColumnlayout);
		ColumnLayoutData columnLayoutData = new ColumnWeightData(1);
		TableColumn nameColumn = new TableColumn(schemeTable, SWT.NONE, 0);
		nameColumn.setText(IDEWorkbenchMessages.UrlHandlerPreferencePage_ColumnName_SchemeName);
		tableColumnlayout.setColumnData(nameColumn, columnLayoutData);
		TableColumn descriptionColumn = new TableColumn(schemeTable, SWT.NONE, 1);
		descriptionColumn.setText(IDEWorkbenchMessages.UrlHandlerPreferencePage_ColumnName_SchemeDescription);
		tableColumnlayout.setColumnData(descriptionColumn, columnLayoutData);

		tableViewer = new TableViewer(schemeTable);
		tableViewer.setContentProvider(ArrayContentProvider.getInstance());
		tableViewer.setLabelProvider(new ItemLabelProvider());
		try {
			schemeInformationList = retrieveSchemeInformationList();
			currentLocation = System.getProperty("eclipse.home.location"); //$NON-NLS-1$
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
			ISchemeInformation schemeInformation = (ISchemeInformation) (tableScheme.getData());
			if (schemeInformation != null && schemeInformation.isHandled()) {
				tableScheme.setChecked(true);
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

	private Collection<ISchemeInformation> retrieveSchemeInformationList() throws Exception {
		Collection<Scheme> schemes = IUriSchemeExtensionReader.INSTANCE.getSchemes();
		if (operatingSystemRegistration != null) {
			return operatingSystemRegistration.getSchemesInformation(schemes);
		}
		return Collections.emptyList();
	}

	@Override
	public void init(IWorkbench workbench) {
		operatingSystemRegistration = IOperatingSystemRegistration.getInstance();
	}

	@Override
	public boolean performOk() {
		if (operatingSystemRegistration != null) {
			try {
				operatingSystemRegistration.handleSchemes(schemesToRegister, schemesToDeregister);
				schemesToRegister.clear();
				schemesToDeregister.clear();
			} catch (Exception e) {
				IStatus status = new Status(IStatus.ERROR, IDEWorkbenchPlugin.IDE_WORKBENCH, 1,
						IDEWorkbenchMessages.UrlHandlerPreferencePage_Error_Writing_Scheme, e);
				IDEWorkbenchPlugin.log(IDEWorkbenchMessages.UrlHandlerPreferencePage_Error_Writing_Scheme, status);
			}
		}
		return true;
	}



	private class TableSchemeSelectionListener implements Listener {
		@Override
		public void handleEvent(Event event) {
			if (event.detail == SWT.CHECK) {
				TableItem tableItem = (TableItem) event.item;
				if (tableItem != null && tableItem.getData() instanceof ISchemeInformation) {
					ISchemeInformation schemeInforamtion = (ISchemeInformation) tableItem.getData();
					if (tableItem.getChecked()) {
						addSchemeInformation(schemeInforamtion);
						schemeInforamtion.setHandlerLocation(currentLocation);
						handlerLocation.setText(NLS.bind(
								IDEWorkbenchMessages.UrlHandlerPreferencePage_Label_Handler_Location,
								NLS.bind(IDEWorkbenchMessages.UrlHandlerPreferencePage_Label_Message_Current_Location,
										schemeInforamtion.getHandlerInstanceLocation())));
					} else {
						removeSchemeInformation(schemeInforamtion);
						schemeInforamtion.setHandlerLocation(
								IDEWorkbenchMessages.UrlHandlerPreferencePage_Label_Message_No_Location);
						handlerLocation
								.setText(NLS.bind(IDEWorkbenchMessages.UrlHandlerPreferencePage_Label_Handler_Location,
										schemeInforamtion.getHandlerInstanceLocation()));
					}
				}
			}
			else {
				IStructuredSelection selection = tableViewer.getStructuredSelection();
				Object firstElement = selection != null ? selection.getFirstElement() : null;
				if (firstElement != null && firstElement instanceof ISchemeInformation) {
					ISchemeInformation schemeInfo = (ISchemeInformation) firstElement;
					if (schemeInfo.getHandlerInstanceLocation() == null) {
						schemeInfo.setHandlerLocation(
								IDEWorkbenchMessages.UrlHandlerPreferencePage_Label_Message_No_Location);
					}
					handlerLocation
							.setText(NLS.bind(IDEWorkbenchMessages.UrlHandlerPreferencePage_Label_Handler_Location,
									schemeInfo.getHandlerInstanceLocation()));
				} else {
					handlerLocation
							.setText(NLS.bind(IDEWorkbenchMessages.UrlHandlerPreferencePage_Label_Handler_Location,
									IDEWorkbenchMessages.UrlHandlerPreferencePage_Label_Message_No_Location));
				}
			}
		}

		private void removeSchemeInformation(ISchemeInformation schemeInforamtion) {
			if (findFromSchemesToregister(schemeInforamtion))
				schemesToRegister.remove(schemeInforamtion);
			else
				schemesToDeregister.add(schemeInforamtion);
		}

		private boolean findFromSchemesToregister(ISchemeInformation addSchemeInformation) {
			for (ISchemeInformation addCheck : schemesToRegister) {
				if (addCheck.getScheme().equals(addSchemeInformation.getScheme())) {
					return true;
				}
			}
			return false;
		}

		private void addSchemeInformation(ISchemeInformation schemeInforamtion) {
			if (findFromSchemesToDeregister(schemeInforamtion))
				schemesToDeregister.remove(schemeInforamtion);
			else
				schemesToRegister.add(schemeInforamtion);
		}

		private boolean findFromSchemesToDeregister(ISchemeInformation removeSchemeInformation) {
			for (ISchemeInformation removeCheck : schemesToDeregister) {
				if (removeCheck.getScheme().equals(removeSchemeInformation.getScheme())) {
					return true;
				}
			}
			return false;
		}
	}

	private final class ItemLabelProvider extends LabelProvider implements ITableLabelProvider {

		@Override
		public Image getColumnImage(Object element, int columnIndex) {
			return null;
		}

		@Override
		public String getColumnText(Object element, int columnIndex) {
			ISchemeInformation schemeInfo = null;
			if (element instanceof ISchemeInformation) {
				schemeInfo = (ISchemeInformation) element;
			}
			switch (columnIndex) {
			case 0:
				if (schemeInfo != null) {
					return schemeInfo.getScheme();
				}
				break;
			case 1:
				if (schemeInfo != null) {
					return schemeInfo.getDescription();
				}
				break;

			default:
				Assert.isLegal(false);
			}
			return null; // cannot happen

		}

		@Override
		public boolean isLabelProperty(Object element, String property) {
			return false;
		}
	}
}
