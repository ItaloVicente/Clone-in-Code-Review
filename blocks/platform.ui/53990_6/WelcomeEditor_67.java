        this.colorListener = event -> {
		    if (event.getProperty()
		            .equals(JFacePreferences.HYPERLINK_COLOR)) {
		        Color fg = JFaceColors.getHyperlinkText(editorComposite
		                .getDisplay());
		        Iterator links = hyperlinkRanges.iterator();
		        while (links.hasNext()) {
		            StyleRange range = (StyleRange) links.next();
		            range.foreground = fg;
		        }
		    }
		};
