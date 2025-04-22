				if (obj.has(added))
					continue;

				pm.update(1);
				pw.addObject(obj);
				obj.add(added);

				src.representation(rep
				if (rep.getFormat() != PACK_DELTA)
					continue;

				RevObject base = rw.lookupAny(rep.getDeltaBase()
				if (!base.has(added) && !base.has(isBase)) {
					baseObjects.add(base);
					base.add(isBase);
