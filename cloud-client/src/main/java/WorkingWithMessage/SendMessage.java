package WorkingWithMessage;

import Communication.MyClientServer;
import com.sun.deploy.util.ArrayUtil;
import com.sun.tools.javac.util.ArrayUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class SendMessage {
    //end_message
    //commands 254,13 - send auth
    private MyClientServer messageService;


    private Byte[] commandSignIn = new Byte[]{-127,-126};

    public SendMessage(MyClientServer messageService) {
        this.messageService = messageService;
    }

    public void sendSighIn(String login, String pass) {
        ArrayList<Byte> arrayList = new ArrayList<>();


        arrayList.addAll(Arrays.asList(commandSignIn));

        addInfoWord(arrayList,login);
        addInfoWord(arrayList,pass);

        byte [] out = arrayOut(arrayList);
        System.out.println(Arrays.toString(out));

        messageService.sendMessage(out);
    }

    //add length and word in Byte
    private ArrayList<Byte>addInfoWord(ArrayList<Byte> in, String word){
        byte[] tmp = word.getBytes();
        in.add((byte) word.length());
        for (byte item:tmp) {
            in.add(item);
        }
        return in;
    }

    private byte[] arrayOut(ArrayList<Byte> in){
        byte [] out = new byte[in.size()];
        for (int i = 0; i < in.size(); i++) {
            out[i] = in.get(i);
        }
        return out;
    }

    public void sendFileToServer(String userName, File file) throws IOException {
        //отправляем -124,-123 - начало передачи файла
        //продалжаем отправку -124, -122
        //длина имени пользователя и имя пользователя
        //длина имения, потом само имя
        //на севреве ждем завершающий знак -124 -121
        //-124 -120 файл передан удачно

        //для теста
        byte[] byteArray = new byte[1024];
        byte[] tmpArray;

        //add command
        byteArray[0] = -124;
        byteArray[1] = -123;

        //add user name
        byteArray[2] = (byte) userName.length();
        tmpArray = userName.getBytes();
        for (int i = 0; i < userName.length(); i++) {
            byteArray[3+i] = tmpArray[i];
        }

        //add name of file
        //String fileName = file.getName();
        byteArray[3+byteArray[2]] = (byte) file.getName().length();
        tmpArray = file.getName().getBytes();
        for (int i = 0; i < tmpArray.length; i++) {
            byteArray[4+byteArray[2]+i] = tmpArray[i];
        }

        System.out.println(Arrays.toString(byteArray));

        //начинаем работу с файлом
        FileInputStream fileInputStream = new FileInputStream(file.getPath());

        long lengthFile = file.length();
        System.out.println(lengthFile);
        int off = 2+byteArray[2]+1+byteArray[3+byteArray[2]]+1;

        int i = fileInputStream.read(byteArray,off,byteArray.length-off);
        lengthFile-=i;
        messageService.sendMessage(byteArray);
        System.out.println(i);
        System.out.println(Arrays.toString(byteArray));

        boolean flag=true;

        while (flag){
            if(lengthFile<=0){
                //send command about end
                flag = false;
                byteArray = new byte[]{-124,-121};
                messageService.sendMessage(byteArray);
                System.out.println(Arrays.toString(byteArray));
            } else {
                if(lengthFile<1024){
                    byteArray = new byte[(int) lengthFile+2];
                } else {
                    byteArray = new byte[1024];
                }
                byteArray[0] = -124;
                byteArray[1] = -122;
                i = fileInputStream.read(byteArray,2,byteArray.length-2);
                lengthFile-=i;
                messageService.sendMessage(byteArray);
                System.out.println(i+" "+lengthFile);
                System.out.println(Arrays.toString(byteArray));
            }
        }

        fileInputStream.close();
    }
}
