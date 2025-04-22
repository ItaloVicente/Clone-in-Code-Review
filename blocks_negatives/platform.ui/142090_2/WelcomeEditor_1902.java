		        }
		        if (previousTabAbortTraversal) {
		            previousTabAbortTraversal = false;
		            return;
		        }
		        StyleRange previousLink = findPreviousLink(text);
		        if (previousLink == null) {
		            if (text.getSelection().x == 0) {
		                StyledText previousText = previousText(text);
		                previousText.setSelection(previousText
		                        .getCharCount());
		                previousLink = findPreviousLink(previousText);
		                if (previousLink == null) {
