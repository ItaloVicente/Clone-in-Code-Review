            shell.getDisplay().syncExec(new Runnable() {
                @Override
				public void run() {
                    ErrorDialog.openError(shell, READ_ONLY_EXCEPTION_MESSAGE,
                            null, exception.getStatus());
                }
            });
