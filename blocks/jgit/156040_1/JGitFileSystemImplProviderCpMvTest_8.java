	@Test
	public void testCopyBranches() throws IOException {
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


		provider.copy(source

		final DirectoryStream<Path> stream = provider

		assertThat(stream).isNotNull().hasSize(2);

		assertThatThrownBy(() -> provider.copy(source


		assertThatThrownBy(() -> provider.copy(notExists
	}

	@Test
	public void testCopyFiles() throws IOException {
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
