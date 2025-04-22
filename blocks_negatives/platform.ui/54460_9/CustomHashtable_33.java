                Object result = key ? entry.key : entry.value;
                entry = entry.next;
                return result;
            } else {
				throw new NoSuchElementException();
			}
