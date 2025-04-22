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
