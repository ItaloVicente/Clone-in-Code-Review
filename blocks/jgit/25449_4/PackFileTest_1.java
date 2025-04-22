			List<PackedObjectInfo> list = new ArrayList<PackedObjectInfo>();
			list.add(a);
			list.add(b);
			Collections.sort(list);
			new PackIndexWriterV1(f).write(list
		} finally {
			f.close();
