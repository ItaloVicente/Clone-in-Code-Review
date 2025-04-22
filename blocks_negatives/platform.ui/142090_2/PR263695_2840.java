                display.addFilter(SWT.MouseMove, listener);
                display.addFilter(SWT.MouseDown, listener);
                display.addFilter(SWT.MouseUp, listener);

                final Shell shell = new Shell(display);
                shell.setLayout(new FillLayout());
                final Label label1 = new Label(shell, SWT.BORDER);
                label1.setText("TEXT");
                final Label label2 = new Label(shell, SWT.BORDER);
                setDragDrop(label1);
                setDragDrop(label2);
                shell.setSize(200, 200);
                shell.open();

                Rectangle bounds = label1.getBounds();
                bounds = display.map(label1.getParent(), null, bounds);
                final int downX = bounds.x + (bounds.width / 2);
                final int downY = bounds.y + (bounds.height / 2);

                bounds = label2.getBounds();
                bounds = display.map(label2.getParent(), null, bounds);
                final int upX = bounds.x + (bounds.width / 2);
                final int upY = bounds.y + (bounds.height / 2);

                display.setCursorLocation(downX, downY);
