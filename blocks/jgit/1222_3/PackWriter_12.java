					RevObject o = q.next();
					if (o == null)
						break;
					if (not.contains(o.copy()))
						walker.markUninteresting(o);
					else
						walker.markStart(o);
				} catch (MissingObjectException e) {
					if (ignoreMissingUninteresting
							&& not.contains(e.getObjectId()))
