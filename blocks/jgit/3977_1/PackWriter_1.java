				int pathHash = walker.getPathHashCode();
				byte[] pathBuf = walker.getPathBuffer();
				int pathLen = walker.getPathLength();
				bases.addBase(o.getType()
				addObject(o
				countingMonitor.update(1);
			}
		} else {
			RevObject o;
			while ((o = walker.nextObject()) != null) {
				if (o.has(RevFlag.UNINTERESTING))
					continue;
				addObject(o
				countingMonitor.update(1);
			}
