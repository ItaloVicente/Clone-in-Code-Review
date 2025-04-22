				for (int i = 0; i < ms.length; i++) {
					tmp = ms[i].getBounds();
					if (tmp.contains(p)) {
						bounds = tmp;
						break;
					}
