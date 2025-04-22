            fail("ClassCastException expected");
        } catch (ClassCastException e) {
            if (e.getCause() != null) {
                fail("No cause expected for sub map that are not Map<String, ?>");
            }
        } catch (Exception e) {
            fail("ClassCastException expected, not " + e);
