                    startTime = System.currentTimeMillis();
                    if (!timerStarted) {
                        timerStarted = true;
                        display.timerExec(TIME * 2 / 3, runnable[0]);
                    }
                    break;
                case SWT.MouseDown:
                    mouseUpEvent = null;
                    arrowKeyDown = false;
                    break;
                case SWT.Expand:
                    expandOccurred = true;
                    break;
                case SWT.Collapse:
                    collapseOccurred = true;
                    break;
                case SWT.MouseUp:
                    mouseMoveEvent = null;
                    if ((e.button != 1) || ((e.stateMask & ~SWT.BUTTON1) != 0)) {
