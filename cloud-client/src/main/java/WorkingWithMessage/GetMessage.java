package WorkingWithMessage;

import Communication.Network;
import Controllers.Controller;
import WorkingWithMessage.Message.SingIn;
import WorkingWithMessage.Message.WorkFile;

import java.util.Arrays;

public class GetMessage {

    private Network network;
    private Controller controller;

    public GetMessage(Network network, Controller controller) {
        this.controller = controller;
        this.network = network;
    }

    public void workingWithInnerMessage(byte[] innerByte) {
        switch (innerByte[0]){
            case -127:
                (new SingIn(controller,innerByte)).checkUser();
                break;
            case -124:
                switch (innerByte[1]){
                    case -120:
                        (new WorkFile(controller)).addSucceed();
                }

                break;
            default:
                System.out.println("no such cast: "+ Arrays.toString(innerByte));
        }
    }
}
