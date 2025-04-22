package org.eclipse.ui.help;

import org.eclipse.help.IContext;
import org.eclipse.help.IHelp;
import org.eclipse.jface.action.IAction;
import org.eclipse.swt.events.HelpListener;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.commands.ICommand;
import org.eclipse.ui.internal.help.WorkbenchHelpSystem;

@Deprecated
public class WorkbenchHelp {

    private WorkbenchHelp() {
    }

    public static void displayHelp() {
    	PlatformUI.getWorkbench().getHelpSystem().displayHelp();
    }

    public static void displayContext(IContext context, int x, int y) {
    	PlatformUI.getWorkbench().getHelpSystem().displayContext(context, x, y);
    }

    public static void displayHelpResource(String href) {
    	PlatformUI.getWorkbench().getHelpSystem().displayHelpResource(href);
    }

    public static HelpListener createHelpListener(ICommand command) {
    	return WorkbenchHelpSystem.getInstance().createHelpListener(command);
    }

    public static void displayHelp(String contextId) {
    	PlatformUI.getWorkbench().getHelpSystem().displayHelp(contextId);    	
    }

    public static void displayHelp(IContext context) {
    	PlatformUI.getWorkbench().getHelpSystem().displayHelp(context);
    }

    @Deprecated
	public static Object getHelp(Control control) {
        return control.getData(WorkbenchHelpSystem.HELP_KEY);
    }

    @Deprecated
	public static Object getHelp(Menu menu) {
        return menu.getData(WorkbenchHelpSystem.HELP_KEY);
    }

    @Deprecated
	public static Object getHelp(MenuItem menuItem) {
        return menuItem.getData(WorkbenchHelpSystem.HELP_KEY);
    }

    @Deprecated
	public static IHelp getHelpSupport() {
    	return WorkbenchHelpSystem.getInstance().getHelpSupport();
    }

    
    public static boolean isContextHelpDisplayed() {
    	return PlatformUI.getWorkbench().getHelpSystem().isContextHelpDisplayed();
    }

    @Deprecated
	public static void setHelp(IAction action, final Object[] contexts) {
    	WorkbenchHelpSystem.getInstance().setHelp(action, contexts);
    }

    @Deprecated
	public static void setHelp(IAction action, final IContextComputer computer) {
    	WorkbenchHelpSystem.getInstance().setHelp(action, computer);
    }

    @Deprecated
	public static void setHelp(Control control, Object[] contexts) {
    	WorkbenchHelpSystem.getInstance().setHelp(control, contexts);
    }

    @Deprecated
	public static void setHelp(Control control, IContextComputer computer) {
    	WorkbenchHelpSystem.getInstance().setHelp(control, computer);
    }

    @Deprecated
	public static void setHelp(Menu menu, Object[] contexts) {
    	WorkbenchHelpSystem.getInstance().setHelp(menu, contexts);
    }

    @Deprecated
	public static void setHelp(Menu menu, IContextComputer computer) {
    	WorkbenchHelpSystem.getInstance().setHelp(menu, computer);
    }

    @Deprecated
	public static void setHelp(MenuItem item, Object[] contexts) {
    	WorkbenchHelpSystem.getInstance().setHelp(item, contexts);
    }

    @Deprecated
	public static void setHelp(MenuItem item, IContextComputer computer) {
    	WorkbenchHelpSystem.getInstance().setHelp(item, computer);
    }

    public static void setHelp(IAction action, final String contextId) {
    	PlatformUI.getWorkbench().getHelpSystem().setHelp(action, contextId);
    }

    public static void setHelp(Control control, String contextId) {
    	PlatformUI.getWorkbench().getHelpSystem().setHelp(control, contextId);    	
    }

    public static void setHelp(Menu menu, String contextId) {
    	PlatformUI.getWorkbench().getHelpSystem().setHelp(menu, contextId);
    }

    public static void setHelp(MenuItem item, String contextId) {
    	PlatformUI.getWorkbench().getHelpSystem().setHelp(item, contextId);
    }
}
