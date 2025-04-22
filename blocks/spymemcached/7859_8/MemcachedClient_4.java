            try {
                conn.handleIO();
            } catch (IOException e) {
                logRunException(e);
            } catch (CancelledKeyException e) {
                logRunException(e);
            } catch (ClosedSelectorException e) {
                logRunException(e);
            } catch (IllegalStateException e) {
                logRunException(e);
            }
