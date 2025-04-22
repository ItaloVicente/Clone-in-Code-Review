		if (deltaBase instanceof ObjectToPack) {
			ObjectToPack base = (ObjectToPack) deltaBase;
			ObjectToPack child = base.deltaChild;
			if (child == this) {
				base.deltaChild = deltaNext;
			} else {
				ObjectToPack n = child.deltaNext;
				while (n != null) {
					if (n == this) {
						child.deltaNext = deltaNext;
						break;
					}
					child = n;
					n = n.deltaNext;
				}
			}
			deltaNext = null;
		}

