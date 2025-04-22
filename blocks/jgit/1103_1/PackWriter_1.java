			switch (object.getType()) {
			case Constants.OBJ_TREE:
			case Constants.OBJ_BLOB:
				ObjectToPack otp = new ObjectToPack(object);
				otp.setPathHash(pathHashCode);
				edgeObjects.add(otp);
				thin = true;
				break;
			}
