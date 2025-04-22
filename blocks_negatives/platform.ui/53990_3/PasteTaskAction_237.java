            getShell().getDisplay().asyncExec(new Runnable() {
                @Override
				public void run() {
                    TaskList taskList = getTaskList();
                    taskList.setSelection(new StructuredSelection(op.getMarkers()),
                            true);
                }
            });
