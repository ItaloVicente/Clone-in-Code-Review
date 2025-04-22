    public void testOps() throws Exception {
        MembaseClient mc = null;
           try {
            mc = new MembaseClient(Arrays.asList(base), "default", "Administrator", "password");
        } catch (IOException ex) {
            Logger.getLogger(VBucketMemcachedClientTest.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ConfigurationException ex) {
            Logger.getLogger(VBucketMemcachedClientTest.class.getName()).log(Level.SEVERE, null, ex);
        } catch (URISyntaxException ex) {
            Logger.getLogger(VBucketMemcachedClientTest.class.getName()).log(Level.SEVERE, null, ex);
        }

        Integer i;
        for (i = 0; i < 10000; i++) {
            mc.set("test" + i, 0, i.toString());
        }
        mc.set("hello", 0, "world");
        String result = (String) mc.get("hello");
        assert (result.equals("world"));

        for (i = 0; i < 10000; i++) {
            String res = (String) mc.get("test" + i);
            assert (res.equals(i.toString()));
        }

        mc.shutdown(3, TimeUnit.SECONDS);
