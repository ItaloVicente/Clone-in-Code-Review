        BusyIndicator.showWhile(getShell().getDisplay(), new Runnable() {
            @Override
			public void run() {
                IResource resource = (IResource) event.getElement();
                boolean state = event.getChecked();

                tree.setGrayed(resource, false);
                if (resource instanceof IContainer) {
                    setSubtreeChecked((IContainer) resource, state, true);
                }
                updateParentState(resource);
                validateInput();
            }
        });
