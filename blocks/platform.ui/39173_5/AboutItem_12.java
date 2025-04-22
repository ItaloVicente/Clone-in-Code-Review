package org.eclipse.e4.ui.internal.workbench.swt.dialog.about.ui;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import org.eclipse.core.runtime.IBundleGroup;
import org.eclipse.core.runtime.IBundleGroupProvider;
import org.eclipse.core.runtime.Platform;
import org.eclipse.e4.ui.internal.workbench.swt.dialog.about.IWorkbenchHelpContextIds;
import org.eclipse.e4.ui.workbench.swt.internal.copy.WorkbenchSWTMessages;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.util.ConfigureColumns;
import org.eclipse.osgi.util.NLS;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.osgi.framework.Bundle;

public class AboutFeaturesPage extends ProductInfoPage {

	private static final String ID = "productInfo.features"; //$NON-NLS-1$
	private static final int TABLE_HEIGHT = 150;

	private static final int INFO_HEIGHT = 100;

	private final static int MORE_ID = IDialogConstants.CLIENT_ID + 1;

	private final static int PLUGINS_ID = IDialogConstants.CLIENT_ID + 2;

	private final static int COLUMNS_ID = IDialogConstants.CLIENT_ID + 3;

	private Table table;

	private Label imageLabel;

	private StyledText text;

	private AboutTextManager textManager;

	private Composite infoArea;

	private Map<ImageDescriptor, Image> cachedImages = new HashMap<ImageDescriptor, Image>();

	private AboutBundleGroupData[] bundleGroupInfos;

	private String columnTitles[] = { WorkbenchSWTMessages.AboutFeaturesDialog_provider,
			WorkbenchSWTMessages.AboutFeaturesDialog_featureName, WorkbenchSWTMessages.AboutFeaturesDialog_version,
			WorkbenchSWTMessages.AboutFeaturesDialog_featureId, };

	private int lastColumnChosen = 0; // initially sort by provider

	private boolean reverseSort = false; // initially sort ascending

	private AboutBundleGroupData lastSelection = null;

	private Button pluginsButton, moreButton;

	private static Map featuresMap;

	public void setBundleGroupInfos(AboutBundleGroupData[] bundleGroupInfos) {
		this.bundleGroupInfos = bundleGroupInfos;
	}

	@Override
	String getId() {
		return ID;
	}

	private void initializeBundleGroupInfos() {
		if (bundleGroupInfos == null) {

			IBundleGroupProvider[] providers = Platform.getBundleGroupProviders();
			LinkedList<AboutBundleGroupData> groups = new LinkedList<AboutBundleGroupData>();
			if (providers != null) {
				for (int i = 0; i < providers.length; ++i) {
					IBundleGroup[] bundleGroups = providers[i].getBundleGroups();
					for (int j = 0; j < bundleGroups.length; ++j) {
						groups.add(new AboutBundleGroupData(bundleGroups[j]));
					}
				}
			}
			bundleGroupInfos = (AboutBundleGroupData[]) groups.toArray(new AboutBundleGroupData[0]);
		} else {
			AboutBundleGroupData[] clientArray = bundleGroupInfos;
			bundleGroupInfos = new AboutBundleGroupData[clientArray.length];
			System.arraycopy(clientArray, 0, bundleGroupInfos, 0, clientArray.length);
		}
		AboutData.sortByProvider(reverseSort, bundleGroupInfos);
	}

	private void handlePluginInfoPressed() {
		TableItem[] items = table.getSelection();
		if (items.length <= 0) {
			return;
		}

		AboutBundleGroupData info = (AboutBundleGroupData) items[0].getData();
		IBundleGroup bundleGroup = info.getBundleGroup();
		Bundle[] bundles = bundleGroup == null ? new Bundle[0] : bundleGroup.getBundles();

		AboutPluginsDialog d = new AboutPluginsDialog(getShell(), getProductName(), bundles,
				WorkbenchSWTMessages.AboutFeaturesDialog_pluginInfoTitle, NLS.bind(
						WorkbenchSWTMessages.AboutFeaturesDialog_pluginInfoMessage, bundleGroup.getIdentifier()),
				IWorkbenchHelpContextIds.ABOUT_FEATURES_PLUGINS_DIALOG);
		d.open();
	}

	@Override
	public void createPageButtons(Composite parent) {
		moreButton = createButton(parent, MORE_ID, WorkbenchSWTMessages.AboutFeaturesDialog_moreInfo);
		pluginsButton = createButton(parent, PLUGINS_ID, WorkbenchSWTMessages.AboutFeaturesDialog_pluginsInfo);
		createButton(parent, COLUMNS_ID, WorkbenchSWTMessages.AboutFeaturesDialog_columns);
		TableItem[] items = table.getSelection();
		if (items.length > 0) {
			updateButtons((AboutBundleGroupData) items[0].getData());
		}
	}

	@Override
	public void createControl(Composite parent) {
		initializeDialogUnits(parent);
		parent.getShell().addDisposeListener(new DisposeListener() {
			@Override
			public void widgetDisposed(DisposeEvent arg0) {
				disposeImages();
			}
		});


		Composite outer = createOuterComposite(parent);

		createTable(outer);
		createInfoArea(outer);
		setControl(outer);
	}

	protected void createInfoArea(Composite parent) {
		Font font = parent.getFont();

		infoArea = new Composite(parent, SWT.BORDER);
		infoArea.setBackground(infoArea.getDisplay().getSystemColor(SWT.COLOR_LIST_BACKGROUND));
		infoArea.setBackgroundMode(SWT.INHERIT_FORCE);
		GridData data = new GridData(GridData.FILL, GridData.FILL, true, false);
		data.heightHint = convertVerticalDLUsToPixels(INFO_HEIGHT);
		infoArea.setLayoutData(data);

		GridLayout layout = new GridLayout();
		layout.numColumns = 2;
		infoArea.setLayout(layout);

		imageLabel = new Label(infoArea, SWT.NONE);
		data = new GridData(GridData.FILL, GridData.BEGINNING, false, false);
		data.widthHint = 32;
		data.heightHint = 32;
		imageLabel.setLayoutData(data);
		imageLabel.setFont(font);

		text = new StyledText(infoArea, SWT.MULTI | SWT.WRAP | SWT.READ_ONLY | SWT.V_SCROLL);
		text.setAlwaysShowScrollBars(false);


		text.setFont(parent.getFont());
		data = new GridData(GridData.FILL, GridData.FILL, true, true);
		text.setLayoutData(data);
		text.setFont(font);
		text.setCursor(null);

		textManager = new AboutTextManager(text);

		TableItem[] items = table.getSelection();
		if (items.length > 0) {
			updateInfoArea((AboutBundleGroupData) items[0].getData());
		}
	}

	protected void createTable(Composite parent) {

		initializeBundleGroupInfos();

		table = new Table(parent, SWT.H_SCROLL | SWT.V_SCROLL | SWT.SINGLE | SWT.FULL_SELECTION | SWT.BORDER);

		GridData gridData = new GridData(GridData.FILL, GridData.FILL, true, true);
		gridData.heightHint = convertVerticalDLUsToPixels(TABLE_HEIGHT);
		table.setLayoutData(gridData);
		table.setHeaderVisible(true);

		table.setLinesVisible(true);
		table.setFont(parent.getFont());
		table.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if (e.item == null)
					return;
				AboutBundleGroupData info = (AboutBundleGroupData) e.item.getData();
				updateInfoArea(info);
				updateButtons(info);
			}
		});

		int[] columnWidths = { convertHorizontalDLUsToPixels(120), convertHorizontalDLUsToPixels(120),
				convertHorizontalDLUsToPixels(70), convertHorizontalDLUsToPixels(130) };

		for (int i = 0; i < columnTitles.length; i++) {
			TableColumn tableColumn = new TableColumn(table, SWT.NULL);
			tableColumn.setWidth(columnWidths[i]);
			tableColumn.setText(columnTitles[i]);
			final int columnIndex = i;
			tableColumn.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent e) {
					sort(columnIndex);
				}
			});
		}

		String selId = lastSelection == null ? null : lastSelection.getId();
		int sel = 0;
		for (int i = 0; i < bundleGroupInfos.length; i++) {
			if (bundleGroupInfos[i].getId().equals(selId)) {
				sel = i;
			}

			TableItem item = new TableItem(table, SWT.NULL);
			item.setText(createRow(bundleGroupInfos[i]));
			item.setData(bundleGroupInfos[i]);
		}

		if (bundleGroupInfos.length > 0) {
			table.setSelection(sel);
			table.showSelection();
		}
	}

	private void disposeImages() {
		Iterator<Image> iter = cachedImages.values().iterator();
		while (iter.hasNext()) {
			Image image = iter.next();
			image.dispose();
		}
	}

	private void updateButtons(AboutBundleGroupData info) {
		if (info == null) {
			moreButton.setEnabled(false);
			pluginsButton.setEnabled(false);
			return;
		}

		if (featuresMap == null) {
			moreButton.setEnabled(true);
			pluginsButton.setEnabled(true);
			return;
		}

		moreButton.setEnabled(info.getLicenseUrl() != null);
		pluginsButton.setEnabled(true);
	}

	private void updateInfoArea(AboutBundleGroupData info) {
		if (info == null) {
			imageLabel.setImage(null);
			text.setText(""); //$NON-NLS-1$
			return;
		}

		ImageDescriptor desc = info.getFeatureImage();
		Image image = cachedImages.get(desc);
		if (image == null && desc != null) {
			image = desc.createImage();
			cachedImages.put(desc, image);
		}
		imageLabel.setImage(image);

		String aboutText = info.getAboutText();
		textManager.setItem(null);
		if (aboutText != null) {
			textManager.setItem(AboutUtils.scan(aboutText));
		}

		if (textManager.getItem() == null) {
			text.setText(WorkbenchSWTMessages.AboutFeaturesDialog_noInformation);
		}
	}

	public void setInitialSelection(AboutBundleGroupData info) {
		lastSelection = info;
	}

	private void sort(int column) {
		if (lastColumnChosen == column) {
			reverseSort = !reverseSort;
		} else {
			reverseSort = false;
			lastColumnChosen = column;
		}

		if (table.getItemCount() <= 1) {
			return;
		}

		int sel = table.getSelectionIndex();
		if (sel != -1) {
			lastSelection = bundleGroupInfos[sel];
		}

		switch (column) {
		case 0:
			AboutData.sortByProvider(reverseSort, bundleGroupInfos);
			break;
		case 1:
			AboutData.sortByName(reverseSort, bundleGroupInfos);
			break;
		case 2:
			AboutData.sortByVersion(reverseSort, bundleGroupInfos);
			break;
		case 3:
			AboutData.sortById(reverseSort, bundleGroupInfos);
			break;
		}
		table.setSortColumn(table.getColumn(column));
		table.setSortDirection(reverseSort ? SWT.DOWN : SWT.UP);

		refreshTable();
	}

	private void refreshTable() {
		TableItem[] items = table.getItems();

		for (int i = 0; i < items.length; i++) {
			items[i].setText(createRow(bundleGroupInfos[i]));
			items[i].setData(bundleGroupInfos[i]);
		}

		int sel = -1;
		if (lastSelection != null) {
			String oldId = lastSelection.getId();
			for (int k = 0; k < bundleGroupInfos.length; k++) {
				if (oldId.equalsIgnoreCase(bundleGroupInfos[k].getId())) {
					sel = k;
				}
			}

			table.setSelection(sel);
			table.showSelection();
		}

		updateInfoArea(lastSelection);
	}

	private static String[] createRow(AboutBundleGroupData info) {
		return new String[] { info.getProviderName(), info.getName(), info.getVersion(), info.getId() };
	}

	protected Collection<Object> getSelectionValue() {
		if (table == null || table.isDisposed())
			return null;

		TableItem[] items = table.getSelection();
		if (items.length <= 0) {
			return null;
		}

		List<Object> list = new ArrayList<Object>(1);
		list.add(items[0].getData());

		return list;
	}

	private void handleColumnsPressed() {
		ConfigureColumns.forTable(table, this);
	}

	private void handleMoreInfoPressed() {
		TableItem[] items = table.getSelection();
		if (items.length <= 0) {
			return;
		}

		AboutBundleGroupData info = (AboutBundleGroupData) items[0].getData();
		if (info == null || !AboutUtils.openBrowser(getShell(), info.getLicenseUrl())) {
			MessageDialog.openInformation(getShell(), WorkbenchSWTMessages.AboutFeaturesDialog_noInfoTitle,
					WorkbenchSWTMessages.AboutFeaturesDialog_noInformation);
		}
	}

	@Override
	protected void buttonPressed(int buttonId) {
		switch (buttonId) {
		case MORE_ID:
			handleMoreInfoPressed();
			break;
		case PLUGINS_ID:
			handlePluginInfoPressed();
			break;
		case COLUMNS_ID:
			handleColumnsPressed();
			break;
		default:
			super.buttonPressed(buttonId);
			break;
		}
	}
}
