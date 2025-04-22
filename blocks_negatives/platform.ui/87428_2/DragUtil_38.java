        tracker.addListener(SWT.Move, new Listener() {
            @Override
			public void handleEvent(final Event event) {
                display.syncExec(new Runnable() {
                    @Override
					public void run() {
                        Point location = new Point(event.x, event.y);

                    	IDropTarget target = null;

                        Control targetControl = display.getCursorControl();

                        target = getDropTarget(targetControl,
                                draggedItem, location,
                                tracker.getRectangles()[0]);

                        Rectangle snapTarget = null;
                        if (target != null) {
                            snapTarget = target.getSnapRectangle();

                            tracker.setCursor(target.getCursor());
                        } else {
                            tracker.setCursor(DragCursors
                                    .getCursor(DragCursors.INVALID));
                        }

                        if (allowSnapping) {
                            if (snapTarget == null) {
                                snapTarget = new Rectangle(sourceBounds.x
                                        + location.x - initialLocation.x,
                                        sourceBounds.y + location.y
                                                - initialLocation.y,
                                        sourceBounds.width, sourceBounds.height);
                            }

                            Rectangle[] currentRectangles = tracker.getRectangles();

                            if (!(currentRectangles.length == 1 && currentRectangles[0]
                                    .equals(snapTarget))) {
								tracker.setRectangles(new Rectangle[] { Geometry.copy(snapTarget) });
                            }
                        }
                    }
                });
            }
        });
