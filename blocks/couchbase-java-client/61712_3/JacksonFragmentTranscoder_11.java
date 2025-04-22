            for (Iterator<?> iterator = multiValue.iterator(); iterator.hasNext(); ) {
                Object o = iterator.next();
                mapper.writeValue(out, o);
                if (iterator.hasNext()) {
                   out.writeBytes(",");
                }
            }
            return out.buffer();
        } catch (IOException e) {
