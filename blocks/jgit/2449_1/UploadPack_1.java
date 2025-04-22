
					if (!ref.isPeeled())
						ref = db.peel(ref);

					ObjectId peeledId = ref.getPeeledObjectId();
					if (peeledId == null)
