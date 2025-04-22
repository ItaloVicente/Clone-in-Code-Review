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
        String toolTip = NLS.bind(WorkbenchMessages.HeapStatus_memoryToolTip, new Object[] { usedStr, totalStr, maxStr, markStr });
        if (!toolTip.equals(getToolTipText())) {
            setToolTipText(toolTip);
        }
    }

    /**
     * Converts the given number of bytes to a printable number of megabytes (rounded up).
     */
    private String convertToMegString(long numBytes) {
