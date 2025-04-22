			while ((o = itr.nextObject()) != null) {
				if (o.has(added))
					continue;
				addResult(o, itr.getPathHashCode());
				o.add(added);
				if (restartedProgress != 0)
					restartedProgress--;
				else
