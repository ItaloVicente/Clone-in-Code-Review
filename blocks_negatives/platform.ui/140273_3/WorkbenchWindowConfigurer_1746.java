        showMenuBar = show;
        WorkbenchWindow win = (WorkbenchWindow) getWindow();
        Shell shell = win.getShell();
        if (shell != null) {
            boolean showing = shell.getMenuBar() != null;
            if (show != showing) {
                if (show) {
