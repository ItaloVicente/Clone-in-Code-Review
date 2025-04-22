		for (MemTable.Cell cell : table.scanFamily(repo.asBytes()
			try {
				out.add(CachedPackInfo.parseFrom(cell.getValue()));
			} catch (InvalidProtocolBufferException e) {
				throw new DhtException(MessageFormat.format(
						DhtText.get().invalidCachedPackInfo
						CachedPackKey.fromBytes(cell.getName()))
			}
		}
