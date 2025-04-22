
package org.eclipse.ui.views.properties;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.help.IContext;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IStatusLineManager;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.util.ConfigureColumns;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.window.SameShellProvider;
import org.eclipse.swt.dnd.Clipboard;
import org.eclipse.swt.dnd.DND;
import org.eclipse.swt.dnd.DragSource;
import org.eclipse.swt.dnd.DragSourceAdapter;
import org.eclipse.swt.dnd.DragSourceEvent;
import org.eclipse.swt.dnd.DragSourceListener;
import org.eclipse.swt.dnd.TextTransfer;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.swt.events.HelpEvent;
import org.eclipse.swt.events.HelpListener;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.IPartListener;
import org.eclipse.ui.ISaveablePart;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.help.IContextComputer;
import org.eclipse.ui.help.IWorkbenchHelpSystem;
import org.eclipse.ui.internal.views.ViewsPlugin;
import org.eclipse.ui.internal.views.properties.PropertiesMessages;
import org.eclipse.ui.part.CellEditorActionHandler;
import org.eclipse.ui.part.Page;

public class PropertySheetPage extends Page implements IPropertySheetPage, IAdaptable {
    public static final String HELP_CONTEXT_PROPERTY_SHEET_PAGE = "org.eclipse.ui.property_sheet_page_help_context"; //$NON-NLS-1$

    private PropertySheetViewer viewer;
    
    private PropertySheetSorter sorter;

    private IPropertySheetEntry rootEntry;

    private IPropertySourceProvider provider;

    private DefaultsAction defaultsAction;

    private FilterAction filterAction;

    private CategoriesAction categoriesAction;

    private CopyPropertyAction copyAction;

    private ICellEditorActivationListener cellEditorActivationListener;

    private CellEditorActionHandler cellEditorActionHandler;

    private Clipboard clipboard;

	private IWorkbenchPart sourcePart;

	private class PartListener implements IPartListener {
		@Override
		public void partActivated(IWorkbenchPart part) {
		}

		@Override
		public void partBroughtToTop(IWorkbenchPart part) {
		}

		@Override
		public void partClosed(IWorkbenchPart part) {
			if (sourcePart == part) {
				if (sourcePart != null)
					sourcePart.getSite().getPage().removePartListener(partListener);
				sourcePart = null;
				if (viewer != null && !viewer.getControl().isDisposed()) {
					viewer.setInput(new Object[0]);
				}
			}
		}

		@Override
		public void partDeactivated(IWorkbenchPart part) {
		}

		@Override
		public void partOpened(IWorkbenchPart part) {
		}
	}
	
	private PartListener partListener = new PartListener();

	private Action columnsAction;
	
    public PropertySheetPage() {
        super();
    }

    @Override
	public void createControl(Composite parent) {
        viewer = new PropertySheetViewer(parent);
        viewer.setSorter(sorter);
        
        if (rootEntry == null) {
            PropertySheetEntry root = new PropertySheetEntry();
            if (provider != null) {
                root.setPropertySourceProvider(provider);
			}
            rootEntry = root;
        }
        viewer.setRootEntry(rootEntry);
        viewer.addActivationListener(getCellEditorActivationListener());
        viewer.addSelectionChangedListener(new ISelectionChangedListener() {
            @Override
			public void selectionChanged(SelectionChangedEvent event) {
                handleEntrySelection(event.getSelection());
            }
        });
        initDragAndDrop();
        makeActions();

        MenuManager menuMgr = new MenuManager("#PopupMenu"); //$NON-NLS-1$
        menuMgr.add(copyAction);
        menuMgr.add(new Separator());
        menuMgr.add(defaultsAction);
        Menu menu = menuMgr.createContextMenu(viewer.getControl());
        viewer.getControl().setMenu(menu);

        viewer.getControl().addHelpListener(new HelpListener() {
            @Override
			public void helpRequested(HelpEvent e) {
                IStructuredSelection selection = (IStructuredSelection) viewer
                        .getSelection();
                if (!selection.isEmpty()) {
                    IPropertySheetEntry entry = (IPropertySheetEntry) selection
                            .getFirstElement();
                    Object helpContextId = entry.getHelpContextIds();
                    if (helpContextId != null) {
                    	IWorkbenchHelpSystem helpSystem = PlatformUI.getWorkbench().getHelpSystem();
                    	
                        if (helpContextId instanceof String) {
                            helpSystem.displayHelp((String) helpContextId);
                            return;
                        }

                        Object context= getFirstContext(helpContextId, e);
                        if (context instanceof IContext) {
							helpSystem.displayHelp((IContext) context);
						} else if (context instanceof String) {
							helpSystem.displayHelp((String) context);
						}
                        return;
                    }
                }

                PlatformUI.getWorkbench().getHelpSystem().displayHelp(HELP_CONTEXT_PROPERTY_SHEET_PAGE);
            }

			@Deprecated
			private Object getFirstContext(Object helpContext, HelpEvent e) {
				Object[] contexts;
				if (helpContext instanceof IContextComputer) {
					contexts= ((IContextComputer)helpContext)
				            .getLocalContexts(e);
				} else {
					contexts= (Object[])helpContext;
				}

				if (contexts.length > 0)
					return contexts[0];
				return null;
			}
        });
    }

    @Override
	public void dispose() {
        super.dispose();
        if (sourcePart != null) {
        	sourcePart.getSite().getPage().removePartListener(partListener);
        }
        if (rootEntry != null) {
            rootEntry.dispose();
            rootEntry = null;
        }
        if (clipboard != null) {
            clipboard.dispose();
            clipboard = null;
        }
    }

    @Override
	public Object getAdapter(Class adapter) {
		if (ISaveablePart.class.equals(adapter)) {
			return getSaveablePart();
		}
    	return null;
    }
    
	protected ISaveablePart getSaveablePart() {
		if (sourcePart instanceof ISaveablePart) {
			return (ISaveablePart) sourcePart;
		}
		return null;
	}
    
    private ICellEditorActivationListener getCellEditorActivationListener() {
        if (cellEditorActivationListener == null) {
            cellEditorActivationListener = new ICellEditorActivationListener() {
                @Override
				public void cellEditorActivated(CellEditor cellEditor) {
                    if (cellEditorActionHandler != null) {
						cellEditorActionHandler.addCellEditor(cellEditor);
					}
                }

                @Override
				public void cellEditorDeactivated(CellEditor cellEditor) {
                    if (cellEditorActionHandler != null) {
						cellEditorActionHandler.removeCellEditor(cellEditor);
					}
                }
            };
        }
        return cellEditorActivationListener;
    }

    @Override
	public Control getControl() {
        if (viewer == null) {
			return null;
		}
        return viewer.getControl();
    }

    public void handleEntrySelection(ISelection selection) {
        if (defaultsAction != null) {
            if (selection.isEmpty()) {
                defaultsAction.setEnabled(false);
                return;
            }
            boolean editable = viewer.getActiveCellEditor() != null;
            defaultsAction.setEnabled(editable);
        }
    }

    protected void initDragAndDrop() {
        int operations = DND.DROP_COPY;
        Transfer[] transferTypes = new Transfer[] { TextTransfer.getInstance() };
        DragSourceListener listener = new DragSourceAdapter() {
            @Override
			public void dragSetData(DragSourceEvent event) {
                performDragSetData(event);
            }

            @Override
			public void dragFinished(DragSourceEvent event) {
            }
        };
        DragSource dragSource = new DragSource(
                viewer.getControl(), operations);
        dragSource.setTransfer(transferTypes);
        dragSource.addDragListener(listener);
    }

    void performDragSetData(DragSourceEvent event) {
        IStructuredSelection selection = (IStructuredSelection) viewer
                .getSelection();
        if (selection.isEmpty()) {
			return;
		}
        IPropertySheetEntry entry = (IPropertySheetEntry) selection
                .getFirstElement();

        StringBuffer buffer = new StringBuffer();
        buffer.append(entry.getDisplayName());
        buffer.append("\t"); //$NON-NLS-1$
        buffer.append(entry.getValueAsString());

        event.data = buffer.toString();
    }

    private void makeActions() {
        ISharedImages sharedImages = PlatformUI.getWorkbench()
                .getSharedImages();

        defaultsAction = new DefaultsAction(viewer, "defaults"); //$NON-NLS-1$
        defaultsAction.setText(PropertiesMessages.Defaults_text);
        defaultsAction.setToolTipText(PropertiesMessages.Defaults_toolTip);
        defaultsAction
                .setImageDescriptor(ViewsPlugin.getViewImageDescriptor("elcl16/defaults_ps.png")); //$NON-NLS-1$
        defaultsAction
                .setDisabledImageDescriptor(ViewsPlugin.getViewImageDescriptor("dlcl16/defaults_ps.png")); //$NON-NLS-1$
        defaultsAction.setEnabled(false);

        filterAction = new FilterAction(viewer, "filter"); //$NON-NLS-1$
        filterAction.setText(PropertiesMessages.Filter_text);
        filterAction.setToolTipText(PropertiesMessages.Filter_toolTip);
        filterAction
                .setImageDescriptor(ViewsPlugin.getViewImageDescriptor("elcl16/filter_ps.png")); //$NON-NLS-1$
        filterAction.setChecked(false);

        categoriesAction = new CategoriesAction(viewer, "categories"); //$NON-NLS-1$
        categoriesAction.setText(PropertiesMessages.Categories_text);
        categoriesAction.setToolTipText(PropertiesMessages.Categories_toolTip);
        categoriesAction
                .setImageDescriptor(ViewsPlugin.getViewImageDescriptor("elcl16/tree_mode.png")); //$NON-NLS-1$
        categoriesAction.setChecked(true);

        columnsAction = new Action(PropertiesMessages.Columns_text){
        	@Override
			public void run() {
        		Tree tree = (Tree) viewer.getControl();
        		ConfigureColumns.forTree(tree, new SameShellProvider(tree));
        	}
		};
        columnsAction.setToolTipText(PropertiesMessages.Columns_toolTip);
        
        Shell shell = viewer.getControl().getShell();
        clipboard = new Clipboard(shell.getDisplay());
        copyAction = new CopyPropertyAction(viewer, "copy", clipboard); //$NON-NLS-1$
        copyAction.setText(PropertiesMessages.CopyProperty_text);
        copyAction.setImageDescriptor(sharedImages
                .getImageDescriptor(ISharedImages.IMG_TOOL_COPY));
    }

    @Override
	public void makeContributions(IMenuManager menuManager,
            IToolBarManager toolBarManager, IStatusLineManager statusLineManager) {

        toolBarManager.add(categoriesAction);
        toolBarManager.add(filterAction);
        toolBarManager.add(defaultsAction);

        menuManager.add(categoriesAction);
        menuManager.add(filterAction);
        menuManager.add(columnsAction);

        viewer.setStatusLineManager(statusLineManager);
    }

    public void refresh() {
        if (viewer == null) {
			return;
		}
        viewer.setInput(viewer.getInput());
    }

    @Override
	public void selectionChanged(IWorkbenchPart part, ISelection selection) {
        if (viewer == null) {
			return;
		}

        if (sourcePart != null) {
        	sourcePart.getSite().getPage().removePartListener(partListener);
        	sourcePart = null;
        }
        
        if (selection instanceof IStructuredSelection) {
        	sourcePart = part;
            viewer.setInput(((IStructuredSelection) selection).toArray());
        }

        if (sourcePart != null) {
        	sourcePart.getSite().getPage().addPartListener(partListener);
        }
    }

    @Override
	public void setActionBars(IActionBars actionBars) {
        super.setActionBars(actionBars);
        cellEditorActionHandler = new CellEditorActionHandler(actionBars);
        cellEditorActionHandler.setCopyAction(copyAction);
    }

    @Override
	public void setFocus() {
        viewer.getControl().setFocus();
    }

    public void setPropertySourceProvider(IPropertySourceProvider newProvider) {
        provider = newProvider;
        if (rootEntry instanceof PropertySheetEntry) {
            ((PropertySheetEntry) rootEntry)
                    .setPropertySourceProvider(provider);
            viewer.setRootEntry(rootEntry);
        }
    }

    public void setRootEntry(IPropertySheetEntry entry) {
        rootEntry = entry;
        if (viewer != null) {
            viewer.setRootEntry(rootEntry);
		}
    }

	protected void setSorter(PropertySheetSorter sorter) {
		this.sorter = sorter;
        if (viewer != null) {
        	viewer.setSorter(sorter);
        	
        	if(null != viewer.getRootEntry()) {
				viewer.setRootEntry(rootEntry);
			}
        }
	}

}
