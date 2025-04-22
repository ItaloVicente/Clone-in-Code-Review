package org.eclipse.ui.tests.performance.layout;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.WorkbenchException;
import org.eclipse.ui.internal.WindowTrimProxy;
import org.eclipse.ui.internal.layout.TrimLayout;

public class RecursiveTrimLayoutWidgetFactory extends TestWidgetFactory {

    private Shell shell;
    
    public String getName() {
        return "Massively Recursive TrimLayout";
    }
    
    public void done() throws CoreException, WorkbenchException {
        super.done();
        
        shell.dispose();
    }
    
    public void init() throws CoreException, WorkbenchException {
        super.init();
        
		Display display = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell().getDisplay();
		
		shell = new Shell(display);
	
		createTrimLayout(shell, 10, SWT.TOP);
		
		shell.setBounds(0,0,1024,768);
		shell.setVisible(true);
    }
    
    
    public void createTrimLayout(Composite composite, int depth, int side) {
        if (depth == 0) {
            composite.setLayout(new ConstantAreaLayout(5000, 300));
        } else {
            TrimLayout layout = new TrimLayout();
            
            composite.setLayout(layout);
            
            int nextSide = SWT.TOP; 
            
            switch (side) {
            	case SWT.TOP : nextSide = SWT.RIGHT; break;
            	case SWT.RIGHT : nextSide = SWT.BOTTOM; break;
            	case SWT.BOTTOM : nextSide = SWT.LEFT; break;
            	case SWT.LEFT : nextSide = SWT.TOP; break;
            }
            
            Composite child = new Composite(composite, SWT.NONE);
            WindowTrimProxy proxy = new WindowTrimProxy(child, 
            		"child1." + side + "." + depth, "Resizable Child", SWT.NONE, true);
            layout.addTrim(side, proxy);
            createTrimLayout(child, depth - 1, nextSide); 

            child = new Composite(composite, SWT.NONE);
            proxy = new WindowTrimProxy(child, 
            		"child2." + side + "." + depth, "Non-Resizable Child", SWT.NONE, false);
            layout.addTrim(side, proxy);
            createTrimLayout(child, depth - 1, nextSide);
            
            child = new Composite(composite, SWT.NONE);
            layout.setCenterControl(child);
            child.setLayout(new ConstantAreaLayout(3000, 150));
        }
    }
    
    public Composite getControl() throws CoreException, WorkbenchException {
		return shell;
    }

}
