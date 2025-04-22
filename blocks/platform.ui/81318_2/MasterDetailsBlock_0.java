			case SWT.MouseExit:
				e.widget.setData("hover", null); //$NON-NLS-1$
				((Control) e.widget).redraw();
				break;
			case SWT.Paint:
				onSashPaint(e);
				break;
			case SWT.Resize:
				hookSashListeners();
