package org.eclipse.jface.tests.action;

import org.eclipse.jface.action.ActionContributionItem;
import org.eclipse.jface.action.GroupMarker;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.action.IContributionItem;
import org.eclipse.jface.action.IContributionManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.Shell;

public class MenuManagerTest extends JFaceActionTest {

    private int groupMarkerCount = 0;
    private int separatorCount = 0;

    public MenuManagerTest(String name) {
        super(name);
    }

    public void testMenuWithNoConcreteVisibleItemsIsHidden() {
        MenuManager menuBarMgr = createMenuBarManager();

        MenuManager fileMenu = createMenuManager("File", "gsgn");
        menuBarMgr.add(fileMenu);
        menuBarMgr.updateAll(false);
        assertEquals(0, getShell().getMenuBar().getItems().length);
    }

    public void testAddingConcreteItemToMenuWithNoConcreteVisibleItems() {
        MenuManager menuBarMgr = createMenuBarManager();

        MenuManager fileMenuMgr = createMenuManager("File", "gsgn");
        menuBarMgr.add(fileMenuMgr);
        menuBarMgr.updateAll(false);
        
        Menu menuBar = getShell().getMenuBar();
        assertEquals(0, menuBar.getItems().length);
        
        fileMenuMgr.add(createItem('a'));
        menuBarMgr.updateAll(false);
        assertEquals(1, menuBar.getItems().length);
        
        assertEquals("File", menuBar.getItems()[0].getText());
        
        Menu fileMenu = menuBar.getItems()[0].getMenu();
        assertEquals(1, fileMenu.getItems().length);
    }
    
    public void testDisposedMenuIsDirty() {
        MenuManager menuBarMgr = createMenuBarManager();

        MenuManager fileMenuMgr = createMenuManager("File", "gsgn");
        menuBarMgr.add(fileMenuMgr);
        menuBarMgr.updateAll(false);
        
        assertFalse(menuBarMgr.isDirty());
        
        menuBarMgr.dispose();
        assertTrue(menuBarMgr.isDirty());
    }
    
    public void testEmptyMenuManagerNPE() {
    	Menu menu = new Menu(getShell());
    	MenuManager manager = new MenuManager();
    	manager.fill(menu, -1);
    }

    private MenuManager createMenuManager(String name, String template) {
        MenuManager menuMgr = new MenuManager(name);
        addItems(menuMgr, template);
        return menuMgr;
    }

    private void addItems(IContributionManager manager, String template) {
        for (int i = 0; i < template.length(); ++i) {
            manager.add(createItem(template.charAt(i)));
        }
    }
    
    private IContributionItem createItem(char template) {
        switch (template) {
        	case 'g':
        	    return new GroupMarker("testGroup" + groupMarkerCount++);
        	case 's':
        	    return new Separator("testSeparator" + separatorCount++);
        	case 'a': {
        	    IAction action = new DummyAction();
        	    return new ActionContributionItem(action);
        	}
        	case 'n': {
        	    IAction action = new DummyAction();
        	    ActionContributionItem item = new ActionContributionItem(action);
        	    item.setVisible(false);
        	    return item;
        	}
        	default:
        	    throw new IllegalArgumentException("Unknown template char: " + template);
        }
    }

    protected MenuManager createMenuBarManager() {
        Shell shell = getShell();
        MenuManager menuMgr = new MenuManager();
        Menu menuBar = menuMgr.createMenuBar(shell);
        shell.setMenuBar(menuBar);
        return menuMgr;
    }
}
