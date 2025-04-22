				Object[] toSave = dialog.getResult();
				Save[] retSaves = new Save[parts.size()];
				Arrays.fill(retSaves, Save.NO);
				for (int i = 0; i < retSaves.length; i++) {
					MPart part = parts.get(i);
					for (Object o : toSave) {
						if (o == part) {
							retSaves[i] = Save.YES;
							break;
