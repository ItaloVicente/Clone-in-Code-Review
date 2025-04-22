		if (object instanceof GitModelCommit) {
			int kind = ((GitModelCommit) object).getKind();
			switch (getConfiguration().getMode()) {
			case ISynchronizePageConfiguration.OUTGOING_MODE:
				return (kind & Differencer.RIGHT) != 0;
			case ISynchronizePageConfiguration.INCOMING_MODE:
				return (kind & Differencer.LEFT) != 0;
			default:
				break;
			}
		}
