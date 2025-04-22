			else /* if (beginB == endB) */
				return Type.DELETE;

		} else /* if (beginA == endA) */{
			if (beginB < endB)
				return Type.INSERT;
			else /* if (beginB == endB) */
				return Type.EMPTY;
