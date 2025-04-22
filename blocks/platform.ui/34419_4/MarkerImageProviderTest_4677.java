package org.eclipse.ui.tests.adaptable;

import java.util.ArrayList;
import java.util.Iterator;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.action.IMenuListener;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.dialogs.IDialogSettings;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.viewers.DecoratingLabelProvider;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.ILabelDecorator;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.osgi.util.NLS;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IDecoratorManager;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IEditorReference;
import org.eclipse.ui.IFileEditorInput;
import org.eclipse.ui.IMemento;
import org.eclipse.ui.IPartListener;
import org.eclipse.ui.IViewSite;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.actions.ActionContext;
import org.eclipse.ui.internal.views.navigator.ResourceNavigatorMessages;
import org.eclipse.ui.part.DrillDownAdapter;
import org.eclipse.ui.part.ViewPart;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.eclipse.ui.views.navigator.ResourceSorter;

public class AdaptedResourceNavigator extends ViewPart {
    private TreeViewer viewer;

    private IDialogSettings settings;

    private IMemento memento;

    protected TestNavigatorActionGroup actionGroup;

    private static final String LINK_NAVIGATOR_TO_EDITOR = "LINK_NAVIGATOR_TO_EDITOR"; //$NON-NLS-1$

    private IPartListener partListener = new IPartListener() {
        @Override
		public void partActivated(IWorkbenchPart part) {
            if (part instanceof IEditorPart) {
				editorActivated((IEditorPart) part);
			}
        }

        @Override
		public void partBroughtToTop(IWorkbenchPart part) {
        }

        @Override
		public void partClosed(IWorkbenchPart part) {
        }

        @Override
		public void partDeactivated(IWorkbenchPart part) {
        }

        @Override
		public void partOpened(IWorkbenchPart part) {
        }
    };

    public AdaptedResourceNavigator() {
        IDialogSettings workbenchSettings = getPlugin().getDialogSettings();
        settings = workbenchSettings.getSection("ResourceNavigator"); //$NON-NLS-1$
        if (settings == null)
		 {
			settings = workbenchSettings.addNewSection("ResourceNavigator"); //$NON-NLS-1$
		}
    }

    StructuredSelection convertSelection(ISelection selection) {
        ArrayList list = new ArrayList();
        if (selection instanceof IStructuredSelection) {
            IStructuredSelection ssel = (IStructuredSelection) selection;
            for (Iterator i = ssel.iterator(); i.hasNext();) {
                Object o = i.next();
                IResource resource = null;
                if (o instanceof IResource) {
                    resource = (IResource) o;
                } else {
                    if (o instanceof IAdaptable) {
                        resource = (IResource) ((IAdaptable) o)
                                .getAdapter(IResource.class);
                    }
                }
                if (resource != null) {
                    list.add(resource);
                }
            }
        }
        return new StructuredSelection(list);
    }

    @Override
	public void createPartControl(Composite parent) {
        viewer = new TreeViewer(parent, SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL);
        viewer.setUseHashlookup(true);
        viewer.setContentProvider(new TestAdaptableContentProvider());
        IDecoratorManager manager = getSite().getWorkbenchWindow()
                .getWorkbench().getDecoratorManager();
        viewer.setLabelProvider(new DecoratingLabelProvider(
                new TestAdaptableWorkbenchAdapter(), manager
                        .getLabelDecorator()));

        viewer.setInput(getInitialInput());
        updateTitle();

        MenuManager menuMgr = new MenuManager("#PopupMenu"); //$NON-NLS-1$
        menuMgr.setRemoveAllWhenShown(true);
        menuMgr.addMenuListener(new IMenuListener() {
            @Override
			public void menuAboutToShow(IMenuManager manager) {
                AdaptedResourceNavigator.this.fillContextMenu(manager);
            }
        });
        Menu menu = menuMgr.createContextMenu(viewer.getTree());
        viewer.getTree().setMenu(menu);
        getSite().registerContextMenu(menuMgr, viewer);

        makeActions();

        IStructuredSelection selection = (IStructuredSelection) viewer
                .getSelection();
        actionGroup.updateGlobalActions(selection);

        viewer.addSelectionChangedListener(new ISelectionChangedListener() {
            @Override
			public void selectionChanged(SelectionChangedEvent event) {
                handleSelectionChanged(event);
            }
        });
        viewer.addDoubleClickListener(new IDoubleClickListener() {
            @Override
			public void doubleClick(DoubleClickEvent event) {
                handleDoubleClick(event);
            }
        });
        viewer.getControl().addKeyListener(new KeyListener() {
            @Override
			public void keyPressed(KeyEvent event) {
                handleKeyPressed(event);
            }

            @Override
			public void keyReleased(KeyEvent event) {
                handleKeyReleased(event);
            }
        });

        actionGroup.fillActionBars(selection);

        getSite().setSelectionProvider(viewer);

        getSite().getPage().addPartListener(partListener);

        if (memento != null) {
			restoreState(memento);
		}
        memento = null;
    }

    @Override
	public void dispose() {
        getSite().getPage().removePartListener(partListener);
        super.dispose();
    }

    void editorActivated(IEditorPart editor) {
        if (!isLinkingEnabled()) {
			return;
		}

        IEditorInput input = editor.getEditorInput();
        if (input instanceof IFileEditorInput) {
            IFileEditorInput fileInput = (IFileEditorInput) input;
            IFile file = fileInput.getFile();
            ISelection newSelection = new StructuredSelection(file);
            if (!viewer.getSelection().equals(newSelection)) {
                viewer.setSelection(newSelection);
            }
        }

    }

    void fillContextMenu(IMenuManager menu) {
        actionGroup.setContext(new ActionContext(getViewer().getSelection()));
        actionGroup.fillContextMenu(menu);
    }

    IContainer getInitialInput() {
        IAdaptable input = getSite().getPage().getInput();
        IResource resource = null;
        if (input instanceof IResource) {
            resource = (IResource) input;
        } else {
            resource = (IResource) input.getAdapter(IResource.class);
        }
        if (resource != null) {
            switch (resource.getType()) {
            case IResource.FILE:
                return resource.getParent();
            case IResource.FOLDER:
            case IResource.PROJECT:
            case IResource.ROOT:
                return (IContainer) resource;
            default:
                break;
            }
        }
        return ResourcesPlugin.getWorkspace().getRoot();
    }

    public AbstractUIPlugin getPlugin() {
        return (AbstractUIPlugin) Platform.getPlugin(PlatformUI.PLUGIN_ID);
    }

    public ResourceSorter getSorter() {
        return (ResourceSorter) getViewer().getSorter();
    }

    public TreeViewer getViewer() {
        return viewer;
    }

    public Shell getShell() {
        return getViewSite().getShell();
    }

    String getStatusLineMessage(IStructuredSelection selection) {
        if (selection.size() == 1) {
            Object o = selection.getFirstElement();
            if (o instanceof IResource) {
                return ((IResource) o).getFullPath().makeRelative().toString();
            }
			return ResourceNavigatorMessages.ResourceNavigator_oneItemSelected;
        }
        if (selection.size() > 1) {
            return NLS.bind(ResourceNavigatorMessages.ResourceNavigator_statusLine, new Integer(selection.size()));
        }
        return ""; //$NON-NLS-1$
    }

    String getToolTipText(Object element) {
        if (element instanceof IResource) {
            IPath path = ((IResource) element).getFullPath();
            if (path.isRoot()) {
                return ResourceNavigatorMessages.ResourceManager_toolTip;
            }
			return path.makeRelative().toString();
        }
		return ((ILabelProvider) getViewer().getLabelProvider())
		        .getText(element);
    }

    protected void handleDoubleClick(DoubleClickEvent event) {
        IStructuredSelection selection = (IStructuredSelection) event
                .getSelection();
        Object element = selection.getFirstElement();

        if (viewer.isExpandable(element)) {
            viewer.setExpandedState(element, !viewer.getExpandedState(element));
        }

    }

    protected void handleSelectionChanged(SelectionChangedEvent event) {
        IStructuredSelection sel = (IStructuredSelection) event.getSelection();
        updateStatusLine(sel);
        actionGroup.updateGlobalActions(sel);
        actionGroup.selectionChanged(sel);
        linkToEditor(sel);
    }

    protected void handleKeyPressed(KeyEvent event) {

    }

    protected void handleKeyReleased(KeyEvent event) {

    }

    @Override
	public void init(IViewSite site, IMemento memento) throws PartInitException {
        super.init(site, memento);
        this.memento = memento;
    }

    void initDrillDownAdapter(TreeViewer viewer) {
        DrillDownAdapter drillDownAdapter = new DrillDownAdapter(viewer) {
            @Override
			protected void updateNavigationButtons() {
                super.updateNavigationButtons();
                updateTitle();
            }
        };
        drillDownAdapter.addNavigationActions(getViewSite().getActionBars()
                .getToolBarManager());
    }

    protected boolean isLinkingEnabled() {
        IPreferenceStore store = getPlugin().getPreferenceStore();
        return store.getBoolean(LINK_NAVIGATOR_TO_EDITOR);
    }

    protected void linkToEditor(IStructuredSelection selection) {
        if (!isLinkingEnabled()) {
			return;
		}

        Object obj = selection.getFirstElement();
        if (obj instanceof IFile && selection.size() == 1) {
            IFile file = (IFile) obj;
            IWorkbenchPage page = getSite().getPage();
            IEditorReference editorArray[] = page.getEditorReferences();
            for (IEditorReference element : editorArray) {
                IEditorPart editor = element.getEditor(true);
                IEditorInput input = editor.getEditorInput();
                if (input instanceof IFileEditorInput
                        && file.equals(((IFileEditorInput) input).getFile())) {
                    page.bringToTop(editor);
                    return;
                }
            }
        }
    }

    protected void makeActions() {
        actionGroup = new TestNavigatorActionGroup(this);
    }


    protected void restoreState(IMemento memento) {
    }

    @Override
	public void saveState(IMemento memento) {
    }

    public void selectReveal(ISelection selection) {
        StructuredSelection ssel = convertSelection(selection);
        if (!ssel.isEmpty()) {
            getViewer().setSelection(ssel, true);
        }
    }

    @Override
	public void setFocus() {
        getViewer().getTree().setFocus();
    }

    public void setLabelDecorator(ILabelDecorator decorator) {
        DecoratingLabelProvider provider = (DecoratingLabelProvider) getViewer()
                .getLabelProvider();
        if (decorator == null) {
            IDecoratorManager manager = getSite().getWorkbenchWindow()
                    .getWorkbench().getDecoratorManager();
            provider.setLabelDecorator(manager.getLabelDecorator());
        } else {
            provider.setLabelDecorator(decorator);
        }
    }

    void updateStatusLine(IStructuredSelection selection) {
        String msg = getStatusLineMessage(selection);
        getViewSite().getActionBars().getStatusLineManager().setMessage(msg);
    }

    void updateTitle() {
        Object input = getViewer().getInput();
        IWorkspace workspace = ResourcesPlugin.getWorkspace();
        if (input == null || input.equals(workspace)
                || input.equals(workspace.getRoot())) {
            setContentDescription(""); //$NON-NLS-1$
            setTitleToolTip(""); //$NON-NLS-1$
        } else {
            ILabelProvider labelProvider = (ILabelProvider) getViewer()
                    .getLabelProvider();
            setContentDescription(labelProvider.getText(input));
            setTitleToolTip(getToolTipText(input));
        }
    }

}
