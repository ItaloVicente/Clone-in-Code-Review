package org.eclipse.e4.ui.dialogs.filteredtree;

import java.net.URL;

import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.e4.ui.dialogs.textbundles.E4DialogMessages;
import org.eclipse.jface.action.ToolBarManager;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.jface.viewers.IContentProvider;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.osgi.util.NLS;
import org.eclipse.swt.SWT;
import org.eclipse.swt.accessibility.ACC;
import org.eclipse.swt.accessibility.AccessibleAdapter;
import org.eclipse.swt.accessibility.AccessibleControlAdapter;
import org.eclipse.swt.accessibility.AccessibleControlEvent;
import org.eclipse.swt.accessibility.AccessibleEvent;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.events.FocusAdapter;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseMoveListener;
import org.eclipse.swt.events.MouseTrackListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.TraverseEvent;
import org.eclipse.swt.events.TraverseListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;
import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;

public class FilteredTree extends Composite {

	protected Text filterText;

	protected ToolBarManager filterToolBar;

	protected Control clearButtonControl;

	protected TreeViewer treeViewer;

	protected Composite filterComposite;

	private PatternFilter patternFilter;

	protected String initialText = ""; //$NON-NLS-1$

	private Job refreshJob;

	protected Composite parent;

	protected boolean showFilterControls;

	protected Composite treeComposite;

	private static final String CLEAR_ICON = "org.eclipse.ui.internal.dialogs.CLEAR_ICON"; //$NON-NLS-1$

	private static final String DISABLED_CLEAR_ICON = "org.eclipse.ui.internal.dialogs.DCLEAR_ICON"; //$NON-NLS-1$

	private static final long SOFT_MAX_EXPAND_TIME = 200;

	static {
		Bundle bundle = FrameworkUtil.getBundle(FilteredTree.class);
		IPath enabledPath = new Path("$nl$/icons/full/etool16/clear_co.gif");
		URL enabledURL = FileLocator.find(bundle, enabledPath, null);
		ImageDescriptor enabledDesc = ImageDescriptor.createFromURL(enabledURL);
		if (enabledDesc != null) {
			JFaceResources.getImageRegistry().put(CLEAR_ICON, enabledDesc);
		}

		IPath disabledPath = new Path("$nl$/icons/full/etool16/clear_co.gif");
		URL disabledURL = FileLocator.find(bundle, disabledPath, null);
		ImageDescriptor disabledDesc = ImageDescriptor
				.createFromURL(disabledURL);
		if (disabledDesc != null) {
			JFaceResources.getImageRegistry().put(DISABLED_CLEAR_ICON,
					disabledDesc);
		}
	}

	public FilteredTree(Composite parent, int treeStyle, PatternFilter filter) {
		super(parent, SWT.NONE);
		this.parent = parent;
		init(treeStyle, filter);
	}

	@Deprecated
	public FilteredTree(Composite parent, int treeStyle, PatternFilter filter,
			boolean useNewLook) {
		this(parent, treeStyle, filter);
	}

	protected FilteredTree(Composite parent) {
		super(parent, SWT.NONE);
		this.parent = parent;
	}

	@Deprecated
	protected FilteredTree(Composite parent, boolean useNewLook) {
		this(parent);
	}

	protected void init(int treeStyle, PatternFilter filter) {
		patternFilter = filter;
		showFilterControls = true; // PlatformUI.getPreferenceStore().getBoolean(
		createControl(parent, treeStyle);
		createRefreshJob();
		setInitialText(E4DialogMessages.FilteredTree_FilterMessage);
		setFont(parent.getFont());
	}

	protected void createControl(Composite parent, int treeStyle) {
		GridLayout layout = new GridLayout();
		layout.marginHeight = 0;
		layout.marginWidth = 0;
		setLayout(layout);
		setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

		if (showFilterControls) {
			if (useNativeSearchField(parent)) {
				filterComposite = new Composite(this, SWT.NONE);
			} else {
				filterComposite = new Composite(this, SWT.BORDER);
				filterComposite.setBackground(getDisplay().getSystemColor(
						SWT.COLOR_LIST_BACKGROUND));
			}
			GridLayout filterLayout = new GridLayout(2, false);
			filterLayout.marginHeight = 0;
			filterLayout.marginWidth = 0;
			filterComposite.setLayout(filterLayout);
			filterComposite.setFont(parent.getFont());

			createFilterControls(filterComposite);
			filterComposite.setLayoutData(new GridData(SWT.FILL, SWT.BEGINNING,
					true, false));
		}

		treeComposite = new Composite(this, SWT.NONE);
		GridLayout treeCompositeLayout = new GridLayout();
		treeCompositeLayout.marginHeight = 0;
		treeCompositeLayout.marginWidth = 0;
		treeComposite.setLayout(treeCompositeLayout);
		GridData data = new GridData(SWT.FILL, SWT.FILL, true, true);
		treeComposite.setLayoutData(data);
		createTreeControl(treeComposite, treeStyle);
	}

	private static Boolean useNativeSearchField;

	private static boolean useNativeSearchField(Composite composite) {
		if (useNativeSearchField == null) {
			useNativeSearchField = Boolean.FALSE;
			Text testText = null;
			try {
				testText = new Text(composite, SWT.SEARCH | SWT.ICON_CANCEL);
				useNativeSearchField = new Boolean(
						(testText.getStyle() & SWT.ICON_CANCEL) != 0);
			} finally {
				if (testText != null) {
					testText.dispose();
				}
			}

		}
		return useNativeSearchField.booleanValue();
	}

	protected Composite createFilterControls(Composite parent) {
		createFilterText(parent);
		createClearText(parent);
		if (clearButtonControl != null) {
			clearButtonControl.setVisible(false);
		}
		if (filterToolBar != null) {
			filterToolBar.update(false);
			filterToolBar.getControl().setVisible(false);
		}
		return parent;
	}

	protected Control createTreeControl(Composite parent, int style) {
		treeViewer = doCreateTreeViewer(parent, style);
		GridData data = new GridData(SWT.FILL, SWT.FILL, true, true);
		treeViewer.getControl().setLayoutData(data);
		treeViewer.getControl().addDisposeListener(new DisposeListener() {
			@Override
			public void widgetDisposed(DisposeEvent e) {
				refreshJob.cancel();
			}
		});
		if (treeViewer instanceof NotifyingTreeViewer) {
			patternFilter.setUseCache(true);
		}
		treeViewer.addFilter(patternFilter);
		return treeViewer.getControl();
	}

	protected TreeViewer doCreateTreeViewer(Composite parent, int style) {
		return new NotifyingTreeViewer(parent, style);
	}

	private TreeItem getFirstMatchingItem(TreeItem[] items) {
		for (TreeItem item : items) {
			if (patternFilter.isLeafMatch(treeViewer, item.getData())
					&& patternFilter.isElementSelectable(item.getData())) {
				return item;
			}
			TreeItem treeItem = getFirstMatchingItem(item.getItems());
			if (treeItem != null) {
				return treeItem;
			}
		}
		return null;
	}

	private void createRefreshJob() {
		refreshJob = doCreateRefreshJob();
		refreshJob.setSystem(true);
	}

	protected BasicUIJob doCreateRefreshJob() {
		return new BasicUIJob("Refresh Filter", parent.getDisplay()) {//$NON-NLS-1$
			@Override
			public IStatus runInUIThread(IProgressMonitor monitor) {
				if (treeViewer.getControl().isDisposed()) {
					return Status.CANCEL_STATUS;
				}

				String text = getFilterString();
				if (text == null) {
					return Status.OK_STATUS;
				}

				boolean initial = initialText != null
						&& initialText.equals(text);
				if (initial) {
					patternFilter.setPattern(null);
				} else if (text != null) {
					patternFilter.setPattern(text);
				}

				Control redrawFalseControl = treeComposite != null ? treeComposite
						: treeViewer.getControl();
				try {
					redrawFalseControl.setRedraw(false);
					if (!narrowingDown) {
						TreeItem[] is = treeViewer.getTree().getItems();
						for (TreeItem item : is) {
							if (item.getExpanded()) {
								treeViewer.setExpandedState(item.getData(),
										false);
							}
						}
					}
					treeViewer.refresh(true);

					if (text.length() > 0 && !initial) {
						TreeItem[] items = getViewer().getTree().getItems();
						int treeHeight = getViewer().getTree().getBounds().height;
						int numVisibleItems = treeHeight
								/ getViewer().getTree().getItemHeight();
						long stopTime = SOFT_MAX_EXPAND_TIME
								+ System.currentTimeMillis();
						boolean cancel = false;
						if (items.length > 0
								&& recursiveExpand(items, monitor, stopTime,
										new int[] { numVisibleItems })) {
							cancel = true;
						}

						updateToolbar(true);

						if (cancel) {
							return Status.CANCEL_STATUS;
						}
					} else {
						updateToolbar(false);
					}
				} finally {
					TreeItem[] items = getViewer().getTree().getItems();
					if (items.length > 0
							&& getViewer().getTree().getSelectionCount() == 0) {
						treeViewer.getTree().setTopItem(items[0]);
					}
					redrawFalseControl.setRedraw(true);
				}
				return Status.OK_STATUS;
			}

			private boolean recursiveExpand(TreeItem[] items,
					IProgressMonitor monitor, long cancelTime,
					int[] numItemsLeft) {
				boolean canceled = false;
				for (int i = 0; !canceled && i < items.length; i++) {
					TreeItem item = items[i];
					boolean visible = numItemsLeft[0]-- >= 0;
					if (monitor.isCanceled()
							|| (!visible && System.currentTimeMillis() > cancelTime)) {
						canceled = true;
					} else {
						Object itemData = item.getData();
						if (itemData != null) {
							if (!item.getExpanded()) {
								treeViewer.setExpandedState(itemData, true);
							}
							TreeItem[] children = item.getItems();
							if (items.length > 0) {
								canceled = recursiveExpand(children, monitor,
										cancelTime, numItemsLeft);
							}
						}
					}
				}
				return canceled;
			}

		};
	}

	protected void updateToolbar(boolean visible) {
		if (clearButtonControl != null) {
			clearButtonControl.setVisible(visible);
		}
		if (filterToolBar != null) {
			filterToolBar.getControl().setVisible(visible);
		}
	}

	protected void createFilterText(Composite parent) {
		filterText = doCreateFilterText(parent);
		filterText.getAccessible().addAccessibleListener(
				new AccessibleAdapter() {
					@Override
					public void getName(AccessibleEvent e) {
						String filterTextString = filterText.getText();
						if (filterTextString.length() == 0
								|| filterTextString.equals(initialText)) {
							e.result = initialText;
						} else {
							e.result = NLS
									.bind(
											E4DialogMessages.FilteredTree_AccessibleListenerFiltered,
											new String[] {
													filterTextString,
													String.valueOf(getFilteredItemsCount()) });
						}
					}

					private int getFilteredItemsCount() {
						int total = 0;
						TreeItem[] items = getViewer().getTree().getItems();
						for (TreeItem item : items) {
							total += itemCount(item);

						}
						return total;
					}

					private int itemCount(TreeItem treeItem) {
						int count = 1;
						TreeItem[] children = treeItem.getItems();
						for (TreeItem element : children) {
							count += itemCount(element);

						}
						return count;
					}
				});

		filterText.addFocusListener(new FocusAdapter() {

			@Override
			public void focusLost(FocusEvent e) {
				if (filterText.getText().equals(initialText)) {
					setFilterText(""); //$NON-NLS-1$
					textChanged();
				}
			}
		});

		filterText.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				if (filterText.getText().equals(initialText)) {
					setFilterText(""); //$NON-NLS-1$
					textChanged();
				}
			}
		});

		filterText.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				boolean hasItems = getViewer().getTree().getItemCount() > 0;
				if (hasItems && e.keyCode == SWT.ARROW_DOWN) {
					treeViewer.getTree().setFocus();
					return;
				}
			}
		});

		filterText.addTraverseListener(new TraverseListener() {
			@Override
			public void keyTraversed(TraverseEvent e) {
				if (e.detail == SWT.TRAVERSE_RETURN) {
					e.doit = false;
					if (getViewer().getTree().getItemCount() == 0) {
						Display.getCurrent().beep();
					} else {
						boolean hasFocus = getViewer().getTree().setFocus();
						boolean textChanged = !getInitialText().equals(
								filterText.getText().trim());
						if (hasFocus && textChanged
								&& filterText.getText().trim().length() > 0) {
							Tree tree = getViewer().getTree();
							TreeItem item;
							if (tree.getSelectionCount() > 0) {
								item = getFirstMatchingItem(tree.getSelection());
							} else {
								item = getFirstMatchingItem(tree.getItems());
							}
							if (item != null) {
								tree.setSelection(new TreeItem[] { item });
								ISelection sel = getViewer().getSelection();
								getViewer().setSelection(sel, true);
							}
						}
					}
				}
			}
		});

		filterText.addModifyListener(new ModifyListener() {
			@Override
			public void modifyText(ModifyEvent e) {
				textChanged();
			}
		});

		if ((filterText.getStyle() & SWT.ICON_CANCEL) != 0) {
			filterText.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetDefaultSelected(SelectionEvent e) {
					if (e.detail == SWT.ICON_CANCEL) {
						clearText();
					}
				}
			});
		}

		GridData gridData = new GridData(SWT.FILL, SWT.CENTER, true, false);
		if ((filterText.getStyle() & SWT.ICON_CANCEL) != 0) {
			gridData.horizontalSpan = 2;
		}
		filterText.setLayoutData(gridData);
	}

	protected Text doCreateFilterText(Composite parent) {
		if (useNativeSearchField(parent)) {
			return new Text(parent, SWT.SINGLE | SWT.BORDER | SWT.SEARCH
					| SWT.ICON_CANCEL);
		}
		return new Text(parent, SWT.SINGLE);
	}

	private String previousFilterText;

	private boolean narrowingDown;

	protected void textChanged() {
		narrowingDown = previousFilterText == null
				|| previousFilterText
				.equals(E4DialogMessages.FilteredTree_FilterMessage)
				|| getFilterString().startsWith(previousFilterText);
		previousFilterText = getFilterString();
		refreshJob.cancel();
		refreshJob.schedule(getRefreshJobDelay());
	}

	protected long getRefreshJobDelay() {
		return 200;
	}

	@Override
	public void setBackground(Color background) {
		super.setBackground(background);
		if (filterComposite != null && (useNativeSearchField(filterComposite))) {
			filterComposite.setBackground(background);
		}
		if (filterToolBar != null && filterToolBar.getControl() != null) {
			filterToolBar.getControl().setBackground(background);
		}
	}


	private void createClearText(Composite parent) {
		if ((filterText.getStyle() & SWT.ICON_CANCEL) == 0) {
			final Image inactiveImage = JFaceResources.getImageRegistry()
					.getDescriptor(DISABLED_CLEAR_ICON).createImage();
			final Image activeImage = JFaceResources.getImageRegistry()
					.getDescriptor(CLEAR_ICON).createImage();
			final Image pressedImage = new Image(getDisplay(), activeImage,
					SWT.IMAGE_GRAY);

			final Label clearButton = new Label(parent, SWT.NONE);
			clearButton.setLayoutData(new GridData(SWT.BEGINNING, SWT.CENTER,
					false, false));
			clearButton.setImage(inactiveImage);
			clearButton.setBackground(parent.getDisplay().getSystemColor(
					SWT.COLOR_LIST_BACKGROUND));
			clearButton
			.setToolTipText(E4DialogMessages.FilteredTree_ClearToolTip);
			clearButton.addMouseListener(new MouseAdapter() {
				private MouseMoveListener fMoveListener;

				@Override
				public void mouseDown(MouseEvent e) {
					clearButton.setImage(pressedImage);
					fMoveListener = new MouseMoveListener() {
						private boolean fMouseInButton = true;

						@Override
						public void mouseMove(MouseEvent e) {
							boolean mouseInButton = isMouseInButton(e);
							if (mouseInButton != fMouseInButton) {
								fMouseInButton = mouseInButton;
								clearButton
								.setImage(mouseInButton ? pressedImage
										: inactiveImage);
							}
						}
					};
					clearButton.addMouseMoveListener(fMoveListener);
				}

				@Override
				public void mouseUp(MouseEvent e) {
					if (fMoveListener != null) {
						clearButton.removeMouseMoveListener(fMoveListener);
						fMoveListener = null;
						boolean mouseInButton = isMouseInButton(e);
						clearButton.setImage(mouseInButton ? activeImage
								: inactiveImage);
						if (mouseInButton) {
							clearText();
							filterText.setFocus();
						}
					}
				}

				private boolean isMouseInButton(MouseEvent e) {
					Point buttonSize = clearButton.getSize();
					return 0 <= e.x && e.x < buttonSize.x && 0 <= e.y
							&& e.y < buttonSize.y;
				}
			});
			clearButton.addMouseTrackListener(new MouseTrackListener() {
				@Override
				public void mouseEnter(MouseEvent e) {
					clearButton.setImage(activeImage);
				}

				@Override
				public void mouseExit(MouseEvent e) {
					clearButton.setImage(inactiveImage);
				}

				@Override
				public void mouseHover(MouseEvent e) {
				}
			});
			clearButton.addDisposeListener(new DisposeListener() {
				@Override
				public void widgetDisposed(DisposeEvent e) {
					inactiveImage.dispose();
					activeImage.dispose();
					pressedImage.dispose();
				}
			});
			clearButton.getAccessible().addAccessibleListener(
					new AccessibleAdapter() {
						@Override
						public void getName(AccessibleEvent e) {
							e.result = E4DialogMessages.FilteredTree_AccessibleListenerClearButton;
						}
					});
			clearButton.getAccessible().addAccessibleControlListener(
					new AccessibleControlAdapter() {
						@Override
						public void getRole(AccessibleControlEvent e) {
							e.detail = ACC.ROLE_PUSHBUTTON;
						}
					});
			this.clearButtonControl = clearButton;
		}
	}

	protected void clearText() {
		setFilterText(""); //$NON-NLS-1$
		textChanged();
	}

	protected void setFilterText(String string) {
		if (filterText != null) {
			filterText.setText(string);
			selectAll();
		}
	}

	public final PatternFilter getPatternFilter() {
		return patternFilter;
	}

	public TreeViewer getViewer() {
		return treeViewer;
	}

	public Text getFilterControl() {
		return filterText;
	}

	protected String getFilterString() {
		return filterText != null ? filterText.getText() : null;
	}

	public void setInitialText(String text) {
		initialText = text;
		if (filterText != null) {
			filterText.setMessage(text);
			if (filterText.isFocusControl()) {
				setFilterText(initialText);
				textChanged();
			} else {
				getDisplay().asyncExec(new Runnable() {
					@Override
					public void run() {
						if (!filterText.isDisposed()
								&& filterText.isFocusControl()) {
							setFilterText(initialText);
							textChanged();
						}
					}
				});
			}
		} else {
			setFilterText(initialText);
			textChanged();
		}
	}

	protected void selectAll() {
		if (filterText != null) {
			filterText.selectAll();
		}
	}

	protected String getInitialText() {
		return initialText;
	}

	public static Font getBoldFont(Object element, FilteredTree tree,
			PatternFilter filter) {
		String filterText = tree.getFilterString();

		if (filterText == null) {
			return null;
		}

		String initialText = tree.getInitialText();
		if (!filterText.equals("") && !filterText.equals(initialText)) {//$NON-NLS-1$
			if (tree.getPatternFilter() != filter) {
				boolean initial = initialText != null
						&& initialText.equals(filterText);
				if (initial) {
					filter.setPattern(null);
				} else if (filterText != null) {
					filter.setPattern(filterText);
				}
			}
			if (filter.isElementVisible(tree.getViewer(), element)
					&& filter.isLeafMatch(tree.getViewer(), element)) {
				return JFaceResources.getFontRegistry().getBold(
						JFaceResources.DIALOG_FONT);
			}
		}
		return null;
	}

	class NotifyingTreeViewer extends TreeViewer {

		public NotifyingTreeViewer(Composite parent, int style) {
			super(parent, style);
		}

		@Override
		public void add(Object parentElementOrTreePath, Object childElement) {
			getPatternFilter().clearCaches();
			super.add(parentElementOrTreePath, childElement);
		}

		@Override
		public void add(Object parentElementOrTreePath, Object[] childElements) {
			getPatternFilter().clearCaches();
			super.add(parentElementOrTreePath, childElements);
		}

		@Override
		protected void inputChanged(Object input, Object oldInput) {
			getPatternFilter().clearCaches();
			super.inputChanged(input, oldInput);
		}

		@Override
		public void insert(Object parentElementOrTreePath, Object element,
				int position) {
			getPatternFilter().clearCaches();
			super.insert(parentElementOrTreePath, element, position);
		}

		@Override
		public void refresh() {
			getPatternFilter().clearCaches();
			super.refresh();
		}

		@Override
		public void refresh(boolean updateLabels) {
			getPatternFilter().clearCaches();
			super.refresh(updateLabels);
		}

		@Override
		public void refresh(Object element) {
			getPatternFilter().clearCaches();
			super.refresh(element);
		}

		@Override
		public void refresh(Object element, boolean updateLabels) {
			getPatternFilter().clearCaches();
			super.refresh(element, updateLabels);
		}

		@Override
		public void remove(Object elementsOrTreePaths) {
			getPatternFilter().clearCaches();
			super.remove(elementsOrTreePaths);
		}

		@Override
		public void remove(Object parent, Object[] elements) {
			getPatternFilter().clearCaches();
			super.remove(parent, elements);
		}

		@Override
		public void remove(Object[] elementsOrTreePaths) {
			getPatternFilter().clearCaches();
			super.remove(elementsOrTreePaths);
		}

		@Override
		public void replace(Object parentElementOrTreePath, int index,
				Object element) {
			getPatternFilter().clearCaches();
			super.replace(parentElementOrTreePath, index, element);
		}

		@Override
		public void setChildCount(Object elementOrTreePath, int count) {
			getPatternFilter().clearCaches();
			super.setChildCount(elementOrTreePath, count);
		}

		@Override
		public void setContentProvider(IContentProvider provider) {
			getPatternFilter().clearCaches();
			super.setContentProvider(provider);
		}

		@Override
		public void setHasChildren(Object elementOrTreePath, boolean hasChildren) {
			getPatternFilter().clearCaches();
			super.setHasChildren(elementOrTreePath, hasChildren);
		}

	}

}
