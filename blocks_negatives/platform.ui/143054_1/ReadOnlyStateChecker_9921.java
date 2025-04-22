        		return super.getShellStyle() | SWT.SHEET;
        	}
        };
        shell.getDisplay().syncExec(() -> dialog.open());
        int result = dialog.getReturnCode();
        if (result == 0) {
