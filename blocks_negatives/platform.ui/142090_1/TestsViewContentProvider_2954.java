    /*
     * We will set up a dummy model to initialize tree heararchy. In a real
     * code, you will connect to a real model and expose its hierarchy.
     */
    void initialize() {
        TreeNode[] nodes = new TreeNode[] {
            new TreeNode(new Information("Informational Message One")),//$NON-NLS-1$
            new TreeNode(new Information("Informational Message Two")),//$NON-NLS-1$
            new TreeNode(new Error("Error Message One")),//$NON-NLS-1$
            new TreeNode(new Warning("Warning Message One")),//$NON-NLS-1$
            new TreeNode(new File("file.txt")),//$NON-NLS-1$
            new TreeNode(new File("another.txt")),//$NON-NLS-1$
            new TreeNode(new Folder("folder")),//$NON-NLS-1$
        invisibleRoot.setChildren(nodes);
    }
