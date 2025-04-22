        assertThat(extendedAttrs.readAttributes().isDirectory()).isTrue();
        assertThat(extendedAttrs.readAttributes().isRegularFile()).isFalse();
        assertThat(extendedAttrs.readAttributes().isHidden()).isEqualTo(true);
        assertThat(extendedAttrs.readAttributes().size()).isEqualTo(-1L);
        assertThat(extendedAttrs.readAttributes().creationTime()).isNotNull();
        assertThat(extendedAttrs.readAttributes().lastModifiedTime()).isNotNull();
    }
