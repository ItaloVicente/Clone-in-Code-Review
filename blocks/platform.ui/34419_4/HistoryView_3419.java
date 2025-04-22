package org.eclipse.ui.examples.rcp.browser;

import org.eclipse.swt.graphics.Point;
import org.eclipse.ui.application.ActionBarAdvisor;
import org.eclipse.ui.application.IActionBarConfigurer;
import org.eclipse.ui.application.IWorkbenchWindowConfigurer;
import org.eclipse.ui.application.WorkbenchWindowAdvisor;

public class BrowserWindowAdvisor extends WorkbenchWindowAdvisor {

    public BrowserWindowAdvisor(IWorkbenchWindowConfigurer configurer) {
        super(configurer);
    }

    public void preWindowOpen() {
        IWorkbenchWindowConfigurer configurer = getWindowConfigurer();
        configurer.setInitialSize(new Point(800, 600));
        
        
    }

    
    public ActionBarAdvisor createActionBarAdvisor(
            IActionBarConfigurer actionBarConfigurer) {
        return new BrowserActionBarAdvisor(actionBarConfigurer);
    }
    
      public void createWindowContents(Shell shell) {
          IWorkbenchWindowConfigurer configurer = getWindowConfigurer();
          Menu menuBar = configurer.createMenuBar();
          shell.setMenuBar(menuBar);
          
          GridLayout shellLayout = new GridLayout();
          shellLayout.marginWidth = 0;
          shellLayout.marginHeight = 0;
          shellLayout.verticalSpacing = 0;
          shell.setLayout(shellLayout);
    
          if (!Util.isMac()) {
              Label sep1 = new Label(shell, SWT.SEPARATOR | SWT.HORIZONTAL);
              sep1.setLayoutData(new GridData(GridData.FILL, GridData.CENTER, true, false));
          }
          Control coolBar = configurer.createCoolBarControl(shell);
          coolBar.setLayoutData(new GridData(GridData.FILL, GridData.CENTER, true, false));
    
          Label sep2 = new Label(shell, SWT.SEPARATOR | SWT.HORIZONTAL);
          sep2.setLayoutData(new GridData(GridData.FILL, GridData.CENTER, true, false));
          
          Control pageComposite = configurer.createPageComposite(shell);
          pageComposite.setLayoutData(new GridData(GridData.FILL, GridData.FILL, true, true));
    
          Label sep3 = new Label(shell, SWT.SEPARATOR | SWT.HORIZONTAL);
          sep3.setLayoutData(new GridData(GridData.FILL, GridData.CENTER, true, false));
          Control statusLine = configurer.createStatusLineControl(shell);
          statusLine.setLayoutData(new GridData(GridData.FILL, GridData.CENTER, true, false));
          shell.layout(true);
      }
}
