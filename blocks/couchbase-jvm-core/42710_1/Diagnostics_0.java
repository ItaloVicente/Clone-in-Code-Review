                infos.put("proc.cpu.time", sunBean.getProcessCpuTime());
                infos.put("mem.physical.total", sunBean.getTotalPhysicalMemorySize());
                infos.put("mem.physical.free", sunBean.getFreePhysicalMemorySize());
                infos.put("mem.virtual.comitted", sunBean.getCommittedVirtualMemorySize());
                infos.put("mem.swap.total", sunBean.getTotalSwapSpaceSize());
                infos.put("mem.swap.free", sunBean.getFreeSwapSpaceSize());
            }
        } catch(final Throwable err) {
            LOGGER.debug("com.sun.management.OperatingSystemMXBean not available, skipping extended system info.");
