package org.eclipse.ui.examples.undo.views;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.operations.IOperationApprover;
import org.eclipse.core.commands.operations.IOperationHistory;
import org.eclipse.core.commands.operations.IUndoContext;
import org.eclipse.core.commands.operations.ObjectUndoContext;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.action.IMenuListener;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseMoveListener;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.IWorkbenchActionConstants;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.actions.ActionFactory;
import org.eclipse.ui.examples.undo.AddBoxOperation;
import org.eclipse.ui.examples.undo.Box;
import org.eclipse.ui.examples.undo.Boxes;
import org.eclipse.ui.examples.undo.ClearBoxesOperation;
import org.eclipse.ui.examples.undo.MoveBoxOperation;
import org.eclipse.ui.examples.undo.PromptingUserApprover;
import org.eclipse.ui.examples.undo.UndoExampleMessages;
import org.eclipse.ui.examples.undo.UndoPlugin;
import org.eclipse.ui.examples.undo.preferences.PreferenceConstants;
import org.eclipse.ui.operations.RedoActionHandler;
import org.eclipse.ui.operations.UndoActionHandler;
import org.eclipse.ui.part.ViewPart;

public class BoxView extends ViewPart {
	private Canvas paintCanvas;

	private GC gc;

	private Boxes boxes = new Boxes();

	private UndoActionHandler undoAction;

	private RedoActionHandler redoAction;

	private IAction clearBoxesAction;

	private IUndoContext undoContext;

	private IOperationApprover operationApprover;

	private IPropertyChangeListener propertyChangeListener;

	private boolean dragInProgress = false;

	private boolean moveInProgress = false;

	private Box movingBox;

	int diffX, diffY;

	private Point anchorPosition = new Point(-1, -1);

	private Point tempPosition = new Point(-1, -1);

	private Point rubberbandPosition = new Point(-1, -1);

	public BoxView() {
		super();
		initializeOperationHistory();
	}

	@Override
	public void createPartControl(Composite parent) {
		paintCanvas = new Canvas(parent, SWT.BORDER | SWT.V_SCROLL
				| SWT.H_SCROLL | SWT.NO_REDRAW_RESIZE);

		gc = new GC(paintCanvas);
		gc.setForeground(paintCanvas.getForeground());
		gc.setLineStyle(SWT.LINE_SOLID);

		addListeners();

		makeActions();
		hookContextMenu();
		createGlobalActionHandlers();
		contributeToActionBars();
	}

	private void addListeners() {
		paintCanvas.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent event) {
				if (event.button != 1) {
					return;
				}
				if (dragInProgress || moveInProgress)
				 {
					return; // spurious event
				}

				tempPosition.x = event.x;
				tempPosition.y = event.y;
				Box box = boxes.getBox(event.x, event.y);
				if (box != null) {
					moveInProgress = true;
					movingBox = box;
					anchorPosition.x = box.x1;
					anchorPosition.y = box.y1;
					diffX = event.x - box.x1;
					diffY = event.y - box.y1;
				} else {
					dragInProgress = true;
					anchorPosition.x = event.x;
					anchorPosition.y = event.y;
				}
			}

			@Override
			public void mouseUp(MouseEvent event) {
				if (event.button != 1) {
					resetDrag(true); // abort if right or middle mouse button
					return;
				}
				if (anchorPosition.x == -1)
				 {
					return; // spurious event
				}

				if (dragInProgress) {
					Box box = new Box(anchorPosition.x, anchorPosition.y,
							tempPosition.x, tempPosition.y);
					if (box.getWidth() > 0 && box.getHeight() > 0) {
						try {
							getOperationHistory().execute(
									new AddBoxOperation(
											UndoExampleMessages.BoxView_Add,
											undoContext, boxes, box, paintCanvas),
									null, null);
						} catch (ExecutionException e) {
						}
						dragInProgress = false;
					}
				} else if (moveInProgress) {
					try {
						getOperationHistory().execute(
								new MoveBoxOperation(
										UndoExampleMessages.BoxView_Move,
										undoContext, movingBox, paintCanvas,
										anchorPosition), null, null);
					} catch (ExecutionException e) {
					}
					moveInProgress = false;
					movingBox = null;
				}
				resetDrag(false);

				paintCanvas.redraw();
			}

			@Override
			public void mouseDoubleClick(MouseEvent event) {
			}
		});
		paintCanvas.addMouseMoveListener(new MouseMoveListener() {
			@Override
			public void mouseMove(MouseEvent event) {
				if (dragInProgress) {
					clearRubberBandSelection();
					tempPosition.x = event.x;
					tempPosition.y = event.y;
					addRubberBandSelection();
				} else if (moveInProgress) {
					clearRubberBandSelection();
					anchorPosition.x = event.x - diffX;
					anchorPosition.y = event.y - diffY;
					tempPosition.x = anchorPosition.x + movingBox.getWidth();
					tempPosition.y = anchorPosition.y + movingBox.getHeight();
					addRubberBandSelection();
				}
			}
		});
		paintCanvas.addPaintListener(new PaintListener() {
			@Override
			public void paintControl(PaintEvent event) {
				event.gc.setForeground(paintCanvas.getForeground());
				boxes.draw(event.gc);
			}
		});

		paintCanvas.addDisposeListener(new DisposeListener() {
			@Override
			public void widgetDisposed(DisposeEvent event) {
				gc.dispose();
				removeListeners();
			}
		});

		propertyChangeListener = new IPropertyChangeListener() {
			@Override
			public void propertyChange(PropertyChangeEvent event) {
				if (event.getProperty() == PreferenceConstants.PREF_UNDOLIMIT) {
					int limit = UndoPlugin.getDefault().getPreferenceStore()
							.getInt(PreferenceConstants.PREF_UNDOLIMIT);
					getOperationHistory().setLimit(undoContext, limit);
				}
			}
		};
		UndoPlugin.getDefault().getPreferenceStore().addPropertyChangeListener(
				propertyChangeListener);

	}

	private void removeListeners() {
		UndoPlugin.getDefault().getPreferenceStore()
				.removePropertyChangeListener(propertyChangeListener);
		getOperationHistory().removeOperationApprover(operationApprover);
	}

	private void hookContextMenu() {
		MenuManager menuMgr = new MenuManager("#PopupMenu");
		menuMgr.setRemoveAllWhenShown(true);
		menuMgr.addMenuListener(new IMenuListener() {
			@Override
			public void menuAboutToShow(IMenuManager manager) {
				BoxView.this.fillContextMenu(manager);
			}
		});
		Menu menu = menuMgr.createContextMenu(paintCanvas);
		paintCanvas.setMenu(menu);
	}

	private void contributeToActionBars() {
		IActionBars bars = getViewSite().getActionBars();
		fillLocalPullDown(bars.getMenuManager());
		fillLocalToolBar(bars.getToolBarManager());
	}

	private void fillLocalPullDown(IMenuManager manager) {
		manager.add(undoAction);
		manager.add(redoAction);
		manager.add(new Separator());
		manager.add(clearBoxesAction);
	}

	private void fillContextMenu(IMenuManager manager) {
		manager.add(undoAction);
		manager.add(redoAction);
		manager.add(new Separator());
		manager.add(clearBoxesAction);
		manager.add(new Separator(IWorkbenchActionConstants.MB_ADDITIONS));
	}

	private void fillLocalToolBar(IToolBarManager manager) {
		manager.add(clearBoxesAction);
	}

	private void makeActions() {
		clearBoxesAction = new Action() {
			@Override
			public void run() {
				try {
					getOperationHistory().execute(
							new ClearBoxesOperation(
									UndoExampleMessages.BoxView_ClearBoxes,
									undoContext, boxes, paintCanvas), null,
							null);
				} catch (ExecutionException e) {
				}
			}
		};
		clearBoxesAction.setText(UndoExampleMessages.BoxView_ClearBoxes);
		clearBoxesAction
				.setToolTipText(UndoExampleMessages.BoxView_ClearBoxesToolTipText);
		clearBoxesAction.setImageDescriptor(PlatformUI.getWorkbench()
				.getSharedImages().getImageDescriptor(
						ISharedImages.IMG_TOOL_DELETE));
	}

	private void createGlobalActionHandlers() {
		undoAction = new UndoActionHandler(this.getSite(), undoContext);
		redoAction = new RedoActionHandler(this.getSite(), undoContext);
		IActionBars actionBars = getViewSite().getActionBars();
		actionBars.setGlobalActionHandler(ActionFactory.UNDO.getId(),
				undoAction);
		actionBars.setGlobalActionHandler(ActionFactory.REDO.getId(),
				redoAction);
	}

	@Override
	public void setFocus() {
		paintCanvas.setFocus();
	}

	private void resetDrag(boolean clearRubberband) {
		if (clearRubberband) {
			clearRubberBandSelection();
		}
		dragInProgress = false;
		moveInProgress = false;
		movingBox = null;
		anchorPosition.x = anchorPosition.y = tempPosition.x = tempPosition.y = rubberbandPosition.x = rubberbandPosition.y = -1;
	}

	private void clearRubberBandSelection() {
		gc.setForeground(paintCanvas.getBackground());
		gc.drawRectangle(anchorPosition.x, anchorPosition.y,
				rubberbandPosition.x - anchorPosition.x, rubberbandPosition.y
						- anchorPosition.y);
		paintCanvas.redraw(anchorPosition.x, anchorPosition.y,
				rubberbandPosition.x - anchorPosition.x, rubberbandPosition.y
						- anchorPosition.y, false);
		paintCanvas.update();
		rubberbandPosition = new Point(-1, -1);
		gc.setForeground(paintCanvas.getForeground());
	}

	private void addRubberBandSelection() {
		rubberbandPosition = tempPosition;
		gc.drawRectangle(anchorPosition.x, anchorPosition.y,
				rubberbandPosition.x - anchorPosition.x, rubberbandPosition.y
						- anchorPosition.y);
	}

	private void initializeOperationHistory() {
		undoContext = new ObjectUndoContext(this);

		int limit = UndoPlugin.getDefault().getPreferenceStore().getInt(
				PreferenceConstants.PREF_UNDOLIMIT);
		getOperationHistory().setLimit(undoContext, limit);

		operationApprover = new PromptingUserApprover(undoContext);
		getOperationHistory().addOperationApprover(operationApprover);
	}

	private IOperationHistory getOperationHistory() {
		return PlatformUI.getWorkbench().getOperationSupport()
				.getOperationHistory();
	}

}
