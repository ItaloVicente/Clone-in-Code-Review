        assertThat(content).isNotNull().isEqualTo("my cool content");
    }

    @Test(expected = FileSystemNotFoundException.class)
    public void testGetPathFileSystemNotExisting() {
    }
