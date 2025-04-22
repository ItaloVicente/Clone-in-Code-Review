						} else {
                            e.doit = false;
                            nextTabAbortTraversal = true;
                            lastText.traverse(SWT.TRAVERSE_TAB_NEXT);
                            return;
                        }
