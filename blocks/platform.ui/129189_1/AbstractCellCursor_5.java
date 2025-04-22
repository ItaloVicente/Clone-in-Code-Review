				}
				break;

			case SWT.MouseDoubleClick:
				getParent().notifyListeners(SWT.MouseDoubleClick, copyEvent(event));
				break;
