
        List<ByteBuf> signatureList = inbound.signature().timeout(1, TimeUnit.SECONDS).toList().toBlocking().single();
        if (expectedSignature != null) {
            assertEquals(1, signatureList.size());
            String signatureJson = signatureList.get(0).toString(CharsetUtil.UTF_8);
            assertNotNull(signatureJson);
            assertEquals(expectedSignature, signatureJson.replaceAll("\\s", ""));
            ReferenceCountUtil.releaseLater(signatureList.get(0));
        } else {
            assertEquals(0, signatureList.size());
        }
