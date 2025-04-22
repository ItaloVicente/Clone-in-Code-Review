		for (RevObject obj : baseObjects) {
			if (!obj.has(added)) {
				pm.update(1);
				pw.addObject(obj);
				obj.add(added);
			}
		}
