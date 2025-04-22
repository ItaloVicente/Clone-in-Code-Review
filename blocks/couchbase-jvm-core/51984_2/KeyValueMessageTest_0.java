    private void assertValidMetadata(MutationDescriptor descriptor) throws Exception {
        if (isMutationMetadataEnabled()) {
            assertNotNull(descriptor);
            assertTrue(descriptor.seqNo() > 0);
            assertTrue(descriptor.vbucketUUID() != 0);
        } else {
            assertNull(descriptor);
        }
    }

    private void assertMetadataSequence(MutationDescriptor first, MutationDescriptor second) throws Exception {
        if (isMutationMetadataEnabled()) {
            assertNotNull(first);
            assertNotNull(second);
            assertTrue(first.vbucketUUID() != 0);
            assertEquals(first.vbucketUUID(), second.vbucketUUID());
            assertTrue((first.seqNo()+1) == second.seqNo());
        } else {
            assertNull(first);
            assertNull(second);
        }
    }

