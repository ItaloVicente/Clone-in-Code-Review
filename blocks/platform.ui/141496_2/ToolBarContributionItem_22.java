			}

			coolItem.addDisposeListener(event -> handleWidgetDispose(event));

			updateSize(true);
		}
	}

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

	@Override
