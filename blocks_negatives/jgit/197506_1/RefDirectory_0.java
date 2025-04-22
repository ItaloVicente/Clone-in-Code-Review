			} catch (FileNotFoundException noPackedRefs) {
				if (packedRefsFile.exists()) {
					throw noPackedRefs;
				}
				return NO_PACKED_REFS;
			}
