		if (beginA == endA && beginB < endB)
			return Type.INSERT;
		if (beginA < endA && beginB == endB)
			return Type.DELETE;
		if (beginA == endA && beginB == endB)
			return Type.EMPTY;
		return Type.REPLACE;
