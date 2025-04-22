package org.eclipse.ui.internal.ide.application.dialogs;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Properties;

import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.layout.PixelConverter;
import org.eclipse.jface.layout.TableColumnLayout;
import org.eclipse.jface.preference.PreferencePage;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ColumnLayoutData;
import org.eclipse.jface.viewers.ColumnWeightData;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.TableViewer;
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

public class UrlHandlerPreferencePage extends PreferencePage implements IWorkbenchPreferencePage {

	private static final String CAPTION_MESSAGE = "captionMessage"; //$NON-NLS-1$
	private Label handlerLocation;
	private TableViewer tableViewer;
	private IOperatingSystemRegistration operatingSystemRegistration;
	private Collection<ISchemeInformation> schemeInformationList = null;

	@Override
	protected Control createContents(Composite parent) {

		Properties strings = new Properties();
		Label label = new Label(parent, SWT.WRAP);
		label.setText(
				strings.getProperty(CAPTION_MESSAGE, IDEWorkbenchMessages.UrlHandlerPreferencePage_Page_Description));
		addFiller(parent, 2);
		createTableViewerForSchemes(parent);
		GridData gridData = new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1);
		handlerLocation = new Label(parent, SWT.FILL);
		handlerLocation.setLayoutData(gridData);
		return parent;
	}

	private void createTableViewerForSchemes(Composite parent) {

		Composite editorComposite = new Composite(parent, SWT.NONE);
		GridData gd;
		gd = new GridData(SWT.FILL, SWT.TOP, true, false);
		gd.horizontalSpan = 2;
		gd.horizontalIndent = 20;
		editorComposite.setLayoutData(gd);

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
		} catch (Exception e) {
			IStatus status = new Status(IStatus.ERROR, IDEWorkbenchPlugin.IDE_WORKBENCH, 1,
					IDEWorkbenchMessages.UrlHandlerPreferencePage_Error_Reading_Scheme, e);
			IDEWorkbenchPlugin.log(IDEWorkbenchMessages.UrlHandlerPreferencePage_Error_Reading_Scheme, status);
		}
		tableViewer.setInput(schemeInformationList);
		TableItem[] tableSchemes = tableViewer.getTable().getItems();
		if (tableSchemes != null) {
			for (TableItem tableScheme : tableSchemes) {
				ISchemeInformation schemeInformation = (ISchemeInformation) (tableScheme.getData());
				if (schemeInformation != null && schemeInformation.isHandled()) {
					tableScheme.setChecked(true);
				}
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
		Collection<ISchemeInformation> schemesToRegister = getListofSchemesToRegister();
		Collection<ISchemeInformation> schemesToDeregister = getListofSchemesToDeregister();
		if (operatingSystemRegistration != null) {
			try {
				operatingSystemRegistration.handleSchemes(schemesToRegister, schemesToDeregister);
			} catch (Exception e) {
				IStatus status = new Status(IStatus.ERROR, IDEWorkbenchPlugin.IDE_WORKBENCH, 1,
						IDEWorkbenchMessages.UrlHandlerPreferencePage_Error_Writing_Scheme, e);
				IDEWorkbenchPlugin.log(IDEWorkbenchMessages.UrlHandlerPreferencePage_Error_Writing_Scheme, status);
			}
		}
		return true;
	}

	private Collection<ISchemeInformation> getListofSchemesToRegister() {
		Collection<ISchemeInformation> schemesToRegister = new ArrayList<>();
		Table table = tableViewer.getTable();
		TableItem[] tableItems = table.getItems();
		if (tableItems != null) {
			for (TableItem tableItem : tableItems) {
				Object data = tableItem.getData();
				if (data != null && data instanceof ISchemeInformation) {
					ISchemeInformation schemeInformation = (ISchemeInformation) data;
					if (schemeInformation.getHandlerInstanceLocation()
							.equals(IDEWorkbenchMessages.UrlHandlerPreferencePage_Label_Message_Current_Location))
						schemesToRegister.add((ISchemeInformation) data);
				}
			}
		}
		return schemesToRegister;
	}

	private Collection<ISchemeInformation> getListofSchemesToDeregister() {
		Collection<ISchemeInformation> schemesToDeregister = new ArrayList<>();
		Table table = tableViewer.getTable();
		TableItem[] tableItems = table.getItems();
		if (tableItems != null) {
			for (TableItem tableItem : tableItems) {
				Object data = tableItem.getData();
				if (data != null && data instanceof ISchemeInformation) {
					ISchemeInformation schemeInformation = (ISchemeInformation) data;
					if (schemeInformation.getHandlerInstanceLocation()
							.equals(IDEWorkbenchMessages.UrlHandlerPreferencePage_Label_Message_No_Location))
						schemesToDeregister.add((ISchemeInformation) data);
				}
			}
		}
		return schemesToDeregister;
	}

	private class TableSchemeSelectionListener implements Listener {
		@Override
		public void handleEvent(Event event) {
			if (event.detail == SWT.CHECK) {
				TableItem tableItem = (TableItem) event.item;
				if (tableItem != null && tableItem.getData() instanceof ISchemeInformation) {
					ISchemeInformation schemeInforamtion = (ISchemeInformation) tableItem.getData();
					if (tableItem.getChecked()) {
						schemeInforamtion.setHandlerLocation(
								IDEWorkbenchMessages.UrlHandlerPreferencePage_Label_Message_Current_Location);
						handlerLocation.setText(IDEWorkbenchMessages.UrlHandlerPreferencePage_Label_Handler_Location
								+ schemeInforamtion.getHandlerInstanceLocation());
					} else {
						schemeInforamtion.setHandlerLocation(
								IDEWorkbenchMessages.UrlHandlerPreferencePage_Label_Message_No_Location);
						handlerLocation.setText(IDEWorkbenchMessages.UrlHandlerPreferencePage_Label_Handler_Location
								+ schemeInforamtion.getHandlerInstanceLocation());
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
					handlerLocation.setText(IDEWorkbenchMessages.UrlHandlerPreferencePage_Label_Handler_Location
							+ schemeInfo.getHandlerInstanceLocation());
				} else {
					handlerLocation.setText(IDEWorkbenchMessages.UrlHandlerPreferencePage_Label_Handler_Location
							+ IDEWorkbenchMessages.UrlHandlerPreferencePage_Label_Message_No_Location);
				}
			}
		}
	}

	private final class ItemLabelProvider implements ITableLabelProvider {

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
		public void addListener(ILabelProviderListener listener) {
		}

		@Override
		public void dispose() {
		}

		@Override
		public boolean isLabelProperty(Object element, String property) {
			return false;
		}

		@Override
		public void removeListener(ILabelProviderListener listener) {
		}
	}
}
