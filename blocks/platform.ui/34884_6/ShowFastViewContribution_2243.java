package org.eclipse.ui.internal;

import java.util.Iterator;
import java.util.LinkedList;

import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.events.ShellAdapter;
import org.eclipse.swt.events.ShellEvent;
import org.eclipse.swt.events.ShellListener;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;

public class ShellPool {
    
    private int flags;
    
    private Shell parentShell;
    
    private LinkedList availableShells = new LinkedList();
    
    private final static String CLOSE_LISTENER = "close listener"; //$NON-NLS-1$
    
    private boolean isDisposed = false;
    
    private DisposeListener disposeListener = new DisposeListener() {
        @Override
		public void widgetDisposed(DisposeEvent e) {
            WorkbenchPlugin.log(new RuntimeException("Widget disposed too early!")); //$NON-NLS-1$
        }  
    };
    
    private ShellListener closeListener = new ShellAdapter() {
        
        @Override
		public void shellClosed(ShellEvent e) {
                if (isDisposed) {
                    return;
                }
                
                if (e.doit) {
                    Shell s = (Shell)e.widget;
                    ShellListener l = (ShellListener)s.getData(CLOSE_LISTENER);
                    
                    if (l != null) {
                        s.setData(CLOSE_LISTENER, null);
                        l.shellClosed(e);
                        
                        if (e.doit) {
                            Control[] children = s.getChildren();
	                        for (int i = 0; i < children.length; i++) {
	                            Control control = children[i];
	                          
	                            control.dispose();
	                        }
	                        availableShells.add(s);
	                        s.setVisible(false);
                        }
                        else {
                            s.setData(CLOSE_LISTENER, l);
                        }
                    }
                }
                e.doit = false;
         }
    };
    
    public ShellPool(Shell parentShell, int childFlags) {
        this.parentShell = parentShell;
        this.flags = childFlags;
    }
    
    public Shell allocateShell(ShellListener closeListener) {
        Shell result;
        if (!availableShells.isEmpty()) {
            result = (Shell)availableShells.removeFirst();
        } else {
            result = new Shell(parentShell, flags);
            result.addShellListener(this.closeListener);
            result.addDisposeListener(disposeListener);
        }
        
        result.setData(CLOSE_LISTENER, closeListener);
        return result;
    }
    
    public void dispose() {
        for (Iterator iter = availableShells.iterator(); iter.hasNext();) {
            Shell next = (Shell) iter.next();
            next.removeDisposeListener(disposeListener);
            
            next.dispose();
        }
        
        availableShells.clear();
        isDisposed = true;
    }
}
