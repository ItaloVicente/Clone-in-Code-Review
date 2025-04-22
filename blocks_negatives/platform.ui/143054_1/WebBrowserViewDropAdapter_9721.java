   /**
    * The view to which this drop support has been added.
    */
   private BrowserViewer view;

   /**
    * The current operation.
    */
   private int currentOperation = DND.DROP_NONE;

   /**
    * The last valid operation.
    */
   private int lastValidOperation = DND.DROP_NONE;

   protected WebBrowserViewDropAdapter(BrowserViewer view) {
