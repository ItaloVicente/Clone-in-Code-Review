				char chg = 'M';
				if (nTree == 2) {
					final int m0 = walk.getRawMode(0);
					final int m1 = walk.getRawMode(1);
					if (m0 == 0 && m1 != 0)
						chg = 'A';
					else if (m0 != 0 && m1 == 0)
						chg = 'D';
					else if (m0 != m1 && walk.idEqual(0
						chg = 'T';
				}
				outw.print(chg);
