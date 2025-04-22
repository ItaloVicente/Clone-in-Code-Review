		exercise(new TestRunnable() {
			@Override
			public void run() {

				TestTreeElement input = new TestTreeElement(0, null);
				viewer.setInput(input);
				input.createChildren(total);
				if (preSort)
					viewer.getSorter().sort(viewer, input.children);
				Collection<Object> batches = new ArrayList<>();
				int blocks = input.children.length / increment;
				for (int j = 0; j < blocks; j = j + increment) {
					Object[] batch = new Object[increment];
					System.arraycopy(input.children, j * increment, batch, 0,
							increment);
					batches.add(batch);
				}
				processEvents();
				Object[] batchArray = batches.toArray();
				startMeasuring();

				for (Object batch : batchArray) {
					viewer.add(input, (Object[]) batch);
					processEvents();
				}
