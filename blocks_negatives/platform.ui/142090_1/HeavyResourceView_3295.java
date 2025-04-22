        control.setFocus();
    }

    public void useAll() {
        releaseAll();
        tempShell = new Shell(Display.getCurrent(), SWT.NONE);
        try {
            for(;;) {
                new Composite(tempShell, SWT.NONE);
            }
        } catch (SWTError e) {
            TestPlugin.getDefault().getLog().log(WorkbenchPlugin.getStatus(e));
        }
    }

    public void releaseAll() {
        if (tempShell != null) {
            tempShell.dispose();
            tempShell = null;
        }
    }

    @Override
