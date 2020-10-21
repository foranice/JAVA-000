package com.test;

import java.io.*;

public class MyClassLoader extends ClassLoader {

    protected Class<?> findClass(String name) throws ClassNotFoundException
    {
        String filePath=System.getProperty("user.dir")+"/src/com/test/"+name+".xlass";
        try{
            byte[] bytes = readFile(filePath);
            bytes=decodeFile(bytes);
            Class<?> c = this.defineClass(name, bytes, 0, bytes.length);
            return c;
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return super.findClass(name);
    }
    private byte[] readFile (String strFile) throws  IOException {

            InputStream stream = new FileInputStream(strFile);
            int iAvail = stream.available();
            byte[] bytes = new byte[iAvail];
            stream.read(bytes);
            stream.close();
            return bytes;

    }
    private byte[] decodeFile( byte[]rawFile){
        for(int i=0 ;i<rawFile.length;i++){
            rawFile[i]=(byte)(255-rawFile[i]);
        }
        return rawFile;
    }
}
