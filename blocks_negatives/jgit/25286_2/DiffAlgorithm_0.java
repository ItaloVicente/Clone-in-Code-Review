			Edit last = e.get(e.size() - 1);
			if (last.getType() == Edit.Type.INSERT) {
				while (last.endB < b.size()
						&& cmp.equals(b, last.beginB, b, last.endB)) {
					last.beginA++;
					last.endA++;
					last.beginB++;
					last.endB++;
				}
