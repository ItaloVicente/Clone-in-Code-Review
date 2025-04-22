package org.eclipse.ui.application;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.swt.dnd.DropTargetListener;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.ui.IMemento;
import org.eclipse.ui.IWorkbenchWindow;

public interface IWorkbenchWindowConfigurer {
    public IWorkbenchWindow getWindow();

    public IWorkbenchConfigurer getWorkbenchConfigurer();

    public IActionBarConfigurer getActionBarConfigurer();

    public String getTitle();

    public void setTitle(String title);

    public boolean getShowMenuBar();

    public void setShowMenuBar(boolean show);

    public boolean getShowCoolBar();

    public void setShowCoolBar(boolean show);

    public boolean getShowStatusLine();

    public void setShowStatusLine(boolean show);

    public boolean getShowPerspectiveBar();

    public void setShowPerspectiveBar(boolean show);

    public boolean getShowFastViewBars();

    public void setShowFastViewBars(boolean enable);

    public boolean getShowProgressIndicator();

    public void setShowProgressIndicator(boolean show);

    public int getShellStyle();

    public void setShellStyle(int shellStyle);

    public Point getInitialSize();

    public void setInitialSize(Point initialSize);

    public Object getData(String key);

    public void setData(String key, Object data);

    public void addEditorAreaTransfer(Transfer transfer);

    public void configureEditorAreaDropListener(
            DropTargetListener dropTargetListener);

	@Deprecated
    public Menu createMenuBar();

	@Deprecated
    public Control createCoolBarControl(Composite parent);

	@Deprecated
    public Control createStatusLineControl(Composite parent);

	@Deprecated
    public Control createPageComposite(Composite parent);
	
	public IStatus saveState(IMemento memento);
}
