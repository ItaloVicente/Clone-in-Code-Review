            StringBuilder writer = new StringBuilder();
            for (Object o : multiValue) {
                writer.append(mapper.writeValueAsString(o)).append(',');
            }
            if (writer.length() > 0) {
                writer.deleteCharAt(writer.length() - 1);
            }

            return Unpooled.copiedBuffer(writer.toString(), CharsetUtil.UTF_8);
