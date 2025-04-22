			boolean flags[] = new boolean[4];
			flags[0] = sourceOfLvalue.isTriggerPoint(e1);
			flags[1] = sourceOfLvalue.isTriggerPoint(e2);
			flags[2] = sourceOfRvalue.isTriggerPoint(e1);
			flags[3] = sourceOfRvalue.isTriggerPoint(e2);

			int whoknows = 0;
			whoknows = whoknows | (flags[0] & flags[1] ? LEFT_UNDERSTANDS : 0);
			whoknows = whoknows | (flags[2] & flags[3] ? RIGHT_UNDERSTANDS : 0);

			switch (whoknows) {
			case BOTH_UNDERSTAND:
				sorter = sourceOfLvalue.getSequenceNumber() < sourceOfRvalue.getSequenceNumber() ? sorterService
						.findSorter(sourceOfLvalue, parent, e1, e2)
						: sorterService.findSorter(sourceOfRvalue, parent, e1, e2);
				break;
			case LEFT_UNDERSTANDS:
				sorter = sorterService.findSorter(sourceOfLvalue, parent, e1, e2);
				break;
			case RIGHT_UNDERSTANDS:
				sorter = sorterService.findSorter(sourceOfRvalue, parent, e1, e2);
				break;
