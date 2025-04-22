package org.eclipse.ui.tests.api;

import java.util.ArrayList;

import junit.framework.Assert;
import junit.framework.TestCase;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IMenuListener;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.ListViewer;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.ui.IWorkbenchActionConstants;

public class ListView extends MockViewPart implements IMenuListener {

    ListViewer viewer;

    ArrayList input;

    MenuManager menuMgr;

    Menu menu;

    Action addAction;

    String ADD_ACTION_ID = "addAction";

    public ListView() {
        super();
        input = new ArrayList();
    }

    @Override
	public void createPartControl(Composite parent) {
        callTrace.add("createPartControl");

        viewer = new ListViewer(parent);
        viewer.setLabelProvider(new LabelProvider());
        viewer.setContentProvider(new ListContentProvider());
        viewer.setInput(input);

        createPopupMenu();

        getSite().setSelectionProvider(viewer);
    }

    public void createPopupMenu() {
        addAction = new Action("Add Standard Items") {
            @Override
			public void run() {
                addStandardItems();
            }
        };
        addAction.setId(ADD_ACTION_ID);

        if (useStaticMenu())
            createStaticPopupMenu();
        else
            createDynamicPopupMenu();
    }

    public void createDynamicPopupMenu() {
        menuMgr = new MenuManager();
        menuMgr.setRemoveAllWhenShown(true);
        menuMgr.addMenuListener(this);
        menu = menuMgr.createContextMenu(viewer.getControl());
        viewer.getControl().setMenu(menu);
        getSite().registerContextMenu(menuMgr, viewer);
    }

    public void createStaticPopupMenu() {
        menuMgr = new MenuManager();
        menu = menuMgr.createContextMenu(viewer.getControl());
        viewer.getControl().setMenu(menu);
        getSite().registerContextMenu(menuMgr, viewer);
        menuAboutToShow(menuMgr);
    }

    public void addElement(ListElement el) {
        input.add(el);
        viewer.refresh();
        viewer.getControl().update();
    }

    public void selectElement(ListElement el) {
        if (el == null)
            viewer.setSelection(new StructuredSelection());
        else
            viewer.setSelection(new StructuredSelection(el));
    }

    public MenuManager getMenuManager() {
        return menuMgr;
    }

    @Override
	public void menuAboutToShow(IMenuManager menuMgr) {
        menuMgr.add(addAction);
        menuMgr.add(new Separator(IWorkbenchActionConstants.MB_ADDITIONS));
    }

    public void verifyActions(TestCase test, IMenuManager menuMgr) {
        Assert.assertNotNull(menuMgr.find(ADD_ACTION_ID));
    }

    public void addStandardItems() {
        addElement(new ListElement("red"));
        addElement(new ListElement("blue"));
        addElement(new ListElement("green"));
        addElement(new ListElement("red", true));
    }

    private boolean useStaticMenu() {
        Object data = getData();
        if (data instanceof String) {
            String arg = (String) data;
            return arg.indexOf("-staticMenu") >= 0; //$NON-NLS-1$
        }
        return false;
    }
}

