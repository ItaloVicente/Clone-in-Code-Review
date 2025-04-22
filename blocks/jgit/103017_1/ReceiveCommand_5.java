			String expTarget = getOldSymref();
			boolean detach = getNewSymref() != null
					|| (type == Type.DELETE && expTarget != null);
			RefUpdate ru = rp.getRepository().updateRef(getRefName()
			if (expTarget != null) {
				if (!ru.getRef().isSymbolic() || !ru.getRef().getTarget()
						.getName().equals(expTarget)) {
					setResult(Result.LOCK_FAILURE);
					return;
				}
			}

