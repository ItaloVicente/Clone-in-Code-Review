package org.eclipse.ui.internal.ide.application.dialogs;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Properties;

import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.RegistryFactory;
import org.eclipse.jface.preference.PreferencePage;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;
import org.eclipse.ui.internal.ide.IDEWorkbenchMessages;
import org.eclipse.urischeme.IOperatingSystemRegistration;
import org.eclipse.urischeme.ISchemeInformation;

public class UrlHandlerPreferencePage extends PreferencePage implements IWorkbenchPreferencePage {

	private static final String CAPTION_MESSAGE = "captionMessage"; //$NON-NLS-1$
	private Label handlerLocation;
	private TableViewer tableViewer;
	private IOperatingSystemRegistration operatingSystemRegistration;
	private IConfigurationElement[] configurationElements = null;
	private Iterable<ISchemeInformation> schemes = null;
	private static final String EXT_POINT_ID_URI_SCHEME_HANDLERS = "org.eclipse.urischeme.uriSchemeHandlers"; //$NON-NLS-1$
	private static final String EXT_POINT_ATTRIBUTE_URI_SCHEME = "uriScheme"; //$NON-NLS-1$
	private static final String EXT_POINT_ATTRIBUTE_URI_SCHEME_DESCRIPTION = "uriSchemeDescription"; //$NON-NLS-1$

	@Override
	protected Control createContents(Composite parent) {
		createTableViewerForSchemes(parent);
		GridData data = new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1);
		handlerLocation = new Label(parent, SWT.FILL);
		handlerLocation.setLayoutData(data);
		return parent;
	}

	private void createTableViewerForSchemes(Composite parent) {
		Properties strings = new Properties();
		Label label = new Label(parent, SWT.WRAP);
		label.setText(
				strings.getProperty(CAPTION_MESSAGE, IDEWorkbenchMessages.UrlHandlerPreferencePage_Page_Description));
		tableViewer = new TableViewer(parent,
				SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL | SWT.FULL_SELECTION | SWT.BORDER | SWT.CHECK);
		createColumns(tableViewer);
		tableViewer.getTable().setHeaderVisible(true);
		tableViewer.getTable().setLinesVisible(true);
		tableViewer.setContentProvider(ArrayContentProvider.getInstance());

		tableViewer.getTable().addListener(SWT.Selection, new TableSchemeSelectionListener());

		try {
			schemes = buildSchemes();
		} catch (IOException e) {
			e.printStackTrace();
		}
		tableViewer.setInput(schemes);
		TableItem[] tableSchemes = tableViewer.getTable().getItems();
		for (TableItem tableScheme : tableSchemes) {
			ISchemeInformation schemeInformation = (ISchemeInformation) (tableScheme.getData());
			if (schemeInformation.isHandled()) {
				tableScheme.setChecked(true);
			}
		}
	}

	private Iterable<ISchemeInformation> buildSchemes() throws IOException {
		Collection<ISchemeInformation> schemes = getAllSchemesFromExtensionPoints();
		if (operatingSystemRegistration != null) {
			return operatingSystemRegistration.getSchemesInformation(schemes);
		}
		return new ArrayList<>();

	}

	private void createColumns(TableViewer viewer) {
		TableViewerColumn linkNameCol = new TableViewerColumn(viewer, SWT.NONE);
		linkNameCol.getColumn().setWidth(300);
		linkNameCol.getColumn().setText(IDEWorkbenchMessages.UrlHandlerPreferencePage_ColumnName_SchemeName);
		linkNameCol.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				if (element instanceof ISchemeInformation) {
					ISchemeInformation p = (ISchemeInformation) element;
					return p.getScheme();
				}
				return null;
			}
		});

		TableViewerColumn linkLocationNameCol = new TableViewerColumn(viewer, SWT.NONE);
		linkLocationNameCol.getColumn().setWidth(700);
		linkLocationNameCol.getColumn()
				.setText(IDEWorkbenchMessages.UrlHandlerPreferencePage_ColumnName_SchemeDescription);
		linkLocationNameCol.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				if (element instanceof ISchemeInformation) {
					ISchemeInformation p = (ISchemeInformation) element;
					return p.getDescription();
				}
				return null;
			}
		});
	}

	private Collection<ISchemeInformation> getAllSchemesFromExtensionPoints() {
		IConfigurationElement[] elements = getOrReadConfigurationElements();
		Collection<ISchemeInformation> schemes = new ArrayList<>();
		for (IConfigurationElement element : elements) {
			String schemeName = element.getAttribute(EXT_POINT_ATTRIBUTE_URI_SCHEME);
			String schemeDescription = element.getAttribute(EXT_POINT_ATTRIBUTE_URI_SCHEME_DESCRIPTION);
			ISchemeInformation schemeInformation = ISchemeInformation.getInstance(schemeName, schemeDescription, ""); //$NON-NLS-1$
			schemes.add(schemeInformation);
		}
		return schemes;
	}

	private IConfigurationElement[] getOrReadConfigurationElements() {
		if (this.configurationElements == null) {
			IExtensionRegistry registry = RegistryFactory.getRegistry();
			this.configurationElements = registry.getConfigurationElementsFor(EXT_POINT_ID_URI_SCHEME_HANDLERS);
		}
		return configurationElements;
	}

	@Override
	public void init(IWorkbench workbench) {
		operatingSystemRegistration = IOperatingSystemRegistration.getInstance();
	}

	@Override
	public boolean performOk() {
		Collection<ISchemeInformation> toAdd = getListofSchemesToRegister();
		Collection<ISchemeInformation> toRemove = getListofSchemesToDeregister();
		if (operatingSystemRegistration != null) {
			try {
				operatingSystemRegistration.handleSchemes(toAdd, toRemove);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return true;
	}

	private Collection<ISchemeInformation> getListofSchemesToRegister() {
		Collection<ISchemeInformation> toAdd = new ArrayList<>();
		Table table = tableViewer.getTable();
		TableItem[] tableItems = table.getItems();
		for (TableItem tableItem : tableItems) {
			Object data = tableItem.getData();
			if (data instanceof ISchemeInformation) {
				ISchemeInformation data1 = (ISchemeInformation) data;
				if (data1.getHandlerInstanceLocation()
						.equals(IDEWorkbenchMessages.UrlHandlerPreferencePage_Label_Message_Current_Location))
					toAdd.add((ISchemeInformation) data);
			}
		}
		return toAdd;
	}

	private Collection<ISchemeInformation> getListofSchemesToDeregister() {
		Collection<ISchemeInformation> toRemove = new ArrayList<>();
		Table table = tableViewer.getTable();
		TableItem[] tableItems = table.getItems();
		for (TableItem tableItem : tableItems) {
			Object data = tableItem.getData();
			if (data instanceof ISchemeInformation) {
				ISchemeInformation data1 = (ISchemeInformation) data;
				if (data1.getHandlerInstanceLocation()
						.equals(IDEWorkbenchMessages.UrlHandlerPreferencePage_Label_Message_No_Location))
					toRemove.add((ISchemeInformation) data);
			}
		}
		return toRemove;
	}

	private class TableSchemeSelectionListener implements Listener {
		@Override
		public void handleEvent(Event event) {
			if (event.detail == SWT.CHECK) {
				TableItem ti = (TableItem) event.item;
				ISchemeInformation item = (ISchemeInformation) ti.getData();
				if (ti.getChecked()) {
					item.setHandlerInstanceLocation(
							IDEWorkbenchMessages.UrlHandlerPreferencePage_Label_Message_Current_Location);
					handlerLocation.setText("Handler : " + item.getHandlerInstanceLocation()); //$NON-NLS-1$
				} else {
					item.setHandlerInstanceLocation(
							IDEWorkbenchMessages.UrlHandlerPreferencePage_Label_Message_No_Location);
					handlerLocation.setText("Handler : " + item.getHandlerInstanceLocation()); //$NON-NLS-1$
				}

			} else {
				IStructuredSelection selection = tableViewer.getStructuredSelection();
				Object firstElement = selection.getFirstElement();
				if (firstElement != null && firstElement instanceof ISchemeInformation) {
					handlerLocation
							.setText(IDEWorkbenchMessages.UrlHandlerPreferencePage_Label_Handler_Location + ((ISchemeInformation) firstElement).getHandlerInstanceLocation());
				} else {
					handlerLocation.setText(IDEWorkbenchMessages.UrlHandlerPreferencePage_Label_Handler_Location
							+ IDEWorkbenchMessages.UrlHandlerPreferencePage_Label_Message_No_Location);
				}
			}
		}
	}

}
