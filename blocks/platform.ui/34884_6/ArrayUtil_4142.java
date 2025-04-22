package org.eclipse.ui.tests.harness.util;

import java.lang.reflect.Method;

import junit.framework.Assert;
import junit.framework.TestCase;

import org.eclipse.jface.action.ActionContributionItem;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.action.IContributionItem;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.SubContributionItem;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.internal.WorkbenchWindow;

public class ActionUtil {

    public static void runAction(TestCase test, IContributionItem item) {
        Assert.assertTrue(item instanceof ActionContributionItem);
        ((ActionContributionItem) item).getAction().run();
    }

    public static void runActionWithLabel(TestCase test, IMenuManager mgr,
            String label) {
        IContributionItem[] items = mgr.getItems();
        for (int nX = 0; nX < items.length; nX++) {
            IContributionItem item = items[nX];
            if (item instanceof SubContributionItem)
                item = ((SubContributionItem) item).getInnerItem();
            if (item instanceof ActionContributionItem) {
                IAction action = ((ActionContributionItem) item).getAction();
                if (label.equals(action.getText())) {
                    action.run();
                    return;
                }
            }
        }
        Assert.fail("Unable to find action: " + label);
    }

    public static void runActionWithLabel(TestCase test, IWorkbenchWindow win,
            String label) {
        WorkbenchWindow realWin = (WorkbenchWindow) win;
        IMenuManager mgr = realWin.getMenuBarManager();
        runActionWithLabel(test, mgr, label);
    }

    public static void runActionUsingPath(TestCase test, IMenuManager mgr,
            String idPath) {
        IContributionItem item = mgr.findUsingPath(idPath);
        Assert.assertNotNull(item);
        runAction(test, item);
    }

    public static void runActionUsingPath(TestCase test, IWorkbenchWindow win,
            String idPath) {
    	WorkbenchWindow realWin = (WorkbenchWindow) win;
        IMenuManager mgr = realWin.getMenuBarManager();
        runActionUsingPath(test, mgr, idPath);
    }

    public static IAction getActionWithLabel(IMenuManager mgr, String label) {
        IContributionItem[] items = mgr.getItems();
        for (int nX = 0; nX < items.length; nX++) {
            IContributionItem item = items[nX];
            if (item instanceof SubContributionItem)
                item = ((SubContributionItem) item).getInnerItem();
            if (item instanceof ActionContributionItem) {
                IAction action = ((ActionContributionItem) item).getAction();
                if (label.equals(action.getText())) {
                    return action;
                }
            }
        }
        return null;
    }

    public static void fireAboutToShow(MenuManager mgr) throws Throwable {
        Class clazz = mgr.getClass();
        Method method = clazz.getDeclaredMethod("handleAboutToShow",
                new Class[0]);
        method.setAccessible(true);
        method.invoke(mgr, new Object[0]);
    }
}

