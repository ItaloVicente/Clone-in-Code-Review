				list.removeAll();
				startMeasuring();
				for (String item : items) {
					list.add(item);
				}
				processEvents();
				stopMeasuring();
			}
		});
