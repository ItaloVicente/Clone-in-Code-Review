		};
		
		Listener treeListener = new Listener () {
			Shell tip = null;
			Label label = null;
			public void handleEvent (Event event) {
				switch (event.type) {
					case SWT.Dispose:
					case SWT.KeyDown:
					case SWT.MouseMove: {
						if (tip == null) break;
						tip.dispose ();
						tip = null;
						label = null;
						break;
					}
					case SWT.MouseHover: {
						Point coords = new Point(event.x, event.y);
						TreeItem item = v.getTree().getItem(coords);
						if (item != null) {
							int columns = v.getTree().getColumnCount();

							for (int i = 0; i < columns || i == 0; i++) {
								if (item.getBounds(i).contains(coords)) {
									if (tip != null  && !tip.isDisposed ()) tip.dispose ();
									tip = new Shell (v.getTree().getShell(), SWT.ON_TOP | SWT.NO_FOCUS | SWT.TOOL);
									tip.setBackground (v.getTree().getDisplay().getSystemColor (SWT.COLOR_INFO_BACKGROUND));
									FillLayout layout = new FillLayout ();
									layout.marginWidth = 2;
									tip.setLayout (layout);
									label = new Label (tip, SWT.NONE);
									label.setForeground (v.getTree().getDisplay().getSystemColor (SWT.COLOR_INFO_FOREGROUND));
									label.setBackground (v.getTree().getDisplay().getSystemColor (SWT.COLOR_INFO_BACKGROUND));
									label.setData ("_TABLEITEM", item);
									label.setText ("Tooltip: " + item.getData()+ " => Column: " + i);
									label.addListener (SWT.MouseExit, labelListener);
									label.addListener (SWT.MouseDown, labelListener);
									Point size = tip.computeSize (SWT.DEFAULT, SWT.DEFAULT);
									Rectangle rect = item.getBounds (i);
									Point pt = v.getTree().toDisplay (rect.x, rect.y);
									tip.setBounds (pt.x, pt.y, size.x, size.y);
									tip.setVisible (true);
									break;
								}
							}
