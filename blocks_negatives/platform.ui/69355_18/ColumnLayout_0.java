		if (wHint == 0)
			return computeSize(composite, wHint, hHint, minNumColumns);
		else if (wHint == SWT.DEFAULT)
			return computeSize(composite, wHint, hHint, maxNumColumns);
		else
			return computeSize(composite, wHint, hHint, -1);
