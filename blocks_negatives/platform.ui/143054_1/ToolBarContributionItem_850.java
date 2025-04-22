            }

            coolItem.addDisposeListener(event -> handleWidgetDispose(event));

            updateSize(true);
        }
    }

    /**
     * Returns a consistent set of wrap indices. The return value will always
     * include at least one entry and the first entry will always be zero.
     * CoolBar.getWrapIndices() is inconsistent in whether or not it returns an
     * index for the first row.
     */
    private int[] getAdjustedWrapIndices(int[] wraps) {
        int[] adjustedWrapIndices;
        if (wraps.length == 0) {
            adjustedWrapIndices = new int[] { 0 };
        } else {
            if (wraps[0] != 0) {
                adjustedWrapIndices = new int[wraps.length + 1];
                adjustedWrapIndices[0] = 0;
                for (int i = 0; i < wraps.length; i++) {
                    adjustedWrapIndices[i + 1] = wraps[i];
                }
            } else {
                adjustedWrapIndices = wraps;
            }
        }
        return adjustedWrapIndices;
    }

    /**
     * Returns the current height of the corresponding cool item.
     *
     * @return the current height
     */
    @Override
