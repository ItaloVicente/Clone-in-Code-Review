			Map<String
			for (Ref r : refs)
				map.put(r.getName()

			Ref r = RefDatabase.findRef(map
			return r != null ? r.getObjectId() : null;
