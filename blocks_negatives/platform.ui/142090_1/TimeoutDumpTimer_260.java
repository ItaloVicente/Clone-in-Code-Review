    private void logStackTraces(int num) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        dumpStackTraces(num, new PrintStream(outputStream));
        logWarning(outputStream.toString());
    }

    private void dumpStackTraces(int num, PrintStream out) {
        out.println("DumpStackTracesTimer almost reached timeout '" + timeoutArg + "'.");
        out.println("totalMemory:            " + Runtime.getRuntime().totalMemory());
        out.println("freeMemory (before GC): " + Runtime.getRuntime().freeMemory());
        System.gc();
        out.println("freeMemory (after GC):  " + Runtime.getRuntime().freeMemory());
        String time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss Z", Locale.US).format(new Date());
        out.println("Thread dump " + num + " at " + time + ":");
        out.flush();
        Map<Thread, StackTraceElement[]> stackTraces = Thread.getAllStackTraces();
        for (Entry<Thread, StackTraceElement[]> entry : stackTraces.entrySet()) {
            String name = entry.getKey().getName();
            StackTraceElement[] stack = entry.getValue();
            Exception exception = new Exception("ThreadDump for thread \"" + name + "\"");
            exception.setStackTrace(stack);
            exception.printStackTrace(out);
        }
        out.flush();
    }
