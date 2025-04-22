				boolean confirm = true;
				int response = -2;
				if (saveOptions != null) {
					for (Saveable saveableKey : saveables) {
						Save saveVal = saveOptions.get(saveableKey);
						if (saveVal == Save.NO) {
							confirm = true;
							break;
						} else if (saveVal == Save.CANCEL) {
							response = ISaveablePart2.CANCEL;
							break;
						} else {
							confirm = false;
						}
					}
				}
				if (response == -2) {
					response = SaveableHelper.savePart(saveablePart2, window, confirm);
				}
