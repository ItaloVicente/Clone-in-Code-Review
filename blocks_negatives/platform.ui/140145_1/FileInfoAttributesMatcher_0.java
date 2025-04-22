			double versionNumber = 0.0;
			int index = system.indexOf('.');
			if (index != -1) {
				versionNumber = Integer.decode(system.substring(0, index)).doubleValue();
				system = system.substring(index + 1);
				index = system.indexOf('.');
				if (index != -1) {
					versionNumber += Double.parseDouble(system.substring(0, index)) / 10.0;
				}
			}
			return versionNumber >= 1.7;
