            view.getShell().getDisplay().asyncExec(new Runnable() {
                @Override
				public void run() {
                    view.getViewer().setSelection(
                            new StructuredSelection(op.getMarkers()));
                    view.updatePasteEnablement();
                }
            });
