    	 Perspective persp = getActivePerspective();
         if (persp != null) {
             ActionSetRegistry reg = WorkbenchPlugin.getDefault()
                  .getActionSetRegistry();
             
             IActionSetDescriptor desc = reg.findActionSet(actionSetID);
             if (desc != null) {
                 persp.addActionSet(desc);
                 legacyWindow.updateActionSets();
                 legacyWindow.firePerspectiveChanged(this, getPerspective(),
                         CHANGE_ACTION_SET_SHOW);
             }
         }
    }

    /**
     * See IWorkbenchPage.
     */
    @Override
