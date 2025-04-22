			switch (d.open()) {
			case 0: // yes
				break;
			case 1: // no
				return;
			case 2: // cancel
				cancelPressed();
				return;
			default:
				return;
			}
		}

		super.okPressed();
	}

	@Override
