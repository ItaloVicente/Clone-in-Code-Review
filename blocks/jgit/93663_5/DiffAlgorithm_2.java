	private static <S extends Sequence> EditList normalize(
		SequenceComparator<? super S> cmp
		Edit prev = null;
		for (int i = e.size() - 1; i >= 0; i--) {
			Edit cur = e.get(i);
			Edit.Type curType = cur.getType();

			int maxA = (prev == null) ? a.size() : prev.beginA;
			int maxB = (prev == null) ? b.size() : prev.beginB;

			if (curType == Edit.Type.INSERT) {
				while (cur.endA < maxA && cur.endB < maxB
					&& cmp.equals(b
					cur.shift(1);
				}
			} else if (curType == Edit.Type.DELETE) {
				while (cur.endA < maxA && cur.endB < maxB
					&& cmp.equals(a
					cur.shift(1);
				}
			}
			prev = cur;
		}
		return e;
	}

