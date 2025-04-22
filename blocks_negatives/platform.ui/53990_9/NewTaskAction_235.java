            getShell().getDisplay().asyncExec(new Runnable() {
                @Override
				public void run() {
                    getTaskList().setSelection(new StructuredSelection(marker),
                            true);
                }
            });
