			try {
				for (final ObjectId want : askFor.keySet())
					ow.markStart(ow.parseAny(want));
				for (final Ref ref : transport.local.getAllRefs().values())
					ow.markUninteresting(ow.parseAny(ref.getObjectId()));
				ow.checkConnectivity();
			} finally {
				ow.release();
			}
