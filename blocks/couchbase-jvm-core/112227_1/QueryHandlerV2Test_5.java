            channel.pipeline().remove(testHandler);
            assertEquals(2, keepAliveEventCounter.get());
            assertEquals(ResponseStatus.NOT_EXISTS, keepAliveResponse.status());
            assertEquals(0, responseEnd.refCnt());
        } finally {
            env.shutdown();
        }
