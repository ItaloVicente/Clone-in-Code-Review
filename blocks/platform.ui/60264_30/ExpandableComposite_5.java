
			width = Math.max(0, width);

			int widthForTcAndLabel = Math.max(0, width - gapBetweenTcAndLabel - toggleWidthPlusGap);

			int tcWidthBeforeSplit = Math.min(width, tcDefault.x);
			int labelWidthBeforeSplit = Math.min(width, labelDefault.x);

			int tcWidthAfterSplit = tcWidthBeforeSplit;
			int labelWidthAfterSplit = labelWidthBeforeSplit;

			int expectedWidthForTcAndLabel = tcWidthBeforeSplit + labelWidthBeforeSplit;

			if (expectedWidthForTcAndLabel > widthForTcAndLabel) {
				labelWidthAfterSplit = widthForTcAndLabel * labelWidthBeforeSplit / expectedWidthForTcAndLabel;
				tcWidthAfterSplit = widthForTcAndLabel - labelWidthAfterSplit;
