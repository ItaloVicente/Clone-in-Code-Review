package org.eclipse.ui.internal;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.action.IContributionItem;
import org.eclipse.jface.action.ICoolBarManager;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IStatusLineManager;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.internal.provisional.action.IToolBarContributionItem;
import org.eclipse.jface.internal.provisional.action.ToolBarContributionItem2;
import org.eclipse.jface.internal.provisional.action.ToolBarManager2;
import org.eclipse.jface.window.Window;
import org.eclipse.osgi.util.TextProcessor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.dnd.DropTargetListener;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IMemento;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.application.IActionBarConfigurer;
import org.eclipse.ui.application.IWorkbenchConfigurer;
import org.eclipse.ui.application.IWorkbenchWindowConfigurer;
import org.eclipse.ui.application.WorkbenchAdvisor;
import org.eclipse.ui.internal.provisional.application.IActionBarConfigurer2;

public final class WorkbenchWindowConfigurer implements
        IWorkbenchWindowConfigurer {

    private WorkbenchWindow window;

    private int shellStyle = SWT.SHELL_TRIM | Window.getDefaultOrientation();

    private String windowTitle;

    private boolean showFastViewBars = false;

    private boolean showPerspectiveBar = false;

    private boolean showStatusLine = true;

    private boolean showToolBar = true;

    private boolean showMenuBar = true;

    private boolean showProgressIndicator = false;

    private Map extraData = new HashMap(1);

    private ArrayList transferTypes = new ArrayList(3);

    private DropTargetListener dropTargetListener = null;

    private WindowActionBarConfigurer actionBarConfigurer = null;

    private Point initialSize = new Point(1024, 768);

    class WindowActionBarConfigurer implements IActionBarConfigurer2 {

        private IActionBarConfigurer2 proxy;

        public void setProxy(IActionBarConfigurer2 proxy) {
            this.proxy = proxy;
        }

        @Override
		public IWorkbenchWindowConfigurer getWindowConfigurer() {
            return window.getWindowConfigurer();
        }

            ICoolBarManager cbManager = getCoolBarManager();
            if (cbManager == null) {
				return false;
			}
            IContributionItem cbItem = cbManager.find(id);
            if (cbItem == null) {
				return false;
			}
            return true;
        }

        @Override
		public IStatusLineManager getStatusLineManager() {
            if (proxy != null) {
                return proxy.getStatusLineManager();
            }
			return window.getStatusLineManager();
        }

        @Override
		public IMenuManager getMenuManager() {
            if (proxy != null) {
                return proxy.getMenuManager();
            }
			return window.getMenuManager();
        }

        @Override
		public ICoolBarManager getCoolBarManager() {
            if (proxy != null) {
                return proxy.getCoolBarManager();
            }
			return window.getCoolBarManager2();
        }

        @Override
		public void registerGlobalAction(IAction action) {
            if (proxy != null) {
                proxy.registerGlobalAction(action);
            }
            window.registerGlobalAction(action);
        }

		@Override
		public IToolBarManager createToolBarManager() {
			if (proxy != null) {
				return proxy.createToolBarManager();
			}
			return new ToolBarManager2(SWT.WRAP | SWT.FLAT | SWT.RIGHT);
		}

		@Override
		public IToolBarContributionItem createToolBarContributionItem(IToolBarManager toolBarManager, String id) {
			if (proxy != null) {
				return proxy.createToolBarContributionItem(toolBarManager, id);
			}
			return new ToolBarContributionItem2(toolBarManager, id);
		}
    }

    WorkbenchWindowConfigurer(WorkbenchWindow window) {
        if (window == null) {
            throw new IllegalArgumentException();
        }
        this.window = window;
        windowTitle = WorkbenchPlugin.getDefault().getProductName();
        if (windowTitle == null) {
            windowTitle = ""; //$NON-NLS-1$
        }
    }

    @Override
	public IWorkbenchWindow getWindow() {
        return window;
    }

    @Override
	public IWorkbenchConfigurer getWorkbenchConfigurer() {
        return Workbench.getInstance().getWorkbenchConfigurer();
    }

        return windowTitle;
    }

    @Override
	public String getTitle() {
        Shell shell = window.getShell();
        if (shell != null) {
            windowTitle = shell.getText();
        }
        return windowTitle;
    }

    @Override
	public void setTitle(String title) {
        if (title == null) {
            throw new IllegalArgumentException();
        }
        windowTitle = title;
        Shell shell = window.getShell();
        if (shell != null && !shell.isDisposed()) {
            shell.setText(TextProcessor.process(title, WorkbenchWindow.TEXT_DELIMITERS));
        }
    }

    @Override
	public boolean getShowMenuBar() {
        return showMenuBar;
    }

    @Override
	public void setShowMenuBar(boolean show) {
        showMenuBar = show;
        WorkbenchWindow win = (WorkbenchWindow) getWindow();
        Shell shell = win.getShell();
        if (shell != null) {
            boolean showing = shell.getMenuBar() != null;
            if (show != showing) {
                if (show) {
					shell.setMenuBar(null);
                } else {
                    shell.setMenuBar(null);
                }
            }
        }
    }

    @Override
	public boolean getShowCoolBar() {
        return showToolBar;
    }

    @Override
	public void setShowCoolBar(boolean show) {
        showToolBar = show;
    }

    @Override
	public boolean getShowFastViewBars() {
        return showFastViewBars;
    }

    @Override
	public void setShowFastViewBars(boolean show) {
        showFastViewBars = show;
        window.setFastViewBarVisible(show);
    }

    @Override
	public boolean getShowPerspectiveBar() {
        return showPerspectiveBar;
    }

    @Override
	public void setShowPerspectiveBar(boolean show) {
        showPerspectiveBar = show;
    }

    @Override
	public boolean getShowStatusLine() {
        return showStatusLine;
    }

    @Override
	public void setShowStatusLine(boolean show) {
        showStatusLine = show;
        window.setStatusLineVisible(show);
    }

    @Override
	public boolean getShowProgressIndicator() {
        return showProgressIndicator;
    }

    @Override
	public void setShowProgressIndicator(boolean show) {
        showProgressIndicator = show;
    }

    @Override
	public Object getData(String key) {
        if (key == null) {
            throw new IllegalArgumentException();
        }
        return extraData.get(key);
    }

    @Override
	public void setData(String key, Object data) {
        if (key == null) {
            throw new IllegalArgumentException();
        }
        if (data != null) {
            extraData.put(key, data);
        } else {
            extraData.remove(key);
        }
    }

    @Override
	public void addEditorAreaTransfer(Transfer tranfer) {
		if (tranfer != null && !transferTypes.contains(tranfer)) {
			transferTypes.add(tranfer);
		}
    }

    @Override
	public void configureEditorAreaDropListener(
            DropTargetListener dropTargetListener) {
		this.dropTargetListener = dropTargetListener;
    }

        Transfer[] transfers = new Transfer[transferTypes.size()];
        transferTypes.toArray(transfers);
        return transfers;
    }

        return dropTargetListener;
    }

    @Override
	public IActionBarConfigurer getActionBarConfigurer() {
        if (actionBarConfigurer == null) {
            actionBarConfigurer = new WindowActionBarConfigurer();
        }
        return actionBarConfigurer;
    }

        getActionBarConfigurer();
        return actionBarConfigurer.containsCoolItem(id);
    }

    @Override
	public int getShellStyle() {
        return shellStyle;
    }

    @Override
	public void setShellStyle(int shellStyle) {
        this.shellStyle = shellStyle;
    }

    @Override
	public Point getInitialSize() {
        return initialSize;
    }

    @Override
	public void setInitialSize(Point size) {
        initialSize = size;
    }

    public void createDefaultContents(Shell shell) {

    }

    @Override
	public Menu createMenuBar() {
		return null;
    }

    @Override
	public Control createCoolBarControl(Composite parent) {

        return null;
    }

    @Override
	public Control createStatusLineControl(Composite parent) {
		return null;
    }

    @Override
	public Control createPageComposite(Composite parent) {
		return null;
    }

	@Override
	public IStatus saveState(IMemento memento) {
		return null;
	}

}
