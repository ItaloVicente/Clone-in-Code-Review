			EditList e = Subsequence.toBase(diffNonCommon(cs

			Edit last = e.get(e.size() - 1);
			if (last.getType() == Edit.Type.INSERT) {
				while (last.endB < b.size()
						&& cmp.equals(b
					last.beginA++;
					last.endA++;
					last.beginB++;
					last.endB++;
				}
			}

			return e;
