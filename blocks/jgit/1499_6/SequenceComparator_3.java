
	public Edit reduceCommonStartEnd(S a
		while (e.beginA < e.endA && e.beginB < e.endB
				&& equals(a
			e.beginA++;
			e.beginB++;
		}

		while (e.beginA < e.endA && e.beginB < e.endB
				&& equals(a
			e.endA--;
			e.endB--;
		}

		return e;
	}
