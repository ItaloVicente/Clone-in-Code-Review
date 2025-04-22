            fail("ClassCastException expected");
        } catch (ClassCastException e) {
            if (!(e.getCause() instanceof IllegalArgumentException)) {
                fail("ClassCastException with an IllegalArgumentException cause expected");
            }
        } catch (Exception e) {
            fail("ClassCastException expected");
