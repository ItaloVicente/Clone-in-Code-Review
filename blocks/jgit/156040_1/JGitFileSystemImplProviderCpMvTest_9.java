		final DirectoryStream<Path> stream = provider

		for (Path path1 : stream) {
			System.out.println("content: " + path1.toUri());
		}

		assertThat(stream).isNotNull().hasSize(3);
	}

	@Test
	public void testCopyDir() throws IOException {
		provider.newFileSystem(newRepo

		{
			final OutputStream outStream = provider.newOutputStream(path);
			outStream.write("my cool content".getBytes());
			outStream.close();
		}
		{
			final OutputStream outStream2 = provider.newOutputStream(path2);
			outStream2.write("my cool content".getBytes());
			outStream2.close();
		}
		{
			final OutputStream outStream3 = provider.newOutputStream(path3);
			outStream3.write("my cool content".getBytes());
			outStream3.close();
		}

		{

			provider.copy(source

			final DirectoryStream<Path> stream = provider.newDirectoryStream(target

			assertThat(stream).hasSize(3);
		}

		{

			provider.copy(source

			final DirectoryStream<Path> stream = provider.newDirectoryStream(target

			assertThat(stream).hasSize(2);
		}

		{

			provider.copy(source

			final DirectoryStream<Path> stream = provider.newDirectoryStream(target

			assertThat(stream).hasSize(2);
		}

		{

			provider.copy(source

			final DirectoryStream<Path> stream = provider.newDirectoryStream(target

			assertThat(stream).hasSize(1);
		}

		{

			assertThatThrownBy(() -> provider.copy(source
		}

		{

			assertThatThrownBy(() -> provider.copy(source
		}
	}

	@Test
	public void testCopyFilesAcrossRepositories() throws IOException {
		provider.newFileSystem(newRepo1

		provider.newFileSystem(newRepo2

		{
			final OutputStream outStream = provider.newOutputStream(path);
			outStream.write("my cool content".getBytes());
			outStream.close();
		}


		provider.copy(path

		final DirectoryStream<Path> stream = provider

		for (Path path1 : stream) {
			System.out.println("content: " + path1.toUri());
		}

		assertThat(stream).isNotNull().hasSize(1);
	}

	@Test
	public void testCopyDirAcrossRepositories() throws IOException {
		provider.newFileSystem(newRepo1

		provider.newFileSystem(newRepo2

		{
			final OutputStream outStream = provider.newOutputStream(path);
			outStream.write("my cool content".getBytes());
			outStream.close();
		}
		{
			final OutputStream outStream2 = provider.newOutputStream(path2);
			outStream2.write("my cool content".getBytes());
			outStream2.close();
		}
		{
			final OutputStream outStream3 = provider.newOutputStream(path3);
			outStream3.write("my cool content".getBytes());
			outStream3.close();
		}

		{

			provider.copy(source

			final DirectoryStream<Path> stream = provider.newDirectoryStream(target

			assertThat(stream).isNotNull().hasSize(3);
		}

		{

			provider.copy(source

			final DirectoryStream<Path> stream = provider.newDirectoryStream(target

			assertThat(stream).isNotNull().hasSize(2);
		}

		{

			provider.copy(source

			final DirectoryStream<Path> stream = provider.newDirectoryStream(target

			assertThat(stream).isNotNull().hasSize(2);
		}

		{

			assertThatThrownBy(() -> provider.copy(source
		}

		{

			assertThatThrownBy(() -> provider.copy(source
		}
	}

	@Test
	public void testMoveBranches() throws IOException {
		provider.newFileSystem(newRepo

		{

			final OutputStream outStream = provider.newOutputStream(path);
			outStream.write("my cool content".getBytes());
			outStream.close();
		}

		{
			final Path path2 = provider

			final OutputStream outStream2 = provider.newOutputStream(path2);
			outStream2.write("my cool content".getBytes());
			outStream2.close();
		}
		{

			final OutputStream outStream3 = provider.newOutputStream(path3);
			outStream3.write("my cool content".getBytes());
			outStream3.close();
		}


		assertThatThrownBy(() -> provider.move(source


		try {
			provider.move(source2
		} catch (final Exception e) {
			fail("should not throw");
		}
	}

	@Test
	public void testMoveFiles() throws IOException {
		provider.newFileSystem(newRepo

		{
			final OutputStream outStream = provider.newOutputStream(path);
			outStream.write("my cool content".getBytes());
			outStream.close();
		}
		{
			final OutputStream outStream2 = provider.newOutputStream(path2);
			outStream2.write("my cool content".getBytes());
			outStream2.close();
		}
		{
			final OutputStream outStream3 = provider.newOutputStream(path3);
			outStream3.write("my cool content".getBytes());
			outStream3.close();
		}


		try {
			provider.move(path
		} catch (final Exception e) {
			fail("should move file"
		}
	}

	@Test
	public void testMoveDir() throws IOException {
		provider.newFileSystem(newRepo

		{
			final OutputStream outStream = provider.newOutputStream(path);
			outStream.write("my cool content".getBytes());
			outStream.close();
		}
		{
			final OutputStream outStream2 = provider.newOutputStream(path2);
			outStream2.write("my cool content".getBytes());
			outStream2.close();
		}
		{
			final OutputStream outStream3 = provider.newOutputStream(path3);
			outStream3.write("my cool content".getBytes());
			outStream3.close();
		}

		{

			try {
				provider.move(source
			} catch (IOException e) {
				assertThat(e).isInstanceOf(DirectoryNotEmptyException.class);
			}
		}
	}

	@Test
	public void testCherryPick() throws IOException {
		provider.newFileSystem(newRepo

		{

			final OutputStream outStream = provider.newOutputStream(path);
			outStream.write("my cool content".getBytes());
			outStream.close();
		}

		{
			final Path path2 = provider

			final OutputStream outStream2 = provider.newOutputStream(path2);
			outStream2.write("my cool content".getBytes());
			outStream2.close();
		}
		{

			final OutputStream outStream3 = provider.newOutputStream(path3);
			outStream3.write("my cool content".getBytes());
			outStream3.close();
		}

		String commit2CherryPick;
		String cherryPickContent = "my 2nd cool content";
		{

			final OutputStream outStream = provider.newOutputStream(path);
			outStream.write(cherryPickContent.getBytes());
			outStream.close();

			final VersionAttributes versionAttributes = provider.readAttributes(path

			assertThat(versionAttributes.history().records()).isNotNull().hasSize(2);
			commit2CherryPick = versionAttributes.history().records()
					.get(versionAttributes.history().records().size() - 1).id();

			final OutputStream outStream2 = provider.newOutputStream(path);
			outStream2.write("my 3rd cool content".getBytes());
			outStream2.close();
		}


		provider.copy(source

		String commit2CherryPick2;
		String cherryPickContent2 = "my 4tn cool content";
		{

			final OutputStream outStream = provider.newOutputStream(path);
			outStream.write(cherryPickContent2.getBytes());
			outStream.close();

			final VersionAttributes versionAttributes = provider.readAttributes(path

			commit2CherryPick2 = versionAttributes.history().records()
					.get(versionAttributes.history().records().size() - 1).id();
		}

		provider.copy(source

		{
			provider.copy(source

			String result = convertStreamToString(provider.newInputStream(
			assertThat(result).isEqualTo(cherryPickContent);
		}

		{
			provider.copy(source

			final String result = convertStreamToString(provider.newInputStream(
			assertThat(result).isEqualTo(cherryPickContent2);
		}
	}

	private static String convertStreamToString(java.io.InputStream is) {
		java.util.Scanner s = new java.util.Scanner(is).useDelimiter("\\A");
		return s.hasNext() ? s.next() : "";
	}
