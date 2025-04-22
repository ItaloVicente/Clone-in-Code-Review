package org.eclipse.ui.internal.quickaccess;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Inject;
import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.expressions.EvaluationResult;
import org.eclipse.core.expressions.Expression;
import org.eclipse.core.expressions.ExpressionInfo;
import org.eclipse.core.expressions.IEvaluationContext;
import org.eclipse.core.runtime.Assert;
import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.ui.model.application.MApplication;
import org.eclipse.e4.ui.model.application.ui.basic.MPart;
import org.eclipse.e4.ui.model.application.ui.basic.MWindow;
import org.eclipse.e4.ui.workbench.IPresentationEngine;
import org.eclipse.e4.ui.workbench.modeling.EPartService;
import org.eclipse.jface.dialogs.IDialogSettings;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.jface.util.Geometry;
import org.eclipse.jface.window.Window;
import org.eclipse.osgi.util.NLS;
import org.eclipse.swt.SWT;
import org.eclipse.swt.accessibility.ACC;
import org.eclipse.swt.accessibility.AccessibleAdapter;
import org.eclipse.swt.accessibility.AccessibleEvent;
import org.eclipse.swt.events.ControlEvent;
import org.eclipse.swt.events.ControlListener;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.events.FocusAdapter;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.FocusListener;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.ShellAdapter;
import org.eclipse.swt.events.ShellEvent;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Monitor;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.ISources;
import org.eclipse.ui.IWorkbenchCommandConstants;
import org.eclipse.ui.handlers.IHandlerService;
import org.eclipse.ui.internal.WorkbenchPlugin;
import org.eclipse.ui.swt.IFocusService;


public class SearchField {

	private static final String TEXT_ARRAY = "textArray"; //$NON-NLS-1$
	private static final String TEXT_ENTRIES = "textEntries"; //$NON-NLS-1$
	private static final String ORDERED_PROVIDERS = "orderedProviders"; //$NON-NLS-1$
	private static final String ORDERED_ELEMENTS = "orderedElements"; //$NON-NLS-1$
	private static final int MAXIMUM_NUMBER_OF_ELEMENTS = 60;
	private static final int MAXIMUM_NUMBER_OF_TEXT_ENTRIES_PER_ELEMENT = 3;
	private static final String DIALOG_HEIGHT = "dialogHeight"; //$NON-NLS-1$
	private static final String DIALOG_WIDTH = "dialogWidth"; //$NON-NLS-1$

	Shell shell;
	private Text text;

	private QuickAccessContents quickAccessContents;

	private MWindow window;

	private Map<String, QuickAccessProvider> providerMap = new HashMap<String, QuickAccessProvider>();

	private Map<String, QuickAccessElement> elementMap = new HashMap<String, QuickAccessElement>();

	private Map<QuickAccessElement, ArrayList<String>> textMap = new HashMap<QuickAccessElement, ArrayList<String>>();

	private LinkedList<QuickAccessElement> previousPicksList = new LinkedList<QuickAccessElement>();
	private int dialogHeight = -1;
	private int dialogWidth = -1;
	private Control previousFocusControl;

	@Inject
	private EPartService partService;
	private Table table;

	private String selectedString = ""; //$NON-NLS-1$
	private AccessibleAdapter accessibleListener;


	@PostConstruct
	void createWidget(final Composite parent, MApplication application, MWindow window) {
		this.window = window;
		final Composite comp = new Composite(parent, SWT.NONE);
		comp.setLayout(new GridLayout());
		text = createText(comp);

		parent.getShell().addControlListener(new ControlListener() {
			@Override
			public void controlResized(ControlEvent e) {
				closeDropDown();
			}

			@Override
			public void controlMoved(ControlEvent e) {
				closeDropDown();
			}

			private void closeDropDown() {
				if (shell == null || shell.isDisposed() || text.isDisposed() || !shell.isVisible())
					return;
				quickAccessContents.doClose();
			}
		});

		hookUpSelectAll();

		final CommandProvider commandProvider = new CommandProvider();
		QuickAccessProvider[] providers = new QuickAccessProvider[] {
				new PreviousPicksProvider(previousPicksList),
				new EditorProvider(), new ViewProvider(application, window),
				new PerspectiveProvider(), commandProvider, new ActionProvider(),
				new WizardProvider(), new PreferenceProvider(), new PropertiesProvider() };
		for (int i = 0; i < providers.length; i++) {
			providerMap.put(providers[i].getId(), providers[i]);
		}
		restoreDialog();

		quickAccessContents = new QuickAccessContents(providers) {
			@Override
			protected void updateFeedback(boolean filterTextEmpty, boolean showAllMatches) {
			}

			@Override
			protected void doClose() {
				text.setText(""); //$NON-NLS-1$
				resetProviders();
				dialogHeight = shell.getSize().y;
				dialogWidth = shell.getSize().x;
				shell.setVisible(false);
				removeAccessibleListener();
			}

			@Override
			protected QuickAccessElement getPerfectMatch(String filter) {
				return elementMap.get(filter);
			}

			@Override
			protected void handleElementSelected(String string, Object selectedElement) {
				if (selectedElement instanceof QuickAccessElement) {
					QuickAccessElement element = (QuickAccessElement) selectedElement;
					addPreviousPick(string, element);
					text.setText(""); //$NON-NLS-1$
					element.execute();

					if (text.isFocusControl()) {
						MPart activePart = partService.getActivePart();
						if (activePart != null) {
							IPresentationEngine pe = activePart.getContext().get(
									IPresentationEngine.class);
							pe.focusGui(activePart);
						}
					}
				}
			}
		};
		quickAccessContents.hookFilterText(text);
		shell = new Shell(parent.getShell(), SWT.RESIZE | SWT.ON_TOP);
		shell.setBackground(shell.getDisplay().getSystemColor(SWT.COLOR_WHITE));
		shell.setText(QuickAccessMessages.QuickAccess_EnterSearch); // just for debugging, not shown anywhere
		shell.addShellListener(new ShellAdapter() {
			@Override
			public void shellClosed(ShellEvent e) {
				quickAccessContents.doClose();
				e.doit = false; // that would prevent shell from being disposed
			}
		});
		text.addDisposeListener(new DisposeListener() {
			@Override
			public void widgetDisposed(DisposeEvent e) {
				if (!shell.isDisposed()) {
					storeShellSize();
					shell.close();
					shell.dispose();
				}
			}
		});
		GridLayoutFactory.fillDefaults().applyTo(shell);
		table = quickAccessContents.createTable(shell, Window.getDefaultOrientation());
		text.addFocusListener(new FocusListener() {
			@Override
			public void focusLost(FocusEvent e) {
				table.getDisplay().asyncExec(new Runnable() {
					@Override
					public void run() {
						checkFocusLost(table, text);
					}
				});
			}

			@Override
			public void focusGained(FocusEvent e) {
				IHandlerService hs = SearchField.this.window.getContext()
						.get(IHandlerService.class);
				if (commandProvider.getContextSnapshot() == null) {
					commandProvider.setSnapshot(hs.createContextSnapshot(true));
				}

				previousFocusControl = (Control) e.getSource();
			}
			
		});
		table.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				table.getDisplay().asyncExec(new Runnable() {
					@Override
					public void run() {
						checkFocusLost(table, text);
					}
				});
			}
		});
		text.addModifyListener(new ModifyListener() {
			@Override
			public void modifyText(ModifyEvent e) {
				boolean wasVisible = shell.getVisible();
				boolean nowVisible = text.getText().length() > 0;
				if (!wasVisible && nowVisible) {
					layoutShell();
					addAccessibleListener();
					quickAccessContents.preOpen();
				}
				if (wasVisible && !nowVisible) {
					removeAccessibleListener();
				}
				if (nowVisible) {
					notifyAccessibleTextChanged();
				}
				shell.setVisible(nowVisible);
			}
		});
		text.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.keyCode == SWT.ESC) {
					text.setText(""); //$NON-NLS-1$
					if (previousFocusControl != null && !previousFocusControl.isDisposed())
						previousFocusControl.setFocus();
				} else if (e.keyCode == SWT.ARROW_UP) {
					e.doit = false;
				} else if (e.keyCode == SWT.ARROW_DOWN) {
					e.doit = false;
				}
				if (e.doit == false) {
					notifyAccessibleTextChanged();
				}
			}
		});
		quickAccessContents.createInfoLabel(shell);
	}

	private Text createText(Composite parent) {
		Text text = new Text(parent, SWT.SEARCH);
		text.setMessage(QuickAccessMessages.QuickAccess_EnterSearch);

		GC gc = new GC(text);
		Point p = gc.textExtent(QuickAccessMessages.QuickAccess_EnterSearch);
		Rectangle r = text.computeTrim(0, 0, p.x, p.y);
		gc.dispose();

		GridDataFactory.fillDefaults().hint(r.width - r.x, SWT.DEFAULT).applyTo(text);
		return text;
	}

	private void hookUpSelectAll() {
		final IEclipseContext windowContext = window.getContext();
		IFocusService focus = windowContext.get(IFocusService.class);
		focus.addFocusTracker(text, SearchField.class.getName());

		Expression focusExpr = new Expression() {
			@Override
			public void collectExpressionInfo(ExpressionInfo info) {
				info.addVariableNameAccess(ISources.ACTIVE_FOCUS_CONTROL_ID_NAME);
			}

			@Override
			public EvaluationResult evaluate(IEvaluationContext context) {
				return EvaluationResult.valueOf(SearchField.class.getName().equals(
						context.getVariable(ISources.ACTIVE_FOCUS_CONTROL_ID_NAME)));
			}
		};

		IHandlerService whService = windowContext.get(IHandlerService.class);
		whService.activateHandler(IWorkbenchCommandConstants.EDIT_SELECT_ALL,
				new AbstractHandler() {
					@Override
					public Object execute(ExecutionEvent event) {
						text.selectAll();
						return null;
					}
				}, focusExpr);
		whService.activateHandler(IWorkbenchCommandConstants.EDIT_CUT, new AbstractHandler() {
			@Override
			public Object execute(ExecutionEvent event) {
				text.cut();
				return null;
			}
		}, focusExpr);
		whService.activateHandler(IWorkbenchCommandConstants.EDIT_COPY, new AbstractHandler() {
			@Override
			public Object execute(ExecutionEvent event) {
				text.copy();
				return null;
			}
		}, focusExpr);
		whService.activateHandler(IWorkbenchCommandConstants.EDIT_PASTE, new AbstractHandler() {
			@Override
			public Object execute(ExecutionEvent event) {
				text.paste();
				return null;
			}
		}, focusExpr);
	}

	private static Monitor getClosestMonitor(Display toSearch, Point toFind) {
		int closest = Integer.MAX_VALUE;

		Monitor[] monitors = toSearch.getMonitors();
		Monitor result = monitors[0];

		for (int idx = 0; idx < monitors.length; idx++) {
			Monitor current = monitors[idx];

			Rectangle clientArea = current.getClientArea();

			if (clientArea.contains(toFind)) {
				return current;
			}

			int distance = Geometry.distanceSquared(Geometry.centerPoint(clientArea), toFind);
			if (distance < closest) {
				closest = distance;
				result = current;
			}
		}

		return result;
	}

	private Rectangle getConstrainedShellBounds(Display display, Rectangle preferredSize) {
		Rectangle result = new Rectangle(preferredSize.x, preferredSize.y, preferredSize.width,
				preferredSize.height);

		Point topLeft = new Point(preferredSize.x, preferredSize.y);
		Monitor mon = getClosestMonitor(display, topLeft);
		Rectangle bounds = mon.getClientArea();

		if (result.height > bounds.height) {
			result.height = bounds.height;
		}

		if (result.width > bounds.width) {
			result.width = bounds.width;
		}

		result.x = Math.max(bounds.x, Math.min(result.x, bounds.x + bounds.width - result.width));
		result.y = Math.max(bounds.y, Math.min(result.y, bounds.y + bounds.height - result.height));

		return result;
	}

	void layoutShell() {
		Display display = text.getDisplay();
		Rectangle tempBounds = text.getBounds();
		Rectangle compBounds = display.map(text, null, tempBounds);
		int preferredWidth = dialogWidth == -1 ? 350 : dialogWidth;
		int width = Math.max(preferredWidth, compBounds.width);
		int height = dialogHeight == -1 ? 250 : dialogHeight;
		
		Rectangle shellBounds = text.getShell().getBounds();
		if (compBounds.x + width > shellBounds.x + shellBounds.width){
			compBounds.x = Math.max(shellBounds.x, (compBounds.x + compBounds.width - width));
		}
		
		shell.setBounds(getConstrainedShellBounds(display, new Rectangle(compBounds.x, compBounds.y
				+ compBounds.height, width, height)));
		shell.layout();
	}

	public void activate(Control previousFocusControl) {
		this.previousFocusControl = previousFocusControl;
		if (!shell.isVisible()) {
			layoutShell();
			quickAccessContents.preOpen();
			shell.setVisible(true);
			addAccessibleListener();
			quickAccessContents.refresh(text.getText().toLowerCase());
		} else {
			quickAccessContents.setShowAllMatches(!quickAccessContents.getShowAllMatches());
		}
	}

	protected void checkFocusLost(final Table table, final Text text) {
		if (!shell.isDisposed() && !table.isDisposed() && !text.isDisposed()) {
			if (table.getDisplay().getActiveShell() == table.getShell()) {
				text.setFocus();
				return;
			}
			if (!shell.isFocusControl() && !table.isFocusControl()
					&& !text.isFocusControl()) {
				quickAccessContents.doClose();
			}
		}
	}

	private void addAccessibleListener() {
		if (accessibleListener == null) {
			accessibleListener = new AccessibleAdapter() {
				@Override
				public void getName(AccessibleEvent e) {
					e.result = selectedString;
				}
			};
			text.getAccessible().addAccessibleListener(accessibleListener);
		}
	}

	private void removeAccessibleListener() {
		if (accessibleListener != null) {
			text.getAccessible().removeAccessibleListener(accessibleListener);
			accessibleListener = null;
		}
		selectedString = ""; //$NON-NLS-1$
	}

	private void notifyAccessibleTextChanged() {
		if (table.getSelection().length == 0) {
			return;
		}
		TableItem item = table.getSelection()[0];
		selectedString = NLS.bind(QuickAccessMessages.QuickAccess_SelectedString, item.getText(0),
				item.getText(1));
		text.getAccessible().sendEvent(ACC.EVENT_NAME_CHANGED, null);
	}

	private void restoreDialog() {
		IDialogSettings dialogSettings = getDialogSettings();
		if (dialogSettings != null) {
			String[] orderedElements = dialogSettings.getArray(ORDERED_ELEMENTS);
			String[] orderedProviders = dialogSettings.getArray(ORDERED_PROVIDERS);
			String[] textEntries = dialogSettings.getArray(TEXT_ENTRIES);
			String[] textArray = dialogSettings.getArray(TEXT_ARRAY);
			try {
				dialogHeight = dialogSettings.getInt(DIALOG_HEIGHT);
				dialogWidth = dialogSettings.getInt(DIALOG_WIDTH);
			} catch (NumberFormatException e) {
				dialogHeight = -1;
				dialogWidth = -1;
			}

			if (orderedElements != null && orderedProviders != null && textEntries != null
					&& textArray != null) {
				int arrayIndex = 0;
				for (int i = 0; i < orderedElements.length; i++) {
					QuickAccessProvider quickAccessProvider = providerMap.get(orderedProviders[i]);
					int numTexts = Integer.parseInt(textEntries[i]);
					if (quickAccessProvider != null) {
						QuickAccessElement quickAccessElement = quickAccessProvider
								.getElementForId(orderedElements[i]);
						if (quickAccessElement != null) {
							ArrayList<String> arrayList = new ArrayList<String>();
							for (int j = arrayIndex; j < arrayIndex + numTexts; j++) {
								String text = textArray[j];
								if (text.length() > 0) {
									arrayList.add(text);
									elementMap.put(text, quickAccessElement);
								}
							}
							textMap.put(quickAccessElement, arrayList);
							previousPicksList.add(quickAccessElement);
						}
					}
					arrayIndex += numTexts;
				}
			}
		}
	}

	@PreDestroy
	void dispose() {
		storeDialog();
	}

	private void storeDialog() {
		String[] orderedElements = new String[previousPicksList.size()];
		String[] orderedProviders = new String[previousPicksList.size()];
		String[] textEntries = new String[previousPicksList.size()];
		ArrayList<String> arrayList = new ArrayList<String>();
		for (int i = 0; i < orderedElements.length; i++) {
			QuickAccessElement quickAccessElement = previousPicksList.get(i);
			ArrayList<String> elementText = textMap.get(quickAccessElement);
			Assert.isNotNull(elementText);
			orderedElements[i] = quickAccessElement.getId();
			orderedProviders[i] = quickAccessElement.getProvider().getId();
			arrayList.addAll(elementText);
			textEntries[i] = elementText.size() + ""; //$NON-NLS-1$
		}
		String[] textArray = arrayList.toArray(new String[arrayList.size()]);
		IDialogSettings dialogSettings = getDialogSettings();
		dialogSettings.put(ORDERED_ELEMENTS, orderedElements);
		dialogSettings.put(ORDERED_PROVIDERS, orderedProviders);
		dialogSettings.put(TEXT_ENTRIES, textEntries);
		dialogSettings.put(TEXT_ARRAY, textArray);
		storeShellSize();
	}

	private void storeShellSize() {
		if (!shell.isDisposed()) {
			IDialogSettings dialogSettings = getDialogSettings();
			dialogSettings.put(DIALOG_HEIGHT, shell.getSize().y);
			dialogSettings.put(DIALOG_WIDTH, shell.getSize().x);
		}
	}

	private IDialogSettings getDialogSettings() {
		final IDialogSettings workbenchDialogSettings = WorkbenchPlugin.getDefault()
				.getDialogSettings();
		IDialogSettings result = workbenchDialogSettings.getSection(getId());
		if (result == null) {
			result = workbenchDialogSettings.addNewSection(getId());
		}
		return result;
	}

	private String getId() {
		return "org.eclipse.ui.internal.QuickAccess"; //$NON-NLS-1$
	}

	private void addPreviousPick(String text, QuickAccessElement element) {
		previousPicksList.remove(element);
		if (previousPicksList.size() == MAXIMUM_NUMBER_OF_ELEMENTS) {
			Object removedElement = previousPicksList.removeLast();
			ArrayList<String> removedList = textMap.remove(removedElement);
			for (int i = 0; i < removedList.size(); i++) {
				elementMap.remove(removedList.get(i));
			}
		}
		previousPicksList.addFirst(element);

		ArrayList<String> textList = textMap.get(element);
		if (textList == null) {
			textList = new ArrayList<String>();
			textMap.put(element, textList);
		}

		textList.remove(text);
		if (textList.size() == MAXIMUM_NUMBER_OF_TEXT_ENTRIES_PER_ELEMENT) {
			Object removedText = textList.remove(0);
			elementMap.remove(removedText);
		}

		if (text.length() > 0) {
			textList.add(text);

			QuickAccessElement replacedElement = elementMap.put(text, element);
			if (replacedElement != null && !replacedElement.equals(element)) {
				textList = textMap.get(replacedElement);
				if (textList != null) {
					textList.remove(text);
					if (textList.isEmpty()) {
						textMap.remove(replacedElement);
						previousPicksList.remove(replacedElement);
					}
				}
			}
		}
	}

	public Shell getQuickAccessShell() {
		return shell;
	}

	public Text getQuickAccessSearchText() {
		return text;
	}

	public Table getQuickAccessTable(){
		return table;
	}
}
