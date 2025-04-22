        Listener listener = event -> {
		    switch (event.type) {
		    case SWT.Dispose:
		    	doDispose();
		        break;
		    case SWT.Resize:
		        Rectangle rect = getClientArea();
		        canvas.setBounds(rect.width - imgBounds.width - 1, 1, imgBounds.width, rect.height - 2);
		        break;
		    case SWT.Paint:
		        if (event.widget == HeapStatus.this) {
		        	paintComposite(event.gc);
		        }
		        else if (event.widget == canvas) {
		            paintButton(event.gc);
		        }
		        break;
		    case SWT.MouseUp:
		        if (event.button == 1) {
