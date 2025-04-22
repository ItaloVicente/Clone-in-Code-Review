				if (getNewSymref() != null) {
					setResult(ru.link(getNewSymref()));
				} else {
					ru.setNewObjectId(getNewId());
					setResult(ru.update(rp.getRevWalk()));
				}
