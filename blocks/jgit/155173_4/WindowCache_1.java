
		@Override
		public Map<String
			return Collections.unmodifiableMap(
					openByteCountPerRepository.entrySet().stream()
							.collect(Collectors.toMap(Map.Entry::getKey
									e -> Long.valueOf(e.getValue().sum())
									(u
		}
