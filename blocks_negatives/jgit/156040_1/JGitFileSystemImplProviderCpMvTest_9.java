    @Test
    public void testCopyBranches() throws IOException {
        provider.newFileSystem(newRepo,
                               EMPTY_ENV);

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


        provider.copy(source,
                      target);

                                                                         null);

        assertThat(stream).isNotNull().hasSize(2);

        assertThatThrownBy(() -> provider.copy(source, target))
                .isInstanceOf(FileAlreadyExistsException.class);


        assertThatThrownBy(() -> provider.copy(notExists, notExists2))
                .isInstanceOf(NoSuchFileException.class);
    }

    @Test
    public void testCopyFiles() throws IOException {
        provider.newFileSystem(newRepo,
                               EMPTY_ENV);

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


        provider.copy(path,
                      target);

                                                                         null);

        for (Path path1 : stream) {
            System.out.println("content: " + path1.toUri());
        }

        assertThat(stream).isNotNull().hasSize(3);
    }

    @Test
    public void testCopyDir() throws IOException {
        provider.newFileSystem(newRepo,
                               EMPTY_ENV);

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

            provider.copy(source,
                          target);

            final DirectoryStream<Path> stream = provider.newDirectoryStream(target,
                                                                             null);

            assertThat(stream).hasSize(3);
        }

        {

            provider.copy(source,
                          target);

            final DirectoryStream<Path> stream = provider.newDirectoryStream(target,
                                                                             null);

            assertThat(stream).hasSize(2);
        }

        {

            provider.copy(source,
                          target);

            final DirectoryStream<Path> stream = provider.newDirectoryStream(target,
                                                                             null);

            assertThat(stream).hasSize(2);
        }

        {

            provider.copy(source,
                          target);

            final DirectoryStream<Path> stream = provider.newDirectoryStream(target,
                                                                             null);

            assertThat(stream).hasSize(1);
        }

        {

            assertThatThrownBy(() -> provider.copy(source, target))
                    .isInstanceOf(NoSuchFileException.class);
        }

        {

            assertThatThrownBy(() -> provider.copy(source, target))
                    .isInstanceOf(FileAlreadyExistsException.class);
        }
    }

    @Test
    public void testCopyFilesAcrossRepositories() throws IOException {
        provider.newFileSystem(newRepo1,
                               EMPTY_ENV);

        provider.newFileSystem(newRepo2,
                               EMPTY_ENV);

        {
            final OutputStream outStream = provider.newOutputStream(path);
            outStream.write("my cool content".getBytes());
            outStream.close();
        }


        provider.copy(path,
                      target);

                                                                         null);

        for (Path path1 : stream) {
            System.out.println("content: " + path1.toUri());
        }
