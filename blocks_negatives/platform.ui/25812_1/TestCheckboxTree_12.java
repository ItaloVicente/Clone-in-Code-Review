    public static void main(String[] args) {
        TestBrowser browser = new TestCheckboxTree();
        if (args.length > 0 && args[0].equals("-twopanes"))
            browser.show2Panes();
        browser.setBlockOnOpen(true);
        browser.open(TestElement.createModel(3, 10));
    }
