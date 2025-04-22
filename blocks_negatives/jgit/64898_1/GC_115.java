			newRefs = new HashMap<String, Ref>();
			for (Iterator<Map.Entry<String, Ref>> i = getAllRefs().entrySet()
					.iterator(); i.hasNext();) {
				Entry<String, Ref> newEntry = i.next();
				Ref old = lastPackedRefs.get(newEntry.getKey());
				if (!equals(newEntry.getValue(), old))
					newRefs.put(newEntry.getKey(), newEntry.getValue());
