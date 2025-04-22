			}
		}
	}


	public void checkVisibleRange(int includeIndex) {
		int start = Math.min(table.getTopIndex() - 1, includeIndex);
		int length = Math.max(table.getVisibleItemCount(), includeIndex - start);
		Range r = lastRange;

		if (start != r.start || length != r.length) {
			updateTable();
		}
	}

	private void updateTable() {

		synchronized(this) {

			if (sentObjects.length != knownObjects.length) {
				Object[] newSentObjects = new Object[knownObjects.length];
				System.arraycopy(newSentObjects, 0, sentObjects, 0,
						Math.min(newSentObjects.length, sentObjects.length));
				sentObjects = newSentObjects;
				table.setItemCount(newSentObjects.length);
			}

			int start = Math.min(table.getTopIndex(), knownObjects.length);
			int length = Math.min(table.getVisibleItemCount(), knownObjects.length - start);
			int itemCount = table.getItemCount();

			int oldStart = lastRange.start;
			int oldLen = lastRange.length;

			lastRange = new Range(start, length);
