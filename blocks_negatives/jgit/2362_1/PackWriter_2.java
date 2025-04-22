			while ((o = itr.next()) != null) {
				if (o.has(added))
					continue;
				addResult(o, 0);
				o.add(added);
				if (restartedProgress != 0)
					restartedProgress--;
				else
					countingMonitor.update(1);
