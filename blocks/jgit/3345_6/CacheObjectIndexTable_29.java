				Collection<ObjectInfo> list;
				try {
					list = decode(e.getValue());
				} catch (InvalidProtocolBufferException badCell) {
					client.modify(
							Collections.singleton(Change.remove(e.getKey()))
							Sync.<Void> none());
					continue;
				}
