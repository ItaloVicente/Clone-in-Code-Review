            assertEquals(2, keepAliveEventCounter.get());
            assertEquals(ResponseStatus.NOT_EXISTS, keepAliveResponse.status());
            channel.close().awaitUninterruptibly();
        } finally {
            env.shutdown();
        }
