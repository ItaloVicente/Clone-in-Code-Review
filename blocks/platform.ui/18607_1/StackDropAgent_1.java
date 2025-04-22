
			int selIndex = kids.indexOf(curSel);
			boolean curSelProcessed = false;
			while (kids.size() > 1) {
				MStackElement kid = curSelProcessed ? kids.get(kids.size() - 2) : kids.get(kids
						.size() - 1);
				if (kid == curSel) {
					curSelProcessed = true;
					continue;
				}

				kids.remove(kid);
