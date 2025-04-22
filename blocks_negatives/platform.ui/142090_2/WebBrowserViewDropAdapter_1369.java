       if (!performDrop(event.data))
           event.detail = DND.DROP_NONE;

       currentOperation = event.detail;
   }

   /*
    * Method declared on DropTargetAdapter.
    * Last chance for the action to disable itself
    */
   @Override
