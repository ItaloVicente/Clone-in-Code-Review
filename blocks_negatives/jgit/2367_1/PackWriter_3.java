			addResult(o, walker.getPathHashCode());
			o.add(added);
			if (restartedProgress != 0)
				restartedProgress--;
			else
				countingMonitor.update(1);
		}

		ObjectListIterator itr = reader.openObjectList(listName, walker);
		try {
			while ((o = itr.next()) != null) {
				if (o.has(added))
					continue;
				addResult(o, 0);
				o.add(added);
				if (restartedProgress != 0)
					restartedProgress--;
				else
					countingMonitor.update(1);
			}
			while ((o = itr.nextObject()) != null) {
				if (o.has(added))
					continue;
				addResult(o, itr.getPathHashCode());
				o.add(added);
				if (restartedProgress != 0)
					restartedProgress--;
				else
					countingMonitor.update(1);
			}
		} finally {
			itr.release();
