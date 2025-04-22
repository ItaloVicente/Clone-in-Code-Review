   /*
    * Method declared on DropTargetAdapter.
    * The mouse has moved over the drop target.  If the
    * target item has changed, notify the action and check
    * that it is still enabled.
    */
   private void doDropValidation(DropTargetEvent event) {
       if (event.detail != DND.DROP_NONE)
           lastValidOperation = event.detail;

       if (validateDrop(event.detail, event.currentDataType))
           currentOperation = lastValidOperation;
       else
           currentOperation = DND.DROP_NONE;

       event.detail = currentOperation;
   }

   /*
    * Method declared on DropTargetAdapter.
    * The drop operation has changed, see if the action
    * should still be enabled.
    */
   @Override
