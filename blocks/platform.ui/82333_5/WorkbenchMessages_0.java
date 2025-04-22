
package org.eclipse.ui.internal;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.ParameterizedCommand;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExecutableExtension;
import org.eclipse.e4.ui.internal.workbench.renderers.swt.AbstractTableInformationControl;
import org.eclipse.e4.ui.model.application.ui.basic.MStackElement;
import org.eclipse.e4.ui.workbench.modeling.EPartService;
import org.eclipse.e4.ui.workbench.modeling.EPartService.PartState;
import org.eclipse.jface.bindings.Trigger;
import org.eclipse.jface.bindings.TriggerSequence;
import org.eclipse.jface.bindings.keys.KeyStroke;
import org.eclipse.jface.bindings.keys.SWTKeySupport;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.FocusAdapter;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.FocusListener;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.MouseMoveListener;
import org.eclipse.swt.events.TraverseEvent;
import org.eclipse.swt.events.TraverseListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IEditorReference;
import org.eclipse.ui.IPerspectiveDescriptor;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.IWorkbenchPartReference;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.contexts.IContextService;
import org.eclipse.ui.handlers.HandlerUtil;
import org.eclipse.ui.keys.IBindingService;


public abstract class FilteredTableBaseHandler extends AbstractHandler implements
		IExecutableExtension {

	private static final String EMPTY_STRING = ""; //$NON-NLS-1$

	private Object selection;

	protected IWorkbenchWindow window;

	protected boolean gotoDirection;

	private TriggerSequence[] backwardTriggerSequences = null;

	protected ParameterizedCommand commandBackward = null;

	protected ParameterizedCommand commandForward = null;
	private TriggerSequence[] forwardTriggerSequences = null;


	protected int getCurrentItemIndex() {
		return 0;
	}

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		window = HandlerUtil.getActiveWorkbenchWindowChecked(event);

		IWorkbenchPage page = window.getActivePage();
		IWorkbenchPart activePart= page.getActivePart();
		getTriggers();
		openDialog((WorkbenchPage) page, activePart);
		clearTriggers();
		activate(page, selection);

		return null;
	}

	protected Object result;
	protected Shell dialog;
	private Text text;
	private Label labelTitle = null;
	private Label labelSeparator;
	private Table table;
	private TableViewer tableViewer;
	private TableColumn tc;
	private TableViewerColumn tableViewerColumn;

	public void openDialog(WorkbenchPage page, IWorkbenchPart activePart) {
		final int MAX_ITEMS = 15;
		Shell shell = null;
		selection = null;

		if (activePart != null)
			shell = activePart.getSite().getShell();
		if (shell == null)
			shell = window.getShell();
		dialog = new Shell(shell, SWT.MODELESS);
		dialog.setBackground(getBackground());
		dialog.setMinimumSize(new Point(150, 120));
		Display display = dialog.getDisplay();
		dialog.setLayout(new FillLayout());

		Composite composite = new Composite(dialog, SWT.NONE);
		composite.setBackground(getBackground());
		GridLayout gl_composite = new GridLayout(1, false);
		composite.setLayout(gl_composite);

		if (isFiltered()) {
			text = new Text(composite, SWT.NONE);
			text.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
			text.setBackground(getBackground());
		} else {
			labelTitle = new Label(composite, SWT.NONE);
			labelTitle.setText(getTableHeader(activePart));
			labelTitle.setBackground(getBackground());
			labelTitle.setForeground(getForeground());
		}

		labelSeparator = new Label(composite, SWT.HORIZONTAL);
		labelSeparator.setBackgroundImage(getSeparatorBgImage());
		GridData gd_label = new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1);
		gd_label.heightHint = 1;
		labelSeparator.setLayoutData(gd_label);

		tableViewer = new TableViewer(composite, SWT.SINGLE | SWT.FULL_SELECTION);
		table = tableViewer.getTable();
		table.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		table.setBackground(getBackground());

		tableViewerColumn = new TableViewerColumn(tableViewer, SWT.NONE);
		tableViewerColumn.setLabelProvider(getColumnLabelProvider());
		tc = tableViewerColumn.getColumn();
		tc.setResizable(false);

		tableViewer.setContentProvider(ArrayContentProvider.getInstance());

		if (isFiltered()) {
			tableViewer.addFilter(getFilter());
			addModifyListener(text);
			addKeyListener(text);
			text.setMessage(WorkbenchMessages.FilteredTableBase_Filter);
			text.setText(EMPTY_STRING);
		}

		tableViewer.setInput(page.getSortedEditorReferences());

		int tableItemCount = table.getItemCount();

		switch (tableItemCount) {
		case 0:
			cancel(dialog);
			return;
		case 1:
			table.setSelection(0);
			break;
		default:
			int i;
			if (gotoDirection) {
				i= getCurrentItemIndex() + 1;
				if (i >= tableItemCount)
					i= 0;
			} else {
				i= getCurrentItemIndex() - 1;
				if (i < 0)
					i= tableItemCount - 1;
			}
			table.setSelection(i);
		}

		tc.pack();
		table.pack();
		dialog.pack();

		Rectangle tableBounds = table.getBounds();
		tableBounds.height = Math.min(tableBounds.height, table.getItemHeight() * MAX_ITEMS);
		table.setBounds(tableBounds);

		Rectangle dialogBounds = dialog.getBounds();
		if (!isFiltered()) {
			dialogBounds.height = labelTitle.getBounds().height + labelSeparator.getBounds().height + tableBounds.height
					+ gl_composite.marginHeight * 2 + gl_composite.verticalSpacing * 2;
		} else {
			dialogBounds.height = text.getBounds().height + +labelSeparator.getBounds().height + tableBounds.height
					+ gl_composite.marginHeight * 2 + gl_composite.verticalSpacing * 2;
		}
		dialog.setBounds(dialogBounds);
		tc.setWidth(table.getClientArea().width);

		if (isFiltered()) {
			text.setFocus();
			text.addFocusListener(fAdapter);
		} else {
			table.setFocus();
		}
		table.addFocusListener(fAdapter);
		dialog.addFocusListener(fAdapter);

		table.addMouseMoveListener(new MouseMoveListener() {
			TableItem fLastItem = null;

			@Override
			public void mouseMove(MouseEvent e) {
				if (table.equals(e.getSource())) {
					Object o = table.getItem(new Point(e.x, e.y));
					if (fLastItem == null ^ o == null) {
						table.setCursor(o == null ? null : table.getDisplay().getSystemCursor(
								SWT.CURSOR_HAND));
					}
					if (o instanceof TableItem) {
						if (!o.equals(fLastItem)) {
							fLastItem = (TableItem) o;
							table.setSelection(new TableItem[] { fLastItem });
						}
					} else if (o == null) {
						fLastItem = null;
					}
				}
			}
		});

		setDialogLocation(dialog, activePart);

		final IContextService contextService = window
				.getWorkbench().getService(IContextService.class);
		try {
			dialog.open();
			addMouseListener(table, dialog);
			contextService.registerShell(dialog, IContextService.TYPE_NONE);
			addKeyListener(table, dialog);
			addTraverseListener(table);

			while (!dialog.isDisposed()) {
				if (!display.readAndDispatch()) {
					display.sleep();
				}
			}
		} finally {
			if (!dialog.isDisposed()) {
				cancel(dialog);
			}
			contextService.unregisterShell(dialog);
		}
	}

	private Image getSeparatorBgImage() {
		Image backgroundImage = new Image(Display.getDefault(), 1, 1);
		GC gc = new GC(backgroundImage);
		gc.setBackground(new Color(dialog.getDisplay(), 127, 127, 127));
		gc.fillRectangle(0, 0, 1, 1);
		gc.dispose();
		return backgroundImage;
	}

	private FocusAdapter fAdapter = new FocusAdapter() {
		@Override
		public void focusLost(FocusEvent e) {
			dialog.getDisplay().asyncExec(() -> {
				if (dialog.isDisposed()) {
					return;
				}
				Control fc = dialog.getDisplay().getFocusControl();
				if (fc != text && fc != table && fc != dialog) {
					cancel(dialog);
				}
			});
		}
	};

	protected void setDialogLocation(final Shell dialog, IWorkbenchPart activePart) {
		Display display = dialog.getDisplay();
		Rectangle dialogBounds = dialog.getBounds();
		Rectangle parentBounds = dialog.getParent().getBounds();

		Rectangle monitorBounds = activePart == null ? display.getPrimaryMonitor().getBounds()
				: ((Control) ((PartSite) activePart.getSite()).getModel().getWidget()).getMonitor().getBounds();

		dialogBounds.x = parentBounds.x
				+ ((parentBounds.width - dialogBounds.width) / 2);
		dialogBounds.y = parentBounds.y
				+ ((parentBounds.height - dialogBounds.height) / 2);
		if (!monitorBounds.contains(dialogBounds.x, dialogBounds.y)
				|| !monitorBounds.contains(dialogBounds.x + dialogBounds.width,
						dialogBounds.y + dialogBounds.height)) {
			dialogBounds.x = Math.max(0,
					monitorBounds.x + (monitorBounds.width - dialogBounds.width) / 2);
			dialogBounds.y =  Math.max(0,
					monitorBounds.y + (monitorBounds.height - dialogBounds.height) / 2);
		}

		dialog.setLocation(dialogBounds.x, dialogBounds.y);
	}

	protected void clearTriggers() {
		forwardTriggerSequences = null;
		backwardTriggerSequences = null;
	}

	protected void getTriggers() {
		commandForward = getForwardCommand();
		commandBackward = getBackwardCommand();

		final IBindingService bindingService = window
				.getWorkbench().getService(IBindingService.class);
		forwardTriggerSequences = bindingService
				.getActiveBindingsFor(commandForward);
		backwardTriggerSequences = bindingService
				.getActiveBindingsFor(commandBackward);
	}

	private KeyStroke computeKeyStroke(KeyEvent e) {
		int accelerator = SWTKeySupport.convertEventToUnmodifiedAccelerator(e);
		return SWTKeySupport.convertAcceleratorToKeyStroke(accelerator);
	}

	boolean computeAcceleratorForward(KeyEvent e) {
		boolean acceleratorForward = false;
		if (commandForward != null) {
			if (forwardTriggerSequences != null) {
				final int forwardCount = forwardTriggerSequences.length;
				for (int i = 0; i < forwardCount; i++) {
					final TriggerSequence triggerSequence = forwardTriggerSequences[i];

					final Trigger[] triggers = triggerSequence.getTriggers();
					final int triggersLength = triggers.length;
					if ((triggersLength > 0) && (triggers[triggersLength - 1].equals(computeKeyStroke(e)))) {
						acceleratorForward = true;
						break;
					}
				}
			}
		}
		return acceleratorForward;
	}

	boolean computeAcceleratorBackward(KeyEvent e) {
		boolean acceleratorBackward = false;
		if (commandBackward != null) {
			if (backwardTriggerSequences != null) {
				final int backwardCount = backwardTriggerSequences.length;
				for (int i = 0; i < backwardCount; i++) {
					final TriggerSequence triggerSequence = backwardTriggerSequences[i];

					final Trigger[] triggers = triggerSequence.getTriggers();
					final int triggersLength = triggers.length;
					if ((triggersLength > 0) && (triggers[triggersLength - 1].equals(computeKeyStroke(e)))) {
						acceleratorBackward = true;
						break;
					}
				}
			}
		}
		return acceleratorBackward;
	}

	protected void addModifyListener(Text text) {
		text.addModifyListener(e -> {
			String searchText = ((Text) e.widget).getText();
			setMatcherString(searchText);
			tableViewer.refresh();
		});
	}

	protected void addKeyListener(Text text) {
		text.addKeyListener(new KeyListener() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (computeAcceleratorForward(e)) {
					moveForward();
				} else if (computeAcceleratorBackward(e)) {
					moveBackward();
				}
				switch (e.keyCode) {
				case SWT.CR:
				case SWT.KEYPAD_CR:
					ok(dialog, table);
					break;
				case SWT.ARROW_DOWN:
					moveForward();
					break;
				case SWT.ARROW_UP:
					moveBackward();
					break;
				case SWT.ESC:
					cancel(dialog);
					break;
				}
			}

			@Override
			public void keyReleased(KeyEvent e) {
			}
		});
	}

	protected Color getForeground(){
		return dialog.getDisplay().getSystemColor(SWT.COLOR_TITLE_FOREGROUND);
	}
	protected Color getBackground() {
		return dialog.getDisplay().getSystemColor(SWT.COLOR_LIST_BACKGROUND);
	}

	private static String RE_TEXT = "[ \\w\\d_\\-\\+\\.\\*\\?\\$\\|\\(\\)\\[\\]\\{\\}@#]"; //$NON-NLS-1$

	protected void addKeyListener(final Table table, final Shell dialog) {
		table.addKeyListener(new KeyListener() {
			private boolean firstKey = true;

			private boolean quickReleaseMode = false;

			@Override
			public void keyPressed(KeyEvent e) {
				int keyCode = e.keyCode;
				char character = e.character;

				boolean acceleratorForward = computeAcceleratorForward(e);
				boolean acceleratorBackward = computeAcceleratorBackward(e);

				if (character == SWT.CR || character == SWT.LF) {
					ok(dialog, table);
				} else if (acceleratorForward) {
					if (firstKey && e.stateMask != 0) {
						quickReleaseMode = true;
					}

					moveForward();
				} else if (acceleratorBackward) {
					if (firstKey && e.stateMask != 0) {
						quickReleaseMode = true;
					}

					moveBackward();
				} else if (keyCode != SWT.ALT && keyCode != SWT.COMMAND
						&& keyCode != SWT.CTRL && keyCode != SWT.SHIFT
						&& keyCode != SWT.ARROW_DOWN && keyCode != SWT.ARROW_UP
						&& keyCode != SWT.ARROW_LEFT
						&& keyCode != SWT.ARROW_RIGHT) {
					if (!isFiltered()) {
						cancel(dialog);
					} else {
						String s = Character.toString(e.character);
						if (e.keyCode == SWT.BS) {
							String curText = text.getText();
							if (curText.length() > 0) {
								text.setText(curText.substring(0, curText.length() - 1));
							}
						} else if (s.matches(RE_TEXT)) {
							text.append(Character.toString(e.character));
						}
						else {
							cancel(dialog);
						}
					}
				} else if (keyCode == SWT.ARROW_DOWN && table.getSelectionIndex() == table.getItemCount() - 1) {
					moveForward();
					e.doit = false;
				} else if (keyCode == SWT.ARROW_UP && table.getSelectionIndex() == 0) {
					moveBackward();
					e.doit = false;
				}

				firstKey = false;
			}

			@Override
			public void keyReleased(KeyEvent e) {
				int keyCode = e.keyCode;
				int stateMask = e.stateMask;

				final IPreferenceStore store = WorkbenchPlugin.getDefault()
						.getPreferenceStore();
				final boolean stickyCycle = store
						.getBoolean(IPreferenceConstants.STICKY_CYCLE);
				if (!isFiltered() && (!stickyCycle && (firstKey || quickReleaseMode))
						&& keyCode == stateMask) {
					ok(dialog, table);
				}
			}
		});
	}

	private void moveForward() {
		int index = table.getSelectionIndex();
		if (isFiltered()) {
			if (text.isFocusControl()) {
				table.setFocus();
			} else if (index == table.getItemCount() - 1) {
				text.setFocus();
				return;
			}
		}

		table.setSelection((index + 1) % table.getItemCount());

	}

	private void moveBackward() {
		int index = table.getSelectionIndex();
		if (!isFiltered()) {
			table.setSelection(index >= 1 ? index - 1 : table.getItemCount() - 1);
		} else {
			if (text.isFocusControl()) {
				table.setFocus();
				table.setSelection(index >= 1 ? index - 1 : table.getItemCount() - 1);
			} else if (index == 0) {
				text.setFocus();
			} else {
				table.setSelection(index >= 1 ? index - 1 : table.getItemCount() - 1);
			}
		}
	}

	protected final void addTraverseListener(final Table table) {
		table.addTraverseListener(new TraverseListener() {
			@Override
			public final void keyTraversed(final TraverseEvent event) {
				event.doit = false;
			}
		});
	}

	protected void activate(IWorkbenchPage page, Object selectedItem) {
		if (selectedItem != null) {
			if (selectedItem instanceof MStackElement) {
				EPartService partService = page.getWorkbenchWindow().getService(EPartService.class);
				partService.showPart(((MStackElement) selectedItem).getElementId(), PartState.ACTIVATE);

				return;
			}
			if (selectedItem instanceof IEditorReference) {
				page.setEditorAreaVisible(true);
			}
			if (selectedItem instanceof IWorkbenchPartReference) {
				IWorkbenchPart part = ((IWorkbenchPartReference) selectedItem)
						.getPart(true);
				if (part != null) {
					page.activate(part);
				}
				return;
			}
			if (selectedItem instanceof IPerspectiveDescriptor){
	            IPerspectiveDescriptor persp = (IPerspectiveDescriptor) selectedItem;
	            page.setPerspective(persp);
				IWorkbenchPart activePart = page.getActivePart();
				if (activePart != null) {
					activePart.setFocus();
				}
			}
		}
	}

	protected void cancel(Shell dialog) {
		selection = null;
		dialog.close();
	}

	protected void ok(Shell dialog, final Table table) {
		TableItem[] items = table.getSelection();

		if (items != null && items.length == 1) {
			selection = items[0].getData();
		}

		dialog.close();
	}

	protected void addMouseListener(final Table table, final Shell dialog) {
		table.addMouseListener(new MouseListener() {
			@Override
			public void mouseDoubleClick(MouseEvent e) {
				ok(dialog, table);
			}

			@Override
			public void mouseDown(MouseEvent e) {
				ok(dialog, table);
			}

			@Override
			public void mouseUp(MouseEvent e) {
				ok(dialog, table);
			}
		});
	}

	protected boolean isFiltered() {
		return true;
	}

	protected boolean isPersistent() {
		return true;
	}

	protected abstract ColumnLabelProvider getColumnLabelProvider();

	protected abstract ViewerFilter getFilter();

	protected abstract void setMatcherString(String pattern);

	protected abstract Object getInput(WorkbenchPage page);

	protected abstract ParameterizedCommand getBackwardCommand();

	protected abstract ParameterizedCommand getForwardCommand();

	protected String getTableHeader(IWorkbenchPart activePart) {
		return EMPTY_STRING;
	}


	public Object getSelection() {
		return selection;
	}

	public IWorkbenchWindow getWindow() {
		return window;
	}

	public TriggerSequence[] getBackwardTriggerSequences() {
		return backwardTriggerSequences;
	}

	public TriggerSequence[] getForwardTriggerSequences() {
		return forwardTriggerSequences;
	}

	@Override
	public void setInitializationData(IConfigurationElement config,
			String propertyName, Object data) throws CoreException {
		gotoDirection = data == null || "true".equals(data); //$NON-NLS-1$
	}
}
