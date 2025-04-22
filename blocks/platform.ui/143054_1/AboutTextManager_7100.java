				break;
			case SWT.TRAVERSE_TAB_PREVIOUS:
				Point previousSelection = styledText.getSelection();
				if ((previousSelection.x == 0) && (previousSelection.y == 0)) {
					styledText.setSelection(styledText.getCharCount());
				}
				StyleRange previousRange = findPreviousRange();
				if (previousRange == null) {
					styledText.setSelection(styledText.getCharCount());
					e.doit = true;
				} else {
					styledText.setSelectionRange(previousRange.start, previousRange.length);
					e.doit = true;
					e.detail = SWT.TRAVERSE_NONE;
				}
				break;
			default:
				break;
			}
