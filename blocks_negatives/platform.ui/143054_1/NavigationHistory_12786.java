                    }

                    /*
                     * Promote the entry of the last closed editor to be the active
                     * one, see: https://bugs.eclipse.org/bugs/show_bug.cgi?id=154431
                     */
                    if (!isEntryDisposed && page.getActiveEditor() == null && activeEntry < history.size())
                    	activeEntry++;

                    updateActions();
                }
            }
        });
    }

    private Display getDisplay() {
        return page.getWorkbenchWindow().getShell().getDisplay();
    }

    private boolean isPerTabHistoryEnabled() {
