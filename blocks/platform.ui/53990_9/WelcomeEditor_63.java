					}
					e.doit = false;
					nextTabAbortTraversal = true;
					lastText.traverse(SWT.TRAVERSE_TAB_NEXT);
					return;
		        }
		        if (nextTabAbortTraversal) {
		            nextTabAbortTraversal = false;
		            return;
		        }
		        StyleRange nextLink = findNextLink(text);
		        if (nextLink == null) {
		            StyledText nextText = nextText(text);
		            nextText.setSelection(0);
		            focusOn(nextText, 0);
		        } else {
		            focusOn(text, text.getSelection().x);
		            text.setSelectionRange(nextLink.start, nextLink.length);
		        }
		        e.detail = SWT.TRAVERSE_NONE;
		        e.doit = true;
		        break;
		    case SWT.TRAVERSE_TAB_PREVIOUS:
		        if ((e.stateMask & SWT.CTRL) != 0) {
		            if (e.widget == firstText) {
