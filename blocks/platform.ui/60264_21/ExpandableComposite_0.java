
			int gapBetweenTcAndLabel = (textClient != null && textLabel != null) ? IGAP : 0;

			int widthForTcAndLabel = twidth - gapBetweenTcAndLabel;

			Point tcDefault = textClientCache.computeSize(SWT.DEFAULT, SWT.DEFAULT);
			Point labelDefault = this.textLabelCache.computeSize(SWT.DEFAULT, SWT.DEFAULT);

			int tcWidthBeforeSplit = Math.min(twidth, tcDefault.x);
			int labelWidthBeforeSplit = Math.min(twidth, labelDefault.x);

			int tcWidthAfterSplit = tcWidthBeforeSplit;
			int labelWidthAfterSplit = labelWidthBeforeSplit;

			if (tcWidthBeforeSplit + labelWidthAfterSplit > widthForTcAndLabel) {
				if (labelWidthBeforeSplit < widthForTcAndLabel / 2) {
					labelWidthAfterSplit = labelWidthBeforeSplit;
				} else {
					labelWidthAfterSplit = widthForTcAndLabel * labelWidthBeforeSplit
							/ (tcWidthBeforeSplit + labelWidthBeforeSplit);
