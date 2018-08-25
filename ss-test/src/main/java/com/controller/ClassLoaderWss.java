package com.controller;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;

public class ClassLoaderWss extends ClassLoader {

    private String classpathmo;

    public ClassLoaderWss(String classpathmo) {
        super(ClassLoader.getSystemClassLoader());
        this.classpathmo = classpathmo;
    }

    @Override
    public Class<?> findClass(String name )throws ClassNotFoundException{
        byte[] data=null;
        return  this.defineClass(name,data,0,data.length);
    }

    private  byte[] loadClassData(String name){
        try{
            name=name.replace(".","//");
            FileInputStream wssFileStream =new FileInputStream(new File(classpathmo+name+".class"));
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            int b;
            while ((b=wssFileStream.read())!=-1){
                byteArrayOutputStream.write(b);
            }
            wssFileStream.close();
            return  byteArrayOutputStream.toByteArray();
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
