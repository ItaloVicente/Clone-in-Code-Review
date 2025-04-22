                    if (window.getShell() != null
                            && !window.getShell().isDisposed()) {
                        window.getShell().getDisplay().syncExec(new Runnable() {
                            public void run() {
                                updatePinActionToolbar();
                            }
                        });
                    }
                }
            }
        };
        /*
         * In order to ensure that the pin action toolbar sets its size
         * correctly, the pin action should set its visiblity before we call updatePinActionToolbar().
         * 
         * In other words we always want the PinActionContributionItem to be notified before the
         * WorkbenchActionBuilder.
         */
        WorkbenchPlugin.getDefault().getPreferenceStore()
                .addPropertyChangeListener(propPrefListener);
        resourceListener = new IResourceChangeListener() {
