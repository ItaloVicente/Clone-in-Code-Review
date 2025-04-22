
				if (tcWidthBeforeSplit < widthForTcAndLabel / 2) {
					tcWidthAfterSplit = tcWidthBeforeSplit;
					labelWidthAfterSplit = widthForTcAndLabel - tcWidthAfterSplit;
				} else {
					tcWidthAfterSplit = widthForTcAndLabel - labelWidthAfterSplit;
