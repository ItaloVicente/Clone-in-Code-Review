        GC gc = new GC(parent);
        gc.setAdvanced(true);
        forceHorizontal = !gc.getAdvanced();
        gc.dispose();

        region = new Composite(parent, SWT.NONE) {
