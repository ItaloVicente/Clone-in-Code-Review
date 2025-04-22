
			RevWalk rw = walk;
			if (wantAll.isEmpty()) {
				pw.preparePack(pm
			} else {
				walk.reset();

				ObjectWalk ow = walk.toObjectWalkWithSameObjects();
				pw.preparePack(pm
				rw = ow;
			}

