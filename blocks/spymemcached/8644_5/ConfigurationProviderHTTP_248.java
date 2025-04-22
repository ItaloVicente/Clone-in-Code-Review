      } else {
        throw new IOException("Unexpected URI type encountered");
      }
      reader = new BufferedReader(new InputStreamReader(inStream));
      String str;
      StringBuilder buffer = new StringBuilder();
      while ((str = reader.readLine()) != null) {
        buffer.append(str);
      }
      return buffer.toString();
    } finally {
      if (reader != null) {
        reader.close();
      }
