
	@Test
	public void testResourceCompare() throws Exception {
		TestProject p = new TestProject();
		p.createFolder("test");
		IFile f1 = p.createFile("test/z.txt", "z".getBytes("UTF-8"));
		IFile f2 = p.createFile("test/d.txt", "d".getBytes("UTF-8"));
		IFile f3 = p.createFile("test/a.txt", "a".getBytes("UTF-8"));
		List<IResource> expected = Arrays
				.asList(new IResource[] { f3, f2,
				f1 });
		List<IResource> tmp = new ArrayList<IResource>(expected);
		Collections.shuffle(tmp, new Random(1));
		Collections.sort(tmp, CommonUtils.RESOURCE_NAME_COMPARATOR);
		assertEquals(expected, tmp);
	}
