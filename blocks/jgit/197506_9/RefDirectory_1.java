							ObjectId.fromRaw(digest.digest(packedRefsContent))
							comparePackedRefsBySha1 ? null : packedRefsContent);
				}
			} catch (NoSuchFileException noPackedRefs) {
				if (packedRefsFile.exists()) {
					throw noPackedRefs;
				}
				return NO_PACKED_REFS;
			} catch (IOException e) {
