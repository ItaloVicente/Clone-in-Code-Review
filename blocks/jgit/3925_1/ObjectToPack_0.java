	void setDeltaBase(ObjectToPack base) {
		if (deltaBase instanceof ObjectToPack)
			clearDeltaBase();

		deltaBase = base;
		deltaNext = base.deltaChild;
		base.deltaChild = this;
	}

