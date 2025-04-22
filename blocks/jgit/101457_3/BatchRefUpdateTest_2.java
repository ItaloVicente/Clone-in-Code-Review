	private void writeRef(String name
		RefUpdate u = diskRepo.updateRef(name);
		u.setRefLogMessage(getClass().getSimpleName()
		u.setForceUpdate(true);
		u.setNewObjectId(id);
		RefUpdate.Result r = u.update();
		switch (r) {
			case NEW:
			case FORCED:
				return;
			default:
				throw new IOException("Got " + r + " while updating " + name);
		}
	}

