                    if (selectionPendent != null
                            && !(collapseOccurred || expandOccurred)) {
                        mouseSelectItem(selectionPendent);
                    } else {
                        mouseUpEvent = e;
                        collapseOccurred = false;
                        expandOccurred = false;
                        selectionPendent= null;
                    }
                    break;
                case SWT.KeyDown:
                    mouseMoveEvent = null;
                    mouseUpEvent = null;
                    arrowKeyDown = ((e.keyCode == SWT.ARROW_UP) || (e.keyCode == SWT.ARROW_DOWN))
                            && e.stateMask == 0;
                    if (e.character == SWT.CR) {
                        if (defaultSelectionPendent != null) {
                            fireOpenEvent(new SelectionEvent(e));
                            enterKeyDown = false;
                            defaultSelectionPendent = null;
                        } else {
                            enterKeyDown = true;
                        }
                    }
                    collapseOccurred= false;
                    expandOccurred= false;
                    break;
                case SWT.Selection:
                    SelectionEvent event = new SelectionEvent(e);
                    fireSelectionEvent(event);
                    mouseMoveEvent = null;
                    if (mouseUpEvent != null) {
