				};

				int choice = SaveableHelper.testGetAutomatedResponse();
				if (SaveableHelper.testGetAutomatedResponse() == SaveableHelper.USER_RESPONSE) {
					choice = d.open();
				}

				switch (choice) {
				case ISaveablePart2.YES: // yes
					break;
				case ISaveablePart2.NO: // no
					return true;
				default:
				case ISaveablePart2.CANCEL: // cancel
					return false;
