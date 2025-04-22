
			int widthForTcAndLabel = width - gapBetweenTcAndLabel - twidth;

			int tcWidthBeforeSplit = Math.min(width, tcDefault.x);
			int labelWidthBeforeSplit = Math.min(width, labelDefault.x);

			int tcWidthAfterSplit = tcWidthBeforeSplit;
			int labelWidthAfterSplit = labelWidthBeforeSplit;

			if (tcWidthBeforeSplit + labelWidthAfterSplit > widthForTcAndLabel) {
				labelWidthAfterSplit = widthForTcAndLabel * labelWidthBeforeSplit
						/ (tcWidthBeforeSplit + labelWidthBeforeSplit);
				tcWidthAfterSplit = widthForTcAndLabel - labelWidthAfterSplit;
