package com.qianfeng.nettools;

/**
 * Created by Administrator on 2016/5/4.
 */

import java.io.ByteArrayOutputStream;



        import java.io.ByteArrayOutputStream;
        import java.io.File;
        import java.io.FileInputStream;
        import java.io.FileNotFoundException;
        import java.io.FileOutputStream;
        import java.io.IOException;
        import android.content.Context;
        import android.os.Environment;

public class FileUtils {
    private Context context;
    public FileUtils(Context context) {
        super();
        this.context = context;
    }
    /**
     * 保存文件
     * @param fileName 文件名称
     * @param content  文件内容
     * @throws IOException
     */
    public void save(String fileName, String content) throws IOException {
        //以私有方式读写数据，创建出来的文件只能被该应用访问
        FileOutputStream fileOutputStream = context.openFileOutput(fileName, Context.MODE_WORLD_READABLE);
        fileOutputStream.write(content.getBytes());
        fileOutputStream.close();
    }

    /**
     * 保存文件
     * @param fileName 文件名称
     * @param content  文件内容
     * @throws IOException
     */
    public void saveToSDCard(String fileName, String content) throws IOException {
        //File file = new File(new File("/mnt/sdcard"),fileName);
        //考虑不同版本的sdCard目录不同，采用系统提供的API获取SD卡的目录
        File file = new File(Environment.getExternalStorageDirectory(),fileName);
        FileOutputStream fileOutputStream = new FileOutputStream(file);
        fileOutputStream.write(content.getBytes());
        fileOutputStream.close();
    }
    /**
     * 读取文件内容
     * @param fileName 文件名称
     * @return 文件内容
     * @throws IOException
     */
    public String read(String fileName) throws IOException {
        FileInputStream fileInputStream = context.openFileInput(fileName);
        //把每次读取的内容写入到内存中，然后从内存中获取
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len =0;
        //只要没读完，不断的读取
        while((len=fileInputStream.read(buffer))!=-1){
            outputStream.write(buffer, 0, len);
        }
        //得到内存中写入的所有数据
        byte[] data = outputStream.toByteArray();
        return new String(data);
    }
}