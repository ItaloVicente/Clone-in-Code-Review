package org.eclipse.e4.ui.internal.about;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.e4.ui.about.BundleSigningInfo;
import org.eclipse.e4.ui.dialogs.filteredtree.BasicUIJob;
import org.eclipse.e4.ui.internal.DialogPlugin;
import org.eclipse.e4.ui.internal.IWorkbenchHelpContextIds;
import org.eclipse.e4.ui.internal.WorkbenchMessages;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.resource.ImageRegistry;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.jface.util.ConfigureColumns;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.IBaseLabelProvider;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.LabelProviderChangedEvent;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerComparator;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.osgi.util.NLS;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.Text;
import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;

public class AboutPluginsPage extends ProductInfoPage {

	private static final String UNKNOWN = "UNKNOWN";

	private static final String SIGNED_YES = "SIGNED_YES";

	private static final String SIGNED_NO = "SIGNED_NO";

	static {

		Bundle bundle = FrameworkUtil.getBundle(AboutPluginsPage.class);

		String rootPath = "$nl$/icons/full/obj16/";
		IPath signedNoPath = new Path(rootPath + "signed_no_tbl.png");
		URL signedNoURL = FileLocator.find(bundle, signedNoPath, null);
		ImageDescriptor signedNoDesc = ImageDescriptor.createFromURL(signedNoURL);
		if (signedNoDesc != null) {
			JFaceResources.getImageRegistry().put(SIGNED_NO, signedNoDesc);
		}

		IPath signedYesPath = new Path(rootPath + "signed_yes_tbl.png");
		URL signedYesURL = FileLocator.find(bundle, signedYesPath, null);
		ImageDescriptor signedYesDesc = ImageDescriptor.createFromURL(signedYesURL);
		if (signedYesDesc != null) {
			JFaceResources.getImageRegistry().put(SIGNED_YES, signedYesDesc);
		}

		IPath unkPath = new Path(rootPath + "signed_unk_tbl.png");
		URL unkURL = FileLocator.find(bundle, unkPath, null);
		ImageDescriptor unkDesc = ImageDescriptor.createFromURL(unkURL);
		if (unkDesc != null) {
			JFaceResources.getImageRegistry().put(UNKNOWN, unkDesc);
		}
	}

	public class BundleTableLabelProvider extends LabelProvider implements ITableLabelProvider {

		private LinkedList<AboutBundleData> resolveQueue = new LinkedList<AboutBundleData>();

		private List<AboutBundleData> updateQueue = new ArrayList<AboutBundleData>();

		private Job resolveJob = new Job(AboutPluginsPage.class.getName()) {
			{
				setSystem(true);
				setPriority(Job.SHORT);
			}

			@Override
			protected IStatus run(IProgressMonitor monitor) {
				while (true) {
					if (vendorInfo == null)
						return Status.OK_STATUS;
					Table table = vendorInfo.getTable();
					if (table == null || table.isDisposed())
						return Status.OK_STATUS;
					AboutBundleData data = null;
					synchronized (resolveQueue) {
						if (resolveQueue.isEmpty())
							return Status.OK_STATUS;
						data = resolveQueue.removeFirst();
					}
					try {
						data.isSigned();

						synchronized (updateQueue) {
							updateQueue.add(data);
						}
						updateJob.schedule();
					} catch (IllegalStateException e) {
					}
				}
			}
		};


		private Job updateJob = new BasicUIJob("Load", Display.getDefault()) {

			@Override
			public IStatus runInUIThread(IProgressMonitor monitor) {
				while (true) {
					Control page = getControl();
					if (page == null || page.isDisposed())
						return Status.OK_STATUS;
					AboutBundleData[] data = null;
					synchronized (updateQueue) {
						if (updateQueue.isEmpty())
							return Status.OK_STATUS;

						data = updateQueue.toArray(new AboutBundleData[updateQueue.size()]);
						updateQueue.clear();

					}
					fireLabelProviderChanged(new LabelProviderChangedEvent(BundleTableLabelProvider.this, data));
				}
			}
		};

		@Override
		public Image getColumnImage(Object element, int columnIndex) {

			if (columnIndex == 0) {

				ImageRegistry imageRegistry = JFaceResources.getImageRegistry();

				if (element instanceof AboutBundleData) {
					final AboutBundleData data = (AboutBundleData) element;
					if (data.isSignedDetermined()) {

						return imageRegistry.get(data.isSigned() ? SIGNED_YES : SIGNED_NO);
					}

					synchronized (resolveQueue) {
						resolveQueue.add(data);
					}
					resolveJob.schedule();

					return imageRegistry.get(UNKNOWN);
				}
			}

			return null;
		}

		@Override
		public String getColumnText(Object element, int columnIndex) {
			if (element instanceof AboutBundleData) {
				AboutBundleData data = (AboutBundleData) element;
				switch (columnIndex) {
				case 1:
					return data.getProviderName();
				case 2:
					return data.getName();
				case 3:
					return data.getVersion();
				case 4:
					return data.getId();
				}
			}
			return ""; //$NON-NLS-1$
		}
	}

	private static final String ID = "productInfo.plugins"; //$NON-NLS-1$

	private static final int TABLE_HEIGHT = 200;

	private final static int MORE_ID = IDialogConstants.CLIENT_ID + 1;
	private final static int SIGNING_ID = MORE_ID + 1;
	private final static int COLUMNS_ID = MORE_ID + 2;

	private static final IPath baseNLPath = new Path("$nl$"); //$NON-NLS-1$

	private static final String PLUGININFO = "about.html"; //$NON-NLS-1$

	private static final int PLUGIN_NAME_COLUMN_INDEX = 2;

	private static final int SIGNING_AREA_PERCENTAGE = 30;

	private TableViewer vendorInfo;

	private Button moreInfo, signingInfo;

	private String message;

	private String helpContextId = IWorkbenchHelpContextIds.ABOUT_PLUGINS_DIALOG;

	private String columnTitles[] = { WorkbenchMessages.AboutPluginsDialog_signed,
			WorkbenchMessages.AboutPluginsDialog_provider, WorkbenchMessages.AboutPluginsDialog_pluginName,
			WorkbenchMessages.AboutPluginsDialog_version, WorkbenchMessages.AboutPluginsDialog_pluginId,

	};
	private Bundle[] bundles = FrameworkUtil.getBundle(getClass()).getBundleContext().getBundles();
	private AboutBundleData[] bundleInfos;
	private SashForm sashForm;
	private BundleSigningInfo signingArea;

	public void setBundles(Bundle[] bundles) {
		this.bundles = bundles;
	}

	public void setHelpContextId(String id) {
		this.helpContextId = id;
	}

	@Override
	public void setMessage(String message) {
		this.message = message;
	}

	protected void handleSigningInfoPressed() {
		if (signingArea == null) {
			signingArea = new BundleSigningInfo();
			AboutBundleData bundleInfo = (AboutBundleData) ((IStructuredSelection) vendorInfo.getSelection())
					.getFirstElement();
			signingArea.setData(bundleInfo);

			signingArea.createContents(sashForm);
			sashForm.setWeights(new int[] { 100 - SIGNING_AREA_PERCENTAGE, SIGNING_AREA_PERCENTAGE });
			signingInfo.setText(WorkbenchMessages.AboutPluginsDialog_signingInfo_hide);

		} else {
			signingInfo.setText(WorkbenchMessages.AboutPluginsDialog_signingInfo_show);
			signingArea.dispose();
			signingArea = null;
			sashForm.setWeights(new int[] { 100 });
		}
	}

	@Override
	public void createPageButtons(Composite parent) {

		moreInfo = createButton(parent, MORE_ID, WorkbenchMessages.AboutPluginsDialog_moreInfo);
		moreInfo.setEnabled(false);

		signingInfo = createButton(parent, SIGNING_ID, WorkbenchMessages.AboutPluginsDialog_signingInfo_show);
		signingInfo.setEnabled(false);

		createButton(parent, COLUMNS_ID, WorkbenchMessages.AboutPluginsDialog_columns);
	}

	public static boolean isReady(Bundle bundle) {
		return bundle != null && isReady(bundle.getState());
	}

	public static boolean isReady(int bundleState) {
		return (bundleState & (Bundle.RESOLVED | Bundle.STARTING | Bundle.ACTIVE | Bundle.STOPPING)) != 0;
	}

	@Override
	public void createControl(Composite parent) {
		initializeDialogUnits(parent);

		Map<String, AboutBundleData> map = new HashMap<String, AboutBundleData>();
		for (int i = 0; i < bundles.length; ++i) {
			AboutBundleData data = new AboutBundleData(bundles[i]);
			if (isReady(data.getState()) && !map.containsKey(data.getVersionedId())) {
				map.put(data.getVersionedId(), data);
			}
		}
		bundleInfos = map.values().toArray(new AboutBundleData[0]);
		DialogPlugin.class.getSigners();

		sashForm = new SashForm(parent, SWT.HORIZONTAL | SWT.SMOOTH);
		FillLayout layout = new FillLayout();
		sashForm.setLayout(layout);
		layout.marginHeight = 0;
		layout.marginWidth = 0;
		GridData data = new GridData(GridData.FILL_BOTH);
		sashForm.setLayoutData(data);

		Composite outer = createOuterComposite(sashForm);

		if (message != null) {
			Label label = new Label(outer, SWT.NONE);
			label.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
			label.setFont(parent.getFont());
			label.setText(message);
		}

		createTable(outer);
		setControl(outer);
	}

	protected void createTable(Composite parent) {
		final Text filterText = new Text(parent, SWT.BORDER | SWT.SEARCH | SWT.ICON_CANCEL);
		filterText.setLayoutData(GridDataFactory.fillDefaults().create());
		filterText.setMessage(WorkbenchMessages.AboutPluginsDialog_filterTextMessage);
		filterText.setFocus();

		vendorInfo = new TableViewer(parent, SWT.H_SCROLL | SWT.V_SCROLL | SWT.SINGLE | SWT.FULL_SELECTION | SWT.BORDER);
		vendorInfo.setUseHashlookup(true);
		vendorInfo.getTable().setHeaderVisible(true);
		vendorInfo.getTable().setLinesVisible(true);
		vendorInfo.getTable().setFont(parent.getFont());
		vendorInfo.addSelectionChangedListener(new ISelectionChangedListener() {

			@Override
			public void selectionChanged(SelectionChangedEvent event) {
				checkEnablement();
			}
		});

		final TableComparator comparator = new TableComparator();
		vendorInfo.setComparator(comparator);
		int[] columnWidths = {
				convertHorizontalDLUsToPixels(30), // signature
				convertHorizontalDLUsToPixels(120), convertHorizontalDLUsToPixels(120),
				convertHorizontalDLUsToPixels(70), convertHorizontalDLUsToPixels(130), };

		for (int i = 0; i < columnTitles.length; i++) {
			TableColumn column = new TableColumn(vendorInfo.getTable(), SWT.NULL);
			if (i == PLUGIN_NAME_COLUMN_INDEX) { // prime initial sorting
				updateTableSorting(i);
			}
			column.setWidth(columnWidths[i]);
			column.setText(columnTitles[i]);
			final int columnIndex = i;
			column.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent e) {
					updateTableSorting(columnIndex);
				}
			});
		}

		vendorInfo.setContentProvider(new ArrayContentProvider());
		vendorInfo.setLabelProvider(new BundleTableLabelProvider());

		final BundlePatternFilter searchFilter = new BundlePatternFilter();
		filterText.addModifyListener(new ModifyListener() {
			@Override
			public void modifyText(ModifyEvent e) {
				searchFilter.setPattern(filterText.getText());
				vendorInfo.refresh();
			}
		});
		vendorInfo.addFilter(searchFilter);

		GridData gridData = new GridData(GridData.FILL, GridData.FILL, true, true);
		gridData.heightHint = convertVerticalDLUsToPixels(TABLE_HEIGHT);
		vendorInfo.getTable().setLayoutData(gridData);

		vendorInfo.setInput(bundleInfos);
	}

	private void updateTableSorting(final int columnIndex) {
		TableComparator comparator = (TableComparator) vendorInfo.getComparator();
		if (columnIndex == comparator.getSortColumn()) {
			comparator.setAscending(!comparator.isAscending());
		}
		comparator.setSortColumn(columnIndex);
		vendorInfo.getTable().setSortColumn(vendorInfo.getTable().getColumn(columnIndex));
		vendorInfo.getTable().setSortDirection(comparator.isAscending() ? SWT.UP : SWT.DOWN);
		vendorInfo.refresh(false);
	}

	private URL getMoreInfoURL(AboutBundleData bundleInfo, boolean makeLocal) {
		Bundle bundle = Platform.getBundle(bundleInfo.getId());
		if (bundle == null) {
			return null;
		}

		URL aboutUrl = Platform.find(bundle, baseNLPath.append(PLUGININFO), null);
		if (!makeLocal) {
			return aboutUrl;
		}
		if (aboutUrl != null) {
			try {
				URL result = Platform.asLocalURL(aboutUrl);
				try {
					URL about = new URL(aboutUrl, "about_files"); //$NON-NLS-1$
					if (about != null) {
						Platform.asLocalURL(about);
					}
				} catch (IOException e) {
				}
				return result;
			} catch (IOException e) {
			}
		}
		return null;
	}

	@Override
	String getId() {
		return ID;
	}

	private void checkEnablement() {
		IStructuredSelection selection = (IStructuredSelection) vendorInfo.getSelection();
		if (selection.getFirstElement() instanceof AboutBundleData) {
			AboutBundleData selected = (AboutBundleData) selection.getFirstElement();
			moreInfo.setEnabled(selectionHasInfo(selected));
			signingInfo.setEnabled(true);
			if (signingArea != null) {
				signingArea.setData(selected);
			}
		} else {
			signingInfo.setEnabled(false);
			moreInfo.setEnabled(false);
		}
	}

	@Override
	protected void buttonPressed(int buttonId) {
		switch (buttonId) {
		case MORE_ID:
			handleMoreInfoPressed();
			break;
		case SIGNING_ID:
			handleSigningInfoPressed();
			break;
		case COLUMNS_ID:
			handleColumnsPressed();
			break;
		default:
			super.buttonPressed(buttonId);
			break;
		}
	}

	private boolean selectionHasInfo(AboutBundleData bundleInfo) {

		URL infoURL = getMoreInfoURL(bundleInfo, false);


		return infoURL != null;
	}

	protected void handleMoreInfoPressed() {
		if (vendorInfo == null) {
			return;
		}

		if (vendorInfo.getSelection().isEmpty())
			return;

		AboutBundleData bundleInfo = (AboutBundleData) ((IStructuredSelection) vendorInfo.getSelection())
				.getFirstElement();

		if (!AboutUtils.openBrowser(getShell(), getMoreInfoURL(bundleInfo, true))) {
			String message = NLS.bind(WorkbenchMessages.AboutPluginsDialog_unableToOpenFile, PLUGININFO,
					bundleInfo.getId());

			AboutUtils.handleStatus(WorkbenchMessages.AboutPluginsDialog_errorTitle + ": " + message);
		}
	}

	private void handleColumnsPressed() {
		ConfigureColumns.forTable(vendorInfo.getTable(), this);
	}
}

class TableComparator extends ViewerComparator {

	private int sortColumn = 0;
	private int lastSortColumn = 0;
	private boolean ascending = true;
	private boolean lastAscending = true;

	@Override
	public int compare(Viewer viewer, Object e1, Object e2) {
		if (sortColumn == 0 && e1 instanceof AboutBundleData && e2 instanceof AboutBundleData) {
			AboutBundleData d1 = (AboutBundleData) e1;
			AboutBundleData d2 = (AboutBundleData) e2;
			int diff = getSignedSortValue(d1) - getSignedSortValue(d2);
			if (diff != 0 || lastSortColumn == 0)
				return ascending ? diff : -diff;
			if (viewer instanceof TableViewer) {
				TableViewer tableViewer = (TableViewer) viewer;
				IBaseLabelProvider baseLabel = tableViewer.getLabelProvider();
				if (baseLabel instanceof ITableLabelProvider) {
					ITableLabelProvider tableProvider = (ITableLabelProvider) baseLabel;
					String e1p = tableProvider.getColumnText(e1, lastSortColumn);
					String e2p = tableProvider.getColumnText(e2, lastSortColumn);
					int result = getComparator().compare(e1p, e2p);
					return lastAscending ? result : (-1) * result;
				}
			}
			return 0;
		}
		if (viewer instanceof TableViewer) {
			TableViewer tableViewer = (TableViewer) viewer;
			IBaseLabelProvider baseLabel = tableViewer.getLabelProvider();
			if (baseLabel instanceof ITableLabelProvider) {
				ITableLabelProvider tableProvider = (ITableLabelProvider) baseLabel;
				String e1p = tableProvider.getColumnText(e1, sortColumn);
				String e2p = tableProvider.getColumnText(e2, sortColumn);
				int result = getComparator().compare(e1p, e2p);
				if (result == 0) {
					if (lastSortColumn != 0) {
						e1p = tableProvider.getColumnText(e1, lastSortColumn);
						e2p = tableProvider.getColumnText(e2, lastSortColumn);
						result = getComparator().compare(e1p, e2p);
						return lastAscending ? result : (-1) * result;
					} // secondary sort is by column 0
					if (e1 instanceof AboutBundleData && e2 instanceof AboutBundleData) {
						AboutBundleData d1 = (AboutBundleData) e1;
						AboutBundleData d2 = (AboutBundleData) e2;
						int diff = getSignedSortValue(d1) - getSignedSortValue(d2);
						return lastAscending ? diff : -diff;
					}
				}
				return ascending ? result : (-1) * result;
			}
		}

		return super.compare(viewer, e1, e2);
	}

	private int getSignedSortValue(AboutBundleData data) {
		if (!data.isSignedDetermined()) {
			return 0;
		} else if (data.isSigned()) {
			return 1;
		} else {
			return -1;
		}
	}

	public int getSortColumn() {
		return sortColumn;
	}

	public void setSortColumn(int sortColumn) {
		if (this.sortColumn != sortColumn) {
			lastSortColumn = this.sortColumn;
			lastAscending = this.ascending;
			this.sortColumn = sortColumn;
		}
	}

	public boolean isAscending() {
		return ascending;
	}

	public void setAscending(boolean ascending) {
		this.ascending = ascending;
	}
}

final class BundlePatternFilter extends ViewerFilter {

	private String pattern;

	public void setPattern(String searchPattern) {
		if (searchPattern == null || searchPattern.length() == 0) {
			pattern = null;
		} else {
			pattern = ".*" + searchPattern + ".*";
		}
	}

	@Override
	public boolean select(Viewer viewer, Object parentElement, Object element) {

		if (pattern != null && element instanceof AboutBundleData) {
			AboutBundleData data = (AboutBundleData) element;
			return data.getName().matches(pattern) || data.getProviderName().matches(pattern)
					|| data.getId().matches(pattern);
		}
		return true;
	}
}
