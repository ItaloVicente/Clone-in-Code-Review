			}
			if (count < n) {
				System.arraycopy(ixs, 0, ixs = new int[count], 0, count);
			}
			listSetSelection(ixs);
			if (reveal) {
				listShowSelection();
			}
		}
	}

	int getElementIndex(Object element) {
