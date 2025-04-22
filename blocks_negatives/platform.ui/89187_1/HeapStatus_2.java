		        break;
		    }
		};
        addListener(SWT.Dispose, listener);
        addListener(SWT.MouseDown, listener);
        addListener(SWT.Paint, listener);
        addListener(SWT.Resize, listener);
        addListener(SWT.MouseEnter, listener);
        addListener(SWT.MouseExit, listener);
        button.addListener(SWT.MouseDown, listener);
        button.addListener(SWT.MouseExit, listener);
        button.addListener(SWT.MouseUp, listener);
        button.addListener(SWT.Paint, listener);
