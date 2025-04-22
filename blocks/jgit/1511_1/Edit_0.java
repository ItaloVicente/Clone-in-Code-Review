		if (beginA < endA) {
			if (beginB < endB)
				return Type.REPLACE;
				return Type.DELETE;

			if (beginB < endB)
				return Type.INSERT;
				return Type.EMPTY;
		}
