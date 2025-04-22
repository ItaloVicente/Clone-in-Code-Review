			Point toggleSize = toggleCache.computeSize(SWT.DEFAULT, SWT.DEFAULT);

			int width = clientArea.width - marginWidth - marginWidth - thmargin - thmargin;
			if (toggleSize.x > 0)
				width -= toggleSize.x + IGAP;

			int gapBetweenTcAndLabel = (textClient != null && textLabel != null) ? IGAP : 0;

			int widthForTcAndLabel = Math.max(0, width - gapBetweenTcAndLabel);

			Point tcDefault = textClientCache.computeSize(SWT.DEFAULT, SWT.DEFAULT);
			Point labelDefault = this.textLabelCache.computeSize(SWT.DEFAULT, SWT.DEFAULT);

			int tcWidthBeforeSplit = Math.min(width, tcDefault.x);
			int labelWidthBeforeSplit = Math.min(width, labelDefault.x);

			int tcWidthAfterSplit = tcWidthBeforeSplit;
			int labelWidthAfterSplit = labelWidthBeforeSplit;

			int expectedWidthForTcAndLabel = tcWidthBeforeSplit + labelWidthBeforeSplit;

			if (expectedWidthForTcAndLabel > widthForTcAndLabel) {
				if (labelWidthBeforeSplit < widthForTcAndLabel / 2) {
					labelWidthAfterSplit = labelWidthBeforeSplit;
				} else {
					labelWidthAfterSplit = widthForTcAndLabel * labelWidthBeforeSplit
							/ expectedWidthForTcAndLabel;
