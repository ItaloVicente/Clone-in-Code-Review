		gc.setForeground(markCol);
		gc.drawLine(x, y + 1, x, y + h - 2);
		gc.drawLine(x - 1, y + 1, x + 1, y + 1);
		gc.drawLine(x - 1, y + h - 2, x + 1, y + h - 2);
	}

	private void updateStats() {
		Runtime runtime = Runtime.getRuntime();
		totalMem = runtime.totalMemory();
		long freeMem = runtime.freeMemory();
		usedMem = totalMem - freeMem;

		if (convertToMeg(prevUsedMem) != convertToMeg(usedMem)) {
			prevUsedMem = usedMem;
			this.hasChanged = true;
		}

		if (prevTotalMem != totalMem) {
			prevTotalMem = totalMem;
			this.hasChanged = true;
		}
	}

	private void updateToolTip() {
		String usedStr = convertToMegString(usedMem);
		String totalStr = convertToMegString(totalMem);
		String maxStr = maxMemKnown ? convertToMegString(maxMem) : WorkbenchMessages.HeapStatus_maxUnknown;
		String markStr = mark == -1 ? WorkbenchMessages.HeapStatus_noMark : convertToMegString(mark);
		String toolTip = NLS.bind(WorkbenchMessages.HeapStatus_memoryToolTip,
				new Object[] { usedStr, totalStr, maxStr, markStr });
		if (!toolTip.equals(getToolTipText())) {
			setToolTipText(toolTip);
		}
