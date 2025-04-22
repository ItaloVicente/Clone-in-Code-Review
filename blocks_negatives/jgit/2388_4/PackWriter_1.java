		if (object.has(RevFlag.UNINTERESTING)) {
			switch (object.getType()) {
			case Constants.OBJ_TREE:
			case Constants.OBJ_BLOB:
				ObjectToPack otp = new ObjectToPack(object);
				otp.setPathHash(0);
				otp.setEdge();
				edgeObjects.addIfAbsent(otp);
				thin = true;
				break;
			}
			return;
		}

