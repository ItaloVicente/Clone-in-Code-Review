    public void test375069ChildShellDifferentiation() throws Exception {
        Display display = Display.getDefault();
        CSSEngine engine = createEngine("Shell.parent { font-style: italic; }", display);

        Shell parent = new Shell(display, SWT.NONE);
        WidgetElement.setCSSClass(parent, "parent");
        Shell child = new Shell(parent, SWT.NONE);
        WidgetElement.setCSSClass(child, "child");
        parent.open();
        child.open();
        engine.applyStyles(parent, true);
        engine.applyStyles(child, true);


        assertEquals(1, parent.getFont().getFontData().length);
        FontData fontData = parent.getFont().getFontData()[0];
        assertEquals(SWT.ITALIC, fontData.getStyle());

        assertEquals(1, child.getFont().getFontData().length);
        fontData = child.getFont().getFontData()[0];
        assertNotSame(SWT.ITALIC, fontData.getStyle());
    }

    public void test375069AllShell() throws Exception {
        Display display = Display.getDefault();
        CSSEngine engine = createEngine("Shell { font-style: italic; }", display);

        Shell parent = new Shell(display, SWT.NONE);
        WidgetElement.setCSSClass(parent, "parent");
        Shell child = new Shell(parent, SWT.NONE);
