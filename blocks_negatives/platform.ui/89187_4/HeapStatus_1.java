        Listener listener = event -> {
		    switch (event.type) {
		    case SWT.Dispose:
		    	doDispose();
		        break;
		    case SWT.Resize:
		        Rectangle rect = getClientArea();
		        button.setBounds(rect.width - imgBounds.width - 1, 1, imgBounds.width, rect.height - 2);
		        break;
		    case SWT.Paint:
		        if (event.widget == HeapStatus.this) {
		        	paintComposite(event.gc);
		        }
		        else if (event.widget == button) {
		            paintButton(event.gc);
		        }
		        break;
		    case SWT.MouseUp:
		        if (event.button == 1) {
					if (!isInGC) {
						arm(false);
						gc();
					}
		        }
		        break;
		    case SWT.MouseDown:
		        if (event.button == 1) {
		            if (event.widget == HeapStatus.this) {
						setMark();
					} else if (event.widget == button) {
						if (!isInGC)
							arm(true);
					}
		        }
		        break;
		    case SWT.MouseEnter:
		    	HeapStatus.this.updateTooltip = true;
		    	updateToolTip();
		    	break;
		    case SWT.MouseExit:
		        if (event.widget == HeapStatus.this) {
		        	HeapStatus.this.updateTooltip = false;
				} else if (event.widget == button) {
					arm(false);
				}
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
